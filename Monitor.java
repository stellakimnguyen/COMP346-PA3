package assignment3;
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
	boolean [] philEat;
	boolean [] cs;
	boolean talkTurn;
	
	/**
	 * Constructor
	 */
	public Monitor(int piNumberOfPhilosophers)
	{
		// TODO: set appropriate number of chopsticks based on the # of philosophers
		cs = new boolean[piNumberOfPhilosophers];
		philEat = new boolean[piNumberOfPhilosophers];
		talkTurn = true;

		for(int i = 0; i < cs.length; i++)
			cs[i] = true;
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
	public synchronized void pickUp(final int piTID) {

		while (!(cs[(piTID-1)%(cs.length)] && cs[(piTID)%(cs.length)])) {
			try {
				this.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		cs[(piTID)%(cs.length)] = false;
		cs[(piTID-1)%(cs.length)] = false;
	}

	/**
	 * When a given philosopher's done eating, they put the chopsticks/forks down
	 * and let others know they are available.
	 */
	public synchronized void putDown(final int piTID)
	{
		cs[(piTID-1)%(cs.length)] = cs[(piTID)%(cs.length)] = true;
		
		this.notifyAll();
	}

	/**
	 * Only one philosopher at a time is allowed to philosophise
	 * (while she is not eating).
	 * @throws InterruptedException 
	 */
	public synchronized void requestTalk() 
	{
		while (!talkTurn) {
			try {
				this.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		talkTurn = true;
	}

	/**
	 * When one philosopher is done talking stuff, others
	 * can feel free to start talking.
	 */
	public synchronized void endTalk()
	{
		talkTurn = true;
		this.notifyAll();
	}
}

// EOF
