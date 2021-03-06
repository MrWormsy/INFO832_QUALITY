package timer;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PeriodicTimerTest {

    @Test
    void getPeriod() {

        // We create a dummy PeriodicTimer and try to get its Period back
        PeriodicTimer periodicTimer1 = new PeriodicTimer(1);
        assertEquals(1, periodicTimer1.getPeriod());

        // We create an other dummy PeriodicTimer and try to get its Period back
        PeriodicTimer periodicTimer2 = new PeriodicTimer(1, 1);
        assertEquals(1, periodicTimer2.getPeriod());

        // RandomTimer - even if we do not have to use those constructors because they are deprecated
        RandomTimer randomTimer = null;
        try {
            randomTimer = new RandomTimer(RandomDistribution.EXP, 1);
        } catch (IncorrectDistributionException e) {
            e.printStackTrace();
        }

        // We create an other dummy PeriodicTimer and try to get its Period back
        PeriodicTimer periodicTimer3 = new PeriodicTimer(1, randomTimer);
        assertEquals(1, periodicTimer3.getPeriod());

        // We create an other dummy PeriodicTimer and try to get its Period back
        PeriodicTimer periodicTimer4 = new PeriodicTimer(1, 1, randomTimer);
        assertEquals(1, periodicTimer4.getPeriod());

    }

    @Test
    void next() {

        // Here we want to have the next value of the PeriodicTimers
        int nextValue = 1;
        PeriodicTimer periodicTimer1 = new PeriodicTimer(nextValue);

        // As the PeriodicTimer has always a next value we can do the test only twice
        assertEquals(nextValue, periodicTimer1.next());
        assertEquals(nextValue, periodicTimer1.next());

        // For this constructor the first time we access the next value it is the at parameter and then the period one
        int periodValue = 2;
        PeriodicTimer periodicTimer2 = new PeriodicTimer(periodValue, nextValue);
        assertEquals(nextValue, periodicTimer2.next());
        assertEquals(periodValue, periodicTimer2.next());

        // We want to create a PeriodicTimer with a random timer to trigger the next() value when there is a moreOrLess Timer
        // Here we only try with a uniform law with the same limit thus the next value will always be 1
        try {
            RandomTimer randomTimer = new RandomTimer(RandomDistribution.UNIFORM, 1, 1);
            PeriodicTimer periodicTimerWithRandomTimer = new PeriodicTimer(nextValue, randomTimer);

            assertEquals(nextValue, periodicTimerWithRandomTimer.next());

        } catch (IncorrectDistributionException e) {
            e.printStackTrace();
        }

        // We want to create a PeriodicTimer with a random timer to trigger the next() value when there is a moreOrLess Timer
        // Here we only try with a uniform law with the same limit thus the next value will always be 1
        try {
            RandomTimer randomTimer = new RandomTimer(RandomDistribution.UNIFORM, 1, 1);
            PeriodicTimer periodicTimerWithRandomTimer = new PeriodicTimer(periodValue, nextValue, randomTimer);

            assertEquals(nextValue, periodicTimerWithRandomTimer.next());

            assertEquals(periodValue, periodicTimerWithRandomTimer.next());

        } catch (IncorrectDistributionException e) {
            e.printStackTrace();
        }

    }

    // We don't need this test because the PeriodicTimer always returns true (but we do it anyways just in case)
    @Test
    void hasNext() {

        // We create a dummy PeriodicTimer and try to get its Period back
        PeriodicTimer periodicTimer1 = new PeriodicTimer(1);
        assertTrue(periodicTimer1.hasNext());

        // We create an other dummy PeriodicTimer and try to get its Period back
        PeriodicTimer periodicTimer2 = new PeriodicTimer(1, 1);
        assertTrue(periodicTimer2.hasNext());

        // RandomTimer - even if we do not have to use those constructors because they are deprecated
        RandomTimer randomTimer = null;
        try {
            randomTimer = new RandomTimer(RandomDistribution.EXP, 1);
        } catch (IncorrectDistributionException e) {
            e.printStackTrace();
        }

        // We create an other dummy PeriodicTimer and try to get its Period back
        PeriodicTimer periodicTimer3 = new PeriodicTimer(1, randomTimer);
        assertTrue(periodicTimer3.hasNext());

        // We create an other dummy PeriodicTimer and try to get its Period back
        PeriodicTimer periodicTimer4 = new PeriodicTimer(1, 1, randomTimer);
        assertTrue(periodicTimer4.hasNext());

    }
}