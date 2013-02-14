package factory;

/**
 * Initializes all buffers and starts the factory workers.
 *
 * Josh Pipe
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
