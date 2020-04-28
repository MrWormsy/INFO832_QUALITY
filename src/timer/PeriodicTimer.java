package timer;

public class PeriodicTimer implements Timer {

    // ===== Variables =====

    private int period;
    private int next;
    private RandomTimer moreOrLess = null;

    // ===== Constructor =====

    public PeriodicTimer(int at) {
        this.period = at;
        this.next = at;
    }

    // ===== Methods =====

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
