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
    static final int FINGER1_INITIAL_CHAR = 108;
    static final int FINGER2_INITIAL_CHAR = 404;
    static final int FINGER2_LAST_CHAR = 700;

    public static Persona parse(byte[] raw) {
        StringBuilder d = new StringBuilder();
        int j = 0;
        for (int i = 0; i < raw.length; i++) {
            if (j == 17) {
                j = 0;
            }
            char c = (char) (keysArray[j] ^ ((char) (raw[i])));
            String normalRegex = "^[a-zA-Z0-9]*$";

            if (i < 108) {
                if ((c + "").matches(normalRegex)) {
                    d.append(c);
                } else {
                    d.append(' ');
                }
            } else {
                d.append(c);
            }

            j++;
        }
        Persona p = new Persona();
        try {
            p.setCedula(d.substring(ID_INITIAL_CHAR, LASTNAME1_INITIAL_CHAR).trim());
            p.setApellido1(d.substring(LASTNAME1_INITIAL_CHAR, LASTNAME2_INITIAL_CHAR).trim());
            p.setApellido2(d.substring(LASTNAME2_INITIAL_CHAR, NAME_INITIAL_CHAR).trim());
            p.setNombre(d.substring(NAME_INITIAL_CHAR, GENDER_INITIAL_CHAR).trim());
            p.setGenero(d.charAt(GENDER_INITIAL_CHAR));
            p.setFechaNacimiento(d.substring(BIRTHDATE_INITIAL_CHAR, 96) + "-" + d.substring(96, 98) + "-" + d.substring(98, EXPIRATION_DATE_INITIAL_CHAR));
            p.setFechaVencimiento(d.substring(EXPIRATION_DATE_INITIAL_CHAR, 104) + "-" + d.substring(104, 106) + "-" + d.substring(106, FINGER1_INITIAL_CHAR));
            p.setHuella1(d.substring(FINGER1_INITIAL_CHAR, FINGER2_INITIAL_CHAR).trim());
            p.setHuella1(d.substring(FINGER2_INITIAL_CHAR, FINGER2_LAST_CHAR).trim());
        } catch (Exception e) {
            p = null;
        }
        return p;
    }
}
