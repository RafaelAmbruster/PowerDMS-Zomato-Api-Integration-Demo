
package powerdms.forkspoon.model.common.cuisine;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Cuisine {

    @SerializedName("cuisine")
    @Expose
    private Cuisine_ cuisine;

    public Cuisine_ getCuisine() {
        return cuisine;
    }

    public void setCuisine(Cuisine_ cuisine) {
        this.cuisine = cuisine;
    }

}
