import static org.junit.Assert.*;

import org.junit.Test;
/**
 * Created by Alfred on 29/11/16.
 */
public class FlikTest {
    @Test
    public void isSameNumberTest() {
        int a = 129;
        int b = 129;
        int c = 54;
        assertTrue(Flik.isSameNumber(a,b));
    }
}
