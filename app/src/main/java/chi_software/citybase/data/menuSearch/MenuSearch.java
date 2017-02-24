
package chi_software.citybase.data.menuSearch;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import chi_software.citybase.data.ErrorClass;

public class MenuSearch extends ErrorClass {

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
