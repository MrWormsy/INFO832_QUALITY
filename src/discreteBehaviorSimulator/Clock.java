package discreteBehaviorSimulator;


import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Singleton class representing to a Clock for measuring time.
 * It is able to measure either real or virtual time.
 */
public class Clock {
	private static Clock instance = null;   // static instance of Clock singleton
	
	private int time;						// current time of the clock
	private int nextJump;					// next planned time jump
	private ReentrantReadWriteLock lock;	// lock allowing both reader and writer to reacquire read or write locks
	private boolean virtual;				// tells if the clock is virtual or not
	
	
	private Set<ClockObserver> observers;	// container of uniques observers
	
	/*
	 * Clock constructor
	 */
	private Clock() {
		this.time = 0;
		this.nextJump = 0;
		this.lock = new ReentrantReadWriteLock();
		this.virtual = true;
		this.observers = new HashSet<ClockObserver>();
	}
	
	/**
	 * <p>Get the instance of the {@link Clock} singleton</p>
	 * @return instance of {@link Clock}
	 */
	public static Clock getInstance() {
		if (Clock.instance == null) {
			Clock.instance = new Clock();
		}
		return Clock.instance;
	}
	
	/**
	 * <p>Add a new observer to the set</p>
	 * @param o new observer to add
	 */
	public void addObserver(ClockObserver o) {
		this.observers.add(o);
	}

	/**
	 * <p>Remove an observer from the set</p>
	 * @param o observer to remove
	 */
	public void removeObserver(ClockObserver o) {
		this.observers.remove(o);
	}
	
	/**
	 * <p>Set {@link virtual Clock#virtual} attribute</p>
	 * @param virtual
	 */
	public void setVirtual(boolean virtual) {
		this.virtual = virtual;
	}

	/**
	 * <p>Get {@link virtual Clock#virtual} attribute</p>
	 * @return true if the clock is virtual, else false
	 */
	public boolean isVirtual() {
		return this.virtual;
	}
	
	/**
	 * <p>Set the {@link nextJump Clock#nextJump} attribute</p>
	 */
	public void setNextJump(int nextJump) {
		this.nextJump = nextJump;
		for(ClockObserver o:this.observers) {
			o.nextClockChange(this.nextJump);
		}
	}

	/**
	 * <p>Increase time on clock and notice this change to an observer</p>
	 * @param time increase value
	 * @throws Exception if provided time value is not equal to the next jump
	 */
	public void increase(int time) throws Exception {

		this.lockWriteAccess();

		if(time != this.nextJump) {
			throw new Exception("Unexpected time change");
		}
		this.time += time;
		for(ClockObserver o:this.observers) {
			o.clockChange(this.time);
		}
		this.unlockWriteAccess();
	}

	/**
	 * <p>Get the current {@link time Clock#time} on the clock</p>
	 * @return current time on the clock as ticks if its virtual, else the current real time in timestamp
	 */
	public long getTime() {
		if(this.virtual) {
			return this.time;
		}else {
			return new Date().getTime();
		}
	}
	
	/**
	 * <p>Lock readlock for read operations</p>
	 */
	public void lockReadAccess() {
		this.lock.readLock().lock();
	}
	
	/**
	 * <p>Unlock readlock for read operations</p>
	 */
	public void unlockReadAccess() {
		this.lock.readLock().unlock();
	}
	
	/**
	 * <p>Lock writeLock for write operations</p>
	 */
	public void lockWriteAccess() {
		this.lock.writeLock().lock();
	}

	/**
	 * <p>Unlock writeLock for write operation</p>
	 */
	public void unlockWriteAccess() {
		this.lock.writeLock().unlock();		
	}
	
	/**
	 * <p>Convert current {@link time Clock#time} into String</p>
	 */
	public String toString() {
		return String.valueOf(this.time);
	}
}
