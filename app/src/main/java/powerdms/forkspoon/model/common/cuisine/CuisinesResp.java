package powerdms.forkspoon.model.common.cuisine;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CuisinesResp {

    @SerializedName("cuisines")
    @Expose
    private List<Cuisine> cuisines = null;

    /**
     * No args constructor for use in serialization
     * 
     */
    public CuisinesResp() {
    }

    /**
     * 
     * @param cuisines
     */
    public CuisinesResp(List<Cuisine> cuisines) {
        super();
        this.cuisines = cuisines;
    }

    public List<Cuisine> getCuisines() {
        return cuisines;
    }

    public void setCuisines(List<Cuisine> cuisines) {
        this.cuisines = cuisines;
    }

}
