package timer;

import java.util.*;

/**
 * DateTimer will be used to TODO
 *
 * DateTimer implements {@link Timer}
 * @author Antonin ROSA-MARTIN
 *
 */

public class DateTimer implements Timer {

	// ===== Variables =====

    /**
     * Used to get the lapsed time between the set of time
     */
    private ArrayList<Integer> lapsTimes;

    /**
     * The iterator of the DateTimer
     */
    private Iterator<Integer> iterator;

	// ===== Constructors =====

    public DateTimer(Set<Integer> dates) {
        this.lapsTimes = new ArrayList<>();
        Integer last;
        Integer current = 0;

        for (Integer date : dates) {
            last = current;
            current = date;
            this.lapsTimes.add(current - last);
        }
        this.iterator = this.lapsTimes.iterator();
    }

    public DateTimer(List<Integer> lapsTimes) {
        this.lapsTimes = new ArrayList<>(lapsTimes);
        this.iterator = this.lapsTimes.iterator();
    }

    // ===== Methods =====

    @Override
    public boolean hasNext() {
        return iterator.hasNext();
    }

    @Override
    public Integer next() {
        return iterator.next();
    }

}
