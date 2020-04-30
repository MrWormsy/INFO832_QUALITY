package timer;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TimeBoundedTimerTest {

    @Test
    void hasNext() {

        OneShotTimer oneShotTimer1 = new OneShotTimer(10);

        TimeBoundedTimer timeBoundedTimer1 = new TimeBoundedTimer(oneShotTimer1, 1);

        assertTrue(timeBoundedTimer1.hasNext());

        timeBoundedTimer1.next();

        // There is an issue here as the Timer is a OneShotTimer we should only get a next value once
        assertFalse(timeBoundedTimer1.hasNext());

    }

    @Test
    void next() {
    }
}