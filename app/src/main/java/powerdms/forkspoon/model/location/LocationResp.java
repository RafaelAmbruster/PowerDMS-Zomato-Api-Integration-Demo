
package powerdms.forkspoon.model.location;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LocationResp {

    @SerializedName("location_suggestions")
    @Expose
    private List<Location> locations = null;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("has_more")
    @Expose
    private Integer hasMore;
    @SerializedName("has_total")
    @Expose
    private Integer hasTotal;

    /**
     * No args constructor for use in serialization
     * 
     */
    public LocationResp() {
    }

    /**
     * 
     * @param hasMore
     * @param status
     * @param locations
     * @param hasTotal
     */
    public LocationResp(List<Location> locations, String status, Integer hasMore, Integer hasTotal) {
        super();
        this.locations = locations;
        this.status = status;
        this.hasMore = hasMore;
        this.hasTotal = hasTotal;
    }

    public List<Location> getLocations() {
        return locations;
    }

    public void setLocations(List<Location> locations) {
        this.locations = locations;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getHasMore() {
        return hasMore;
    }

    public void setHasMore(Integer hasMore) {
        this.hasMore = hasMore;
    }

    public Integer getHasTotal() {
        return hasTotal;
    }

    public void setHasTotal(Integer hasTotal) {
        this.hasTotal = hasTotal;
    }

}
