package factory;

/**
 *
 * This program implements the bounded buffer using Java synchronization.
 *
 * @author Greg Gagne, Peter Galvin, Avi Silberschatz
 * @version 1.0 - July 15, 1999
 * Copyright 2000 by Greg Gagne, Peter Galvin, Avi Silberschatz
 * Applied Operating Systems Concepts - John Wiley and Sons, Inc.
 *
 * Modified by Josh Pipe
 */

public class BoundedBuffer {
    private static final int BUFFER_SIZE = 3;

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

    public synchronized void enter(Item item, FactoryWorker requestor) {
        while (count == BUFFER_SIZE) {
            System.out.println("WARNING: " + requestor + " is waiting to place an item.");
            waitIgnore();
        }

        ++count;
        buffer[in] = item;
        in = (in + 1) % BUFFER_SIZE;

        notify();
    }

    public synchronized Item remove(FactoryWorker requestor) {
        Item item;

        while (count == 0) {
            System.out.println("WARNING: " + requestor + " is waiting to get an item.");
            waitIgnore();
        }

        --count;
        item = buffer[out];
        out = (out + 1) % BUFFER_SIZE;

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
