package com.files.comp220project5;

import java.util.ArrayList;
import java.util.Stack;

public class TextBlock {
    private ArrayList<Character> charList;
    private int cursor;
    /**
     * Queue to keep track of command history using an integer code, so that
     * commands can be undone.
     *
     * Code as follows:
     * 1: character added
     * 2: cursor index moved forward
     * 3: cursor index moved backward
     * IF 300<int<500, then character was deleted, and int-300 is the ASCII value
     *  of the char removed
     * IF 500<int, then character was forward deleted, and int-500 is the ASCII
     *  value of the char removed
     */
    private Stack<Integer> commandHistory = new Stack<Integer>();

    public TextBlock() {
        charList = new ArrayList<Character>();
        cursor = 0;
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        for (int i=0; i<cursor; i++) {
            str.append(charList.get(i));
        }
        str.append("|");
        /*for (int i=cursor; i<charList.size(); i++) {
            str.append(charList.get(i));
        }
        /*for(Character c : charList) {
            str.append(c);
        }
        */
        return str.toString();
    }

    /**
     * Inserts a Character at the cursor index
     * @param toAdd Character to insert
     */
    public void insertText(char toAdd) {
        //TODO: inserts char at cursor index
        charList.add(toAdd);
        cursor++;
    }

    /**
     * Receives a Character to add, and if the Character is a letter, adds the corresponding
     * capital Character, else calls insertText
     * @param toAdd Character to add
     */
    private void insertCap(char toAdd) {
        //TODO: if the character is a letter, will insert a capital instead
        //else, will insertText(toAdd) as normal
    }

    /**
     * Removes the Character at the cursor location
     */
    public void deleteText() {
        //TODO: code to delete the Character at the cursor index
        if (cursor>0) {
            charList.remove(cursor-1);
            cursor--;
        }
    }

    /**
     * Removes the Character after the cursor location
     */
    private void deleteTextForward() {
        //TODO: code to delete the character after the cursor index (if it exists)
        //doesn't need to move the cursor
    }

    /**
     * Moves the cursor forward by one index
     */
    public void moveCursorForward() {
        if (cursor < charList.size()) {
            cursor++;
        }
    }

    /**
     * Moves the cursor backward by one index
     */
    public void moveCursorBackward() {
        if (cursor > 0) {
            cursor--;
        }
    }

    public ArrayList<Character> getList() {return charList;}
}
