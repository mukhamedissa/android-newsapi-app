package mukhamedissa.kz.newsapiapp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Mukhamed Issa on 2/10/17.
 */

public class Source {

    @SerializedName("id")
    @Expose
    private String mId;
    @SerializedName("name")
    @Expose
    private String mName;
    @SerializedName("description")
    @Expose
    private String mDescription;
    @SerializedName("url")
    @Expose
    private String mUrl;
    @SerializedName("category")
    @Expose
    private String mCategory;
    @SerializedName("language")
    @Expose
    private String mLanguage;
    @SerializedName("country")
    @Expose
    private String mCountry;
    @SerializedName("urlsToLogos")
    @Expose
    private UrlsToLogos mUrlsToLogos;
    @SerializedName("sortBysAvailable")
    @Expose
    private List<String> mSortBysAvailable;

    public String getId() {
        return mId;
    }

    public void setId(String id) {
         mId = id;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public String getUrl() {
        return mUrl;
    }

    public void setUrl(String url) {
        mUrl = url;
    }

    public String getCategory() {
        return mCategory;
    }

    public void setCategory(String category) {
        mCategory = category;
    }

    public String getLanguage() {
        return mLanguage;
    }

    public void setLanguage(String language) {
        mLanguage = language;
    }

    public String getCountry() {
        return mCountry;
    }

    public void setCountry(String country) {
        mCountry = country;
    }

    public UrlsToLogos getUrlsToLogos() {
        return mUrlsToLogos;
    }

    public void setUrlsToLogos(UrlsToLogos urlsToLogos) {
        mUrlsToLogos = urlsToLogos;
    }

    public List<String> getSortBysAvailable() {
        return mSortBysAvailable;
    }

    public void setSortBysAvailable(List<String> sortBysAvailable) {
        mSortBysAvailable = sortBysAvailable;
    }
}
