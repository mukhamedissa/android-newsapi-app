package mukhamedissa.kz.newsapiapp.ui.activity;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import io.reactivex.disposables.CompositeDisposable;
import mukhamedissa.kz.newsapiapp.R;
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
        mCompositeDisposable.clear();
    }

    protected NewsApiService getNewsApiService() {
        return mNewsApiService;
    }


    protected static void showErrorToast(Context context){
        Toast.makeText(context, context
                .getResources()
                .getString(R.string.error_no_connection), Toast.LENGTH_LONG)
                .show();
    }
}
