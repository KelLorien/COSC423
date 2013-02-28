package Scheduler.jobs;

import Scheduler.Scheduler;
import Scheduler.jobs.Job;

/**
 * User: jpipe
 * Date: 2/21/13
 */
public class JobTimer extends Thread {

    private Job job;
    private long time;

    public JobTimer(Job j, long ms) {
        this.setName(j.getName() + " Timer");
        this.job = j;
        this.time = ms;
    }

    public void run() {
        try {
            sleep(time);
        } catch (InterruptedException e) {
            System.err.println("Timer for " + job.getName() + " interrupted.");
            e.printStackTrace();
        }
        job.pleaseStop();
    }

}
