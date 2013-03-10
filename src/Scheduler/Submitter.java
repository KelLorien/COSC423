package Scheduler;

import Scheduler.jobs.Job;
import Scheduler.jobs.MattJob;

import java.util.ArrayList;

/**
 * User: jpipe
 * Date: 2/20/13
 */
public class Submitter extends Thread {

    private SystemSimulator os;
    private JobCreator creator;
    private ArrayList<String> descriptions = new ArrayList<String>();
    private ArrayList<Long> delays = new ArrayList<Long>();

    public Submitter(SystemSimulator ss, ArrayList<String> jobs) {
        os = ss;
        this.setName("Submitter");
        creator = new JobCreator();

        for (String s: jobs) {
            String[] tokens = s.split("\\s");

            delays.add(Long.parseLong(tokens[1]));
            descriptions.add(tokens[0] + " " + tokens[2]);
        }
    }

    public void run() {
        for (int i = 0; i < delays.size(); i++) {
            try {
                sleep(delays.get(i));
            } catch (InterruptedException e) {
                System.err.println("Submitter was interrupted!");
            }

            os.addNewProcess(creator.createJob(descriptions.get(i), os, "Process"));
        }
        os.noMoreJobsToSubmit();
    }

    private class JobCreator {
        // Returns an object extending Job for eventual execution.
        public Job createJob(String description, SystemSimulator s, String name) {
            return new MattJob(description, s, name);
        }
    }
}
