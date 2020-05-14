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
        discreteAction.setLapsTime(5);
        discreteAction.spendTime(2);
        Integer integer =3;
        assertEquals(integer,discreteAction.getCurrentLapsTime());
    }


    @Test
    void compareTo() {
        Object object = new Object();
        OneShotTimer oneShotTimer = new OneShotTimer(5);
        DiscreteAction discreteAction = new DiscreteAction(object,"test",oneShotTimer);
        DiscreteAction discreteAction2 = new DiscreteAction(object,"test",oneShotTimer);
        // the same
        discreteAction.setLapsTime(0);
        discreteAction2.setLapsTime(0);
        assertEquals(0,discreteAction.compareTo(discreteAction2));

        // lasptime < lasptime2
        discreteAction2.setLapsTime(1);
        assertEquals(-1,discreteAction.compareTo(discreteAction2));

        //lasptime > lasptime2
        assertEquals(1,discreteAction2.compareTo(discreteAction));
    }


    @Test
    void next() {
        Object object = new Object();
        OneShotTimer oneShotTimer = new OneShotTimer(5);
        DiscreteAction discreteAction = new DiscreteAction(object,"test",oneShotTimer);
        DiscreteAction discreteAction2 = new DiscreteAction(object,"test",oneShotTimer);
        discreteAction2.setLapsTime(5);
        assertEquals(discreteAction.next().getCurrentLapsTime(),discreteAction2.getCurrentLapsTime());

    }

    @Test
    void hasNext() {
        Object object = new Object();
        OneShotTimer oneShotTimer = new OneShotTimer(1);
        DiscreteAction discreteAction = new DiscreteAction(object,"test",oneShotTimer);
        assertEquals(discreteAction.hasNext(),true);
    }
}