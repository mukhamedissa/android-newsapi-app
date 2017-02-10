package mukhamedissa.kz.newsapiapp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Mukhamed Issa on 2/10/17.
 */

public class UrlsToLogos {

    @SerializedName("small")
    @Expose
    private String mSmall;
    @SerializedName("medium")
    @Expose
    private String mMedium;
    @SerializedName("large")
    @Expose
    private String mLarge;

    public String getSmall() {
        return mSmall;
    }

    public void setSmall(String small) {
        mSmall = small;
    }

    public String getMedium() {
        return mMedium;
    }

    public void setMedium(String medium) {
        mMedium = medium;
    }

    public String getLarge() {
        return mLarge;
    }

    public void setLarge(String large) {
         mLarge = large;
    }

}