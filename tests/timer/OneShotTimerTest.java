package timer;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OneShotTimerTest {

    // We will do hasNext() and next() at the same time
    @Test
    void hasNext() {

        int value = 1;

        // We create a dummy Timer
        OneShotTimer oneShotTimer1 = new OneShotTimer(value);

        // The first time we do hasNext() returns true
        assertTrue(oneShotTimer1.hasNext());

        // The first time we do next() we get the value of the timer
        assertEquals(value, oneShotTimer1.next());

        // As this Timer is a OneShotTimer and we already got the value if we do .hasNext() it should returns false
        assertFalse(oneShotTimer1.hasNext());

        // And the return value is null
        assertNull(oneShotTimer1.next());

    }
}