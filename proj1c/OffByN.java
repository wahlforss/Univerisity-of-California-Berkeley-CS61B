/**
 * Created by Alfred on 07/12/16.
 */
public class OffByN implements CharacterComparator {
    private int offBy = 0;

    public OffByN(int N) {
        offBy = N;
    }

    @Override
    public boolean equalChars(char x, char y) {
        if(Math.abs(x-y) == offBy) return true;
        return false;
    }

    public static void main(String[] args) {
        OffByN offby5 = new OffByN(5);
        System.out.print(offby5.equalChars('a', 'f'));
    }
}
