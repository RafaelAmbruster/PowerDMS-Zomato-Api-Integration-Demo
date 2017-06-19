
package powerdms.forkspoon.model.restaurant.search;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Restaurant {

    @SerializedName("restaurant")
    @Expose
    private ZomatoRestaurant restaurant;

    public ZomatoRestaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(ZomatoRestaurant restaurant) {
        this.restaurant = restaurant;
    }

}
