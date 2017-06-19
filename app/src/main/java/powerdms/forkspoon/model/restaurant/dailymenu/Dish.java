
package powerdms.forkspoon.model.restaurant.dailymenu;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Dish {

    @SerializedName("dish_id")
    @Expose
    private String dishId;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("price")
    @Expose
    private String price;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Dish() {
    }

    /**
     * 
     * @param price
     * @param name
     * @param dishId
     */
    public Dish(String dishId, String name, String price) {
        super();
        this.dishId = dishId;
        this.name = name;
        this.price = price;
    }

    public String getDishId() {
        return dishId;
    }

    public void setDishId(String dishId) {
        this.dishId = dishId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

}
