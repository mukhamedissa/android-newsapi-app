package mukhamedissa.kz.newsapiapp.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import mukhamedissa.kz.newsapiapp.R;
import mukhamedissa.kz.newsapiapp.model.Article;
import mukhamedissa.kz.newsapiapp.util.AppUtils;

public class ArticleDetailsActivity extends AppCompatActivity {

    private static final String ARTICLE_EXTRA = "article";

    private ImageView mBackdrop;
    private TextView mTitle;
    private TextView mDescription;
    private TextView mDatePublished;
    private TextView mAuthor;
    private TextView mSource;

    public static Intent createIntent(@NonNull Context context, @NonNull Article article) {
        Intent intent = new Intent(context, ArticleDetailsActivity.class);
        intent.putExtra(ARTICLE_EXTRA, article);

        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_details);

        initToolbar();
        initUI();
        bindArticle(getArticleFromIntent());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    private void initUI() {
        mBackdrop = (ImageView) findViewById(R.id.activity_article_details_backdrop);
        mTitle = (TextView) findViewById(R.id.activity_article_details_title);
        mDescription = (TextView) findViewById(R.id.activity_article_details_text);
        mDatePublished = (TextView) findViewById(R.id.activity_article_details_date);
        mAuthor = (TextView) findViewById(R.id.activity_article_details_author);
        mSource = (TextView) findViewById(R.id.activity_article_details_source);
    }

    private void initToolbar() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void bindArticle(@NonNull Article article) {
        mTitle.setText(article.getTitle());
        mDescription.setText(article.getDescription());
        mDatePublished.setText(AppUtils.formatDate(article.getPublishedAt()));
        mAuthor.setText(article.getAuthor());
        mSource.setText(article.getUrl());

        Picasso.with(this)
                .load(article.getUrlToImage())
                .into(mBackdrop);
    }

    private Article getArticleFromIntent() {
        return getIntent().getParcelableExtra(ARTICLE_EXTRA);
    }
}
