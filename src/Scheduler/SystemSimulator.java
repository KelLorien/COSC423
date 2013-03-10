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
        while (jobsExist() || currentJob != null) {
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
                    //If current job NOT null, then a job has been started.
                    //If it is null, then there was no job in the queue, and the OS is idle. The next interrupt that the
                    //OS receives should be from the submittor in this case, signalling that there is a new job to start.
                    startNext = currentJob == null;
                    if (startNext && jobsExist()) {
                        scheduler.idle(relativeTime());
                    }
                }
            }
        }

        scheduler.printGantt(relativeTime());
    }

    private boolean jobsExist() {
        return jobsStillToSubmit || scheduler.hasJobs();
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
