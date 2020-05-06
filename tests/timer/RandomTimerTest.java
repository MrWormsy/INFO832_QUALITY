package timer;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RandomTimerTest {

    @Test
    void getDistributionParams() {

        // Create a RandomTimer of an exponential law
        try {
            double param = 1.0;
            RandomTimer randomTimerEXP = new RandomTimer(RandomDistribution.EXP, param);

            // We need to get
            assertEquals("rate: " + param, randomTimerEXP.getDistributionParams());
        } catch (IncorrectDistributionException e) {
            e.printStackTrace();
        }

    }

    @Test
    void getMean() {
    }

    @Test
    void hasNext() {
    }

    @Test
    void next() {
    }

    @Test
    void testToString() {
    }
}