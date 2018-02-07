package com.vbalex.cedulascr;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;
import android.widget.ListView;
import android.widget.ArrayAdapter;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class MainActivity extends Activity {
    private DBHelper db;
    private ArrayList<String> als;
    Collection<String> TYPES = Arrays.asList("PDF417");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.db = new DBHelper(this);
        loadScans();
    }

    public void scanBarcode(View view) {
        new IntentIntegrator(this)
                .setOrientationLocked(false)
                .setCaptureActivity(ScanerCedulasCR.class)
                .setDesiredBarcodeFormats(TYPES)
                .initiateScan();
    }

    private void loadScans() {
        als = new ArrayList<>();
        final List<Persona> personas = this.db.getAll();

        for (Persona p : personas) {
            this.als.add(p.toString());
        }

        ListView listView = (ListView) findViewById(R.id.lista);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, this.als);

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Persona p = personas.get(position);
                showToast(p.toString(), Toast.LENGTH_SHORT);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() == null) {
                Log.d("MainActivity", "Scaneo cancelado");
                showToast("Cancelled", Toast.LENGTH_LONG);
            } else {
                try {
                    Persona p;

                    //Workarround debido a que result.getRawBytes() devuelve un array nulo
                    byte d[] = new byte[result.getContents().length()];
                    for (int i = 0; i < d.length; ++i) {
                        d[i] = (byte) result.getContents().codePointAt(i);
                    }

                    p = CedulaCR.parse(d);
                    this.db.add(p);
                    this.als.add(p.toString());
                    Log.d("MainActivity", "Scaneado");
                    showToast(p.toString(), Toast.LENGTH_SHORT);
                    loadScans();
                } catch (Exception e) {
                    e.printStackTrace();
                    showToast("Error: No se pudo hacer el parse" + e.toString(), Toast.LENGTH_LONG);
                }
            }
        } else {
            // This is important, otherwise the result will not be passed to the fragment
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void showToast(String msg, int length) {
        Toast.makeText(getApplicationContext(), msg, length).show();
    }

    public void scanFingerprint(View view) {
        Intent i = new Intent(this, FingerPrintActivity.class);
        startActivityForResult(i, 101);
    }
}
