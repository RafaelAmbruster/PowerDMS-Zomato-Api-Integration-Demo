package powerdms.forkspoon.model.restaurant.dailymenu;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DailyMenuResp {

    @SerializedName("daily_menu")
    @Expose
    private List<DailyMenu> dailyMenu = null;

    /**
     * No args constructor for use in serialization
     * 
     */
    public DailyMenuResp() {
    }

    /**
     * 
     * @param dailyMenu
     */
    public DailyMenuResp(List<DailyMenu> dailyMenu) {
        super();
        this.dailyMenu = dailyMenu;
    }

    public List<DailyMenu> getDailyMenu() {
        return dailyMenu;
    }

    public void setDailyMenu(List<DailyMenu> dailyMenu) {
        this.dailyMenu = dailyMenu;
    }

}
