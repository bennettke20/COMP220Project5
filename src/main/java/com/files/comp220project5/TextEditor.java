package com.files.comp220project5;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;


public class TextEditor extends Application {
    private final TextBlock text = new TextBlock();

    /**
     * Set up the starting scene of your application given the primaryStage (basically the window)
     * https://docs.oracle.com/javase/8/javafx/api/index.html
     *
     * @param primaryStage the primary container for scenes
     */
    @Override
    public void start(Stage primaryStage) {
        // Add a title to the application window
        primaryStage.setTitle("COMP 220 - Text Editor");

        // prepare the scene layout to use a BorderPane -- a top, bottom, left, right, center style pane layout
        // https://docs.oracle.com/javafx/2/layout/builtin_layouts.htm
        BorderPane layout = new BorderPane();

        // Create a new scene using this layout
        // https://docs.oracle.com/javase/8/javafx/api/javafx/scene/Scene.html
        // define the size of this scene
        double WINDOW_WIDTH = 1280;
        double WINDOW_HEIGHT = 720;
        Scene editorScene = new Scene(layout, WINDOW_WIDTH, WINDOW_HEIGHT);

        // make this scene the initial (and for now only) scene in your application
        primaryStage.setScene(editorScene);

        // create a new text node to display text on the interface
        // https://docs.oracle.com/javase/8/javafx/api/javafx/scene/text/Text.html
        Text content = new Text();
        content.setTextAlignment(TextAlignment.LEFT);
        content.setWrappingWidth(1024);
        // add this text field to the layout
        layout.setCenter(content);

        // create a new button object and set its text
        Button btn = new Button("Save");
        // define the code that should run when the button is clicked
        btn.setOnAction(event -> {

        });
        // add this button to the layout centered at the bottom with some spacing from other elements
        BorderPane.setAlignment(btn, Pos.CENTER);
        BorderPane.setMargin(btn, new Insets(16, 16, 16, 16));
        layout.setBottom(btn);


        // define code to run every time a KeyPressed event is detected on this window to check for ESC to close
        // NOTE: there even is of type javafx.scene.input.KeyEvent
        // https://docs.oracle.com/javase/8/javafx/api/javafx/scene/input/KeyEvent.html
        editorScene.setOnKeyPressed(event -> {
            // check if the key that was pressed is the ESC key
            if (event.getCode().equals(KeyCode.ESCAPE)) {
                // exit the program
                System.exit(0);
            }
            else if (event.getCode().equals(KeyCode.BACK_SPACE)) {
                text.deleteText();
                content.setText(text.toString());
            }
            else if (event.getCode().equals(37)) {
                text.moveCursorBackward();
            } else if (event.getCode().equals(39)) {
                text.moveCursorForward();
            }
        });

        // define code to run every time a KeyTyped event is detected on this window to check for ESC to close
        // NOTE: there even is of type javafx.scene.input.KeyEvent
        // https://docs.oracle.com/javase/8/javafx/api/javafx/scene/input/KeyEvent.html
        editorScene.setOnKeyTyped(event -> {
            // TODO: add whatever the typed character is to the text on this page
            text.insertText(event.getCharacter().charAt(0));
            content.setText(text.toString());
            //charList.add(event.getCharacter().charAt(0));
            // NOTE: the typed String can be retrieved with event.getCharacter()
        });

        // display the interface
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
