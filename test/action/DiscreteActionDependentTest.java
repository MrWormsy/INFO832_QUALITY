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



}