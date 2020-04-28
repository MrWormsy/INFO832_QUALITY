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
    private Timer timer2bound;

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



    public TimeBoundedTimer(Timer timer2bound, int startTime, int stopTime) {
        this.timer2bound = timer2bound;
        this.startTime = startTime;
        this.stopTime = stopTime;
        this.init();
    }

    public TimeBoundedTimer(Timer timer2bound, int startTime) {
        this.timer2bound = timer2bound;
        this.startTime = startTime;
        this.stopTime = Integer.MAX_VALUE;
        this.init();
    }



    private void init() {
        this.next = this.timer2bound.next();
        while (this.next < this.startTime) {
            this.next += this.timer2bound.next();
        }
        if (this.next < this.stopTime) {
            this.hasNext = true;
        } else {
            this.hasNext = false;
        }
    }

    @Override
    public boolean hasNext() {
        return this.hasNext;
    }

    @Override
    public Integer next() {
        Integer previousNext = this.next;
        this.time += this.next;
        if (this.time < this.stopTime) {
            this.next = this.timer2bound.next();
        } else {
            previousNext = null;
            this.hasNext = false;
        }
        return previousNext;
    }

}
