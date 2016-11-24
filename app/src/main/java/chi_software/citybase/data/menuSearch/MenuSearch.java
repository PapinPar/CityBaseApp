
package chi_software.citybase.data.menuSearch;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MenuSearch {

    @SerializedName("response")
    @Expose
    private MenuResponse MenuResponse;

    public MenuResponse getMenuResponse () {
        return MenuResponse;
    }

    public void setMenuResponse (MenuResponse MenuResponse) {
        this.MenuResponse = MenuResponse;
    }

}
