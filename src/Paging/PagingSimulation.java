package Paging;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * User: jpipe
 * Date: 4/1/13
 */
public class PagingSimulation {


    public static void main(String[] args) {
        Pager pager = new OptimalPager(3, 1,  2  ,3,  4, 3  ,4  ,2  ,3  ,5  ,6  ,4  ,2  ,1  ,2);
        pager.execute();
        pager.printTable();
        System.out.println("Faults: " + pager.faults);
    }

}
