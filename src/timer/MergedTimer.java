package timer;

/**
 * MergedTimer will be used to TODO
 *
 * MergedTimer implements {@link Timer}
 * @author Antonin ROSA-MARTIN
 *
 */

public class MergedTimer implements Timer {

    /**
     * First timer used to create a merged timer
     */
    private Timer timer1;

    /**
     * Second timer used to create a merged timer
     */
    private Timer timer2;

    /**
     * <p>Merge two given timers</p>
     *
     * @param timer1 The first timer to merge
     * @param timer2 The second timer to merge
     *
     * @throws NullPointerException if at least one of the timer is null
     */
    public MergedTimer(Timer timer1, Timer timer2) throws NullPointerException {

        // If at least one of the timer is null we throw a NullPointerException because we cannot merge a timer and a null object
        if (timer1 == null || timer2 == null) {
            throw new NullPointerException("A timer cannot be null");
        }

        this.timer1 = timer1;
        this.timer2 = timer2;
    }

    /**
     * <p>Returns true if the two {@link Timer} used to create the {@link MergedTimer} have both a next value to return</p>
     *
     * @return true if the {@link MergedTimer} has a next value to return, false otherwise
     */
    @Override
    public boolean hasNext() {
        return (this.timer1.hasNext() && this.timer2.hasNext());
    }

    /**
     * <p>Return the sum of the two {@link Timer} used to create the {@link MergedTimer}</p>
     *
     * @return the next Integer of the {@link MergedTimer}
     */
    @Override
    public Integer next() {
        if (this.hasNext()) {
            return this.timer1.next() + this.timer2.next();
        }
        return null;
    }

    /**
     * @return the the first Timer
     */
    public Timer getTimer1() {
        return timer1;
    }

    /**
     * <p>Set the first Timer</p>
     */
    public void setTimer1(Timer timer1) {
        this.timer1 = timer1;
    }

    /**
     * @return the second Timer
     */
    public Timer getTimer2() {
        return timer2;
    }

    /**
     * <p>Set the second Timer</p>
     */
    public void setTimer2(Timer timer2) {
        this.timer2 = timer2;
    }
}
