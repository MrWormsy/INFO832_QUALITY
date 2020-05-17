package timer;

import java.util.Random;

/**
 * RandomTimer is used to return a random value according to a distribution law
 *
 * RandomTimer implements {@link Timer}
 * @author Antonin ROSA-MARTIN
 *
 */
public class RandomTimer implements Timer {

    /**
     * The random object used by the timer
     */
    private Random random;

    /**
     * The RandomDistribution enum type of the timer
     */
    private RandomDistribution distribution;

    /**
     * The rate of the distribution law
     */
    private double rate;

    /**
     * The mean of the distribution law
     */
    private double mean;

    /**
     * The inferior limit of the distribution law
     */
    private double limitInferior;

    /**
     * The superior limit of the distribution law
     */
    private double limitSuperior;

    /**
     * <p>Creates a Random Timer from a distribution law enum type (either EXP or POISSON) and the parameter of this law</p>
     *
     * @param distribution The distribution law as an enum
     * @param param The parameter of the distribution law
     * @throws IncorrectDistributionException if the distribution law is not a EXP nor a POISSON law
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
     * <p>Creates a Random Timer from a distribution law enum type (either GAUSSIAN or UNIFORM) and the inferior and superior limits of the law</p>
     *
     * @param distribution The distribution law as an enum
     * @param limitInferior The inferior limit of the distribution law
     * @param limitSuperior The superior limit of the distribution law
     * @throws IncorrectDistributionException if the distribution law is not a GAUSSIAN nor a UNIFORM law or if the inferior limit is greater than the superior one
     */
    public RandomTimer(RandomDistribution distribution, double limitInferior, double limitSuperior) throws IncorrectDistributionException {

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

    /**
     * <p>Get the name of the Timer's distribution</p>
     *
     * @return The name of the Timer's distribution
     */
    public String getDistributionName() {
        return this.distribution.name();
    }

    /**
     * <p>Get the parameters of the Timer's distribution law as a string</p>
     *
     * @return The parameters of the Timer's distribution law
     */
    public String getDistributionParams() {
        if (this.distribution == RandomDistribution.EXP) {
            return "rate: " + this.rate;
        } else if (this.distribution == RandomDistribution.POISSON) {
            return "mean: " + this.mean;
        } else if (this.distribution == RandomDistribution.UNIFORM || this.distribution == RandomDistribution.GAUSSIAN) {
            return "Inferior limit: " + this.limitInferior + " Superior limit: " + this.limitSuperior;
        }

        // As the constructors do not let us create an other RandomTimer with an other RandomDistribution that the 4 ones, we won't return null
        return null;
    }

    /**
     * <p>Get the mean of the Timer's distribution law</p>
     *
     * @return The mean of the Timer's distribution law
     */
    public double getMean() {
        return this.mean;
    }

    /** TODO
     * Give good mean
     * Give wrong variance
     */
    private int nextTimeUniform() {
        return (int) (this.random.nextInt((int) ((this.limitSuperior - this.limitInferior) + 1)) + this.limitInferior);
    }

    /** TODO
     * Give good mean
     * Give wrong variance
     */
    private int nextTimeExp() {
        return (int) (-Math.log(1.0 - this.random.nextDouble()) / this.rate);
    }

    /** TODO
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

    /**
     * <p>Get the next Gaussian value of Timer</p>
     *
     * @return the next Gaussian value of the Timer
     */
    private int nextTimeGaussian() {
        return (int) this.limitInferior + (int) ((this.random.nextGaussian() + 1.0) / 2.0 * (this.limitSuperior - this.limitInferior));
    }

    /**
     * <p>Know if the Timer has a next value</p>
     *
     * @return always true
     */
    @Override
    public boolean hasNext() {
        return true;
    }

    /**
     * <p>Get the next value of the Timer depending of the distribution law</p>
     *
     * @return the next value of the Timer
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

        // As the constructors do not let us create an other RandomTimer with an other RandomDistribution that the 4 ones we won't have this case happening
        return -1;
    }

    /**
     * <p>Return the Timer as a String</p>
     *
     * @return The formatted String of the Timer
     */
    @Override
    public String toString() {
        String stringToReturn = this.getDistributionName();
        switch (this.distribution) {
            case UNIFORM:
                stringToReturn += " Inferior limit: " + this.limitInferior + " Superior limit: " + this.limitSuperior;
                break;
            case POISSON:
                stringToReturn += " mean: " + this.mean;
                break;
            case EXP:
                stringToReturn += " rate: " + this.rate;
                break;
            case GAUSSIAN:
                stringToReturn += " Inferior limit: " + this.limitInferior + " Superior limit: " + this.limitSuperior;
                break;
        }

        return stringToReturn;
    }

    public double getRate() {
        return rate;
    }

    public double getLimitInferior() {
        return limitInferior;
    }

    public double getLimitSuperior() {
        return limitSuperior;
    }
}

/**
 * IncorrectDistributionException is an exception thrown when the RandomDistribution is incorrect.
 *
 * @author Antonin ROSA-MARTIN
 *
 */
class IncorrectDistributionException extends Exception {

    /**
     * <p>Create a IncorrectDistributionException with a custom message</p>
     *
     * @param errorMessage The custom message we want to fire
     */
    public IncorrectDistributionException(String errorMessage) {
        super(errorMessage);
    }
}


/**
 * RandomDistribution will be used to determine the kind of distribution model we will be using.
 *
 * @author Antonin ROSA-MARTIN
 *
 */
enum RandomDistribution {
    POISSON, EXP, UNIFORM, GAUSSIAN;
}