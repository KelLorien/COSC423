package factory;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * User: jpipe
 * Date: 1/29/13
 */
public class Item {

    private static int ITEM_COUNT = 0;

    private int itemNum;
    private ArrayList<String> handledBy = new ArrayList<String>();


    public Item() {
        itemNum = ++ITEM_COUNT;
    }

    public void handle(String h) {
        handledBy.add(h);
    }

    @Override
    public String toString() {
        return "Item " + itemNum + "<" + Arrays.toString(handledBy.toArray()) + ">";
    }
}
