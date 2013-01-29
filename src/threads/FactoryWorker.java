package threads;

/**
 * User: jpipe
 * Date: 1/29/13
 */
public class FactoryWorker extends Thread {
    public static final int NAP_TIME = 7;
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
            nap();

            Item item = get();
            item.handle(name);
            send(item);

            productionCount++;
            if (productionCount >= 24) {
                break;
            }
        }
    }

    private void send(Item item) {
        to.enter(item);
        System.out.println(name + " handled " + item);
    }

    private Item get() {
        Item item = from.remove();
        System.out.println(name + " removed " + item);
        return item;
    }

    private void nap() {
        int sleepTime = (int) (NAP_TIME * Math.random() );
        try {
            Thread.sleep(sleepTime*1000);
        } catch(InterruptedException e) {
            //ignore
        }
    }
}
