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

public class Factory {
    public static void main(String args[]) {

        BoundedBuffer A_B = new BoundedBuffer();
        BoundedBuffer B_C = new BoundedBuffer();
        BoundedBuffer C_D = new BoundedBuffer();

        FactoryWorker A = new FactoryWorker("A", null, A_B);
        FactoryWorker B = new FactoryWorker("B", A_B, B_C);
        FactoryWorker C = new FactoryWorker("C", B_C, C_D);
        FactoryWorker D = new FactoryWorker("D", C_D, null);

        A.start();
        B.start();
        C.start();
        D.start();

        System.out.println("finished startup");
    }
}
