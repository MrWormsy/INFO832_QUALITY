package timer;

import org.junit.jupiter.api.Test;

import java.util.ConcurrentModificationException;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

class MergedTimerTest {

    // Here we will do the hasNext() and the next() at the same time

    @Test
    void hasNext() {

        // First Timer
        OneShotTimer oneShotTimer1 = new OneShotTimer(1);

        // Second Timer
        OneShotTimer oneShotTimer2 = new OneShotTimer(2);

        // First we want to check if when we construct a MergeTimer with null object it throws a NullPointerException
        assertThrows(NullPointerException.class, () -> new MergedTimer(null, null));
        assertThrows(NullPointerException.class, () -> new MergedTimer(oneShotTimer1, null));
        assertThrows(NullPointerException.class, () -> new MergedTimer(null, oneShotTimer2));

        // And if we give two not null Timers we are good
        assertDoesNotThrow(() -> new MergedTimer(oneShotTimer1, oneShotTimer2));

        // Now we will test with a OneShotTimer the first timer it returns true and the second time false
        MergedTimer mergedTimer1 = new MergedTimer(oneShotTimer1, oneShotTimer2);

        // As this is the fist time we get True
        assertTrue(mergedTimer1.hasNext());

        // We get the next value and we should get 1 + 2 = 3
        assertEquals(3, mergedTimer1.next());

        // And as the MergeIs composed of two one shot timer if we do hasNext() it returns false.
        assertFalse(mergedTimer1.hasNext());

        // Third Timer
        OneShotTimer oneShotTimer3 = new OneShotTimer(3);

        // We create a dummy infinite Timer to test the MergedTimer
        Timer infiniteTimer = new Timer() {
            @Override
            public Integer next() {
                return 4;
            }

            @Override
            public boolean hasNext() {
                return true;
            }
        };

        MergedTimer oneShotAndInfinite = new MergedTimer(oneShotTimer3, infiniteTimer);

        // We first time we should get a true hasNext()
        assertTrue(oneShotAndInfinite.hasNext());

        // We except 7 = 3 + 4
        assertEquals(7, oneShotAndInfinite.next());

        // Even if the infinite dummy timer returns always true, the OneShotTimer can only be used once
        assertFalse(oneShotAndInfinite.hasNext());

        // if we try to get the next value it should return a null value
        assertNull(oneShotAndInfinite.next());

    }
}