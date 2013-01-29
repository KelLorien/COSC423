package threads;

/**
 * Factory.java
 *
 * This creates the buffer and the producer and consumer threads.
 *
 * @author Greg Gagne, Peter Galvin, Avi Silberschatz
 * @version 1.0 - July 15, 1999
 * @version 2.0 -- Feb. 8, 2010
 * Copyright 2000 by Greg Gagne, Peter Galvin, Avi Silberschatz
 * Applied Operating Systems Concepts - John Wiley and Sons, Inc.
 */

public class Factory
{
	public static void main(String args[]) {
		BoundedBuffer server = new BoundedBuffer();

      		// now create the producer and consumer threads
      		Producer producerThread = new Producer(server);
      		Consumer consumerThread = new Consumer(server);
      
      		producerThread.start();
      		consumerThread.start();               
	}
}
