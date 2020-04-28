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

    /**
     * Used to get the lapsed time between the set of time
     */
    private ArrayList<Integer> lapsTimes;

    /**
     * The iterator of the DateTimer
     */
    private Iterator<Integer> iterator;

    /**
     * <p>{@link DateTimer constructor with a Set<Integer> dates}</p>
     *
     * @param dates The set of dates you want to use to compute the time laps
     */
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

    /**
     * <p>{@link DateTimer constructor with a List<Integer> lapsTimes}</p>
     *
     * @param lapsTimes The list of times you want to wait
     */
    public DateTimer(List<Integer> lapsTimes) {
        this.lapsTimes = new ArrayList<>(lapsTimes);
        this.iterator = this.lapsTimes.iterator();
    }


    /**
     * <p>Check if the the iterator has a next value to return</p>
     */
    @Override
    public boolean hasNext() {
        return iterator.hasNext();
    }

    /**
     * <p>Return the next value of the iterator</p>
     */
    @Override
    public Integer next() {
        return iterator.next();
    }

}
