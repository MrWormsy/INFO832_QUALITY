package timer;

/**
 * PeriodicTimer will be used to TODO
 *
 * PeriodicTimer implements {@link Timer}
 * @author Antonin ROSA-MARTIN
 *
 */

public class PeriodicTimer implements Timer {



    /**
     * Integer used for the period of the {@link PeriodicTimer}
     */
    private int period;

    /**
     * Integer used as the next return value of the {@link PeriodicTimer}
     */
    private int next;

    /**
     * {@link RandomTimer} used to TODO
     */
    private RandomTimer moreOrLess = null;



    public PeriodicTimer(int at) {
        this.period = at;
        this.next = at;
    }



    /**
     * @param at
     * @param moreOrLess use MergedTimer instead
     * @deprecated (when, why, refactoring advice...)
     */
    @Deprecated
    public PeriodicTimer(int at, RandomTimer moreOrLess) {
        this.period = at;
        this.moreOrLess = moreOrLess;
        this.next = at + (int) (this.moreOrLess.next() - this.moreOrLess.getMean());
    }

    public PeriodicTimer(int period, int at) {
        this.period = period;
        this.next = at;
    }

    /**
     * @param period
     * @param at
     * @param moreOrLess use MergedTimer instead
     * @deprecated (when, why, refactoring advice...)
     */
    @Deprecated
    public PeriodicTimer(int period, int at, RandomTimer moreOrLess) {
        this.period = period;
        this.moreOrLess = moreOrLess;
        this.next = at + (int) (this.moreOrLess.next() - this.moreOrLess.getMean());
    }

    public int getPeriod() {
        return this.period;
    }


    @Override
    public Integer next() {

        int previousNext = this.next;

        if (this.moreOrLess != null) {
            this.next = this.period + (int) (this.moreOrLess.next() - this.moreOrLess.getMean());
        } else {
            this.next = this.period;
        }

        return previousNext;
    }

    @Override
    public boolean hasNext() {
        return true;
    }

}
