package com.files.comp220project5;

/**
 * Object representing a read-only block of text, so that no editing commands
 * affect the text, and all text displayed will have no cursor
 */

public class ReadOnlyText extends TextBlock {
    public ReadOnlyText() {
        super();
    }

    public ReadOnlyText(String starter) {
        super(starter);
    }

    @Override
    public void deleteTextBack() {
        // DO NOTHING
    }

    @Override
    public void deleteTextForward() {
        // DO NOTHING
    }

    @Override
    public void insertText(char toAdd) {
        // DO NOTHING
    }

    @Override
    public void moveCursorBackward() {
        // DO NOTHING
    }

    @Override
    public void moveCursorForward() {
        // DO NOTHING
    }

    @Override
    public void undo() {
        // DO NOTHING
    }

    @Override
    public void redo() {
        // DO NOTHING
    }

    @Override
    public String toString() {
        return toStringFile();
        // since this file is read only, we don't want a cursor to display at all
    }
}
