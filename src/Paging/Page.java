package Paging;

/**
 * User: jpipe
 * Date: 4/1/13
 */
public class Page {

    private int id;
    private int lastUsed = 0;
    private int accesses = 1;

    public Page(int id) {
        this.id = id;
    }

    public Page(int pageId, int round) {
        this.id = pageId;
        this.lastUsed = round;
    }

    public void accessed() {
        accesses += 1;
    }

    public void resetAccesses() {
        accesses = 0;
    }

    public double getAccesses() {
        return accesses;
    }

    public int getId() {
        return id;
    }

    public int getLastUsed() {
        return lastUsed;
    }

    public void setLastUsed(int lastUsed) {
        this.lastUsed = lastUsed;
    }
}
