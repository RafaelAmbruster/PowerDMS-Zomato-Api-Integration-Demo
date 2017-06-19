
package powerdms.forkspoon.model.location.locationdetail;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import powerdms.forkspoon.model.location.Location;


public class LocationDetailsResp {

    @SerializedName("popularity")
    @Expose
    private String popularity;
    @SerializedName("nightlife_index")
    @Expose
    private String nightlifeIndex;
    @SerializedName("nearby_res")
    @Expose
    private List<String> nearbyRes = null;
    @SerializedName("top_cuisines")
    @Expose
    private List<String> topCuisines = null;
    @SerializedName("popularity_res")
    @Expose
    private String popularityRes;
    @SerializedName("nightlife_res")
    @Expose
    private String nightlifeRes;
    @SerializedName("subzone")
    @Expose
    private String subzone;
    @SerializedName("subzone_id")
    @Expose
    private Integer subzoneId;
    @SerializedName("city")
    @Expose
    private String city;
    @SerializedName("location")
    @Expose
    private Location location;
    @SerializedName("num_restaurant")
    @Expose
    private Integer numRestaurant;
    @SerializedName("best_rated_restaurant")
    @Expose
    private List<BestRatedRestaurant> bestRatedRestaurant = null;

    /**
     * No args constructor for use in serialization
     * 
     */
    public LocationDetailsResp() {
    }

    /**
     * 
     * @param subzone
     * @param popularityRes
     * @param location
     * @param topCuisines
     * @param bestRatedRestaurant
     * @param nightlifeIndex
     * @param nearbyRes
     * @param numRestaurant
     * @param nightlifeRes
     * @param city
     * @param subzoneId
     * @param popularity
     */
    public LocationDetailsResp(String popularity, String nightlifeIndex, List<String> nearbyRes, List<String> topCuisines, String popularityRes, String nightlifeRes, String subzone, Integer subzoneId, String city, Location location, Integer numRestaurant, List<BestRatedRestaurant> bestRatedRestaurant) {
        super();
        this.popularity = popularity;
        this.nightlifeIndex = nightlifeIndex;
        this.nearbyRes = nearbyRes;
        this.topCuisines = topCuisines;
        this.popularityRes = popularityRes;
        this.nightlifeRes = nightlifeRes;
        this.subzone = subzone;
        this.subzoneId = subzoneId;
        this.city = city;
        this.location = location;
        this.numRestaurant = numRestaurant;
        this.bestRatedRestaurant = bestRatedRestaurant;
    }

    public String getPopularity() {
        return popularity;
    }

    public void setPopularity(String popularity) {
        this.popularity = popularity;
    }

    public String getNightlifeIndex() {
        return nightlifeIndex;
    }

    public void setNightlifeIndex(String nightlifeIndex) {
        this.nightlifeIndex = nightlifeIndex;
    }

    public List<String> getNearbyRes() {
        return nearbyRes;
    }

    public void setNearbyRes(List<String> nearbyRes) {
        this.nearbyRes = nearbyRes;
    }

    public List<String> getTopCuisines() {
        return topCuisines;
    }

    public void setTopCuisines(List<String> topCuisines) {
        this.topCuisines = topCuisines;
    }

    public String getPopularityRes() {
        return popularityRes;
    }

    public void setPopularityRes(String popularityRes) {
        this.popularityRes = popularityRes;
    }

    public String getNightlifeRes() {
        return nightlifeRes;
    }

    public void setNightlifeRes(String nightlifeRes) {
        this.nightlifeRes = nightlifeRes;
    }

    public String getSubzone() {
        return subzone;
    }

    public void setSubzone(String subzone) {
        this.subzone = subzone;
    }

    public Integer getSubzoneId() {
        return subzoneId;
    }

    public void setSubzoneId(Integer subzoneId) {
        this.subzoneId = subzoneId;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Integer getNumRestaurant() {
        return numRestaurant;
    }

    public void setNumRestaurant(Integer numRestaurant) {
        this.numRestaurant = numRestaurant;
    }

    public List<BestRatedRestaurant> getBestRatedRestaurant() {
        return bestRatedRestaurant;
    }

    public void setBestRatedRestaurant(List<BestRatedRestaurant> bestRatedRestaurant) {
        this.bestRatedRestaurant = bestRatedRestaurant;
    }

}
