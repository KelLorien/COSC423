package Paging;

/**
 * User: jpipe
 * Date: 4/1/13
 */
public class Page {

    private int id;
    private double frequency = 1.0;

    public Page(int id) {
        this.id = id;
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

}
