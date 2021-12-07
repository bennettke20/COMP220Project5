package com.files.comp220project5;
import javafx.geometry.Pos;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;


public class TextEditor extends Application {
    private TextBlock text;
    private File currentFile;
    private int fontsize = 12;

    /**
     * Set up the starting scene of your application given the primaryStage (basically the window)
     * https://docs.oracle.com/javase/8/javafx/api/index.html
     *
     * @param primaryStage the primary container for scenes
     */

    @Override
    public void start(Stage primaryStage) {
        // Add a title to the application window
        primaryStage.setTitle("COMP 220 - Text Editor - Untitled");
        AtomicBoolean ctrlDown = new AtomicBoolean(false); // tracks whether CTRL key is down
        AtomicBoolean isUntitled = new AtomicBoolean(true); // tracks whether file is an untitled file
        text = new TextBlock();

        /**
         * Scene Setup
         */
        BorderPane layout = new BorderPane();
        double WINDOW_WIDTH = 1000;
        double WINDOW_HEIGHT = 600;
        Scene editorScene = new Scene(layout, WINDOW_WIDTH, WINDOW_HEIGHT);
        primaryStage.setScene(editorScene);

        // text content setup:
        Text content = new Text();
        content.setFont(new Font(12));
        content.setFocusTraversable(false);
        content.setTextAlignment(TextAlignment.LEFT);
        BorderPane.setAlignment(content, Pos.TOP_LEFT);  //set text to begin at top left -ch
        content.setWrappingWidth(900);
        layout.setCenter(content);
        content.setText(text.toString());

        /**
         * Responsible for handling menu controlled commands:
         */
        MenuBar mb = new MenuBar();
        // FILE MENU SETUP
        Menu filemenu = new Menu("File");
        MenuItem saveItem = new MenuItem("Save");
        MenuItem saveItemAs = new MenuItem("Save As");
        MenuItem openItem = new MenuItem("Open");
        MenuItem newItem = new MenuItem("New");
        MenuItem openReadOnly = new MenuItem ("Open Read-Only");
        filemenu.getItems().add(saveItem);
        filemenu.getItems().add(saveItemAs);
        filemenu.getItems().add(openItem);
        filemenu.getItems().add(newItem);
        filemenu.getItems().add(openReadOnly);
        //TEXT MENU SETUP
        Menu editmenu = new Menu("Edit");
        MenuItem undoCommand = new MenuItem("Undo");
        MenuItem redoCommand = new MenuItem ("Redo");
        editmenu.getItems().add(undoCommand);
        editmenu.getItems().add(redoCommand);

        //FONTS MENU
        Menu fonts = new Menu("Fonts");
        MenuItem eight = new MenuItem("8");
        MenuItem twlv = new MenuItem("12");
        MenuItem sxtn = new MenuItem("16");
        MenuItem twenty = new MenuItem("20");
        MenuItem thirty = new MenuItem("30");
        fonts.getItems().add(eight);
        fonts.getItems().add(twlv);
        fonts.getItems().add(sxtn);
        fonts.getItems().add(twenty);
        fonts.getItems().add(thirty);

        //MENU DISPLAY SETUP
        mb.getMenus().add(filemenu);
        mb.getMenus().add(editmenu);
        mb.getMenus().add(fonts);
        layout.setTop(mb);
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("File");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter(
                "TEXT files (*.txt)", "*.txt")); // i.e. text files only
        /**
         * action for setting font size to 8
         */
        eight.setOnAction(event -> {  //FONT SIZES
          this.fontsize = 8;
          content.setFont(new Font(fontsize));
        });
        /**
         * action for setting font size to 12
         */
        twlv.setOnAction(event -> {
            this.fontsize = 12;
            content.setFont(new Font(fontsize));
        });
        /**
         * action for setting font size to 16
         */
        sxtn.setOnAction(event -> {
            this.fontsize = 16;
            content.setFont(new Font(fontsize));
        });
        /**
         * action for setting font size to 20
         */
        twenty.setOnAction(event -> {
            this.fontsize = 20;
            content.setFont(new Font(fontsize));
        });
        /**
         * action for setting font size to 8\30
         */
        thirty.setOnAction(event -> {
            this.fontsize = 30;
            content.setFont(new Font(fontsize));
        });

        /**
         *ACTION on saveItem
         */
        saveItem.setOnAction(event -> {  //SAVE FILE
            if (isUntitled.getOpaque()) {
                currentFile = fileChooser.showSaveDialog(primaryStage);
                if (currentFile != null) {
                    saveText(text.toStringFile(), currentFile);
                    primaryStage.setTitle("COMP 220 - Text Editor - " + currentFile.getName());
                    isUntitled.set(false);
                }
            } else {
                if (currentFile != null) {
                    saveText(text.toStringFile(), currentFile);
                }
            }
        });
        /**
         * ACTION on saveItemAs
         */
        saveItemAs.setOnAction(event -> {  //SAVE FILE AS
            currentFile = fileChooser.showSaveDialog(primaryStage);
                if (currentFile != null) {
                saveText(text.toStringFile(), currentFile);
                isUntitled.set(false);
                if (text instanceof ReadOnlyText) {
                    primaryStage.setTitle("COMP 220 - Text Editor - " + currentFile.getName() +
                    " (Read Only)");
                } else {
                    primaryStage.setTitle("COMP 220 - Text Editor - " + currentFile.getName());
                }
            }
        });
        /**
         * ACTION on openItem
         */
        openItem.setOnAction(event -> {  //OPEN FILE
            currentFile = fileChooser.showOpenDialog(primaryStage);
                if (currentFile != null) {
                text = new TextBlock(readToOpen(currentFile));
                content.setText(text.toString());
                primaryStage.setTitle("COMP 220 - Text Editor - " + currentFile.getName());
                isUntitled.set(false);
            }
        });
        /**
         * ACTION on newItem
         */
        newItem.setOnAction(event -> {  //RESETS EDITOR
            text = new TextBlock();
            content.setText(text.toString());
            isUntitled.set(true);
            primaryStage.setTitle("COMP 220 - Text Editor - Untitled");
        });
        /**
         *ACTION on openReadOnly
         */
        openReadOnly.setOnAction(event -> { // OPEN READ-ONLY
            currentFile = fileChooser.showOpenDialog(primaryStage);
                if (currentFile != null) {
                text = new ReadOnlyText(readToOpen(currentFile));
                content.setText(text.toString());
                primaryStage.setTitle("COMP 220 - Text Editor - " + currentFile.getName() + " (Read Only)");
                isUntitled.set(false);
            }
        });
        /**
         *ACTION on undoCommand
         */
        undoCommand.setOnAction(event -> { // UNDO COMMAND
            text.undo();
            content.setText(text.toString());
        });
        /**
         *ACTION on redoCommand
         */
        redoCommand.setOnAction(event -> {
            text.redo();
            content.setText(text.toString());
        });

        /**
         * Responsible for handling keyboard controlled commands:
         */
        editorScene.setOnKeyReleased(event -> {
            // CTRL UP COMMAND: tracks whether the control key has been released
            if (event.getCode().equals(KeyCode.CONTROL)) {
                ctrlDown.set(false);
            }
        });
        /**
         * handling action keys such as escape, backspace, and arrows
         */
        editorScene.setOnKeyPressed(event -> {
            // ESCAPE COMMAND: exits the program
            if (event.getCode().equals(KeyCode.ESCAPE)) {
                System.exit(0);}
            // BACKSPACE COMMAND: deletes text backwards
            else if (event.getCode().equals(KeyCode.BACK_SPACE)) {
                text.deleteTextBack();
                content.setText(text.toString());}
            // DELETE COMMAND: deletes text forward
            else if (event.getCode().equals(KeyCode.DELETE)) {
                text.deleteTextForward();
                content.setText(text.toString());}
            // LEFT ARROW KEY COMMAND: moves cursor left
            else if (event.getCode().equals(KeyCode.LEFT)) {
                text.moveCursorBackward();
                content.setText(text.toString());
            }
            // RIGHT ARROW KEY COMMAND: moves cursor right
            else if(event.getCode().equals(KeyCode.RIGHT)) {
                text.moveCursorForward();
                content.setText(text.toString());
                System.out.println("r arr detected");}
            // ENTER COMMAND: adds a new line to the text
            else if (event.getCode().equals(KeyCode.ENTER)) {
                text.insertText('\n');
                content.setText(text.toString());}
            // TAB COMMAND: adds a tab to the text
            else if (event.getCode().equals(KeyCode.TAB)) {
                text.insertText('\t');
                content.setText(text.toString());}
            // CTRL DOWN COMMAND: tracks whether the control key has been pressed
            else if (event.getCode().equals(KeyCode.CONTROL)) {
                ctrlDown.set(true);}
            // SAVE COMMAND (CTRL+S): allows user to save the file
            else if (event.getCode().equals(KeyCode.S) && ctrlDown.getOpaque()) {
                if (isUntitled.getOpaque()) {
                    currentFile = fileChooser.showSaveDialog(primaryStage);
                    if (currentFile != null) {
                        saveText(text.toStringFile(), currentFile);
                        primaryStage.setTitle("COMP 220 - Text Editor - " + currentFile.getName());
                        isUntitled.set(false);
                    }
                } else {
                    if (currentFile != null) {
                        saveText(text.toStringFile(), currentFile);
                    }
                }}
            // UNDO COMMAND (CTRL+Z): allows user to undo the latest text modifying command
            else if (event.getCode().equals(KeyCode.Z) && ctrlDown.getOpaque()) {
                text.undo();
                content.setText(text.toString());}
            // REDO COMMAND (CTRL+Y): allows user to redo the latest undone text modifying
            // command (if possible)
            else if (event.getCode().equals(KeyCode.Y) && ctrlDown.getOpaque()) {
                text.redo();
                content.setText(text.toString());
            }
                });

        /**
         * Responsible for adding all text to the text editor:
         * (dealing with printable keys)
         */
        editorScene.setOnKeyTyped(event -> {
            char c = event.getCharacter().charAt(0);
            if (Character.getType(c)!=Character.CONTROL) {
                text.insertText(c);
                content.setText(text.toString());
            }
            });

        // display the interface
        primaryStage.show();
    };

    /**
     * Saves content to a file
     * @param content String to save
     * @param file text file to save to
     */
    private void saveText(String content, File file) {  //saving helper method
        try {
            PrintWriter writer;
            writer = new PrintWriter(file);
            writer.println(content);
            writer.close();
        } catch (IOException ex) {
            System.out.println(ex.getStackTrace());
        }
    }

    /**
     * Method for extracting String content from files we need to read
     * @param file text file to extract from
     * @return String contents extracted
     */
    private String readToOpen(File file) {
        StringBuilder sb = new StringBuilder();
        try {
            //
            FileInputStream f = new FileInputStream(file);
            Scanner scanner = new Scanner(f);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                (sb.append(line)).append("\n");

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    /**
     * Launches application...
     */
    public static void main(String[] args) {
        launch();
    }
}
