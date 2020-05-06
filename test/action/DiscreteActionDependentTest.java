package action;

import org.junit.jupiter.api.Test;
import timer.OneShotTimer;

import java.util.TreeSet;

class DiscreteActionDependentTest {

    @Test
    void addDependence() {
        Object test1 = new DiscreteAction();
        Object test2 = new Object();
        // must be equal
        // init
        TreeSet<DiscreteAction> actions = new TreeSet<DiscreteAction>();
        actions.add(new DiscreteAction(test2,"test2",new OneShotTimer(2)));
        // test function
        DiscreteActionDependent discreteActionDependent =new DiscreteActionDependent(
                test1,"test1",new OneShotTimer(0));
        discreteActionDependent.addDependence(test2,"test2",new OneShotTimer(2));
        System.out.println(actions);
        System.out.println(discreteActionDependent.dependentActions);


    }

    @Test
    void nextMethod() {
    }

    @Test
    void spendTime() {
    }

    @Test
    void updateTimeLaps() {
    }

    @Test
    void getMethod() {
    }

    @Test
    void getCurrentLapsTime() {
    }

    @Test
    void getObject() {
    }

    @Test
    void compareTo() {
    }

    @Test
    void isEmpty() {
    }

    @Test
    void next() {
    }

    @Test
    void hasNext() {
    }
}