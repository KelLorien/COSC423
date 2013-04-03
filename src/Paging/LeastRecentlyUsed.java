package Paging;

import java.util.ArrayList;
import java.util.List;

/**
 * User: jpipe
 * Date: 4/1/13
 */
public class LeastRecentlyUsed extends Pager{

    public LeastRecentlyUsed(int frameCount, Integer... tries) {
        super(frameCount, tries);
    }

    public LeastRecentlyUsed(int frameCount, List<Integer> tries) {
        super(frameCount, tries);
    }

    @Override
    public void execute() {
        for (int i = 0; i < tries.size(); i++) {
            boolean fault = isPageFault(tries.get(i));
            if (fault) {
                handleFault(tries.get(i), i);
            } else { //if there is no fault, update the page that was used
                updateLastUsed(i);
            }
            takeStateSnapshot(tries.get(i), fault);
        }
    }

    private void updateLastUsed(int i) {
        for (Page p: state) {
            if (p.getId() == tries.get(i)) {
                p.setLastUsed(i);
            }
        }
    }

    private void handleFault(int pageId, int round) {
        if (state.size() < frameCount) {
            state.add(new Page(pageId, round));
        } else {
            int minIndex = 0;
            for (int i = 1; i < state.size(); i++) {
                if (state.get(i).getLastUsed() < state.get(minIndex).getLastUsed()){
                    minIndex = i;
                }
            }
            state.set(minIndex, new Page(pageId, round));
        }
    }
}
