package factory;

/**
 * User: jpipe
 * Date: 1/29/13
 */
public class FactoryWorker extends Thread {
    public static final int WORK_TIME = 10;
    public static final int MAX_PRODUCTION = 24;

    private String name;
    private int productionCount = 0;
    private BoundedBuffer from;
    private BoundedBuffer to;

    public FactoryWorker(String name, BoundedBuffer from, BoundedBuffer to) {
        this.name = name;
        this.from = from;
        this.to = to;
    }

    @Override
    public void run() {
        while (true) {
            Item item = get();
            handle(item);
            send(item);

            productionCount++;
            if (productionCount >= MAX_PRODUCTION) {
                break;
            }
        }
        System.out.println(name + " is finished.");
    }

    private Item get() {
        Item item;
        if (from == null) {         //if 'from' is null, create a new object, else get one from 'from'
            item = new Item();
        } else {
            item = from.remove(this);
            System.out.println(name + " got " + item);
        }

        return item;
    }

    private void handle(Item item) {
        System.out.println(name + " is working on " + item);

        work();

        item.handle(name);
    }

    private void send(Item item) {
        if (to != null) {         //if 'to' is null, item is finished
            to.enter(item, this);
            System.out.println(name + " sent " + item);
        } else {
            System.out.println(item + " is finished.");
        }
    }

    private void work() {
        int sleepTime = (int) (WORK_TIME * Math.random() );
        try {
            Thread.sleep(sleepTime*1000);
        } catch(InterruptedException e) {
            //ignore
        }
    }

    @Override
    public String toString() {
        return name;
    }
}
