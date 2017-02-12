package mukhamedissa.kz.newsapiapp.ui.listener;

import android.support.annotation.NonNull;
import android.view.View;

import mukhamedissa.kz.newsapiapp.model.Article;

/**
 * Created by Mukhamed Issa on 2/12/17.
 */

public interface OnArticleClickListener {

    void onArticleClicked(View view, @NonNull Article article);
}
