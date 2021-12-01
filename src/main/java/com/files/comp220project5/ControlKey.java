package com.files.comp220project5;

//COMP 220 Final Project Method Stubs
//Katherine Bennett, Elsie Park, Carissa Hilscher

/**
 * A Key which corresponds to a command or control (i.e. not printable)
 */
public class ControlKey extends Key {
    /**
     * int corresponding to ASCII code for command (if ESC, DEL, or BS) or unique identifier if no
     * ASCII code exists (128 for "shift_up", 129 for "shift_down", and 130 for "ctrl", 131 for
     * "capslock")
     */
    private int idc; // ranges from 128-129 (one value for shift and one for ctrl)

    /**
     * Constructs a ControlKey representing a key capable of controlling some functionality
     * @param keyName name of the control key
     */
    public ControlKey(String keyName) {
        idc = -1; //128 if shift, 129 if ctrl
    }

    /**
     * Returns unique key id
     * @return int identifier according to idc description
     * @see ControlKey#idc
     */
    @Override
    public int getIdentity() {
        return idc;
    }
}

