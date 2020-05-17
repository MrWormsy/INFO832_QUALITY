package action;

import org.junit.jupiter.api.Test;
import timer.OneShotTimer;
import timer.Timer;
import static org.junit.jupiter.api.Assertions.*;
import java.util.TreeSet;

class TestObject {
   public TestObject(){

   }

   void methodTest(){
       System.out.println("coucou");
   }

}


class DiscreteActionDependentTest {

    @Test
    void addDependence() {
        Object object = new TestObject();
        OneShotTimer oneShotTimer = new OneShotTimer(5);
        DiscreteActionDependent discreteActionDependent = new DiscreteActionDependent(object, "methodTest", oneShotTimer);
        discreteActionDependent.addDependence(object,"methodTest",oneShotTimer);

        DiscreteAction objectWanted = new DiscreteAction(object,"methodTest" ,oneShotTimer);
        assertSame(objectWanted ,discreteActionDependent.depedentActions.first());

    }

    @Test
    void nextMethod() {
        Object object = new TestObject();
        OneShotTimer oneShotTimer = new OneShotTimer(5);
        DiscreteActionDependent discreteActionDependent = new DiscreteActionDependent(object, "methodTest2", oneShotTimer);
        discreteActionDependent.addDependence(object,"methodTest",oneShotTimer);
        discreteActionDependent.nextMethod();
        assertTrue(discreteActionDependent.currentAction == discreteActionDependent.depedentActions.last());

    }


    @Test
    void updateTimeLaps() {
        Object object = new TestObject();
        OneShotTimer oneShotTimer = new OneShotTimer(5);
        DiscreteActionDependent discreteActionDependent = new DiscreteActionDependent(object, "methodTest", oneShotTimer);
        discreteActionDependent.addDependence(object,"methodTest",oneShotTimer);
        discreteActionDependent.updateTimeLaps();
        assertTrue( discreteActionDependent.currentAction == discreteActionDependent.depedentActions.last());
    }


    @Test
    void isEmpty() {
        Object object = new TestObject();
        OneShotTimer oneShotTimer = new OneShotTimer(5);
        DiscreteActionDependent discreteActionDependent = new DiscreteActionDependent(object, "baseMethodName", oneShotTimer);
        discreteActionDependent.addDependence(object,"methodTest",oneShotTimer);
        assertTrue(discreteActionDependent.depedentActions.isEmpty() == true);
    }


    @org.junit.jupiter.api.Test
    void spendTime() {
        Object object = new TestObject();
        OneShotTimer oneShotTimerON = new OneShotTimer(1);
        DiscreteActionDependent discreteActionDependent = new DiscreteActionDependent(object,"methodtest",oneShotTimerON);
        discreteActionDependent.spendTime(5);

    }


    @org.junit.jupiter.api.Test
    void compareTo() {
        // coverage
        Object object = new TestObject();
        OneShotTimer oneShotTimerON = new OneShotTimer(1);
        DiscreteActionDependent discreteActionDependent = new DiscreteActionDependent(object,"methodtest",oneShotTimerON);
        discreteActionDependent.compareTo(discreteActionDependent);
        // compare to tested in DiscreteAction


    }

    @org.junit.jupiter.api.Test
    void next() {
        // coverage
        Object object = new TestObject();
        OneShotTimer oneShotTimerON = new OneShotTimer(1);
        DiscreteActionDependent discreteActionDependent = new DiscreteActionDependent(object,"methodtest",oneShotTimerON);
        discreteActionDependent.next();
        // compare to tested in DiscreteAction

    }

    @org.junit.jupiter.api.Test
    void hasNext() {
        // coverage
        Object object = new TestObject();
        OneShotTimer oneShotTimerON = new OneShotTimer(1);
        DiscreteActionDependent discreteActionDependent = new DiscreteActionDependent(object,"methodtest",oneShotTimerON);
        discreteActionDependent.hasNext();
        // compare to tested in DiscreteAction

    }




}