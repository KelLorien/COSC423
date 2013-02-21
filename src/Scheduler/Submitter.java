package Scheduler;

import Scheduler.jobs.Job;
import Scheduler.jobs.MattJob;

import java.util.ArrayList;
import java.util.StringTokenizer;

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
        creator = new JobCreator();

        for (String s: jobs) {
            StringTokenizer tokens = new StringTokenizer(s, " ,");
            String description = tokens.nextToken();
            delays.add(Long.parseLong(tokens.nextToken()));
            descriptions.add(description + " " + tokens.nextToken());
        }
    }

    public void run() {
        for (int i = 0; i < delays.size(); i++) {
            try {
                sleep(delays.get(i));
            } catch (InterruptedException e) {
                System.err.println("Submitter was interrupted!");
            }

            os.addNewProcess(creator.createJob(descriptions.get(i), os, "Process " + i));
        }
        os.noMoreJobsToSubmit();
    }

    private class JobCreator {
        public Job createJob(String description, SystemSimulator s, String name) {
            // Return a Job or subtype of Job for eventual execution.
            return new MattJob(description, s, name);
        }
    }
}
