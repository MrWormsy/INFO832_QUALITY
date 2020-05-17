package discreteBehaviorSimulator;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


public class DiscreteActionSimulatorTest {

    @Test
    public void startTest() {
        DiscreteActionSimulator discrAct = new DiscreteActionSimulator();
        discrAct.start();

        // testing normal behavior
        assertTrue(discrAct.getRunning());

        // testing deviant behavior : double start
        discrAct.start();
        assertDoesNotThrow(() -> discrAct.start());
    }

    @Test
    public void stopTest() {
        DiscreteActionSimulator discrAct = new DiscreteActionSimulator();
        discrAct.start();
        discrAct.stop();

        // testing normal behavior
        assertFalse(discrAct.getRunning());

        // testing deviant behavior : double stop
        discrAct.start();
        discrAct.stop();
        assertDoesNotThrow(() -> discrAct.stop());
    }

    @Test
    public void toStringTest() {
        DiscreteActionSimulator discrAct = new DiscreteActionSimulator();
        assertTrue(discrAct.toString() instanceof String);
    }

}