package mukhamedissa.kz.newsapiapp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Mukhamed Issa on 2/12/17.
 */

public class SourcesResponse {

    @SerializedName("sources")
    @Expose
    private List<Source> mSources;

    public List<Source> getSources() {
        return mSources;
    }

    public void setSources(List<Source> sources) {
        mSources = sources;
    }
}
