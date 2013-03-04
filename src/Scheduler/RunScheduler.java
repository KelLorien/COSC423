package Scheduler;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * User: jpipe
 * Date: 2/21/13
 */
public class RunScheduler {

    public static PrintStream OUTPUT;

    public static void main(String[] args) {
        if (args.length > 0) {
            if (args[0].startsWith("-d")) {
                OUTPUT = System.out;
            }
        } else {
            try {
                OUTPUT = new PrintStream("sampleOutput.txt");
            } catch (FileNotFoundException e) {
                System.out.println("Could not set output to default output file");
                e.printStackTrace();
                OUTPUT = System.out;
            }
        }

        Thread thisThread = Thread.currentThread();
        thisThread.setPriority(Thread.MAX_PRIORITY);
        SystemSimulator os = new SystemSimulator();

        ArrayList<String> jobs = new ArrayList<String>();

        BufferedReader input = new BufferedReader(new InputStreamReader(getInputStream()));

        String line;
        try {
            while ((line = input.readLine()) != null) {
                line = line.trim();
                if (line.length() > 0) {
                    jobs.add(line);
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading input file.");
            e.printStackTrace();
            return;
        }

        Submitter sub = new Submitter(os, jobs);
        os.setPriority(8);
        os.start(); // Thread executing this instruction has higher priority, so will continue to hold cpu
        sub.setPriority(7); // Should interrupt os only when os is sleeping, but can always interrupt running Jobs
        sub.start();
        // As the driver exits, os has the highest priority, so should get the cpu....
    }

    private static InputStream getInputStream() {
        InputStream stream = null;

        try {
            stream = new FileInputStream("scheduleInput.txt");
        } catch (FileNotFoundException e) {
            while (stream == null) {
                stream = manualInputStream();
            }
        }

        return stream;
    }

    private static InputStream manualInputStream() {
        System.out.println("File not Found. Specify a new file to use for program input: ");
        Scanner scanner = new Scanner(System.in);
        String name = scanner.nextLine();

        if (!name.endsWith(".txt")) {
            name += ".txt";
        }

        try {
            return new FileInputStream(name);
        } catch (FileNotFoundException e) {
            return null;
        }
    }
}