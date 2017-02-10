package mukhamedissa.kz.newsapiapp.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import io.reactivex.disposables.CompositeDisposable;
import mukhamedissa.kz.newsapiapp.service.NewsApiService;
import mukhamedissa.kz.newsapiapp.service.ServiceFactory;

/**
 * Created by Mukhamed Issa on 2/10/17.
 */

public class BaseActivity extends AppCompatActivity {

    private NewsApiService mNewsApiService;
    protected CompositeDisposable mCompositeDisposable;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mNewsApiService = ServiceFactory.getService();
        mCompositeDisposable = new CompositeDisposable();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mCompositeDisposable.dispose();
    }

    protected NewsApiService getNewsApiService() {
        return mNewsApiService;
    }
}
