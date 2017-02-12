package mukhamedissa.kz.newsapiapp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Mukhamed Issa on 2/10/17.
 */

public class ArticlesResponse {

    @SerializedName("articles")
    @Expose
    private List<Article> mArticles;

    public List<Article> getArticles() {
        return mArticles;
    }

    public void setArticles(List<Article> articles) {
        mArticles = articles;
    }
}
