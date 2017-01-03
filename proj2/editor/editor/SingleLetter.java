package editor;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.VPos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * Created by Alfred on 14/12/16.
 */
public class SingleLetter {
    public Rectangle textBoundingBox;
    public Text character;
    private String fontName = "Verdana";
    static private int fontSize = 20;

    public SingleLetter() {
        // Create a rectangle to surround the text that gets displayed.  Initialize it with a size
        // of 0, since there isn't any text yet.
        textBoundingBox = new Rectangle(0, 0);
        textBoundingBox.setFill(Color.WHITE);
    }

    public static void increaseFontSize(int x) {
        fontSize += x;
    }

    public static void decreaseFontSize(int x) {
        fontSize -= x;
    }

    public static int getFontSize() {
        return fontSize;
    }
    public void setFontSize() {
        character.setFont(Font.font(fontName, fontSize));
        double textHeight = character.getLayoutBounds().getHeight();
        double textWidth = character.getLayoutBounds().getWidth();
        textBoundingBox.setWidth(textWidth);
        textBoundingBox.setHeight(textHeight);
    }

    public SingleLetter(double posX, double posY, String c) {
        textBoundingBox = new Rectangle(0, 0);
        character = new Text(posX, posY, c);
        character.setTextOrigin(VPos.TOP);
        character.setFont(Font.font(fontName, fontSize));

        //add bounded box w and h
        double textHeight = character.getLayoutBounds().getHeight();
        double textWidth = character.getLayoutBounds().getWidth();
        textBoundingBox.setWidth(textWidth);
        textBoundingBox.setHeight(textHeight);
        textBoundingBox.setX(posX);
        textBoundingBox.setY(posY);
        textBoundingBox.setFill(Color.WHITE);
    }

    public void setSingleLetterPos(double posX, double posY) {
        character.setX(posX);
        character.setY(posY);
        textBoundingBox.setX(posX);
        textBoundingBox.setY(posY);

    }

    public double getSingleLetterPosX() {
        return character.getX();
    }

    public double getSingleLetterWidth() {

        return textBoundingBox.getWidth();
    }
    public double getSingleLetterHeight() {
        if (character.getText() == "\n") {
            SingleLetter normalLetter = new SingleLetter(character.getX(), character.getY(), "c");
            return normalLetter.getSingleLetterHeight();
        }
        return textBoundingBox.getHeight();
    }
    public Text getCharacter() {
        return character;
    }

    public boolean isSpace() {
        return (character.getText().matches("^\\s*$"));

    }

    public Rectangle getTextBoundingBox() {
        return textBoundingBox;
    }

    public void addToRoot(Group root) {
        root.getChildren().add(textBoundingBox);
        root.getChildren().add(character);
        character.toFront();
    }

    public void removeFromRoot(Group root) {
        root.getChildren().remove(textBoundingBox);
        root.getChildren().remove(character);
    }

}
