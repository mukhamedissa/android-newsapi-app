package mukhamedissa.kz.newsapiapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Mukhamed Issa on 2/12/17.
 */

public class Article extends AbstractModel implements Parcelable {

    public static final Parcelable.Creator<Article> CREATOR = new Parcelable.Creator<Article>() {

        public Article createFromParcel(Parcel in) {
            return new Article(in);
        }

        public Article[] newArray(int size) {
            return new Article[size];
        }
    };

    @SerializedName("author")
    @Expose
    private String mAuthor;
    @SerializedName("title")
    @Expose
    private String mTitle;
    @SerializedName("description")
    @Expose
    private String mDescription;
    @SerializedName("url")
    @Expose
    private String mUrl;
    @SerializedName("urlToImage")
    @Expose
    private String mUrlToImage;
    @SerializedName("publishedAt")
    @Expose
    private String mPublishedAt;

    private String mSourceId;

    public Article() {

    }

    public Article(Parcel in) {
        mAuthor = in.readString();
        mTitle = in.readString();
        mDescription = in.readString();
        mUrl = in.readString();
        mUrlToImage = in.readString();
        mPublishedAt = in.readString();
    }

    public String getAuthor() {
        return mAuthor;
    }

    public void setAuthor(String author) {
        mAuthor = author;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
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

    public String getUrlToImage() {
        return mUrlToImage;
    }

    public void setUrlToImage(String urlToImage) {
        mUrlToImage = urlToImage;
    }

    public String getPublishedAt() {
        return mPublishedAt;
    }

    public void setPublishedAt(String publishedAt) {
        mPublishedAt = publishedAt;
    }

    public String getSourceId() {
        return mSourceId;
    }

    public void setSourceId(String sourceId) {
        mSourceId = sourceId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(mAuthor);
        parcel.writeString(mTitle);
        parcel.writeString(mDescription);
        parcel.writeString(mUrl);
        parcel.writeString(mUrlToImage);
        parcel.writeString(mPublishedAt);
    }
}
