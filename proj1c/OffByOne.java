import static java.lang.Math.abs;

/**
 * Created by Alfred on 07/12/16.
 */
public class OffByOne implements CharacterComparator {

    @Override
    public boolean equalChars(char x, char y) {
        if(abs(x-y) == 1) return true;
        return false;
        }

    }
