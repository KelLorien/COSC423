package threads; /**
 * Consumer.java
 *
 * This is the consumer thread for the bounded buffer problem.
 *
 * @author Greg Gagne, Peter Galvin, Avi Silberschatz
 * @version 1.0 - July 15, 1999
 * Copyright 2000 by Greg Gagne, Peter Galvin, Avi Silberschatz
 * Applied Operating Systems Concepts - John Wiley and Sons, Inc.
 */

import java.util.*;

public class Consumer extends Thread
{
    public Consumer(BoundedBuffer b)
    {
        buffer = b;
    }

    public void run()
    {
        Date message;

        while (true)
        {
            BoundedBuffer.napping();

            // consume an item from the buffer
            System.out.println("Consumer wants to consume.");

            message = (Date)buffer.remove();
        }
    }

    private  BoundedBuffer buffer;
}


