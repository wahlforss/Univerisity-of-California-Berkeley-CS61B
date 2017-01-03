package editor;

import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;

import java.util.ArrayList;

/**
 * Created by Alfred on 14/12/16.
 */
public class TextBuffer {
    private ArrayList<SingleLetter> singleLetterList;
    private double startingTextPositionX = 0;
    private double startingTextPositionY = 0;
    private double currentTextPositionX = 0;
    private double currentTextPositionY = 0;
    private double currentWordWidth;
    private int currentWordStartIndex;
    private int currentWordEndIndex;
    private int currentCursorIndex = -1;
    private SingleLetter currentCursorLetter;
    private Cursor cursor = new Cursor();
    private double lineMultiplier = 24.306640625;
    private ArrayList<LinesIndex> lines = new ArrayList();
    double mousePressedX;
    double mousePressedY;
    private Rectangle windowSize = new Rectangle(1,500);
    private double layoutY;
    private UndoRedo undoRedo;


    public TextBuffer() {
        singleLetterList = new ArrayList();
        lines.add(new LinesIndex());
        undoRedo = new UndoRedo();
    }

    public double yPosOfLastElem() {
        return singleLetterList.get(singleLetterList.size() - 1).getCharacter().getY();
    }

    public void setCurrentCursorIndex(int x) {
        currentCursorIndex = x;
    }

    public void addSingleLetter(int index, SingleLetter s) {
        singleLetterList.add(index, s);
    }
    public void removeSingleLetter(int index) {
        singleLetterList.remove(index);
    }




    public void setWindowSizeWidth(double x) {
        windowSize.setWidth(x);
    }

    public double getWindowSizeWidth() {
        return windowSize.getWidth();
    }

    public double getWindowSizeHeight() {
        return windowSize.getHeight();
    }

    private class KeyEventHandler implements EventHandler<KeyEvent> {
        private Group root;
        private double windowWidth;
        private String fileName;
        //TODO SET RECTANGLE OBJECT AS WINDOW SIZE

        KeyEventHandler(final Group r, double windowW, double windowH, String filename) {
            windowWidth = windowW;
            windowSize.setWidth(windowW);
            windowSize.setHeight(windowH);
            root = r;
            cursor.addCursorRectangle(root);
            fileName = filename;
        }

        @Override
        public void handle(KeyEvent keyEvent) {
            if (keyEvent.getEventType() == KeyEvent.KEY_TYPED && !(keyEvent.isShortcutDown())) {
                // Use the KEY_TYPED event rather than KEY_PRESSED for letter keys, because with
                // the KEY_TYPED event, javafx handles the "Shift" key and associated
                // capitalization.
                String characterTyped = keyEvent.getCharacter();
                undoRedo.resetRedos();

                if (characterTyped.length() > 0 && characterTyped.charAt(0) != 8 && characterTyped.charAt(0) != 13) {
                    // Ignore control keys, which have zero length, as well as the backspace
                    // key, which is represented as a character of value = 8 on Windows.
                    SingleLetter newCharacter = new SingleLetter(0, 0, characterTyped);
                    undoRedo.newAction(currentCursorIndex, newCharacter, "add");
                    currentCursorIndex += 1;
                    singleLetterList.add(currentCursorIndex, newCharacter);


                    wordWrap(getWindowSizeWidth(), windowWidth);

                    cursor.SetCursorRectanglePositionHeight(singleLetterList.get(currentCursorIndex));

                    newCharacter.addToRoot(root);
                    keyEvent.consume();
                } else if (characterTyped.length() > 0 && characterTyped.charAt(0) == 13) {
                    currentCursorIndex += 1;
                    SingleLetter newReturn = new SingleLetter(0, 0, "\n");
                    undoRedo.newAction(currentCursorIndex, newReturn, "add");
                    singleLetterList.add(currentCursorIndex, newReturn);
                    wordWrap(getWindowSizeWidth(), windowWidth);
                    cursor.SetCursorRectanglePositionWithSingle(singleLetterList.get(currentCursorIndex));
                    keyEvent.consume();
                }
            } else if (keyEvent.getEventType() == KeyEvent.KEY_PRESSED) {
                    // Arrow keys should be processed using the KEY_PRESSED event, because KEY_PRESSED
                    // events have a code that we can check (KEY_TYPED events don't have an associated
                    // KeyCode).
                    KeyCode code = keyEvent.getCode();
                    if (code == KeyCode.BACK_SPACE) {
                        if (singleLetterList.size() >= 1) {
                            SingleLetter last = singleLetterList.get(currentCursorIndex);
                            last.removeFromRoot(root);
                            singleLetterList.remove(currentCursorIndex);
                            undoRedo.newAction(currentCursorIndex, last, "remove");
                            wordWrap(getWindowSizeWidth(), windowWidth);
                            currentCursorIndex -= 1;
                            cursor.SetCursorRectanglePositionHeight(singleLetterList.get(currentCursorIndex));
                        }

                    } else if (code == KeyCode.RIGHT) {
                        if (currentCursorIndex < singleLetterList.size() - 1) {
                            currentCursorIndex += 1;
                            cursor.SetCursorRectanglePositionHeight(singleLetterList.get(currentCursorIndex));
                            System.out.println("CURRENT CURSOR INDEX: " + currentCursorIndex);
                        }


                    } else if (code == KeyCode.LEFT) {
                        if (currentCursorIndex > 0) {
                            currentCursorIndex -= 1;
                            cursor.SetCursorRectanglePositionHeight(singleLetterList.get(currentCursorIndex));
                        }



                    } else if (code == KeyCode.UP) {
                        if (cursor.getCursorPosY() > 10) {
                            currentCursorIndex = calculateIndex(cursor.getCursorPosX(), cursor.getCursorPosY() - lineMultiplier);
                            cursor.SetCursorRectanglePositionHeight(singleLetterList.get(currentCursorIndex));
                        }
                    } else if (code == KeyCode.DOWN) {
                        currentCursorIndex = calculateIndex(cursor.getCursorPosX(), cursor.getCursorPosY() + lineMultiplier);
                        cursor.SetCursorRectanglePositionHeight(singleLetterList.get(currentCursorIndex));

                    } else if (keyEvent.isShortcutDown()) {
                        if (code == KeyCode.PLUS || code == KeyCode.EQUALS) {
                            SingleLetter.increaseFontSize(4);
                            wordWrap(getWindowSizeWidth(),windowWidth);
                            cursor.SetCursorRectanglePositionHeight(singleLetterList.get(currentCursorIndex));
                        } else if (code == KeyCode.MINUS) {
                            if (SingleLetter.getFontSize() > 4) {
                                SingleLetter.decreaseFontSize(4);
                                wordWrap(getWindowSizeWidth(),windowWidth);
                                cursor.SetCursorRectanglePositionHeight(singleLetterList.get(currentCursorIndex));
                            }
                        } else if (code == KeyCode.S) {
                            ReadFile.saveFile(singleLetterList, fileName);
                        } else if (code == KeyCode.P) {
                            System.out.println(cursor.getCursorPostion());
                        } else if (code == KeyCode.Z) {
                            undoRedo.undo(TextBuffer.this, root);
                            cursor.SetCursorRectanglePositionHeight(singleLetterList.get(currentCursorIndex));
                            wordWrap(getWindowSizeWidth(), getWindowSizeHeight());
                        } else if (code == KeyCode.Y) {
                            undoRedo.redo(TextBuffer.this, root);
                            cursor.SetCursorRectanglePositionHeight(singleLetterList.get(currentCursorIndex));
                            wordWrap(getWindowSizeWidth(), getWindowSizeHeight());
                        }
                    }

                }



            }
    }

    public void setLayoutY(double x) {
        layoutY = x;
    }

    private class MouseClickEventHandler implements EventHandler<MouseEvent> {
        /** A Text object that will be used to print the current mouse position. */
        private double windowWidth;


        MouseClickEventHandler(double windowW, double lY) {
            windowWidth = windowW;
            setWindowSizeWidth(windowW);
            layoutY = lY;

        }


        @Override
        public void handle(MouseEvent mouseEvent) {
            // Because we registered this EventHandler using setOnMouseClicked, it will only called
            // with mouse events of type MouseEvent.MOUSE_CLICKED.  A mouse clicked event is
            // generated anytime the mouse is pressed and released on the same JavaFX node.
            mousePressedX = mouseEvent.getX();
            mousePressedY = mouseEvent.getY() - layoutY;
            SingleLetter findSingleLetter = singleLetterList.get(calculateIndex(mousePressedX, mousePressedY));
            currentCursorIndex = calculateIndex(mousePressedX, mousePressedY);
            cursor.SetCursorRectanglePositionHeight(findSingleLetter);
        }
    }

    public EventHandler<MouseEvent> getMouseEventHandler(double windowW, double layoutY) {
        return new MouseClickEventHandler(windowW, layoutY);
    }

    public void renderNewFile() {
        if (singleLetterList.size() > 0) {
            wordWrap(getWindowSizeWidth(), getWindowSizeHeight());
            currentCursorIndex = singleLetterList.indexOf(singleLetterList.get(singleLetterList.size() - 1));
            cursor.SetCursorRectanglePositionHeight(singleLetterList.get(currentCursorIndex));
        }

    }

    public void render() {
        wordWrap(getWindowSizeWidth(), getWindowSizeHeight());
        cursor.SetCursorRectanglePositionHeight(singleLetterList.get(currentCursorIndex));
    }

    /** fixes position of text so it word wraps */
    public void wordWrap(double windowW, double windowH) {
        currentTextPositionX = 0;
        currentTextPositionY = 0;
        currentWordWidth = 0;
        currentWordStartIndex = 0;
        currentWordEndIndex = 0;
        lines = new ArrayList();
        lines.add(new LinesIndex());
        int lineNumber = 0;
        int indexStartLine = 0;
        int indexEndLine = 0;

        for (int i = 0; i < singleLetterList.size(); i++) {

            SingleLetter currentItem = singleLetterList.get(i);//Newest character
            currentItem.setFontSize();
            //Add for return statement TODO FIX BUGS...
            if (currentItem.getCharacter().getText() == "\n") {
                currentTextPositionY += lineMultiplier;
                currentTextPositionX = 0;
                currentWordStartIndex = i;
                currentWordEndIndex = i;
                currentItem.setSingleLetterPos(currentTextPositionX, currentTextPositionY);

                indexEndLine = i - 1;
                lines.get(lineNumber).setIndexEnd(indexEndLine);
                indexStartLine = i;
                indexEndLine = i + 1;
                LinesIndex nextLine = new LinesIndex();
                nextLine.setIndexEnd(indexEndLine);
                nextLine.setIndexStart(indexStartLine);
                lineNumber += 1;
                lines.add(lineNumber, nextLine);

            } else {
                lineMultiplier = currentItem.getSingleLetterHeight();

                //if word exceeds window size
                if (currentTextPositionX + currentItem.getSingleLetterWidth() >= windowW) {
                    currentWordEndIndex = i + 1;
                    currentTextPositionY += currentItem.getSingleLetterHeight();
                    currentTextPositionX = 0;
                    currentWordWidth += currentItem.getSingleLetterWidth();

                    //add info about line for cursor.
                    indexEndLine = i - 1;
                    indexStartLine = currentWordStartIndex;
                    LinesIndex currentLine = new LinesIndex();
                    currentLine.setIndexEnd(indexEndLine);
                    currentLine.setIndexStart(indexStartLine);
                    lineNumber += 1;
                    lines.add(lineNumber, currentLine);




                    //if long word which takes up entire page
                    if (currentWordWidth >= windowW) {
                        currentItem.setSingleLetterPos(currentTextPositionX, currentTextPositionY);
                        currentTextPositionX += currentItem.getSingleLetterWidth();
                        lines.get(lineNumber).setIndexStart(i);
                        currentWordEndIndex = i;

                    } else {
                        //change index to first word in
                        lines.get(lineNumber - 1).setIndexEnd(currentWordStartIndex - 2);

                        //moves word down to second line
                        for (int a = currentWordStartIndex; a < currentWordEndIndex; a++) {
                            SingleLetter shiftItem = singleLetterList.get(a);
                            shiftItem.setSingleLetterPos(currentTextPositionX, currentTextPositionY);
                            currentTextPositionX += shiftItem.getSingleLetterWidth();
                        }
                    }

                } else {
                    //if normal setting
                    currentItem.setSingleLetterPos(currentTextPositionX, currentTextPositionY);
                    currentTextPositionX += currentItem.getSingleLetterWidth();
                    currentWordEndIndex = i;
                    currentWordWidth += currentItem.getSingleLetterWidth();
                    indexEndLine = i;
                    lines.get(lineNumber).setIndexEnd(indexEndLine);

                }

                // if space is typed reset word.
                if (currentItem.isSpace()) {
                    currentWordWidth = 0;
                    currentWordStartIndex = i + 1;
                    currentWordEndIndex = 0;
                }

            }



        }
    }

    //TODO Can't select last item...
    public int calculateIndex(double posX, double posY) {
        int row = (int) Math.round(posY/lineMultiplier);
        if (lines.size() - 1 < row) {
            row = lines.size() - 1;
        }

        LinesIndex currentLineIndex = lines.get(row);
        int startIndex = currentLineIndex.getIndexStart();
        int endIndex = currentLineIndex.getIndexEnd();
        System.out.println("start index: " + startIndex);
        System.out.println("end index: " + endIndex);
        if (startIndex >= endIndex) {
            return endIndex;
        }
        double oldCompare = 101020;
        for (int i = startIndex; i <= endIndex; i++) {

            double compare = Math.abs(posX - singleLetterList.get(i).getSingleLetterPosX());
            if (i == endIndex || startIndex > endIndex) {
                return i;
            }
            if (compare < oldCompare) {
                oldCompare = compare;
            } else {
                return i - 2;
            }
        }
        System.out.println("I WAS MONKIFIED TODAY");
        return 1;


    }


    public ArrayList<SingleLetter> getSingleLetterList() {
        return singleLetterList;
    }

    public EventHandler<KeyEvent> getKeyEventHandler(Group root, double windowW, double windowH, String fileName) {
        return new KeyEventHandler(root, windowW, windowH, fileName);
    }
}
