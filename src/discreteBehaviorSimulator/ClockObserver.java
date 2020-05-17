package discreteBehaviorSimulator;

/**
 * <p>Interface for observer of {@link Clock Clock.class}</p>
 */
public interface ClockObserver {

	/**
	 * <p>Notify a change of time in clock</p>
	 * @param time new value of time
	 */
	public void clockChange(int time);

	/**
	 * <p>Notify a time jump in clock</p>
	 * @param nextJump next planned jump in clock
	 */
	public void nextClockChange(int nextJump);
}
