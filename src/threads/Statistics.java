package threads;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

/**
 * User: jpipe
 * Date: 2/11/13
 * Time: 5:34 PM
 */
public class Statistics {

    public static ArrayList<Integer> numbers = new ArrayList<Integer>();

    public static double average;
    public static int minimum;
    public static int maximum;

    public static void main(String[] args) {

        //get command line args
        if (args.length >= 1) {
            for (String i: args) {
                try {
                    numbers.add(Integer.parseInt(i));
                } catch (NumberFormatException e) {
                    //ignore, go to next
                }
            }
        }

        while (numbers.size() < 1) {
            System.out.println("Enter some numbers separated by spaces: ");
            String line = new Scanner(System.in).nextLine();
            for (String s: line.split(" "))  {
                numbers.add(Integer.parseInt(s));
            }
        }

        Thread averageThread = new Thread(new Average());
        Thread minimumThread = new Thread(new Minimum());
        Thread maximumThread = new Thread(new Maximum());

        averageThread.start();
        minimumThread.start();
        maximumThread.start();

        try {
            averageThread.join();
            minimumThread.join();
            maximumThread.join();
        } catch (InterruptedException e) {
            System.err.println("Interrupted Exception!");
            e.printStackTrace();
        }

        System.out.println("Average: " + average + "\nMaximum: " + maximum + "\nMinimum: " + minimum);

    }

    public static class Average implements Runnable {
        public void run() {
            int sum = 0;
            for (int i:  numbers) {
                sum += i;
            }
            average = ((double) sum)/numbers.size();
        }
    }

    public static class Maximum implements Runnable {
        public void run() {
            maximum = Collections.max(numbers);
        }
    }

    public static class Minimum implements Runnable {
        public void run() {
            minimum = Collections.min(numbers);
        }
    }

}
