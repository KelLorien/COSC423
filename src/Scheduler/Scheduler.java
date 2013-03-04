package Scheduler;

import Scheduler.jobs.Job;

import java.util.ArrayList;
import static Scheduler.RunScheduler.OUTPUT;

/**
 * User: jpipe
 * Date: 2/20/13
 */
public class Scheduler {

    private ArrayList<Job> readyQ = new ArrayList<Job>();
    private long lastStart = 0;

    private ArrayList<String> gantt = new ArrayList<String>();

    public Job makeRun(long currentTime) {
        if (hasJobs()) {
            Job next = readyQ.remove(0);
            next.setPriority(6);
            gantt.add(currentTime + "   \t" + (currentTime - lastStart) + "   \t" + next.getName());
            OUTPUT.println("Starting " + next.getName() + " at " + currentTime);
            lastStart = currentTime;
            next.start();
            return next;
        }
        OUTPUT.println("No jobs remaining");
        return null;
    }

    public void add(Job j, long currentTime) {
        OUTPUT.println("Adding " + j.getName() + " to the queue at " + currentTime);
        readyQ.add(j);

    }

    public boolean hasJobs() {
        return readyQ.size() > 0;
    }

    public void printGantt(Long currentTime) {
        OUTPUT.println("GANTT CHART\n" +
                "TIME\tDELTA\tNAME\n" +
                "------------------------------------------");
        for (String s: gantt) {
            OUTPUT.println(s);
        }

        OUTPUT.println(currentTime + "   \t" + (currentTime - lastStart) + "   \tDone");
    }
}
