package Scheduler.jobs;

import Scheduler.SystemSimulator;

import java.util.StringTokenizer;

/**
 * User: jpipe
 * Date: 2/20/13
 */
public abstract class Job extends Thread {

    private SystemSimulator os;
    private long burst;
    private int jobId;
    private int id;

    private boolean shouldRun = true;

    public Job(String descriptor, SystemSimulator os, String name) {
        this.os = os;
        StringTokenizer description = new StringTokenizer(descriptor, ", ");
        this.id = Integer.parseInt(description.nextToken());
        this.burst = Integer.parseInt(description.nextToken());
        this.setName(name);
    }

    public abstract void run();

    protected long getBurstTime() {
        return burst;
    }

    synchronized public void pleaseStop() {
        shouldRun = false;
    }

    synchronized protected boolean shouldRun() {
        return shouldRun;
    }

    public void Exit() {
        System.out.println(getName() + " called Exit");
        os.exit();
    }
}