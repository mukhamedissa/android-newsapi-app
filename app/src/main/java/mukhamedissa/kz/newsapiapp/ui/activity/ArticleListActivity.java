package mukhamedissa.kz.newsapiapp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import java.util.List;

import fr.castorflex.android.smoothprogressbar.SmoothProgressBar;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import mukhamedissa.kz.newsapiapp.R;
import mukhamedissa.kz.newsapiapp.model.Article;
import mukhamedissa.kz.newsapiapp.model.Source;
import mukhamedissa.kz.newsapiapp.ui.adapter.ArticleListAdapter;
import mukhamedissa.kz.newsapiapp.ui.listener.EndlessScrollListener;
import mukhamedissa.kz.newsapiapp.ui.listener.OnArticleClickListener;
import mukhamedissa.kz.newsapiapp.util.AppUtils;
import mukhamedissa.kz.newsapiapp.util.db.orm.ArticleORM;
import mukhamedissa.kz.newsapiapp.util.db.orm.ORMFactory;

public class ArticleListActivity extends BaseActivity implements AdapterView.OnItemSelectedListener,
        EndlessScrollListener.Listener, SwipeRefreshLayout.OnRefreshListener, OnArticleClickListener {

    private static final String TAG = ArticleListActivity.class.getSimpleName();

    private AppCompatSpinner mSourcesSpinner;
    private RecyclerView mArticlesRecyclerView;
    private ArrayAdapter<Source> mSpinnerAdapter;
    private ArticleListAdapter mArticlesAdapter;
    private SmoothProgressBar mProgressBar;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_list);

        initUI();
        loadSources();
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        loadArticlesBySource(getCurrentSourceId(), true);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
    }

    @Override
    public void onNeedToLoadMore() {
        loadArticlesBySource(getCurrentSourceId(), false);
    }

    @Override
    public void onRefresh() {
        loadArticlesBySource(getCurrentSourceId(), true);
    }

    @Override
    public void onArticleClicked(View view, @NonNull Article article) {
        Intent intent = ArticleDetailsActivity.createIntent(this, article);
        startActivity(intent);
    }

    private void initUI() {
        mSourcesSpinner = (AppCompatSpinner) findViewById(R.id.activity_article_list_sources_spinner);
        mArticlesRecyclerView = (RecyclerView) findViewById(R.id.activity_article_list_recycler_view);

        mArticlesAdapter = new ArticleListAdapter();
        mArticlesAdapter.setOnArticleClickListener(this);

        mArticlesRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mArticlesRecyclerView.setAdapter(mArticlesAdapter);

        mSourcesSpinner.setOnItemSelectedListener(this);

        EndlessScrollListener<ArticleListActivity> endlessScrollListener = new EndlessScrollListener<>();
        endlessScrollListener.setListener(this);
        mArticlesRecyclerView.addOnScrollListener(endlessScrollListener);

        mProgressBar = (SmoothProgressBar) findViewById(R.id.activity_article_list_progress_bar);
        mProgressBar.setVisibility(View.GONE);

        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.activity_article_list_refresh_layout);
        mSwipeRefreshLayout.setOnRefreshListener(this);
    }

    private void loadSources() {
        if (AppUtils.isInternetAvailable(this)) {
            ORMFactory.getSourceORM().delete(this);

            loadSourcesFromServer();

            return;
        }

        showErrorToast(this);
        loadSourcesFromLocalDb();
    }

    private void loadSourcesFromServer() {
        mCompositeDisposable.add(getNewsApiService().getSources()
                .flatMap(response -> Observable.fromArray(response.getSources()))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(sources -> ORMFactory.getSourceORM().insert(this.getBaseContext(), sources))
                .subscribe(this::handleSourceResponse, this::handleError));
    }

    private void loadSourcesFromLocalDb(){
        mCompositeDisposable.add(ORMFactory.getSourceORM().get(this)
                .flatMap(Observable::fromArray)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::handleSourceResponse, this::handleError));
    }

    private void loadArticlesBySource(@NonNull String source, boolean isToClear) {
        if (AppUtils.isInternetAvailable(this)) {
            loadArticlesBySourceFromServer(source, isToClear);

            return;
        }

        showErrorToast(this);
        loadArticlesBySourceFromLocalDb(source);
    }

    private void loadArticlesBySourceFromServer(@NonNull String source, boolean isToClear) {
        startProgress();
        mCompositeDisposable.add(getNewsApiService().getArticlesBySource(source)
                .flatMap(response -> Observable.fromArray(response.getArticles()))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(this::cacheArticles)
                .subscribe(articles -> {
                    handleArticleResponse(articles, isToClear);
                }, this::handleError));
    }

    private void loadArticlesBySourceFromLocalDb(@NonNull String source) {
        startProgress();
        mCompositeDisposable.add(ORMFactory.getArticleORM().getArticlesBySourceId(this.getBaseContext(), source)
                .flatMap(Observable::fromArray)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(articles -> {
                    handleArticleResponse(articles, true);
                }, this::handleError));
    }

    private void handleSourceResponse(List<Source> sources) {
        initSpinner(sources);
        loadArticlesBySource(getCurrentSourceId(), true);
    }

    private void handleArticleResponse(List<Article> articles, boolean isToClear) {
        stopProgress();
        if (isToClear) {
            mArticlesAdapter.setItems(articles);

            return;
        }
        mArticlesAdapter.addItems(articles);
    }

    private void handleError(Throwable error) {
        Log.d(TAG, error.getLocalizedMessage());
    }

    private void initSpinner(List<Source> sourcesList) {
        mSpinnerAdapter = new ArrayAdapter<>(this, R.layout.layout_spinner_item, sourcesList);
        mSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSourcesSpinner.setAdapter(mSpinnerAdapter);
    }

    private void startProgress() {
        mProgressBar.setVisibility(View.VISIBLE);
        mProgressBar.progressiveStart();
    }

    private void stopProgress() {
        mSwipeRefreshLayout.setRefreshing(false);
        mProgressBar.setVisibility(View.INVISIBLE);
        mProgressBar.progressiveStop();
    }

    private String getCurrentSourceId() {
        Source source = (Source) mSourcesSpinner.getSelectedItem();

        return source.getId();
    }

    private void cacheArticles(@NonNull List<Article> articles) {
        ArticleORM articleORM = ORMFactory.getArticleORM();
        articleORM.setSourceId(getCurrentSourceId());
        articleORM.insert(this.getBaseContext(), articles);
    }
}