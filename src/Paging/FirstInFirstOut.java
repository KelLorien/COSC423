package Paging;

import java.util.List;

/**
 * User: jpipe
 * Date: 4/1/13
 */
public class FirstInFirstOut extends Pager {

    private int nextOutIndex = 0;

    public FirstInFirstOut(int frameCount, Integer... tries) {
        super(frameCount, tries);
    }

    public FirstInFirstOut(int frameCount, List<Integer> tries) {
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
            state.set(nextOutIndex, new Page(pageId));
            nextOutIndex = (nextOutIndex + 1) % frameCount;
        }
    }

}
