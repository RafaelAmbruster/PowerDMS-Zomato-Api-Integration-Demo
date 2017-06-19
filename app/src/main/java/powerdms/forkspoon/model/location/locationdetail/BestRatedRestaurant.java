package powerdms.forkspoon.model.location.locationdetail;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BestRatedRestaurant {

    @SerializedName("restaurant")
    @Expose
    private Restaurant restaurant;

    /**
     * No args constructor for use in serialization
     * 
     */
    public BestRatedRestaurant() {
    }

    /**
     * 
     * @param restaurant
     */
    public BestRatedRestaurant(Restaurant restaurant) {
        super();
        this.restaurant = restaurant;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

}
