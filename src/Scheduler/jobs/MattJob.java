package Scheduler.jobs;

import Scheduler.*;

/**
 * <p>Title: MattJob</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author Matt Evett
 * @version 1.0
 */

public class MattJob extends Job {

    public MattJob(String descriptor, SystemSimulator s, String name) {
        super(descriptor, s, "Matt"+name);
    }

    public void run() {
        JobTimer t = new JobTimer(this,getBurstTime());  // I created this class.  A Timer sleeps for
        // the given amount of time.  Upon awaking, it invokes pleaseStop() on this Job.
        t.setPriority(Thread.MAX_PRIORITY);
        t.start();  // (control will return here quickly, as the Timer will immediately sleep)

        long startTime = System.currentTimeMillis();
        while (shouldRun()) {  // Inherited from Job
            if (System.currentTimeMillis() >= startTime  + 10) {
                System.out.println("I'm running, says " + getName() + " at " + System.currentTimeMillis());
                startTime = System.currentTimeMillis();
            }
        }
        Exit();  // Inherited from Job
    }


}