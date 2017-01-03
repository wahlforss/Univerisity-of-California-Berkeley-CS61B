package editor;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.geometry.VPos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.ScrollBar;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;

//Simple imports

/**
 * @author Alfred
 * This is a wonderful little word editor.
 */

public class Editor extends Application {
    private ArrayList<SingleLetter> singleLetterList = new ArrayList();
    private TextBuffer textBuffer = new TextBuffer();


    private double WINDOW_WIDTH = 500;
    private double WINDOW_HEIGHT = 500;


    @Override
    public void start(Stage primaryStage) {
        Parameters parameters = getParameters();
        String filename = parameters.getRaw().get(0);

        // Create a Node that will be the parent of all things displayed on the screen.
        Group textroot = new Group();
        Group root = new Group();
        root.getChildren().add(textroot);
        textroot.setLayoutY(0);


        // The Scene represents the window: its height and width will be the height and width
        // of the window displayed.
        Scene scene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT, Color.WHITE);

        // To get information about what keys the user is pressing, create an EventHandler.
        // EventHandler subclasses must override the "handle" function, which will be called
        // by javafx.


        // Make a vertical scroll bar on the right side of the screen.
        ScrollBar scrollBar = new ScrollBar();
        scrollBar.setOrientation(Orientation.VERTICAL);
        // Set the height of the scroll bar so that it fills the whole window.
        scrollBar.setPrefHeight(WINDOW_HEIGHT);

        double usableScreenWidth = WINDOW_WIDTH - scrollBar.getLayoutBounds().getWidth();
        scrollBar.setLayoutX(usableScreenWidth);



        //TODO: FIX FILENAME NEW LINE BUGS


        EventHandler<KeyEvent> keyEventHandler =
                textBuffer.getKeyEventHandler(textroot, usableScreenWidth, WINDOW_HEIGHT, filename);
        EventHandler<MouseEvent> mouseEventHandler =
                textBuffer.getMouseEventHandler(usableScreenWidth, textroot.getLayoutY());
        // Register the event handler to be called for all KEY_PRESSED and KEY_TYPED events.
        ReadFile.addToList(textBuffer.getSingleLetterList(), textroot, filename);
        textBuffer.renderNewFile();

        //SCROLL BAR
        double lastelem = textBuffer.yPosOfLastElem();

        // Make a vertical scroll bar on the right side of the screen.

        // Set the range of the scroll bar.
        scrollBar.setMin(0);
        scrollBar.setMax(lastelem);
        if (lastelem == 0)  scrollBar.setMax(1);

        // Add the scroll bar to the scene graph, so that it appears on the screen.
        root.getChildren().add(scrollBar);


        //The Text is affected by window resize.
        scene.widthProperty().addListener(new ChangeListener<Number>() {
            @Override public void changed(
                    ObservableValue<? extends Number> observableValue,
                    Number oldScreenWidth,
                    Number newScreenWidth) {
                WINDOW_WIDTH = newScreenWidth.doubleValue();
                double usableScreenWidth = WINDOW_WIDTH - scrollBar.getLayoutBounds().getWidth();
                scrollBar.setLayoutX(usableScreenWidth);
                textBuffer.setWindowSizeWidth(usableScreenWidth);
                textBuffer.render();
            }
        });
        scene.heightProperty().addListener(new ChangeListener<Number>() {
            @Override public void changed(
                    ObservableValue<? extends Number> observableValue,
                    Number oldScreenHeight,
                    Number newScreenHeight) {
                WINDOW_HEIGHT = newScreenHeight.intValue();
                scrollBar.setPrefHeight(WINDOW_HEIGHT);
                textBuffer.render();
            }
        });

        // All new Nodes need to be added to the textroot in order to be displayed.


        scene.setOnMouseClicked(mouseEventHandler);
        scene.setOnKeyTyped(keyEventHandler);
        scene.setOnKeyPressed(keyEventHandler);



        /** When the scroll bar changes position, change the height of Josh. */
        scrollBar.valueProperty().addListener(new ChangeListener<Number>() {
            double lastelem = textBuffer.yPosOfLastElem();

            public void changed(
                    ObservableValue<? extends Number> observableValue,
                    Number oldValue,
                    Number newValue) {
                // newValue describes the value of the new position of the scroll bar. The numerical
                // value of the position is based on the position of the scroll bar, and on the min
                // and max we set above. For example, if the scroll bar is exactly in the middle of
                // the scroll area, the position will be:
                //      scroll minimum + (scroll maximum - scroll minimum) / 2
                // Here, we can directly use the value of the scroll bar to set the height of Josh,
                // because of how we set the minimum and maximum above.
                textBuffer.setLayoutY(-newValue.doubleValue());
                lastelem = textBuffer.yPosOfLastElem();
                if (lastelem == 0) lastelem = 1;
                scrollBar.setMax(lastelem);
                textroot.setLayoutY(-newValue.doubleValue());
            }
        });

        primaryStage.setTitle("ALFREDS MEGA EDITOR!");

        // This is boilerplate, necessary to setup the window where things are displayed.
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
