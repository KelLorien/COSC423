package Paging;

/**
 * User: jpipe
 * Date: 4/1/13
 */
public class Page {

    private int id;
    private int lastUsed = 0;
    private double frequency = 1.0;

    public Page(int id) {
        this.id = id;
    }

    public Page(int pageId, int round) {
        this.id = pageId;
        this.lastUsed = round;
    }

    public void accessed() {
        frequency += 1.0;
    }

    public void resetFrequency() {
        frequency = 1.0;
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
