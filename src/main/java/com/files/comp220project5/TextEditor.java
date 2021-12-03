package com.files.comp220project5;
import javafx.geometry.VPos;
import javafx.geometry.Pos;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
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
        double WINDOW_WIDTH = 1000;
        double WINDOW_HEIGHT = 600;
        Scene editorScene = new Scene(layout, WINDOW_WIDTH, WINDOW_HEIGHT);
        //editorScene.setFocusTraversable(false); // removes capability of arrow switch between objects

        // make this scene the initial (and for now only) scene in your application
        primaryStage.setScene(editorScene);

        // create a new text node to display text on the interface
        // https://docs.oracle.com/javase/8/javafx/api/javafx/scene/text/Text.html
        Text content = new Text();
        content.setFocusTraversable(false);
        content.setTextAlignment(TextAlignment.LEFT);
        BorderPane.setAlignment(content, Pos.TOP_LEFT);  //set text to begin at top left -ch
        content.setWrappingWidth(1024);
        // add this text field to the layout
        layout.setCenter(content);

        // create a new button object and set its text
        Button btn = new Button("Save");
        btn.setFocusTraversable(false);
        // define the code that should run when the button is clicked
        btn.setOnAction(event -> {
        //TODO: stuff in here
        });
        // add this button to the layout centered at the bottom with some spacing from other elements
        BorderPane.setAlignment(btn, Pos.CENTER);
        BorderPane.setMargin(btn, new Insets(16, 16, 16, 16));
        layout.setBottom(btn);


        /*
        editorScene.setOnKeyReleased(event -> {
            System.out.println(event.getCode());
            if (event.getCode().equals(KeyCode.L)) {
                text.moveCursorBackward();
                content.setText(text.toString() + "RIGHT ARROW WORKED");
            } else if (event.getCode().equals(KeyCode.R)) {
                text.moveCursorForward();
                content.setText(text.toString() + "LEFT ARROW WORKED");
            }
        });
*/

        // define code to run every time a KeyPressed event is detected on this window to check for ESC to close
        // NOTE: there even is of type javafx.scene.input.KeyEvent
        // https://docs.oracle.com/javase/8/javafx/api/javafx/scene/input/KeyEvent.html
        editorScene.setOnKeyPressed(event -> {
            // check if the key that was pressed is the ESC key
           System.out.println(event.getCode());
            if (event.getCode().equals(KeyCode.ESCAPE)) {
                // exit the program
                System.exit(0);
            }
            else if (event.getCode().equals(KeyCode.BACK_SPACE)) {
                text.deleteText();
                content.setText(text.toString() + "BACKSPACE");
            }
            else if (event.getCode().equals(KeyCode.LEFT)) {
                text.moveCursorBackward();
                content.setText(text.toString() + "LEFT ARROW WORKED");
                System.out.println("L arr detected");
            } else if (event.getCode().equals(KeyCode.RIGHT)) {
                text.moveCursorForward();
                content.setText(text.toString() + "RIGHT ARROW WORKED");
                System.out.println("r arr detected");
            }
        });

        // define code to run every time a KeyTyped event is detected on this window to check for ESC to close
        // NOTE: there even is of type javafx.scene.input.KeyEvent
        // https://docs.oracle.com/javase/8/javafx/api/javafx/scene/input/KeyEvent.html
        editorScene.setOnKeyTyped(event -> {
            // TODO: add whatever the typed character is to the text on this page
            char c = event.getCharacter().charAt(0);
            if (Character.getType(c)!=Character.CONTROL) {
                text.insertText(event.getCharacter().charAt(0));
                content.setText(text.toString() + " TRIGGERED");
            } else {
                System.out.println(c);
            }
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
