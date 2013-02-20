package Scheduler;

import java.util.ArrayList;

/**
 * User: jpipe
 * Date: 2/20/13
 */
public class SystemSimulator extends Thread {

    private static final long QUANTUM = 100L;

    private Scheduler scheduler;

    private ArrayList<Job> readyQ = new ArrayList<Job>();
    private boolean jobsStillToSubmit = true;
    private long startTime;

    public SystemSimulator(Scheduler s) {
        this.scheduler = s;
    }

    public void run() {
        while (jobsStillToSubmit) {
            try {
                sleep(QUANTUM);
            } catch (InterruptedException e) {
                Job nextJob = getNextJob();
                if (nextJob != null) {
                    //TODO: run job
                }
            }
        }
        scheduler.printGannt();
    }

    public void addNewJob(Job j) {
        readyQ.add(j);
    }

    public void noMoreJobsToSubmit() {
        jobsStillToSubmit = false;
    }

    private Job getNextJob() {
        if (readyQ.size() > 0) {
            return readyQ.remove(0);
        }
        return null;
    }

    public void exit() {
        interrupt();
    }

    public long relativeTime() {
        return System.currentTimeMillis() - startTime;
    }
}
