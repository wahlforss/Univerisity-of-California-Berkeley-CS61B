package editor;

import javafx.scene.Group;

import java.util.ArrayList;
import java.util.Stack;

/**
 * Created by Alfred on 19/12/16.
 */
public class UndoRedo {
    private UndoStack undos;
    private Stack<ListOfAction> redos;

    private class UndoStack {
        private ListOfAction[] undo;
        private int index;
        private int size;

        public UndoStack() {
            index = 0;
            size = 0;
            undo = new ListOfAction[100];
        }

        public void push(ListOfAction x) {
            undo[index] = x;
            if (size != 100) size += 1;
            index += 1;
            if (index > 99) index = 0;
            }
        public ListOfAction pop() {
            index -= 1;
            size -= 1;
            if (index < 0) index = 99;
            return undo[index];
        }

        public boolean isEmpty() {
            return size == 0;
        }

        }

    
    private class ListOfAction {
        private int cursorIndex;
        private SingleLetter character;
        private String addOrRemove;
        
        public ListOfAction(int cI, SingleLetter c, String aOR) {
            cursorIndex = cI;
            character = c;
            addOrRemove = aOR;
        }
        
        public int getCursorIndex() {
            return cursorIndex;
        }
        
        public SingleLetter getSingleLetter() {
            return character;
        }
        
        public void setCursorIndex(int x) {
            cursorIndex = x;
        }
        
        public boolean isAdd() {
            return addOrRemove == "add";
        }
        
        public boolean isRemove() {
            return addOrRemove == "remove";
        }
        
    }

    public UndoRedo() {
        undos = new UndoStack();
        redos = new Stack<>();
    }

    public void newAction(int cursorIndex, SingleLetter character,String addOrRemove) {
        ListOfAction currentAction = new ListOfAction(cursorIndex, character, addOrRemove);
        undos.push(currentAction);
    }

    public void remove(TextBuffer textbuffer, Group root, ListOfAction item, int x) {
        textbuffer.removeSingleLetter(item.getCursorIndex() + 1 + x);
        item.getSingleLetter().removeFromRoot(root);
    }

    public void add(TextBuffer textbuffer, Group root, ListOfAction item, int x) {
        textbuffer.addSingleLetter(item.getCursorIndex() + x, item.getSingleLetter());
        item.getSingleLetter().addToRoot(root);
    }

    public void undo(TextBuffer textbuffer, Group root){
        if (!undos.isEmpty()) {
            ListOfAction currentUndo = undos.pop();
            redos.push(currentUndo);
            textbuffer.setCurrentCursorIndex(currentUndo.getCursorIndex());
            if (currentUndo.isRemove()) {
                add(textbuffer, root, currentUndo, 0);
            } else if (currentUndo.isAdd()) {
                remove(textbuffer, root, currentUndo, 0);
            }
        }
    }
    public void redo(TextBuffer textbuffer, Group root){
        if (!redos.isEmpty()) {
            ListOfAction currentRedo = redos.pop();
            undos.push(currentRedo);
            textbuffer.setCurrentCursorIndex(currentRedo.getCursorIndex() + 1);
            if (currentRedo.isRemove()) {
                remove(textbuffer, root, currentRedo, 1);
            } else if (currentRedo.isAdd()) {
                add(textbuffer, root, currentRedo, 1);
            }
        }
    }

    public void resetRedos(){
        redos.clear();
    }

}
