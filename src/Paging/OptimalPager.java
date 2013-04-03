package Paging;

import java.util.ArrayList;

/**
 * User: jpipe
 * Date: 4/3/13
 */
public class OptimalPager extends Pager {

    public OptimalPager(int frameCount, Integer... tries) {
        super(frameCount, tries);
    }

    public OptimalPager(int frameCount, ArrayList<Integer> tries) {
        super(frameCount, tries);
    }

    @Override
    public void execute() {
        for (int page: tries) {
            boolean fault = isPageFault(page);
            if (fault) {
                handleFault(page);
            } //else, do nothing
            takeStateSnapshot(page, fault);
        }
    }

    private void handleFault(int pageId) {
        if (state.size() < frameCount) {
            state.add(new Page(pageId));
        } else {
            state.set(calculateReplacement(), new Page(pageId));
        }
    }

    private int calculateReplacement() {
        Page optimalPage = null;
        int nextUseOfOptimalPage = 0;
        for (Page p: state) {
            int nextUse = nextUseForPage(p.getId());

            if (nextUse < 0) {  //if the next use returned negative, the page is never used again, and so can be
                return state.indexOf(p); //replaced immediately
            } else if (nextUse > nextUseOfOptimalPage) {
                optimalPage = p;
                nextUseOfOptimalPage = nextUse;
            }
        }
        return optimalPage != null ? state.indexOf(optimalPage) : 0;
    }

    private int nextUseForPage(int pageId) {
        for (int i = 0; i < tries.size(); i++) {
            if (tries.get(i) == pageId) {
                return i;
            }
        }
        //if this page was not used again, it can be safely replaced without checking any further pages
        return -1;
    }
}
