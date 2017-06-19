
package powerdms.forkspoon.model.restaurant.search;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Photo {

    @SerializedName("photo")
    @Expose
    private ZomatoPhoto photo;

    public ZomatoPhoto getPhoto() {
        return photo;
    }

    public void setPhoto(ZomatoPhoto photo) {
        this.photo = photo;
    }

}
