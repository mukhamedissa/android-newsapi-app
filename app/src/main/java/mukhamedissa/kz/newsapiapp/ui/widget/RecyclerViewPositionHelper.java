package mukhamedissa.kz.newsapiapp.ui.widget;

import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by Mukhamed Issa on 2/12/17.
 */
public class RecyclerViewPositionHelper {

    private RecyclerViewPositionHelper() {
        throw new IllegalStateException(RecyclerViewPositionHelper.class.getCanonicalName()
                + " cannot be instantiated");
    }

    public static int getItemCount(RecyclerView.LayoutManager layoutManager) {
        return layoutManager == null ? 0 : layoutManager.getItemCount();
    }

    public static int findFirstVisibleItemPosition(RecyclerView recyclerView) {
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        View child = findOneVisibleChild(layoutManager, 0, layoutManager.getChildCount(), false, true);

        return child == null ? RecyclerView.NO_POSITION : recyclerView.getChildAdapterPosition(child);
    }

    public static int findFirstCompletelyVisibleItemPosition(RecyclerView recyclerView) {
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        View child = findOneVisibleChild(layoutManager ,0, layoutManager.getChildCount(), true, false);

        return child == null ? RecyclerView.NO_POSITION : recyclerView.getChildAdapterPosition(child);
    }

    public static int findLastVisibleItemPosition(RecyclerView recyclerView) {
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        View child = findOneVisibleChild(layoutManager, layoutManager.getChildCount() - 1, -1, false, true);

        return child == null ? RecyclerView.NO_POSITION : recyclerView.getChildAdapterPosition(child);
    }

    public static int findLastCompletelyVisibleItemPosition(RecyclerView recyclerView) {
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        View child = findOneVisibleChild(layoutManager ,layoutManager.getChildCount() - 1, -1, true, false);

        return child == null ? RecyclerView.NO_POSITION : recyclerView.getChildAdapterPosition(child);
    }

    static View findOneVisibleChild(RecyclerView.LayoutManager layoutManager,int fromIndex, int toIndex, boolean completelyVisible,
                                    boolean acceptPartiallyVisible) {
        OrientationHelper helper;

        if (layoutManager.canScrollVertically()) {
            helper = OrientationHelper.createVerticalHelper(layoutManager);
        } else {
            helper = OrientationHelper.createHorizontalHelper(layoutManager);
        }

        final int start = helper.getStartAfterPadding();
        final int end = helper.getEndAfterPadding();
        final int next = toIndex > fromIndex ? 1 : -1;
        View partiallyVisible = null;

        for (int i = fromIndex; i != toIndex; i += next) {
            final View child = layoutManager.getChildAt(i);
            final int childStart = helper.getDecoratedStart(child);
            final int childEnd = helper.getDecoratedEnd(child);
            if (childStart < end && childEnd > start) {
                if (completelyVisible) {
                    if (childStart >= start && childEnd <= end) {
                        return child;
                    } else if (acceptPartiallyVisible && partiallyVisible == null) {
                        partiallyVisible = child;
                    }
                } else {
                    return child;
                }
            }
        }

        return partiallyVisible;
    }
}