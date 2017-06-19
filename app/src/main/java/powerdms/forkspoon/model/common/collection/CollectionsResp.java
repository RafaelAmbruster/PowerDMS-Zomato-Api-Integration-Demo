package powerdms.forkspoon.model.common.collection;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CollectionsResp {

    @SerializedName("collections")
    @Expose
    private List<Collection> collections = null;
    @SerializedName("has_more")
    @Expose
    private Integer hasMore;
    @SerializedName("share_url")
    @Expose
    private String shareUrl;
    @SerializedName("display_text")
    @Expose
    private String displayText;
    @SerializedName("has_total")
    @Expose
    private Integer hasTotal;

    /**
     * No args constructor for use in serialization
     * 
     */
    public CollectionsResp() {
    }

    /**
     * 
     * @param hasMore
     * @param displayText
     * @param shareUrl
     * @param hasTotal
     * @param collections
     */
    public CollectionsResp(List<Collection> collections, Integer hasMore, String shareUrl, String displayText, Integer hasTotal) {
        super();
        this.collections = collections;
        this.hasMore = hasMore;
        this.shareUrl = shareUrl;
        this.displayText = displayText;
        this.hasTotal = hasTotal;
    }

    public List<Collection> getCollections() {
        return collections;
    }

    public void setCollections(List<Collection> collections) {
        this.collections = collections;
    }

    public Integer getHasMore() {
        return hasMore;
    }

    public void setHasMore(Integer hasMore) {
        this.hasMore = hasMore;
    }

    public String getShareUrl() {
        return shareUrl;
    }

    public void setShareUrl(String shareUrl) {
        this.shareUrl = shareUrl;
    }

    public String getDisplayText() {
        return displayText;
    }

    public void setDisplayText(String displayText) {
        this.displayText = displayText;
    }

    public Integer getHasTotal() {
        return hasTotal;
    }

    public void setHasTotal(Integer hasTotal) {
        this.hasTotal = hasTotal;
    }

}
