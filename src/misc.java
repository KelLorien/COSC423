import java.util.ArrayList;
import java.util.Collections;

import static java.lang.Math.abs;

/**
 * User: jpipe
 * Date: 4/9/13
 * Time: 7:58 PM
 */
public class misc {
    public static void main(String[] args) {
        ArrayList<Integer> ints = new ArrayList<Integer>();

        Collections.addAll(ints, 2069, 1212, 2296, 2800, 544, 1618, 356, 1523, 4965, 3681);
//        Collections.addAll(ints, 2, 12, 10);

//        fcfs(2150, ints);
        sstf(0, ints);
    }

    static void fcfs(int start, ArrayList<Integer> ints) {
        System.out.println(start + " - " + ints.get(0) + " = " + abs(start - ints.get(0)));
        int sum = abs(abs(start - ints.get(0)));

        for (int i = 0; i < ints.size() - 1; i++) {
            System.out.println(ints.get(i) + " - " + ints.get(i + 1) + " = " + abs(ints.get(i) - ints.get(i + 1)));
            sum += abs(ints.get(i) - ints.get(i + 1));
        }

        System.out.println(sum);
    }

    static void sstf(int start, ArrayList<Integer> ints) {
        int sum = 0;

        int current = start;
        while (ints.size() > 0) {
            int next = ints.remove(sstfNext(current, ints));
            System.out.println(current + " -> " + next);
            sum += abs(current - next);
            current = next;
        }

        System.out.println(sum);

    }

    static int sstfNext(int currentVal, ArrayList<Integer> ints) {
        int minInd = 0;
        int min = Integer.MAX_VALUE;
        for (int i: ints) {
            if (abs(currentVal - i) < min) {
                min = abs(currentVal - i);
                minInd = ints.indexOf(i);
            }
        }

        return minInd;
    }
}
