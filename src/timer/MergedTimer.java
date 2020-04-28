package timer;

/**
 * MergedTimer will be used to TODO
 *
 * MergedTimer implements {@link Timer}
 * @author Antonin ROSA-MARTIN
 *
 */

public class MergedTimer implements Timer {

    // ===== Variables =====

    /**
     * First timer used to create a merged timer
     */
    private Timer timer1;

    /**
     * Second timer used to create a merged timer
     */
    private Timer timer2;

    // ===== Constructor =====

    public MergedTimer(Timer timer1, Timer timer2) {
        this.timer1 = timer1;
        this.timer2 = timer2;
    }

    // ===== Methods =====

    @Override
    public boolean hasNext() {
        return (this.timer1.hasNext() && this.timer2.hasNext());
    }

    @Override
    public Integer next() {
        if (this.hasNext()) {
            return this.timer1.next() + this.timer2.next();
        }
        return null;
    }
}
