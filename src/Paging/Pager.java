package Paging;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * User: jpipe
 * Date: 4/1/13
 */
public abstract class Pager {

    //for output of the table
    public static final int ROW_SIZE = 20;

    protected int frameCount = 0;

    protected List<Page> state = new ArrayList<Page>();
    protected List<Integer> tries = new ArrayList<Integer>();

    protected int faults = 0;

    private List<List<Integer>> stateSnapshot = new ArrayList<List<Integer>>();

    public Pager() {
        frameCount = 3;
        tries = new ArrayList<Integer>();
        Collections.addAll(tries, 1, 2, 3, 4);
    }

    public Pager(int frameCount, Integer... tries) {
        this.frameCount = frameCount;
        Collections.addAll(this.tries, tries);
    }

    public Pager(int frameCount, ArrayList<Integer> tries) {
        this.frameCount = frameCount;
        Collections.copy(this.tries, tries);
    }

    protected boolean isPageFault(int pageNum) {
        for (Page p: state) {
            if (p.getId() == pageNum) {
                return false;
            }
        }
        faults++;
        return true;
    }

    protected void takeStateSnapshot(int currentTry, boolean fault) {
        List<Integer> currentState = new ArrayList<Integer>();
        currentState.add(currentTry);
        if (fault) {
            for (int i = 0; i < frameCount; i++) {
                if (i < state.size()) {
                    currentState.add(state.get(i).getId());
                } else {
                    currentState.add(0);
                }
            }
        }
        stateSnapshot.add(currentState);
    }

    public List<List<Integer>> getStateSnapshot() {
        return stateSnapshot;
    }

    public abstract void execute();

    public int getFaults() {
        return faults;
    }

    public void printTable() {
        printSnapshots(stateSnapshot);
    }

    private void printSnapshots(List<List<Integer>> snapshots) {
        if (snapshots.size() < ROW_SIZE) {
            for (List<Integer> snapshot : snapshots) {
                System.out.print(snapshot.get(0) + "\t");
            }
            System.out.println("\n----------------------------------------" +
                    "----------------------------------------");
            for (int i = 0; i < frameCount; i++) {
                for (List<Integer> snapshot : snapshots) {
                    if (snapshot.size() > 1) {
                        System.out.print(snapshot.get(i + 1) + "\t");
                    } else {
                        System.out.print(" \t");
                    }
                }
                System.out.println();
            }
            System.out.println("========================================" +
                    "========================================");
        } else {
            printSnapshots(snapshots.subList(0, ROW_SIZE - 1));
            printSnapshots(snapshots.subList(ROW_SIZE, snapshots.size() - 1));
        }
    }
}
