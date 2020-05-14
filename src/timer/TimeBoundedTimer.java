package timer;

/**
 * TimeBoundedTimer will be used to create a Timer that can only be used between a certain amount of time (the startTime and the stopTime).
 * The first returned next value is only between the start time and the stop time, and the other time it is the next value of the Timer used to create this TimeBoundedTimer
 *
 * TimeBoundedTimer implements {@link Timer}
 * @author Antonin ROSA-MARTIN
 *
 */
public class TimeBoundedTimer implements Timer {

    /**
     * The timer to bound to this {@link TimeBoundedTimer}
     */
    private Timer timerToBound;

    /**
     * Start time of the {@link TimeBoundedTimer}
     */
    private Integer startTime;

    /**
     * Stop time of the {@link TimeBoundedTimer}
     */
    private Integer stopTime;

    /**
     * Next Integer of the {@link TimeBoundedTimer}
     */
    private Integer next = 0;

    /**
     * Current time of the {@link TimeBoundedTimer}
     */
    private int time = 0;

    /**
     * boolean to know if the {@link TimeBoundedTimer} has a next value
     */
    private boolean hasNext;

    /**
     * <p>Bounds an existing timer with a start and stop time, where the first next value is between the Stop and Start time</p>
     *
     * @param timerToBound The Timer to bound
     * @param startTime The start time of the {@link TimeBoundedTimer}
     * @param stopTime The stop time of the {@link TimeBoundedTimer}
     */
    public TimeBoundedTimer(Timer timerToBound, int startTime, int stopTime) {
        this.timerToBound = timerToBound;
        this.startTime = startTime;
        this.stopTime = stopTime;
        this.init();
    }

    /**
     * <p>Bounds an existing timer with a start time and the stop time will be infinite, where the first next value is between the Stop and Start time</p>
     *
     * @param timerToBound The Timer to bound
     * @param startTime The start time of the {@link TimeBoundedTimer}
     */
    public TimeBoundedTimer(Timer timerToBound, int startTime) {
        this.timerToBound = timerToBound;
        this.startTime = startTime;
        this.stopTime = Integer.MAX_VALUE;
        this.init();
    }

    /**
     * <p>Init the {@link TimeBoundedTimer} to know the first next value of the Timer as well as if it will have a next value</p>
     */
    private void init() {

        // We need to check that if the timerToBound of this Timer has a null return value, otherwise we throw a NullPointerException
        Integer nextValue = this.timerToBound.next();
        if (nextValue == null) {

            // To safely throw this exception we need to set hasNext() to false and the next value to null
            this.hasNext = false;
            this.next = null;

            throw new NullPointerException("The timerToBound does not have a return value");
        }

        this.next = nextValue;

        while (this.next < this.startTime) {
            nextValue = this.timerToBound.next();

            // If the next value is null we need to throw a NullPointerException as we can not use this next value
            if (nextValue == null) {

                // To safely throw this exception we need to set hasNext() to false and the next value to null
                this.hasNext = false;
                this.next = null;

                throw new NullPointerException("The timerToBound does not have a return value");
            }

            this.next += nextValue;
        }

        // If the next value is less than the stop time we can return a next value, otherwise hasNext() will be false AND the next value must be null
        if (this.next < this.stopTime) {
            this.hasNext = true;
        } else {
            this.hasNext = false;
            this.next = null;
        }
    }

    /**
     * <p>Know if the Timer has a next value to return/p>
     *
     * @return true if the Timer has a next value to return, false otherwise
     */
    @Override
    public boolean hasNext() {
        return this.hasNext;
    }

    /**
     * <p>Returns the next value of the Timer and update the future next value</p>
     *
     * @return the next value of the Timer
     */
    @Override
    public Integer next() {
        Integer previousNext = this.next;
        this.time += this.next;

        if (this.time < this.stopTime) {
            this.next = this.timerToBound.next();
        } else {
            // Here this is not previousNext but this.next otherwise there will be a NullPointerException and the null value if for the next method call
            this.next = null;
            this.hasNext = false;
        }
        return previousNext;
    }
}
