import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by Alfred on 06/12/16.
 */
public class TestArrayDeque1B {

    @Test
    public static void TestSize() {
        StudentArrayDeque<Integer> a = new StudentArrayDeque<Integer>();
        assertEquals(0,a.size());
        a.addFirst(5);
        a.addFirst(5);
        a.addFirst(5);
        a.removeFirst();
        a.addLast(123);
        a.addFirst(5);
        a.removeLast();
        a.removeLast();
        a.removeLast();
        a.removeFirst();
        assertEquals(0,a.size());
    }
    @Test
    public static void TestOverall() {
        StudentArrayDeque<Integer> a = new StudentArrayDeque<Integer>();

        assertEquals(3,a.size());
    }
    public static void main(String[] args) {
        TestSize();

    }
}
