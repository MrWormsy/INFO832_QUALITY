package timer;

import java.util.Iterator;

/**
 * Timer will be used as an interface for different Timer kinds
 *
 * @author Antonin ROSA-MARTIN
 *
 */
public interface Timer extends Iterator<Integer> {

    /*
     * Return the delay time of the timer
     * @see java.util.Iterator#next()
     */
    @Override
    public Integer next();
}
