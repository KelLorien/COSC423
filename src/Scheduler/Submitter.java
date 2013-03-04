package Scheduler;

import Scheduler.jobs.Job;
import Scheduler.jobs.MattJob;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

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

        sortJobs(jobs);

        for (String s: jobs) {
            String[] tokens = s.split("\\s");

            delays.add(Long.parseLong(tokens[1]));
            descriptions.add(tokens[0] + " " + tokens[2]);
        }
    }

    private void sortJobs(ArrayList<String> jobs) {
        Collections.sort(jobs, new Comparator<String>() {
            //Sort the array of strings based on the second token, which is the delay, so that the shortest delay will be
            //first in line to be added.
            @Override
            public int compare(String s, String s2) {
                long delay1 = Long.parseLong(s.split("\\s")[1]);
                long delay2 = Long.parseLong(s2.split("\\s")[1]);

                return delay1 < delay2 ? -1 : delay1 == delay2 ? 0 : 1;
            }
        });
    }

    public void run() {
        for (int i = 0; i < delays.size(); i++) {
            try {
                //Sleep until it is time to submit the next job. The time until submission of the next job will be
                //the absolute delay (given in the ArrayList delays) minus the current time according to the OS.
                //If submission time has already passed, do not sleep at all.
                sleep(delays.get(i) > os.relativeTime() ? delays.get(i) - os.relativeTime() : 0L);
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
