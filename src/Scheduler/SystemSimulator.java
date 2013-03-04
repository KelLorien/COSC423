package Scheduler;

import Scheduler.jobs.Job;

import static Scheduler.RunScheduler.OUTPUT;

/**
 * User: jpipe
 * Date: 2/20/13
 */
public class SystemSimulator extends Thread {

    private static final long QUANTUM = 1000L;

    private Scheduler scheduler;

    private boolean jobsStillToSubmit = true;
    private long startTime;
    private Job currentJob = null;
    private boolean startNext = true;

    public SystemSimulator() {
        this.scheduler = new Scheduler();
        this.setName("System Simulator");
    }

    public void run() {
        startTime = System.currentTimeMillis();
        while (jobsStillToSubmit || scheduler.hasJobs() || currentJob != null) {
            try {
                //If the OS was interrupted while not sleep, try to start the next job.
                //This is just to be safe, it could be that this can't occur.
                if (interrupted()) {
                    throw new InterruptedException();
                }
                sleep(QUANTUM);
            } catch (InterruptedException e) {
                if (startNext) {
                    log(currentJob, " terminated at " + relativeTime());
                    currentJob = scheduler.makeRun(relativeTime());
                    //if current job NOT null, then a job has been started. If it is null, the OS should try to execute
                    //the next job when it next gets interrupted. In that case, the next interrupt should be from the
                    //job submitter, signalling that it has submitted the next job.
                    startNext = currentJob == null;
                } else {
                    OUTPUT.println("Interrupted by Submitter");
                }
            }
        }

        scheduler.printGantt(relativeTime());
    }

    public void noMoreJobsToSubmit() {
        OUTPUT.println("No more jobs");
        jobsStillToSubmit = false;
    }

    public void addNewProcess(Job j) {
        scheduler.add(j, relativeTime());
        interrupt();
    }

    public void exit() {
        startNext = true;
        interrupt();
    }

    public long relativeTime() {
        return System.currentTimeMillis() - startTime;
    }

    private void log(Job j, String s) {
        if (j != null) {
            OUTPUT.println(j.getName() + s);
        }
    }
}
