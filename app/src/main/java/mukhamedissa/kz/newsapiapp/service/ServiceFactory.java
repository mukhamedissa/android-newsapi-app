package mukhamedissa.kz.newsapiapp.service;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import mukhamedissa.kz.newsapiapp.util.Config;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Mukhamed Issa on 2/10/17.
 */

public class ServiceFactory {

    private static NewsApiService newsApiService = null;

    private ServiceFactory() {
        throw new IllegalStateException("Class " + ServiceFactory.class.getCanonicalName() + " cannot be instantiated");
    }

    public static NewsApiService getService() {
        if (newsApiService == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Config.BASE_URL)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            newsApiService = retrofit.create(NewsApiService.class);
        }

        return newsApiService;
    }
}
