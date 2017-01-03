package editor;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.util.ArrayList;


/**
 * Created by Alfred on 14/12/16.
 */
public class Cursor {
    private Rectangle cursorRectangle;

    public Cursor() {
        cursorRectangle = new Rectangle(1, 24.30);
    }

    public Rectangle getCursorRectangle() {
        return cursorRectangle;
    }

    public void setCursorRectanglePosition(double posX, double posY) {
        cursorRectangle.setX(posX);
        cursorRectangle.setY(posY);
    }

    public double getCursorPosX() {
        return cursorRectangle.getX();
    }
    public double getCursorPosY() {
        return cursorRectangle.getY();
    }

    public void addCursorRectangle(Group root) {
        root.getChildren().add(cursorRectangle);
        setCursorRectanglePosition(2,0);
        makeCursorBlink();
    }

    public void SetCursorRectanglePositionHeight(SingleLetter c) {
        setCursorRectanglePosition(c.getCharacter().getX() + c.getSingleLetterWidth(), c.getCharacter().getY());
        setCursorRectangleHeight(c.getSingleLetterHeight());
        cursorRectangle.toFront();
    }
    public void SetCursorRectanglePositionWithSingle(SingleLetter c) {
        setCursorRectanglePosition(c.getCharacter().getX() + c.getSingleLetterWidth(), c.getCharacter().getY());
        cursorRectangle.toFront();
    }
    public String getCursorPostion() {
        return Math.round(cursorRectangle.getX()) + ", " + Math.round(cursorRectangle.getY());
    }


    public void setCursorRectangleHeight(double h) {
        cursorRectangle.setHeight(h);
    }
    public void front() {
        cursorRectangle.toFront();
    }

    private class CursorBlinkEventHandler implements EventHandler<ActionEvent> {
        private int currentColorIndex = 0;
        private Color[] boxColors =
                {Color.BLACK, Color.WHITE};


        CursorBlinkEventHandler() {
            // Set the color to be the first color in the list.
            changeColor();
        }

        private void changeColor() {
            cursorRectangle.setFill(boxColors[currentColorIndex]);
            currentColorIndex = (currentColorIndex + 1) % boxColors.length;
        }

        @Override
        public void handle(ActionEvent event) {
            changeColor();
        }
    }
    public void makeCursorBlink() {
        // Create a Timeline that will call the "handle" function of RectangleBlinkEventHandler
        // every 1 second.
        final Timeline timeline = new Timeline();
        // The rectangle should continue blinking forever.
        timeline.setCycleCount(Timeline.INDEFINITE);
        Cursor.CursorBlinkEventHandler cursorChange = new Cursor.CursorBlinkEventHandler();
        KeyFrame keyFrame = new KeyFrame(Duration.seconds(0.5), cursorChange);
        timeline.getKeyFrames().add(keyFrame);
        timeline.play();
    }




}
