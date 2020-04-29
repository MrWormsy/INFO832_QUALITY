package timer;

/**
 * TimeBoundedTimer will be used to TODO
 *
 * TimeBoundedTimer implements {@link Timer}
 * @author Antonin ROSA-MARTIN
 *
 */

public class TimeBoundedTimer implements Timer {



    // Humm I dont understand here TODO
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
     * <p>Bounds an existing timer with a start and stop time</p>
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
     * <p>Bounds an existing timer with a start time and the stop time will be infinite</p>
     *
     * @param timerToBound The Timer to bound
     * @param startTime The start time of the {@link TimeBoundedTimer}
     */
    // TODO Hum there might be a problem here no ? As the stop will be infinite
    public TimeBoundedTimer(Timer timerToBound, int startTime) {
        this.timerToBound = timerToBound;
        this.startTime = startTime;
        this.stopTime = Integer.MAX_VALUE;
        this.init();
    }

    /**
     * <p>Init the {@link TimeBoundedTimer} to know the next value as well as if it will have a next value</p>
     */
    // Here there will be some nasty null pointer exceptions...
    private void init() {
        this.next = this.timerToBound.next();

        // TODO ISSUE HERE
        // This will be infinite if the stop time is infinite
        while (this.next < this.startTime) {
            this.next += this.timerToBound.next();
        }

        if (this.next < this.stopTime) {
            this.hasNext = true;
        } else {
            this.hasNext = false;
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
            previousNext = null;
            this.hasNext = false;
        }
        return previousNext;
    }

}
