
package powerdms.forkspoon.model.common.geocode;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import powerdms.forkspoon.model.restaurant.detail.Restaurant;

public class NearbyRestaurant {

    @SerializedName("restaurant")
    @Expose
    private Restaurant restaurant;

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

}
