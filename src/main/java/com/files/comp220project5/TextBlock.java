package com.files.comp220project5;

import java.util.ArrayList;
import java.util.Stack;

public class TextBlock {
    private ArrayList<Character> charList;
    private int cursor;
    /**
     * Stack to keep track of command history using a String code, so that
     * commands can be undone.
     *
     * 1st char is the command executed:
     *  b - deleteTextBack
     *  d - deleteTextForward
     *  i - insertText
     *  l - cursorMovedLeft
     *  r - cursorMovedRight
     * 2nd char is the removed or added char (if applicable)
     */
    private Stack<String> commandHistory;
    /**
     * Boolean to keep track of whether a commands undone are re-doable
     */
    private boolean redoable;
    /**
     * Stack of re-doable commands, using the following String code:
     *
     * 1st char: same code as commandHistory
     * 2nd char: char to add for insertText (if applicable)
     *
     */
    private Stack<String> redoableCommands;

    /**
     * Constructs a blank TextBlock object
     */
    public TextBlock() {
        charList = new ArrayList<>();
        cursor = 0;
        commandHistory = new Stack<>();
        redoable = false;
        redoableCommands = new Stack<>();
    }

    public TextBlock(String starter) {
        charList = new ArrayList<>();
        char[] a = starter.toCharArray();
        for ( char c : a) {
            charList.add(c);
        }
        cursor = this.charList.size();
        commandHistory = new Stack<>();
        redoable = false;
        redoableCommands = new Stack<>();
    }
    /**
     * Resets TextBlock to blank document
     */
    public void reset() {  //need this for open function
        cursor = 0;
        charList.clear();
        commandHistory.empty();
        redoable = false;
        redoableCommands.empty();
    }

    /**
     * Inserts a Character at the cursor index
     * @param toAdd Character to insert
     */
    public void insertText(char toAdd) {
        // inserts char at cursor index
        charList.add(cursor, toAdd);
        cursor++;
        commandHistory.add("i" + toAdd);
        if (redoable) {
            clearRedo();
        }
    }

    /**
     * Removes the Character at the cursor location
     */
    public void deleteTextBack() {
        // code to delete the Character at the cursor index
        if (cursor>0) {
            commandHistory.push("b" + charList.get(cursor-1)); // add to history
            charList.remove(cursor-1);
            cursor--;
            if (redoable) {
                clearRedo();
            }
        }
    }

    /**
     * Removes the Character after the displayed cursor
     */
    public void deleteTextForward() {
        if ((cursor) < charList.size()) {
            commandHistory.push("d" + charList.get(cursor)); // add to history
            charList.remove(cursor);
            if (redoable) {
                clearRedo();
            }
        }
    }

    /**
     * Moves the cursor forward by one index
     */
    public void moveCursorForward() {
        if (cursor < charList.size()) {
            commandHistory.push("r");
            cursor++;
            if (redoable) {
                redoableCommands.push("r");
            }
        }
    }

    /**
     * Moves the cursor backward by one index
     */
    public void moveCursorBackward() {
        if (cursor > 0) {
            commandHistory.push("l");
            cursor--;
            if (redoable) {
                redoableCommands.push("l");
            }
        }
    }

    /**
     * Undoes the previous text editing command (i.e. a command that does more
     * than just move the cursor)
     */
    public void undo() {
        if (commandHistory.size() > 0) {
            String command = commandHistory.pop();
            redoable = true;
            char c1 = command.charAt(0);
            if (c1 == 'l') {
                cursor++;
                redoableCommands.push("l");
                undo();
            } else if (c1 == 'r') {
                cursor--;
                redoableCommands.push("r");
                undo();
            } else if (c1 == 'i') {
                cursor--;
                redoableCommands.push("i" + charList.get(cursor));
                charList.remove(cursor);
            } else if (c1 == 'b') {
                cursor++;
                charList.add(cursor-1, command.charAt(1));
                redoableCommands.push("b");
            } else if (c1 == 'd') {
                charList.add(cursor, command.charAt(1));
                redoableCommands.push("d");
            }
        }
    }

    /**
     * Redoes the most recent undone text editing command if possible (i.e.
     * if no other text editing command has been called since) and re-adds
     * that command to the undoable command history
     */
    public void redo() {
        if (redoable) {
            String command = redoableCommands.pop();
            char c1 = command.charAt(0);
            if (c1 == 'l') {
                cursor--;
                commandHistory.push("l");
                redo();
            } else if (c1 == 'r') {
                cursor++;
                commandHistory.push("r");
                redo();
            } else if (c1 == 'd') {
                commandHistory.push("d" + charList.get(cursor)); // add to history
                charList.remove(cursor);
            } else if (c1 == 'b') {
                commandHistory.push("b" + charList.get(cursor-1)); // add to history
                charList.remove(cursor-1);
                cursor--;
            } else if (c1 == 'i') {
                char c2 = command.charAt(1);
                charList.add(cursor, c2);
                cursor++;
                commandHistory.add("i" + c2);
            }

            if (redoableCommands.size()<1) {
                redoable = false;
            } // no longer redoable if there are no more redoable commands
        }
    }

    /**
     * Resets redoableCommands if a new command has been executed, and redo
     * is no longer possible
     */
    private void clearRedo() {
        redoable = false;
        redoableCommands.empty();
    }

    /**
     * Returns String representation of the text, including a marked
     * cursor location
     * @return String containing text and cursor
     */
    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        for(int i=0; i<cursor; i++) {
            str.append(charList.get(i));
        }
        str.append("|");
        for (int i=cursor; i<charList.size(); i++) {
            str.append(charList.get(i));
        }
        return str.toString();
    }

    /**
     * Returns a string representation of the text, without a cursor
     * indicator
     * @return String containing text without cursor
     */
    public String toStringFile() {
        StringBuilder str = new StringBuilder();
        for (char c : charList) {
            str.append(c);
        }
        return str.toString();
    }
}
