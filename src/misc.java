import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import static java.lang.Math.abs;

/**
 * User: jpipe
 * Date: 4/9/13
 * Time: 7:58 PM
 */
public class misc {

    static int CYLINDERS = 5000;

    public static void main(String[] args) {
        ArrayList<Integer> ints = new ArrayList<Integer>();

        Collections.addAll(ints, 2069, 1212, 2296, 2800, 544, 1618, 356, 1523, 4965, 3681);
        int start = 2150;
        int prev = 1805;
//
//        Collections.addAll(ints, 2, 12, 10, 8);
//        CYLINDERS = 15;
//        int start = 9;
//        int prev = 6;

        int dir = (start - prev) >= 0 ? 1: -1;

        System.out.println("FSCS");
        fcfs(start, (ArrayList<Integer>) ints.clone());
        System.out.println("SSTF");
        sstf(start, (ArrayList<Integer>) ints.clone());
        System.out.println("Scan");
        scan(start, dir, (ArrayList<Integer>) ints.clone());
        System.out.println("C-scan");
        cscan(start, dir, (ArrayList<Integer>) ints.clone());
        System.out.println("Look");
        look(start, dir, (ArrayList<Integer>) ints.clone());
        System.out.println("C-look");
        clook(start, dir, (ArrayList<Integer>) ints.clone());
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

    static void scan(int start, int dir, ArrayList<Integer> ints) {
        System.out.println("starts moving in " + (dir < 0 ? "negative" : "positive") + " direction");

        Collections.sort(ints);
        boolean[] flags = new boolean[ints.size()];

        int current = start;
        int sum = 0;
        int pointer = 0;

        while (pointer < ints.size() && current > ints.get(pointer)) {
            pointer++;
        }

        if (dir < 0) {
            pointer--;
        }

        if (pointer == ints.size()) {
            System.out.println(current + " -> " + CYLINDERS + " = " + (CYLINDERS - current));
            sum += CYLINDERS - current;
            current = CYLINDERS;
            dir = -1;
            pointer = nextPointer(dir, ints, flags);
        } else if (pointer < 0) {
            System.out.println(current + " -> 0 = " + current);
            sum += current;
            current = 0;
            dir = 1;
            pointer = nextPointer(dir, ints, flags);
        }

        System.out.print(current + " -> ");
        sum += abs(current - ints.get(pointer));
        System.out.println(ints.get(pointer) + " = " + sum );
        current = ints.get(pointer);
        flags[pointer] = true;

        for (int count = ints.size(); count > 1; count--) {
            pointer += dir;

            if (pointer == ints.size()) {
                System.out.println(current + " -> " + CYLINDERS + " = " + (CYLINDERS - current));
                sum += CYLINDERS - current;
                current = CYLINDERS;
                dir = -1;
                pointer = nextPointer(dir, ints, flags);
            } else if (pointer < 0) {
                System.out.println(current + " -> 0 = " + current);
                sum += current;
                current = 0;
                dir = 1;
                pointer = nextPointer(dir, ints, flags);
            }

            sum += abs(current - ints.get(pointer));
            System.out.println(current + " -> " + ints.get(pointer) + " = " + abs(current - ints.get(pointer)));
            current = ints.get(pointer);
            flags[pointer] = true;
        }

        System.out.println(sum);
    }

    static int nextPointer(int dir, ArrayList<Integer> ints, boolean[] flags) {
        int pointer = dir > 0 ? 0: ints.size() - 1;

        while (flags[pointer]) {
            pointer += dir;
            if (pointer > flags.length || pointer < 0) {
                pointer = -1;
                break;
            }
        }

        return pointer;
    }

    static void cscan(int start, int dir, ArrayList<Integer> ints ) {
        System.out.println("starts moving in " + (dir < 0 ? "negative": "positive") + " direction");

        Collections.sort(ints);

        int current = start;
        int sum = 0;
        int pointer = 0;

        while (pointer < ints.size() && current > ints.get(pointer)) {
            pointer++;
        }

        if (dir < 0) {
            pointer--;
        }

        if (pointer == ints.size()) {
            System.out.println(current + "-> 0 = " + (CYLINDERS - current + CYLINDERS));
            sum += CYLINDERS - current + CYLINDERS;
            current = 0;
            pointer = 0;
        } else if (pointer < 0) {
            System.out.println(current + " -> " + CYLINDERS + " = " + (current + CYLINDERS));
            sum += current + CYLINDERS;
            current = CYLINDERS;
            pointer = ints.size() - 1;
        }

        System.out.print(current + " -> ");
        sum += abs(current - ints.get(pointer));
        System.out.println(ints.get(pointer) + " = " + sum );
        current = ints.get(pointer);

        for (int count = ints.size(); count > 1; count--) {
            pointer += dir;

            if (pointer == ints.size()) {
                System.out.println(current + " -> " + CYLINDERS + " -> 0 = " + (CYLINDERS - current + CYLINDERS));
                sum += CYLINDERS - current + CYLINDERS;
                current = 0;
                pointer = 0;
            } else if (pointer < 0) {
                System.out.println(current + " -> 0 -> " + CYLINDERS + " = " + (current + CYLINDERS));
                sum += current + CYLINDERS;
                current = CYLINDERS;
                pointer = ints.size() - 1;
            }

            sum += abs(current - ints.get(pointer));
            System.out.println(current + " -> " + ints.get(pointer) + " = " + abs(current - ints.get(pointer)));
            current = ints.get(pointer);
        }

        System.out.println(sum);

    }

    static void look(int start, int dir, ArrayList<Integer> ints) {
        System.out.println("starts moving in " + (dir < 0 ? "negative": "positive") + " direction");
        System.out.print(start + " -> ");

        Collections.sort(ints);
        boolean[] flags = new boolean[ints.size()];

        int current = start;
        int sum = 0;
        int pointer = 0;

        while (pointer < ints.size() && current > ints.get(pointer)) {
            pointer++;
        }

        if (dir < 0) {
            pointer--;
        }

        if (pointer == ints.size()) {
            dir = -1;
            pointer = nextPointer(dir, ints, flags);
        } else if (pointer < 0) {
            dir = 1;
            pointer = nextPointer(dir, ints, flags);
        }

        sum += abs(current - ints.get(pointer));
        System.out.println(ints.get(pointer) + " = " + sum );
        current = ints.get(pointer);
        flags[pointer] = true;

        for (int count = ints.size(); count > 1; count--) {
            pointer += dir;

            if (pointer == ints.size()) {
                dir = -1;
                pointer = nextPointer(dir, ints, flags);
            } else if (pointer < 0) {
                dir = 1;
                pointer = nextPointer(dir, ints, flags);
            }

            sum += abs(current - ints.get(pointer));
            System.out.println(current + " -> " + ints.get(pointer) + " = " + abs(current - ints.get(pointer)));
            current = ints.get(pointer);
            flags[pointer] = true;
        }

        System.out.println(sum);
    }

    static void clook(int start, int dir, ArrayList<Integer> ints ) {
        System.out.println("starts moving in " + (dir < 0 ? "negative": "positive") + " direction");

        Collections.sort(ints);

        int current = start;
        int sum = 0;
        int pointer = 0;

        while (pointer < ints.size() && current > ints.get(pointer)) {
            pointer++;
        }

        if (dir < 0) {
            pointer--;
        }

        if (pointer == ints.size()) {
            pointer = 0;
        } else if (pointer < 0) {
            pointer = ints.size() - 1;
        }

        System.out.print(current + " -> ");
        sum += abs(current - ints.get(pointer));
        System.out.println(ints.get(pointer) + " = " + sum );
        current = ints.get(pointer);

        for (int count = ints.size(); count > 1; count--) {
            pointer += dir;

            if (pointer == ints.size()) {
                pointer = 0;
            } else if (pointer < 0) {
                pointer = ints.size() - 1;
            }

            sum += abs(current - ints.get(pointer));
            System.out.println(current + " -> " + ints.get(pointer) + " = " + abs(current - ints.get(pointer)));
            current = ints.get(pointer);
        }

        System.out.println(sum);

    }
}
