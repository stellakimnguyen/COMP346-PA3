import java.util.ArrayList;

/**
 * Class Monitor
 * To synchronize dining philosophers.
 *
 * @author Serguei A. Mokhov, mokhov@cs.concordia.ca
 */
public class Monitor
{
	/*
	 * ------------
	 * Data members
	 * ------------
	 */
	boolean [] philosopherIsEating;
	boolean [] chopsticks;
	boolean isTurnToTalk;
	
	/**
	 * Constructor
	 */
	public Monitor(int piNumberOfPhilosophers)
	{
		// TODO: set appropriate number of chopsticks based on the # of philosophers
		// set array of chopsticks boolean to number of participating philosophers on the table
		chopsticks = new boolean[piNumberOfPhilosophers];
		philosopherIsEating = new boolean[piNumberOfPhilosophers];
		isTurnToTalk = true;

		for(int i = 0; i < chopsticks.length; i++) {
			chopsticks[i] = true; // all chopsticks are on the table
		}
	}

	/*
	 * -------------------------------
	 * User-defined monitor procedures
	 * -------------------------------
	 */

	/**
	 * Grants request (returns) to eat when both chopsticks/forks are available.
	 * Else forces the philosopher to wait()
	 */

	// TASK 2
	public synchronized void pickUp(final int piTID) {

		// while chopsticks on the left and right are taken
		while (!(chopsticks[(piTID-1)%(chopsticks.length)] && chopsticks[(piTID)%(chopsticks.length)])) {
			try {
				this.wait(); // wait until notified
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		chopsticks[(piTID)%(chopsticks.length)] = false; // right chopstick are no longer on the table
		chopsticks[(piTID-1)%(chopsticks.length)] = false; // left chopstick are no longer on the table
	}

	/**
	 * When a given philosopher's done eating, they put the chopstiks/forks down
	 * and let others know they are available.
	 */

	// TASK 2
	public synchronized void putDown(final int piTID)
	{
		chopsticks[(piTID-1)%(chopsticks.length)] = chopsticks[(piTID)%(chopsticks.length)] = true; // chopsticks back on the table
		
		this.notifyAll();
	}

	/**
	 * Only one philopher at a time is allowed to philosophy
	 * (while she is not eating).
	 */

	// TASK 2
	public synchronized void requestTalk() 
	{
		while (!isTurnToTalk) {
			try {
				this.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		isTurnToTalk = true;
	}

	/**
	 * When one philosopher is done talking stuff, others
	 * can feel free to start talking.
	 */

	// TASK 2
	public synchronized void endTalk()
	{
		this.notifyAll();
	}
}

// EOF
