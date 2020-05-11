package timer;

/**
 * OneShotTimer will be used to TODO
 *
 * OneShotTimer implements {@link Timer}
 * @author Antonin ROSA-MARTIN
 *
 */

public class OneShotTimer implements Timer {

    /**
     * Integer used to get the time when the {@link OneShotTimer} has been initialized
     */
    private Integer at;

    /**
     * boolean used for the has next method (to be used only once)
     */
    private boolean hasNext;

    /**
     * <p>Create a timer which can be used only once</p>
     *
     * @param at The time of the Timer
     */
    public OneShotTimer(int at) {
        this.at = at;
        this.hasNext = true;
    }

    /**
     * <p>Returns true only if the {@link OneShotTimer} has never been used</p>
     *
     * @return true if the this is the first time we are using this Timer, false otherwise
     */
    @Override
    public boolean hasNext() {
        return this.hasNext;
    }

    /**
     * <p>Return the time of the Timer</p>
     *
     * @return The value of the timer it is called for the first time (null otherwise)
     */
    @Override
    public Integer next() {
        Integer next = this.at;
        this.at = null;
        this.hasNext = false;
        return next;
    }

    /**
     * @return the value of the next value (without triggering next)
     */
    public Integer getAt() {
        return at;
    }

    /**
     * Set the at value of the OneShotTimer
     */
    public void setAt(Integer at) {
        this.at = at;
    }

    public boolean isHasNext() {
        return hasNext;
    }

    public void setHasNext(boolean hasNext) {
        this.hasNext = hasNext;
    }
}
