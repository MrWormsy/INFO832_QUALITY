package action;

import java.lang.reflect.Method;
import java.util.logging.Level;
import java.util.logging.Logger;
import timer.Timer;

/**
 * @author Tiphaine Bulou (2016)
 * @author Flavien Vernier
 *
 */

// TODO must inherit from Action
public class DiscreteAction implements DiscreteActionInterface {
	private Logger logger;
	private Object object;
	private Method method;
	
	private Timer timer;				// timer provides new lapsTime
	//private TreeSet<Integer> dates;	// obsolete, managed in timer 
	//private Vector<Integer> lapsTimes;// obsolete, managed in timer
	private Integer lapsTime; 			// waiting time (null if never used)

	// Constructor
	private DiscreteAction() {

		// Start logger
			this.logger = Logger.getLogger("DAS");
			//this.logger = Logger.getLogger("APP");
			this.logger.setLevel(Level.ALL);
			this.logger.setUseParentHandlers(true);
			
			/*FileHandler logFile; 
			ConsoleHandler logConsole; 
			try{
				this.logFile = new FileHandler(this.getClass().getName() + ".log");
				//this.logFile.setFormatter(new SimpleFormatter());
				this.logFile.setFormatter(new LogFormatter());
				this.logConsole = new ConsoleHandler();
			}catch(Exception e){
				e.printStackTrace();
			}
			this.logger.addHandler(logFile);
			this.logger.addHandler(logConsole);*/
	}

	/**
	 * @param o
	 * @param m
	 * @param timmer
	 */
	public DiscreteAction(Object o, String m, Timer timmer){
		this();
		this.object = o;
		try{	
			this.method = o.getClass().getDeclaredMethod(m, new Class<?>[0]);
		}
		catch(Exception e){
			this.logger.log(Level.WARNING,"exception");
		}
		this.timer = timer;
		this.setLapsTime(0);
	}
	
	// ATTRIBUTION

	/**
	 * @param t
	 */
	public void spendTime(int t) {
		Integer old = this.lapsTime;
		if(this.lapsTime != null) {
			this.lapsTime -= t;
		}

		String string = String.format("[DA] operate spendTime on %s : %s : old time : %d new time : %d", this.getObject().getClass().getName(), this.getObject().hashCode(),old,this.getCurrentLapsTime());
		this.logger.log(Level.FINE, string);
	}

	// RECUPERATION

	public Method getMethod(){
		return method;
	}
	public Integer getCurrentLapsTime(){
		return lapsTime;
	}
	public Object getObject(){
		return object;
	}


	/**
	 * @param c
	 * @return int (-1 or 0 or  1)
	 */
	// COMPARAISON
	public int compareTo(DiscreteActionInterface c) {
		if (this.lapsTime == null) { // no lapstime is equivalent to infinity 
			return 1;
		}else if(c.getCurrentLapsTime() == null){
			return -1;
		}else if(this.lapsTime > c.getCurrentLapsTime()){
    		return 1;
    	}else if (this.lapsTime < c.getCurrentLapsTime()){
    		return -1;
    	}else{
			return 0;
		}
	}

	/**
	 * @return
	 */
	public String toString(){
		System.out.println(this.object);
		System.out.println(this.method);
		return "Object : " + this.object.getClass().getName() + "\n Method : " + this.method.getName()+"\n Stat. : "+ this.timer + "\n delay: " + this.lapsTime;

	}

	/**
	 * @return DiscreteActionInterface
	 */
	public DiscreteActionInterface next() {
		Integer old = this.lapsTime;
		this.lapsTime = this.timer.next();
		String string = String.format("[DA] operate spendTime on %s : %s : old time : %d new time : %d", this.getObject().getClass().getName(), this.getObject().hashCode(),old,this.getCurrentLapsTime());
		this.logger.log(Level.FINE, string);
		return this;
	}

	/**
	 * @return boolean if timer hasNext and timmer not null
	 */
	public boolean hasNext() {
			return (this.timer != null && this.timer.hasNext());
	}

	public void setLapsTime(Integer lapsTime) {
		this.lapsTime = lapsTime;
	}
}
