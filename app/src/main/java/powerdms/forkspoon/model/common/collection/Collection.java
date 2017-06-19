package powerdms.forkspoon.model.common.collection;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Collection {

    @SerializedName("collection_id")
    @Expose
    private long collectionId;
    @SerializedName("res_count")
    @Expose
    private Integer resCount;
    @SerializedName("image_url")
    @Expose
    private String imageUrl;
    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("share_url")
    @Expose
    private String shareUrl;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Collection() {
    }

    /**
     * 
     * @param title
     * @param imageUrl
     * @param collectionId
     * @param description
     * @param shareUrl
     * @param url
     * @param resCount
     */
    public Collection(Integer collectionId, Integer resCount, String imageUrl, String url, String title, String description, String shareUrl) {
        super();
        this.collectionId = collectionId;
        this.resCount = resCount;
        this.imageUrl = imageUrl;
        this.url = url;
        this.title = title;
        this.description = description;
        this.shareUrl = shareUrl;
    }

    public long getCollectionId() {
        return collectionId;
    }

    public void setCollectionId(long collectionId) {
        this.collectionId = collectionId;
    }

    public Integer getResCount() {
        return resCount;
    }

    public void setResCount(Integer resCount) {
        this.resCount = resCount;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getShareUrl() {
        return shareUrl;
    }

    public void setShareUrl(String shareUrl) {
        this.shareUrl = shareUrl;
    }

}
