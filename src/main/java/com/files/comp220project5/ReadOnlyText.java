package com.files.comp220project5;

/**
 * Object representing a read-only block of text, so that no editing commands
 * affect the text, and all text displayed will have no cursor
 */

public class ReadOnlyText extends TextBlock {
    /**
     * Constructs a blank read-only text document
     */
    public ReadOnlyText() {
        super();
    }

    /**
     * Constructs a read-only text document containing a starter string
     * @param starter String to created the document from
     */
    public ReadOnlyText(String starter) {
        super(starter);
    }

    /**
     * Overrides deleteTextBack command to render it ineffectual
     */
    @Override
    public void deleteTextBack() {
        // DO NOTHING
    }

    /**
     * Overrides deleteTextForward command to render it ineffectual
     */
    @Override
    public void deleteTextForward() {
        // DO NOTHING
    }

    /**
     * Overrides insertText command to render it ineffectual
     */
    @Override
    public void insertText(char toAdd) {
        // DO NOTHING
    }

    /**
     * Overrides moveCursorBackward command to render it ineffectual
     */
    @Override
    public void moveCursorBackward() {
        // DO NOTHING
    }

    /**
     * Overrides moveCursorForward command to render it ineffectual
     */
    @Override
    public void moveCursorForward() {
        // DO NOTHING
    }

    /**
     * Overrides undo command to render it ineffectual
     */
    @Override
    public void undo() {
        // DO NOTHING
    }

    /**
     * Overrides redo command to render it ineffectual
     */
    @Override
    public void redo() {
        // DO NOTHING
    }

    /**
     * Overrides toString command so that it always returns a String representation without a
     * cursor (since no text editing will occur)
     */
    @Override
    public String toString() {
        return toStringFile();
        // since this file is read only, we don't want a cursor to display at all
    }
}
