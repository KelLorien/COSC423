package Scheduler.jobs;

import Scheduler.SystemSimulator;

/**
 * User: jpipe
 * Date: 2/20/13
 */
public abstract class Job extends Thread {

    private SystemSimulator os;
    private long burst;
    private int id;

    private boolean shouldRun = true;

    public Job(String descriptor, SystemSimulator os, String name) {
        this.os = os;
        String[] description = descriptor.split(" ");
        this.id = Integer.parseInt(description[0]);
        this.burst = Integer.parseInt(description[1]);
        this.setName(name + this.id);
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
        os.exit();
    }
}