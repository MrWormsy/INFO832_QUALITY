package discreteBehaviorSimulator;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


public class ClockTest {

    @Test
    public void getInstanceTest() {
        Clock c = Clock.getInstance();
        assertTrue(c instanceof Clock);
    }

    @Test
    public void testFunctions() {
        int i = 2;
        int j = 1;
        Clock c = Clock.getInstance();

        // FUNCTION : increase
        // testing normal behavior
        c.setNextJump(i);
        assertDoesNotThrow(() -> c.increase(i));
        assertEquals(i, c.getTime());
        // testing deviant behavior
        assertThrows(Exception.class, () -> c.increase(j), "Unexpected time change");
        assertNotEquals(i+j, c.getTime());

        // FUNCTION : toString
        assertTrue(c.toString() instanceof String);
        assertEquals("2", c.toString());
    }
}