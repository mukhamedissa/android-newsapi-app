package mukhamedissa.kz.newsapiapp.ui.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import mukhamedissa.kz.newsapiapp.R;
import mukhamedissa.kz.newsapiapp.model.Article;
import mukhamedissa.kz.newsapiapp.ui.listener.OnArticleClickListener;
import mukhamedissa.kz.newsapiapp.util.AppUtils;

/**
 * Created by Mukhamed Issa on 2/12/17.
 */

public class ArticleListAdapter extends RecyclerView.Adapter<ArticleListAdapter.ArticleViewHolder> {

    @NonNull
    private List<Article> mArticlesList;
    @NonNull
    private OnArticleClickListener mListener;

    public ArticleListAdapter() {
        mArticlesList = new ArrayList<>();
    }

    @Override
    public ArticleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_article_list,
                parent, false);

        return new ArticleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ArticleViewHolder holder, int position) {
        holder.bind(mArticlesList.get(position));
    }

    @Override
    public int getItemCount() {
        return mArticlesList.size();
    }

    public void setItems(@NonNull List<Article> articleList) {
        mArticlesList.clear();
        mArticlesList.addAll(articleList);
        notifyDataSetChanged();
    }

    public void addItems(@NonNull List<Article> articleList) {
        mArticlesList.addAll(articleList);
        notifyDataSetChanged();
    }

    public void setOnArticleClickListener(@NonNull OnArticleClickListener listener) {
        mListener = listener;
    }

    class ArticleViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView mThumbnailImageView;
        private TextView mTitleTextView;
        private TextView mDateTextView;
        private TextView mDescriptionTextView;

        public ArticleViewHolder(View itemView) {
            super(itemView);
            initViews(itemView);
        }

        public void bind(@NonNull Article article){
            mTitleTextView.setText(article.getTitle());
            mDateTextView.setText(AppUtils.formatDate(article.getPublishedAt()));
            mDescriptionTextView.setText(article.getDescription());
            Picasso.with(itemView.getContext())
                    .load(article.getUrlToImage())
                    .into(mThumbnailImageView);
        }

        @Override
        public void onClick(View v) {
            mListener.onArticleClicked(v, mArticlesList.get(getAdapterPosition()));
        }

        private void initViews(@NonNull View view) {
            mThumbnailImageView = (ImageView) view.findViewById(R.id.layout_item_article_list_thumbnail);
            mTitleTextView = (TextView) view.findViewById(R.id.layout_item_article_list_title);
            mDateTextView = (TextView) view.findViewById(R.id.layout_item_article_list_date);
            mDescriptionTextView = (TextView) view.findViewById(R.id.layout_item_article_list_description);

            view.setOnClickListener(this);
        }
    }
}
