package com.vbalex.cedulascr;

/**
 * Created by alex on 2/19/17.
 */

public class CedulaCR {
    private static byte[] keysArray = new byte[]{
            (byte) 0x27,
            (byte) 0x30,
            (byte) 0x04,
            (byte) 0xA0,
            (byte) 0x00,
            (byte) 0x0F,
            (byte) 0x93,
            (byte) 0x12,
            (byte) 0xA0,
            (byte) 0xD1,
            (byte) 0x33,
            (byte) 0xE0,
            (byte) 0x03,
            (byte) 0xD0,
            (byte) 0x00,
            (byte) 0xDf,
            (byte) 0x00
    };

    // The information contains 700 character
    static final int ID_INITIAL_CHAR = 0;
    static final int LASTNAME1_INITIAL_CHAR = 9;
    static final int LASTNAME2_INITIAL_CHAR = 35;
    static final int NAME_INITIAL_CHAR = 61;
    static final int GENDER_INITIAL_CHAR = 91;
    static final int BIRTHDATE_INITIAL_CHAR = 92;
    static final int EXPIRATION_DATE_INITIAL_CHAR = 100;
    static final int UNKNOWN_INITIAL_CHAR = 108;
    static final int FINGER1_INITIAL_CHAR = 116;
    static final int FINGER2_INITIAL_CHAR = 408;
    static final int FINGER2_LAST_CHAR = 700;

    static final int FINGERPRINTS_NUMBER = 2;
    static final int FINGERPRINT_SIZE = 292;

    public static Persona parse(byte[] raw) {
        StringBuilder datosPersonales = new StringBuilder();
        byte[][] prints = new byte[FINGERPRINTS_NUMBER][FINGERPRINT_SIZE];
        int key = 0;
        int printByte = 0;
        for (int i = 0; i < raw.length; i++) {
            if (key == 17) {
                key = 0;
            }
            if (printByte == 291) {
                printByte = 0;
            }
            char c = (char) (keysArray[key] ^ ((char) (raw[i])));

            String normalRegex = "^[a-zA-Z0-9]*$";
            if (i < FINGER1_INITIAL_CHAR) {
                if (String.valueOf(c).matches(normalRegex)) {
                    datosPersonales.append(c);
                } else {
                    datosPersonales.append(' ');
                }
            } else {
                byte b = (byte) (keysArray[key] ^ ((raw[i])));
                if (i < FINGER2_INITIAL_CHAR) {
                    prints[0][printByte] = b;
                } else {
                    prints[1][printByte] = b;
                }
                printByte ++;
            }

            key++;
        }
        Persona p = new Persona();
        try {
            p.setCedula(datosPersonales.substring(ID_INITIAL_CHAR, LASTNAME1_INITIAL_CHAR).trim());
            p.setApellido1(datosPersonales.substring(LASTNAME1_INITIAL_CHAR, LASTNAME2_INITIAL_CHAR).trim());
            p.setApellido2(datosPersonales.substring(LASTNAME2_INITIAL_CHAR, NAME_INITIAL_CHAR).trim());
            p.setNombre(datosPersonales.substring(NAME_INITIAL_CHAR, GENDER_INITIAL_CHAR).trim());
            p.setGenero(datosPersonales.charAt(GENDER_INITIAL_CHAR));
            p.setFechaNacimiento(datosPersonales.substring(BIRTHDATE_INITIAL_CHAR, 96) + "-" + datosPersonales.substring(96, 98) + "-" + datosPersonales.substring(98, EXPIRATION_DATE_INITIAL_CHAR));
            p.setFechaVencimiento(datosPersonales.substring(EXPIRATION_DATE_INITIAL_CHAR, 104) + "-" + datosPersonales.substring(104, 106) + "-" + datosPersonales.substring(106, UNKNOWN_INITIAL_CHAR));
//            p.setHuella1(datosPersonales.substring(FINGER1_INITIAL_CHAR, FINGER2_INITIAL_CHAR).getBytes());
//            p.setHuella1(datosPersonales.substring(FINGER1_INITIAL_CHAR, FINGER2_INITIAL_CHAR).getBytes());
            p.setHuella1(prints[0]);
            p.setHuella2(prints[1]);
        } catch (Exception e) {
            p = null;
        }
        return p;
    }
}
