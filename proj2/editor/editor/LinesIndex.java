package editor;

import java.util.ArrayList;

/**
 * Created by Alfred on 15/12/16.
 */
public class LinesIndex {
    private int indexStart;
    private int indexEnd;

    public LinesIndex() {
        indexStart = 0;
        indexEnd = 0;
    }
    public void setIndexStart(int x) {
        indexStart = x;
    }

    public void setIndexEnd(int x) {
        indexEnd = x;
    }

    public int getIndexStart() {
        return indexStart;
    }

    public int getIndexEnd() {
        return indexEnd;
    }
}
