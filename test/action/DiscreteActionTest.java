package action;

import org.junit.jupiter.api.Test;
import timer.OneShotTimer;

import static org.junit.jupiter.api.Assertions.*;

class DiscreteActionTest {

    @Test
    void spendTime() {
        Object object = new Object();
        OneShotTimer oneShotTimer = new OneShotTimer(5);
        DiscreteAction discreteAction = new DiscreteAction(object,"test",oneShotTimer);
        discreteAction.spendTime(0);
        assertEquals(0,discreteAction.getCurrentLapsTime());
    }


    @Test
    void compareTo() {
        Object object = new Object();
        OneShotTimer oneShotTimer = new OneShotTimer(5);
        DiscreteAction discreteAction = new DiscreteAction(object,"test",oneShotTimer);
        DiscreteAction discreteAction2 = new DiscreteAction(object,"test",oneShotTimer);
        // the same
        assertEquals(0,discreteAction.compareTo(discreteAction2));
        // lasptime >
        discreteAction.spendTime(2);
        assertEquals(1,discreteAction.compareTo(discreteAction2));
        //lasptime <
        assertEquals(-1,discreteAction2.compareTo(discreteAction));
    }

    @Test
    void testToString() {
    }

    @Test
    void next() {

    }

    @Test
    void hasNext() {
    }
}