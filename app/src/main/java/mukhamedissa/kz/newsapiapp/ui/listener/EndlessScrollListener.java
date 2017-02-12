package mukhamedissa.kz.newsapiapp.ui.listener;

import android.support.v7.widget.RecyclerView;

import mukhamedissa.kz.newsapiapp.ui.widget.RecyclerViewPositionHelper;

/**
 * Created by Mukhamed Issa on 2/12/17.
 */
public class EndlessScrollListener<T extends EndlessScrollListener.Listener> extends RecyclerView.OnScrollListener {
    private static final int DEFAULT_VISIBLE_THRESHOLD = 5;

    private int visibleThreshold;
    private T mListener;

    public interface Listener {
        void onNeedToLoadMore();
    }

    public EndlessScrollListener() {
        this(DEFAULT_VISIBLE_THRESHOLD);
    }

    public EndlessScrollListener(int visibleThreshold) {
        this.visibleThreshold = visibleThreshold;
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        if (mListener == null) {
            return;
        }

        int visibleItemCount = recyclerView.getChildCount();
        int totalItemCount = RecyclerViewPositionHelper.getItemCount(recyclerView.getLayoutManager());
        int firstVisibleItem = RecyclerViewPositionHelper.findFirstVisibleItemPosition(recyclerView);

        if ((totalItemCount - visibleItemCount)
                <= (firstVisibleItem + visibleThreshold)) {
            mListener.onNeedToLoadMore();
        }
    }

    public EndlessScrollListener setListener(T listener) {
        mListener = listener;
        return this;
    }
}
