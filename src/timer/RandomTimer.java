package timer;

import java.util.Random;

/**
 * @author Flavien Vernier
 */

public class RandomTimer implements Timer {

    // ===== Variables =====

    private Random random;
    private RandomDistribution distribution;
    private double rate;
    private double mean;
    private double limitInferior;
    private double limitSuperior;

    // ===== Constructors =====

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

        // If the inferior limit is greater than the limit superior there is an error
        if (limitInferior > limitSuperior) {
            throw new IncorrectDistributionException("Inferior limit cannot be greater than the Superior limit");
        }

        if (distribution == RandomDistribution.GAUSSIAN || distribution == RandomDistribution.UNIFORM) {
            this.distribution = distribution;
            this.mean = (limitSuperior + limitInferior) / 2.0;
            this.rate = Double.NaN;
            this.limitInferior = limitInferior;
            this.limitSuperior = limitSuperior;
            this.random = new Random();
        } else {
            throw new IncorrectDistributionException("Bad Timer constructor for selected distribution");
        }
    }

    // ===== Methods ======

    public String getDistributionName() {
        return this.distribution.name();
    }

    public String getDistributionParams() {
        if (this.distribution == RandomDistribution.EXP) {
            return "rate: " + this.rate;
        } else if (this.distribution == RandomDistribution.POISSON) {
            return "mean: " + this.mean;
        } else if (this.distribution == RandomDistribution.UNIFORM || this.distribution == RandomDistribution.GAUSSIAN) {
            return "Inferior limit: " + this.limitInferior + " Superior limit: " + this.limitSuperior;
        }
        return "null";
    }

    public double getMean() {
        return this.mean;
    }

    /**
     * Give good mean
     * Give wrong variance
     */
    private int nextTimeUniform() {
        return (int) (this.random.nextInt((int) ((this.limitSuperior - this.limitInferior) + 1)) + this.limitInferior);
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

    /* (non-Javadoc)
     * @see methodInvocator.Timer#next()
     */
    @Override
    public Integer next() {
        switch (this.distribution) {
            case UNIFORM:
                return this.nextTimeUniform();
            case POISSON:
                return this.nextTimePoisson();
            case EXP:
                return this.nextTimeExp();
            case GAUSSIAN:
                return this.nextTimeGaussian();
        }
        return -1; // Theoretically impossible !!!
    }

    @Override
    public String toString() {
        String stringToReturn = this.getDistributionName();
        switch (this.distribution) {
            case UNIFORM:
                stringToReturn += " Inferior limit:" + this.limitInferior + " Superior limit:" + this.limitSuperior;
                break;
            case POISSON:
                stringToReturn += " mean:" + this.mean;
                break;
            case EXP:
                stringToReturn += " rate:" + this.rate;
                break;
            case GAUSSIAN:
                stringToReturn += " Inferior limit:" + this.limitInferior + " Superior limit:" + this.limitSuperior;
                break;
        }

        return stringToReturn;
    }
}

class IncorrectDistributionException extends Exception {
    public IncorrectDistributionException(String errorMessage) {
        super(errorMessage);
    }
}

enum RandomDistribution {
    POISSON, EXP, UNIFORM, GAUSSIAN;
}