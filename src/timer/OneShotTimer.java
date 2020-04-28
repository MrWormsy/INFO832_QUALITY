package timer;

/**
 * OneShotTimer will be used to TODO
 *
 * OneShotTimer implements {@link Timer}
 * @author Antonin ROSA-MARTIN
 *
 */

public class OneShotTimer implements Timer {

    // ===== Variables =====

    /**
     * Integer used to get the time when the {@link OneShotTimer} has been initialized
     */
    private Integer at;

    /**
     * boolean used for the has next method (to be used only once)
     */
    private boolean hasNext;

    // ===== Constructor =====

    public OneShotTimer(int at) {
        this.at = at;
        this.hasNext = true;
    }

    // ===== Methods =====

    @Override
    public boolean hasNext() {
        return this.hasNext;
    }

    @Override
    public Integer next() {
        Integer next = this.at;
        this.at = null;
        this.hasNext = false;
        return next;
    }

}
