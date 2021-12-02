package com.files.comp220project5;

//COMP 220 Final Project Method Stubs
//Katherine Bennett, Elsie Park, Carissa Hilscher

/**
 * A Key which corresponds to a printable char
 */
public class PrintableKey extends Key {
    /**
     * int corresponding to the ASCII code of the char of the Key
     */
    private int idUnicode;
    private char c;

    /**
     * Constructs a PrintableKey representing a particular char
     * @param key char
     */
    public PrintableKey(char key) {
        idUnicode = key;
    }

    /**
     * Returns unique key id
     * @return ASCII code id of printable char
     */
    @Override
    public int getIdentity() {
        return idUnicode;
    }
}

