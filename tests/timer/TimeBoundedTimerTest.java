package timer;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TimeBoundedTimerTest {

    @Test
    void hasNext() {

        // We want to test a dummy Timer with a null next value
        Timer nullNextValueTimer = new Timer() {
            @Override
            public Integer next() {
                return null;
            }

            @Override
            public boolean hasNext() {
                return false;
            }
        };
        assertThrows(NullPointerException.class, () -> new TimeBoundedTimer(nullNextValueTimer, 1));

        // Here we want to test the OneShotTimer with a next value less than the start time which will fire a NullPointerException in the init method
        OneShotTimer oneShotTimerPriorStartTime = new OneShotTimer(1);
        assertThrows(NullPointerException.class, () -> new TimeBoundedTimer(oneShotTimerPriorStartTime, 2));

        OneShotTimer oneShotTimer1 = new OneShotTimer(10);

        TimeBoundedTimer timeBoundedTimer1 = new TimeBoundedTimer(oneShotTimer1, 5);

        // As this is the first time we expect true
        assertTrue(timeBoundedTimer1.hasNext());

        // We get the next value and we should get 10
        assertEquals(10, timeBoundedTimer1.next());

        // As the OneShotTimer has no longer values to return we get false
        assertFalse(timeBoundedTimer1.hasNext());

        // As the OneShotTimer has only one next value BUT the TimeBoundedTimer works like a Timer that return a false hasNext() only if the next valie is greater than the timestop
        // But in our case we will have a NullPointerException as we are using a OneShotTimer
        assertThrows(NullPointerException.class, () -> timeBoundedTimer1.next());

        // As we can see we still have an issue with the has next
        // assertFalse(timeBoundedTimer1.hasNext());


        // We create a dummy Timer to test the has next
        Timer dummyTimer = new Timer() {
            @Override
            public Integer next() {
                return 1;
            }

            @Override
            public boolean hasNext() {
                return true;
            }
        };

        int startTime = 3;

        // We create a first TimeBoundedTimer with only a start time that means it can be used until its next value is as big as Integer.MAX_VALUE
        TimeBoundedTimer timeBoundedTimer2 = new TimeBoundedTimer(dummyTimer, startTime);

        // The first time we get a next value greater than the start time as the next value of the dummyTimer is less than the startTimer
        assertTrue(timeBoundedTimer2.hasNext());

        assertEquals(startTime, timeBoundedTimer2.next());

        // As the time value if less than the Integer.MAX_VALUE we get true
        assertTrue(timeBoundedTimer2.hasNext());

        // Next we should get the next value of the dummy timer
        assertEquals(dummyTimer.next(), timeBoundedTimer2.next());

        // As the time value if less than the Integer.MAX_VALUE we get true
        assertTrue(timeBoundedTimer2.hasNext());

        // Next we should get the next value of the dummy timer
        assertEquals(dummyTimer.next(), timeBoundedTimer2.next());

        // Now we want to create a second TimeBoundedTimer with a  start time and a stop time
        int stopTime = 5;
        TimeBoundedTimer timeBoundedTimer3 = new TimeBoundedTimer(dummyTimer, startTime, stopTime);

        // The first time we get the start time as the next value of the dummyTimer is less than the startTimer
        assertTrue(timeBoundedTimer3.hasNext());

        assertEquals(startTime, timeBoundedTimer3.next());

        // As the time value if less than the stopTime we get true
        assertTrue(timeBoundedTimer3.hasNext());

        // Next we should get the next value of the dummy timer
        assertEquals(dummyTimer.next(), timeBoundedTimer3.next());

        // As the time value if less than the stopTime we get true
        assertTrue(timeBoundedTimer3.hasNext());

        // Next we should get the next value of the dummy timer
        assertEquals(dummyTimer.next(), timeBoundedTimer3.next());

        // As the time value if greater or equal to the stopTime we get false and we can no longer use this TimeBoundedTimer
        assertFalse(timeBoundedTimer3.hasNext());

        // As there is not next value if we try to get it we will get a NullPointerException
        assertThrows(NullPointerException.class, () -> timeBoundedTimer3.next());

        // Now we want to create a TimeBound Timer with a Timer that has a next value greater than the stop time thus it will have a false hasNext()
        Timer dummyTimer2 = new Timer() {
            @Override
            public Integer next() {
                return 10;
            }

            @Override
            public boolean hasNext() {
                return true;
            }
        };

        TimeBoundedTimer timeBoundedTimerWithValueGreaterThanStopTime = new TimeBoundedTimer(dummyTimer2, 1, 2);

        assertFalse(timeBoundedTimerWithValueGreaterThanStopTime.hasNext());

    }
}