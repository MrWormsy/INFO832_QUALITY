package timer;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RandomTimerTest {

    // Get the parameters of the RandomTimer as a String
    @Test
    void getDistributionParams() {

        // Create a RandomTimer of an exponential law
        try {
            double param = 1.0;
            RandomTimer randomTimerEXP = new RandomTimer(RandomDistribution.EXP, param);

            // We need to get a rate of 1.0 (as this is an exponential law)
            assertEquals("rate: " + randomTimerEXP.getRate(), randomTimerEXP.getDistributionParams());
        } catch (IncorrectDistributionException e) {
            e.printStackTrace();
        }

        // Create a RandomTimer of a poisson law
        try {
            double param = 1.0;
            RandomTimer randomTimerPOISSON = new RandomTimer(RandomDistribution.POISSON, param);

            // We need to get a mean of 1.0 (as this is a poisson law)
            assertEquals("mean: " + randomTimerPOISSON.getMean(), randomTimerPOISSON.getDistributionParams());
        } catch (IncorrectDistributionException e) {
            e.printStackTrace();
        }

        // Create a RandomTimer of a gaussian law
        try {
            double limitInferior = -1.0;
            double limitSuperior = 1.0;
            RandomTimer randomTimerGAUSSIAN = new RandomTimer(RandomDistribution.GAUSSIAN, limitInferior,limitSuperior);

            // We need to get an inferior limit and a superior limit (as we got a gaussian law)
            assertEquals("Inferior limit: " + randomTimerGAUSSIAN.getLimitInferior() + " Superior limit: " + randomTimerGAUSSIAN.getLimitSuperior(), randomTimerGAUSSIAN.getDistributionParams());
        } catch (IncorrectDistributionException e) {
            e.printStackTrace();
        }

        // Create a RandomTimer of a uniform law
        try {
            double limitInferior = -1.0;
            double limitSuperior = 1.0;
            RandomTimer randomTimerUNIFORM = new RandomTimer(RandomDistribution.UNIFORM, limitInferior,limitSuperior);

            // We need to get an inferior limit and a superior limit (as we got a uniform law)
            assertEquals("Inferior limit: " + randomTimerUNIFORM.getLimitInferior() + " Superior limit: " + randomTimerUNIFORM.getLimitSuperior(), randomTimerUNIFORM.getDistributionParams());
        } catch (IncorrectDistributionException e) {
            e.printStackTrace();
        }

    }

    // Get the mean of the RandomTimer
    @Test
    void getMean() {

        // Create a RandomTimer of an exponential law
        try {
            double param = 1.0;
            RandomTimer randomTimerEXP = new RandomTimer(RandomDistribution.EXP, param);

            assertEquals(param, randomTimerEXP.getMean());
        } catch (IncorrectDistributionException e) {
            e.printStackTrace();
        }

        // Create a RandomTimer of a poisson law
        try {
            double param = 1.0;
            RandomTimer randomTimerPOISSON = new RandomTimer(RandomDistribution.POISSON, param);

            assertEquals(param, randomTimerPOISSON.getMean());
        } catch (IncorrectDistributionException e) {
            e.printStackTrace();
        }

        // Create a RandomTimer of a gaussian law
        try {
            double limitInferior = -1.0;
            double limitSuperior = 1.0;
            RandomTimer randomTimerGAUSSIAN = new RandomTimer(RandomDistribution.GAUSSIAN, limitInferior,limitSuperior);

            assertEquals((limitSuperior + limitInferior) / 2.0, randomTimerGAUSSIAN.getMean());
        } catch (IncorrectDistributionException e) {
            e.printStackTrace();
        }

        // Create a RandomTimer of a uniform law
        try {
            double limitInferior = -1.0;
            double limitSuperior = 1.0;
            RandomTimer randomTimerUNIFORM = new RandomTimer(RandomDistribution.UNIFORM, limitInferior,limitSuperior);

            assertEquals((limitSuperior + limitInferior) / 2.0, randomTimerUNIFORM.getMean());
        } catch (IncorrectDistributionException e) {
            e.printStackTrace();
        }
    }

    // Not need to test hasNext() as it returns always true
    @Test
    void hasNext() {
    }

    // Here we want to know if the law is working, we will simulate 100 000 next appeals and we will compare it to the mean of the law to know if the law is working (with a minor error of 1%)
    // And we will checking if the nextValue are between the inferiorLimit and the superiorLimit
    @Test
    void next() {

        // The number of times we want to get hasNext()
        final int numberIterations = 100000;

        // Create a RandomTimer of an exponential law
        try {
            double param = 0.001;
            RandomTimer randomTimerEXP = new RandomTimer(RandomDistribution.EXP, param);
            int total = 0;
            Integer currentNext;

            for (int i = 0; i < numberIterations; i++) {

                currentNext = randomTimerEXP.next();

                // We check that this value is between the min and the max values it can go through
                assertTrue(randomTimerEXP.getLimitInferior() <= currentNext && currentNext <= randomTimerEXP.getLimitSuperior());
                
                // And we add this value to the sum
                total += currentNext;
            }

            // Here we check that the sum / numberIterations is nearly equal to the mean with a 1% risk
            assertEquals(randomTimerEXP.getMean(), (total * 1.0) / (numberIterations * 1.0), 0.01 * randomTimerEXP.getMean());
            
        } catch (IncorrectDistributionException e) {
            e.printStackTrace();
        }

        // Create a RandomTimer of a poisson law
        try {
            double param = 0.1;
            RandomTimer randomTimerPOISSON = new RandomTimer(RandomDistribution.POISSON, param);
            int total = 0;
            Integer currentNext;

            for (int i = 0; i < numberIterations; i++) {

                currentNext = randomTimerPOISSON.next();

                // We check that this value is between the min and the max values it can go through
                assertTrue(randomTimerPOISSON.getLimitInferior() <= currentNext && currentNext <= randomTimerPOISSON.getLimitSuperior());

                // And we add this value to the sum
                total += currentNext;
            }

            // Here we check that the sum / numberIterations is nearly equal to the mean with a 1% risk
            assertEquals(randomTimerPOISSON.getMean(), (total * 1.0) / (numberIterations * 1.0), 0.01);

        } catch (IncorrectDistributionException e) {
            e.printStackTrace();
        }

        // Create a RandomTimer of a gaussian law
        try {
            double limitInferior = -1000.0;
            double limitSuperior = 1000.0;
            RandomTimer randomTimerGAUSSIAN = new RandomTimer(RandomDistribution.GAUSSIAN, limitInferior, limitSuperior);

            int total = 0;
            Integer currentNext;

            for (int i = 0; i < numberIterations; i++) {

                currentNext = randomTimerGAUSSIAN.next();

                // We check that this value is between the min and the max values it can go through
                assertTrue(randomTimerGAUSSIAN.getLimitInferior() <= currentNext && currentNext <= randomTimerGAUSSIAN.getLimitSuperior());

                // And we add this value to the sum
                total += currentNext;
            }

            System.out.println(total / numberIterations);
            System.out.println(randomTimerGAUSSIAN.getMean());

            // Here we check that the sum / numberIterations is nearly equal to the mean with a 1% risk
            assertEquals(randomTimerGAUSSIAN.getMean(), (total * 1.0) / (numberIterations * 1.0), 0.01 * randomTimerGAUSSIAN.getMean());
        } catch (IncorrectDistributionException e) {
            e.printStackTrace();
        }

        // Create a RandomTimer of a uniform law
        try {
            double limitInferior = -1.0;
            double limitSuperior = 1.0;
            RandomTimer randomTimerUNIFORM = new RandomTimer(RandomDistribution.UNIFORM, limitInferior,limitSuperior);

            assertEquals((limitSuperior + limitInferior) / 2.0, randomTimerUNIFORM.getMean());
        } catch (IncorrectDistributionException e) {
            e.printStackTrace();
        }

    }

    // Test the toString() method
    @Test
    void testToString() {

        // Create a RandomTimer of an exponential law
        try {
            double param = 1.0;
            RandomTimer randomTimerEXP = new RandomTimer(RandomDistribution.EXP, param);

            // We need to get a rate of 1.0 (as this is an exponential law)
            assertEquals(randomTimerEXP.getDistributionName() + " rate: " + randomTimerEXP.getRate(), randomTimerEXP.toString());
        } catch (IncorrectDistributionException e) {
            e.printStackTrace();
        }

        // Create a RandomTimer of a poisson law
        try {
            double param = 1.0;
            RandomTimer randomTimerPOISSON = new RandomTimer(RandomDistribution.POISSON, param);

            // We need to get a mean of 1.0 (as this is a poisson law)
            assertEquals(randomTimerPOISSON.getDistributionName() + " mean: " + randomTimerPOISSON.getMean(), randomTimerPOISSON.toString());
        } catch (IncorrectDistributionException e) {
            e.printStackTrace();
        }

        // Create a RandomTimer of a gaussian law
        try {
            double limitInferior = -1.0;
            double limitSuperior = 1.0;
            RandomTimer randomTimerGAUSSIAN = new RandomTimer(RandomDistribution.GAUSSIAN, limitInferior,limitSuperior);

            // We need to get an inferior limit and a superior limit (as we got a gaussian law)
            assertEquals(randomTimerGAUSSIAN.getDistributionName() + " Inferior limit: " + randomTimerGAUSSIAN.getLimitInferior() + " Superior limit: " + randomTimerGAUSSIAN.getLimitSuperior(), randomTimerGAUSSIAN.toString());
        } catch (IncorrectDistributionException e) {
            e.printStackTrace();
        }

        // Create a RandomTimer of a uniform law
        try {
            double limitInferior = -1.0;
            double limitSuperior = 1.0;
            RandomTimer randomTimerUNIFORM = new RandomTimer(RandomDistribution.UNIFORM, limitInferior,limitSuperior);

            // We need to get an inferior limit and a superior limit (as we got a uniform law)
            assertEquals(randomTimerUNIFORM.getDistributionName() + " Inferior limit: " + randomTimerUNIFORM.getLimitInferior() + " Superior limit: " + randomTimerUNIFORM.getLimitSuperior(), randomTimerUNIFORM.toString());
        } catch (IncorrectDistributionException e) {
            e.printStackTrace();
        }

    }
}