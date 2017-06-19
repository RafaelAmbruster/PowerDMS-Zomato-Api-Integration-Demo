
package powerdms.forkspoon.model.restaurant.search;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "Restaurant")
public class ZomatoRestaurant implements Comparable<ZomatoRestaurant>{

    @DatabaseField(id = true)
    @SerializedName("id")
    @Expose
    private String id;

    @DatabaseField
    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("url")
    @Expose
    private String url;

    @SerializedName("location")
    @Expose
    private Location location;

    @SerializedName("switch_to_order_menu")
    @Expose
    private Integer switchToOrderMenu;

    @DatabaseField
    @SerializedName("cuisines")
    @Expose
    private String cuisines;

    @SerializedName("average_cost_for_two")
    @Expose
    private Integer averageCostForTwo;

    @SerializedName("price_range")
    @Expose
    private Integer priceRange;

    @SerializedName("currency")
    @Expose
    private String currency;

    @SerializedName("offers")
    @Expose
    private List<Object> offers = null;

    @DatabaseField
    @SerializedName("thumb")
    @Expose
    private String thumb;

    @SerializedName("user_rating")
    @Expose
    private UserRating userRating;

    @SerializedName("photos_url")
    @Expose
    private String photosUrl;

    @SerializedName("menu_url")
    @Expose
    private String menuUrl;

    @SerializedName("featured_image")
    @Expose
    private String featuredImage;

    @SerializedName("has_online_delivery")
    @Expose
    private Integer hasOnlineDelivery;

    @SerializedName("is_delivering_now")
    @Expose
    private Integer isDeliveringNow;

    @SerializedName("deeplink")
    @Expose
    private String deeplink;

    @SerializedName("has_table_booking")
    @Expose
    private Integer hasTableBooking;

    @SerializedName("events_url")
    @Expose
    private String eventsUrl;

    @SerializedName("establishment_types")
    @Expose
    private List<Object> establishmentTypes = null;

    @SerializedName("zomato_events")
    @Expose
    private List<ZomatoEvent> zomatoEvents = null;

    @DatabaseField
    private Boolean favorite;

    @DatabaseField
    private float distance;

    @DatabaseField
    private String address;

    @DatabaseField
    private String addressInfo;

    @DatabaseField
    private Double lat;

    @DatabaseField
    private Double lon;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Integer getSwitchToOrderMenu() {
        return switchToOrderMenu;
    }

    public void setSwitchToOrderMenu(Integer switchToOrderMenu) {
        this.switchToOrderMenu = switchToOrderMenu;
    }

    public String getCuisines() {
        return cuisines;
    }

    public void setCuisines(String cuisines) {
        this.cuisines = cuisines;
    }

    public Integer getAverageCostForTwo() {
        return averageCostForTwo;
    }

    public void setAverageCostForTwo(Integer averageCostForTwo) {
        this.averageCostForTwo = averageCostForTwo;
    }

    public Integer getPriceRange() {
        return priceRange;
    }

    public void setPriceRange(Integer priceRange) {
        this.priceRange = priceRange;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public List<Object> getOffers() {
        return offers;
    }

    public void setOffers(List<Object> offers) {
        this.offers = offers;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public UserRating getUserRating() {
        return userRating;
    }

    public void setUserRating(UserRating userRating) {
        this.userRating = userRating;
    }

    public String getPhotosUrl() {
        return photosUrl;
    }

    public void setPhotosUrl(String photosUrl) {
        this.photosUrl = photosUrl;
    }

    public String getMenuUrl() {
        return menuUrl;
    }

    public void setMenuUrl(String menuUrl) {
        this.menuUrl = menuUrl;
    }

    public String getFeaturedImage() {
        return featuredImage;
    }

    public void setFeaturedImage(String featuredImage) {
        this.featuredImage = featuredImage;
    }

    public Integer getHasOnlineDelivery() {
        return hasOnlineDelivery;
    }

    public void setHasOnlineDelivery(Integer hasOnlineDelivery) {
        this.hasOnlineDelivery = hasOnlineDelivery;
    }

    public Integer getIsDeliveringNow() {
        return isDeliveringNow;
    }

    public void setIsDeliveringNow(Integer isDeliveringNow) {
        this.isDeliveringNow = isDeliveringNow;
    }

    public String getDeeplink() {
        return deeplink;
    }

    public void setDeeplink(String deeplink) {
        this.deeplink = deeplink;
    }

    public Integer getHasTableBooking() {
        return hasTableBooking;
    }

    public void setHasTableBooking(Integer hasTableBooking) {
        this.hasTableBooking = hasTableBooking;
    }

    public String getEventsUrl() {
        return eventsUrl;
    }

    public void setEventsUrl(String eventsUrl) {
        this.eventsUrl = eventsUrl;
    }

    public List<Object> getEstablishmentTypes() {
        return establishmentTypes;
    }

    public void setEstablishmentTypes(List<Object> establishmentTypes) {
        this.establishmentTypes = establishmentTypes;
    }

    public List<ZomatoEvent> getZomatoEvents() {
        return zomatoEvents;
    }

    public void setZomatoEvents(List<ZomatoEvent> zomatoEvents) {
        this.zomatoEvents = zomatoEvents;
    }

    public float getDistance() {
        return distance;
    }

    public void setDistance(float distance) {
        this.distance = distance;
    }

    @Override
    public int compareTo(ZomatoRestaurant model) {
        if (this.getDistance() < model.getDistance()) return -1;
        else if (this.getDistance() > model.getDistance()) return 1;
        else return 0;
    }

    public Boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(Boolean favorite) {
        this.favorite = favorite;
    }

    public Boolean getFavorite() {
        return favorite;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddressInfo() {
        return addressInfo;
    }

    public void setAddressInfo(String addressInfo) {
        this.addressInfo = addressInfo;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLon() {
        return lon;
    }

    public void setLon(Double lon) {
        this.lon = lon;
    }
}
