import common.BaseThread;
import java.util.Random;

/**
 * Class Philosopher.
 * Outlines main subrutines of our virtual philosopher.
 *
 * @author Serguei A. Mokhov, mokhov@cs.concordia.ca
 */
public class Philosopher extends common.BaseThread
{
	/**
	 * Max time an action can take (in milliseconds)
	 */
	public static final long TIME_TO_WASTE = 1000;
	
	/**
	 * The act of eating.
	 * - Print the fact that a given phil (their TID) has started eating.
	 * - yield
	 * - Then sleep() for a random interval.
	 * - yield
	 * - The print that they are done eating.
	 */

	// TASK 1
	public void eat()
	{
		try
		{
			System.out.println("Philosopher ID: " + getTID() + " started eating");
			yield();
			sleep((long)(Math.random() * TIME_TO_WASTE));
			yield();
			System.out.println("Philosopher ID: " + getTID() + " finished eating");
		}
		catch(InterruptedException e)
		{
			System.err.println("Philosopher.eat(): ");
			DiningPhilosophers.reportException(e);
			System.exit(1);
		}
	}

	/**
	 * The act of thinking.
	 * - Print the fact that a given phil (their TID) has started thinking.
	 * - yield
	 * - Then sleep() for a random interval.
	 * - yield
	 * - The print that they are done thinking.
	 */

	// TASK 1
	public void think()
	{
		try
		{
			System.out.println("Philosopher ID: " + getTID() + " started thinking");
			yield();
			sleep((long)(Math.random() * TIME_TO_WASTE));
			yield();
			System.out.println("Philosopher ID: " + getTID() + " finished thinking");
		}
		catch (InterruptedException e) {
			System.err.println("Philosopher.think(): ");
			DiningPhilosophers.reportException(e);
			System.exit(1);
		}
	}

	/**
	 * The act of talking.
	 * - Print the fact that a given phil (their TID) has started talking.
	 * - yield
	 * - Say something brilliant at random
	 * - yield
	 * - The print that they are done talking.
	 */

	// TASK 1
	public void talk()
	{
		try {
			System.out.println("Philosopher ID: " + getTID() + " started talking");
			yield();
			saySomething(); // active action instead of constant state
			yield();
			System.out.println("Philosopher ID: " + getTID() + " finished talking");
		}
		catch (Exception e) { // no InterruptedException as there is no sleep in try-block
			System.err.println("Philosopher.talk(): ");
			DiningPhilosophers.reportException(e);
			System.exit(1);
		}
	}

	/**
	 * No, this is not the act of running, just the overridden Thread.run()
	 */

	// TASK 1
	public void run() {
		for (int i = 0; i < DiningPhilosophers.DINING_STEPS; i++) {
			try {
				DiningPhilosophers.soMonitor.pickUp(getTID());

				eat();

				DiningPhilosophers.soMonitor.putDown(getTID());

				think();

				/*
				 * TODO:
				 * A decision is made at random whether this particular
				 * philosopher is about to say something terribly useful.
				 */

				Random randomBool = new Random();

				// Get next random true or false (1/2 chances to talk)
				if (randomBool.nextBoolean()) {
					DiningPhilosophers.soMonitor.requestTalk();
					talk();
					DiningPhilosophers.soMonitor.endTalk();
				}

				yield();
			} catch (Exception e) {
				System.err.println("Philosopher.run(): ");
				DiningPhilosophers.reportException(e);
				System.exit(1);
			}
		}
	} // run()

	/**
	 * Prints out a phrase from the array of phrases at random.
	 * Feel free to add your own phrases.
	 */
	public void saySomething()
	{
		String[] astrPhrases =
		{
			"Eh, it's not easy to be a philosopher: eat, think, talk, eat...",
			"You know, true is false and false is true if you think of it",
			"2 + 2 = 5 for extremely large values of 2...",
			"If thee cannot speak, thee must be silent",
			"My number is " + getTID() + ""
		};

		System.out.println
		(
			"Philosopher " + getTID() + " says: " +
			astrPhrases[(int)(Math.random() * astrPhrases.length)]
		);
	}
}

// EOF
