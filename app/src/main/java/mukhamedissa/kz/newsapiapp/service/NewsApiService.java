package mukhamedissa.kz.newsapiapp.service;

import io.reactivex.Observable;
import mukhamedissa.kz.newsapiapp.model.ArticlesResponse;
import mukhamedissa.kz.newsapiapp.model.SourcesResponse;
import mukhamedissa.kz.newsapiapp.util.Config;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Mukhamed Issa on 2/10/17.
 */
public interface NewsApiService {

    String SOURCES_GET_ALL = "sources";
    String ARTICLES_GET_BY_SOURCE = "articles?" + Config.AUTH;

    @GET(SOURCES_GET_ALL)
    Observable<SourcesResponse> getSources();

    @GET(ARTICLES_GET_BY_SOURCE)
    Observable<ArticlesResponse> getArticlesBySource(@Query("source") String source);
}
