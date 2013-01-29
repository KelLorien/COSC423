package threads;

/**
 * BoundedBuffer.java
 *
 * This program implements the bounded buffer using Java synchronization.
 *
 * @author Greg Gagne, Peter Galvin, Avi Silberschatz
 * @version 1.0 - July 15, 1999
 * Copyright 2000 by Greg Gagne, Peter Galvin, Avi Silberschatz
 * Applied Operating Systems Concepts - John Wiley and Sons, Inc.
 */

public class BoundedBuffer {
    private static final int BUFFER_SIZE = 5;

    private int count;
    private int in;
    private int out;
    private Item[] buffer;

    public BoundedBuffer() {
        count = 0;
        in = 0;
        out = 0;

        buffer = new Item[BUFFER_SIZE];
    }

    public synchronized void enter(Item item) {
        while (count == BUFFER_SIZE) {
            waitIgnore();
        }

        // add an item to the buffer
        ++count;
        buffer[in] = item;
        in = (in + 1) % BUFFER_SIZE;

        if (count == BUFFER_SIZE)
            System.out.println("Producer Entered " + item + " Buffer FULL");
        else
            System.out.println("Producer Entered " + item + " Buffer Size = " +  count);

        notify();
    }

    public synchronized Item remove() {
        Item item;

        while (count == 0) {
            waitIgnore();
        }

        // remove an item from the buffer
        --count;
        item = buffer[out];
        out = (out + 1) % BUFFER_SIZE;

        if (count == 0)
            System.out.println("Consumer Consumed " + item + " Buffer EMPTY");
        else
            System.out.println("Consumer Consumed " + item + " Buffer Size = " + count);

        notify();

        return item;
    }

    private void waitIgnore() {
        try {
            wait();
        } catch (InterruptedException e) {
            // ignore
        }
    }
}
