package Scheduler;

/**
 * User: jpipe
 * Date: 2/20/13
 */
abstract class Job extends Thread {

    private SystemSimulator os;
    private int burst;
    private int jobId;
    private int id;

    private boolean shouldRun = true;

    public Job(String descriptor, SystemSimulator os, String name) {
        this.os = os;
        String[] description  = descriptor.split(",");
        this.id = Integer.parseInt(description[0]);
        this.burst = Integer.parseInt(description[1]);
        this.setName(name);
    }

    public abstract void run();

    protected int getBurstTime() {
        return burst;
    }

    synchronized public void pleaseStop() {
        shouldRun = false;
    }

    synchronized public boolean shouldRun() {
        return shouldRun;
    }

    private void Exit() {
        os.exit();
    }
}