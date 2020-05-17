package discreteBehaviorSimulator;

import java.lang.reflect.Method;
import java.util.Collections;
import java.util.Vector;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

import action.DiscreteAction;
import action.DiscreteActionInterface;


/**
 * <p>This class allows to manage discrete action in the simulation.</p>
 * @author Tiphaine Bulou (2016)
 * @author Flavien Vernier
 * @see Runnable
 */
public class DiscreteActionSimulator implements Runnable {

	private Thread t;						// simulation thread
	private boolean running = false;		// tells if the simulation is running or not
	
	private Clock globalTime;				// clock for measuring time in simulation
	
	private Vector<DiscreteActionInterface> actionsList = new Vector<>();	// list of actions in the simulation
	
	private int nbLoop;						// number of loop in the simulation
	private int step;						// number of the current step
	
	private Logger logger;					// manage log functions
	private FileHandler logFile; 			// manage log writing
	private ConsoleHandler logConsole;		// manage log printing


	/**
	 * <p>write logs about the simulation</p>
	 */
	public DiscreteActionSimulator() {
		
		// Start logger
		this.logger = Logger.getLogger("DAS");
		this.logger.setLevel(Level.ALL);
		this.logger.setUseParentHandlers(true);
		try{
			this.logFile = new FileHandler(this.getClass().getName() + ".log");
			this.logFile.setFormatter(new LogFormatter());
			this.logConsole = new ConsoleHandler();
		} catch(Exception e) {
			e.printStackTrace();
		}
		this.logger.addHandler(logFile);
		this.logger.addHandler(logConsole);

		this.globalTime = Clock.getInstance();
		
		this.t = new Thread(this);
		this.t.setName("Discrete Action Simulator");
	}
	

	/**
	 * <p>Set the number of loop to perform in the simulation</p>
	 * @param nbLoop number of loop in the simulation. Simulation is infinite if this para is negative or 0.
	 */
	public void setNbLoop(int nbLoop) {
		if(nbLoop>0) {
			this.nbLoop = nbLoop;
			this.step = 1;
		}else{ // running infinitely
			this.nbLoop = 0;
			this.step = -1;
		}
	}


	/**
	 * <p>Add a new action to the simulation</p>
	 * @param c action to add
	 */
	public void addAction(DiscreteActionInterface c) {
		if(c.hasNext()) {
			this.actionsList.add(c.next());			// add to list of actions, next is call to the action exist at the first time
			Collections.sort(this.actionsList);		// sort the list for ordered execution 
		}
	}


	/**
	 * <p>Returns the laps of time before the next action</p>
	 * @return laps of time before the next action
	 */
	private int nextLapsTime() {
		DiscreteActionInterface currentAction = this.actionsList.get(0);
		return currentAction.getCurrentLapsTime();
	}


	/**
	 * Returns laps time of the running action
	 * @return laps of time of the current running action
	 */
	private int runAction() {
		// Run the first action
		int sleepTime = 0;

		// TODO Manage parallel execution !  
		DiscreteActionInterface currentAction = this.actionsList.get(0);
		Object o = currentAction.getObject();
		Method m = currentAction.getMethod();
		sleepTime = currentAction.getCurrentLapsTime();
		
		try {
			Thread.yield();
			if(this.globalTime!=null) {
				this.globalTime.increase(sleepTime);
			}
			m.invoke(o);
			if(this.globalTime!=null) {
				this.logger.log(Level.FINE, "[DAS] run action " + m.getName() + " on " + o.getClass().getName() + ":" + o.hashCode() + " at " + this.globalTime.getTime() + " after " + sleepTime + " time units\n");
				System.out.println("[DAS] run action " + m.getName() + " on " + o.getClass().getName() + ":" + o.hashCode() + " at " + this.globalTime.getTime() + " after " + sleepTime + " time units\n");
			} else {
				this.logger.log(Level.FINE, "[DAS] run action " + m.getName() + " on " + o.getClass().getName() + ":" + o.hashCode() + " after " + sleepTime + " time units\n");
				System.out.println("[DAS] run action " + m.getName() + " on " + o.getClass().getName() + ":" + o.hashCode() + " after " + sleepTime + " time units\n");
			
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		return sleepTime;
	}


	/**
	 * <p>Update time laps of all stored actions and reset the first action</p>
	 * @param runningTimeOf1stCapsul
	 */
	private void updateTimes(int runningTimeOf1stCapsul) {
		
		// update time laps of all actions
		for(int i=1 ; i < this.actionsList.size(); i++) {
			this.actionsList.get(i).spendTime(runningTimeOf1stCapsul);
		}

		DiscreteActionInterface a = this.actionsList.remove(0);
		if(a.hasNext()) {
			a = a.next();
			this.actionsList.addElement(a);
			if(this.globalTime!=null) {
				this.logger.log(Level.FINE, "[DAS] reset action " + a.getMethod().getName() + " on " + a.getObject().getClass().getName() + ":" + a.getObject().hashCode() + " at " + this.globalTime.getTime() + " to " + a.getCurrentLapsTime() + " time units\n");
				System.out.println("[DAS] reset action " + a.getMethod().getName() + " on " + a.getObject().getClass().getName() + ":" + a.getObject().hashCode() + " at " + this.globalTime.getTime() + " to " + a.getCurrentLapsTime() + " time units\n");
			} else {
				this.logger.log(Level.FINE, "[DAS] reset action " + a.getMethod().getName() + " on " + a.getObject().getClass().getName() + ":" + a.getObject().hashCode() + " to " + a.getCurrentLapsTime() + " time units\n");
				System.out.println("[DAS] reset action " + a.getMethod().getName() + " on " + a.getObject().getClass().getName() + ":" + a.getObject().hashCode() + " to " + a.getCurrentLapsTime() + " time units\n");
			}
			Collections.sort(this.actionsList);
		}
	}


	/**
	 * <p>Launch the simulation</p>
	 */
	public void run() {
		int count = this.nbLoop;
		boolean finished = false;

		System.out.println("LANCEMENT DU THREAD " + t.getName() + " \n");

		while(running && !finished) {

			if(!this.actionsList.isEmpty()) {
				System.out.println(this);
				this.globalTime.setNextJump(this.nextLapsTime());
				
				this.globalTime.lockWriteAccess();
				int runningTime = this.runAction();
				this.updateTimes(runningTime);
				this.globalTime.unlockWriteAccess();
				try {
					Thread.sleep(100);
				} catch(Exception e) {
					e.printStackTrace();
				}
				//TODO add global time synchronizer for action with list of date and avoid drift 
			} else{
				System.out.println("NOTHING TO DO\n");
				this.stop();
			}

			count -= this.step;
			if(count<=0) {
				finished = true;
			}
		}
		this.running = false;
		if(this.step>0) {
			System.out.println("DAS: " + (this.nbLoop - count) + " actions done for " + this.nbLoop + " turns asked.");
		} else {
			System.out.println("DAS: " + (count) + " actions done!");			
		}
	}

	/**
	 * <p>Start the simulation thread</p>
	 */
	public void start() {
		try {
			this.running = true;
			this.t.start();
		} catch (IllegalThreadStateException e) {
			e.printStackTrace();
		}
	}

	/**
	 * <p>Stop the simulation thread</p>
	 */
	public void stop() {
		try {
			System.out.println("STOP THREAD " + t.getName() + "obj " + this);
			this.running = false;
		} catch (IllegalThreadStateException e) {
			e.printStackTrace();
		}
	}

	/**
	 * <p>Transform all stored action in a more human readable way</p>
	 * @return a {@link StringBuffer} containing the transformed action
	 */
	public String toString() {
		StringBuffer toS = new StringBuffer("------------------\nTestAuto :" + this.actionsList.size());
		for(DiscreteActionInterface c : this.actionsList) {
			toS.append(c.toString() + "\n");
		}
		toS.append("---------------------\n");
		return toS.toString();
	}

	/**
	 * <p>Tells if the simulation is running or not</p>
	 * @return true if the simulation is running, else false
	 */
	public boolean getRunning() {
		return this.running;
	}

}
