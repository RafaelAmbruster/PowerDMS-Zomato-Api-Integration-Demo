
package powerdms.forkspoon.model.common.city;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CitiesResp {

    @SerializedName("location_suggestions")
    @Expose
    private List<City> cities = null;
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
    public CitiesResp() {
    }

    /**
     * 
     * @param hasMore
     * @param status
     * @param cities
     * @param hasTotal
     */
    public CitiesResp(List<City> cities, String status, Integer hasMore, Integer hasTotal) {
        super();
        this.cities = cities;
        this.status = status;
        this.hasMore = hasMore;
        this.hasTotal = hasTotal;
    }

    public List<City> getCities() {
        return cities;
    }

    public void setCities(List<City> cities) {
        this.cities = cities;
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
