package timer;

import java.util.Random;

/**
 * @author Flavien Vernier
 */

enum RandomDistribution {
    POISSON, EXP, POSIBILIST, GAUSSIAN;
}

public class RandomTimer implements Timer {

    private Random random;
    private RandomDistribution distribution;
    private double rate;
    private double mean;
    private double limitInferior;
    private double limitSuperior;
    /**
     * @param param constraint
     * @throws IncorrectDistributionException
     */
    public RandomTimer(RandomDistribution distribution, double param) throws IncorrectDistributionException {
        if (distribution == RandomDistribution.EXP) {
            this.distribution = distribution;
            this.rate = param;
            this.mean = 1 / param;
            this.limitInferior = 0;
            this.limitSuperior = Double.POSITIVE_INFINITY;
            this.random = new Random();
        } else if (distribution == RandomDistribution.POISSON) {
            this.distribution = distribution;
            this.rate = param;
            this.mean = param;
            this.limitInferior = 0;
            this.limitSuperior = Double.POSITIVE_INFINITY;
            this.random = new Random();
        } else {
            throw new IncorrectDistributionException("Bad Timer constructor for selected distribution");
        }
    }

    /**
     * @param limitInferior and limitSuperior constraint
     * @throws IncorrectDistributionException
     */
    public RandomTimer(RandomDistribution distribution, int limitInferior, int limitSuperior) throws IncorrectDistributionException {
        if (distribution == RandomDistribution.POSIBILIST || distribution == RandomDistribution.GAUSSIAN) {
            this.distribution = distribution;
            this.mean = (double) limitInferior + ((double) limitSuperior - limitInferior) / 2;
            this.rate = Double.NaN;
            this.limitInferior = limitInferior;
            this.limitSuperior = limitSuperior;
            this.random = new Random();
        } else {
            throw new IncorrectDistributionException("Bad Timer constructor for selected distribution");
        }
    }



    public static RandomDistribution getDistributionFromString(String distributionName) {
        return RandomDistribution.valueOf(distributionName.toUpperCase());
    }

    public static String getDistributionName(RandomDistribution distribution) {
        return distribution.name();
    }

    public String getDistribution() {
        return this.distribution.name();
    }

    public String getDistributionParam() {
        if (distribution == RandomDistribution.EXP) {
            return "rate: " + this.rate;
        } else if (distribution == RandomDistribution.POISSON) {
            return "mean: " + this.mean;
        } else if (distribution == RandomDistribution.POSIBILIST || distribution == RandomDistribution.GAUSSIAN) {
            return "lolim: " + this.limitInferior + " hilim: " + this.limitSuperior;
        }

        return "null";
    }

    public double getMean() {
        return this.mean;
    }

    public String toString() {
        String s = this.getDistribution();
        switch (this.distribution) {
            case POSIBILIST:
                s += " LoLim:" + this.limitInferior + " HiLim:" + this.limitSuperior;
                break;
            case POISSON:
                s += " mean:" + this.mean;
                break;
            case EXP:
                s += " rate:" + this.rate;
                break;
            case GAUSSIAN:
                s += " LoLim:" + this.limitInferior + " HiLim:" + this.limitSuperior;
                break;
        }

        return s;
    }

    /* (non-Javadoc)
     * @see methodInvocator.Timer#next()
     */
    @Override
    public Integer next() {
        switch (this.distribution) {
            case POSIBILIST:
                return this.nextTimePosibilist();
            case POISSON:
                return this.nextTimePoisson();
            case EXP:
                return this.nextTimeExp();
            case GAUSSIAN:
                return this.nextTimeGaussian();
        }
        return -1; // Theoretically impossible !!!
    }

    /**
     * Give good mean
     * Give wrong variance
     */
    private int nextTimePosibilist() {
        return (int) (this.limitInferior + (this.random.nextDouble() * (this.limitSuperior - this.limitInferior)));
    }

    /**
     * Give good mean
     * Give wrong variance
     */
    private int nextTimeExp() {
        return (int) (-Math.log(1.0 - this.random.nextDouble()) / this.rate);
    }

    /**
     * Give good mean
     * Give good variance
     */
    private int nextTimePoisson() {

        double l = Math.exp(-this.mean);
        int k = 0;
        double p = 1.0;
        do {
            p = p * this.random.nextDouble();
            k++;
        } while (p > l);
        return k - 1;
    }

    private int nextTimeGaussian() {
        return (int) this.limitInferior + (int) ((this.random.nextGaussian() + 1.0) / 2.0 * (this.limitSuperior - this.limitInferior));
    }

    @Override
    public boolean hasNext() {
        return true;
    }
}

class IncorrectDistributionException extends Exception {
    public IncorrectDistributionException(String errorMessage) {
        super(errorMessage);
    }
}
