package threads;

/**
 * User: jpipe
 * Date: 1/29/13
 */
public class Item {

    private static int ITEM_COUNT = 0;

    private int itemNum;
    private String handledBy;


    public Item(String starter) {
        itemNum = ++ITEM_COUNT;
        handledBy += starter;
    }

    public void handle(String h) {
        handledBy += ", " + h;
    }

    @Override
    public String toString() {
        return "Item " + itemNum + "<" + handledBy + ">";
    }
}
