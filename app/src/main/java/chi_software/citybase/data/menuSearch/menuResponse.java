
package chi_software.citybase.data.menuSearch;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class MenuResponse {

    @SerializedName("types")
    @Expose
    private List<String> types = new ArrayList<String>();
    @SerializedName("cities")
    @Expose
    private List<String> cities = new ArrayList<String>();
    @SerializedName("admin_regions")
    @Expose
    private List<String> adminRegions = new ArrayList<String>();
    @SerializedName("places")
    @Expose
    private List<String> places = new ArrayList<String>();
    @SerializedName("guides")
    @Expose
    private List<String> guides = new ArrayList<String>();
    @SerializedName("streets")
    @Expose
    private List<String> streets = new ArrayList<String>();

    public List<String> getTypes () {
        return types;
    }

    public void setTypes (List<String> types) {
        this.types = types;
    }

    public List<String> getCities () {
        return cities;
    }

    public void setCities (List<String> cities) {
        this.cities = cities;
    }

    public List<String> getAdminRegions () {
        return adminRegions;
    }

    public void setAdminRegions (List<String> adminRegions) {
        this.adminRegions = adminRegions;
    }

    public List<String> getPlaces () {
        return places;
    }

    public void setPlaces (List<String> places) {
        this.places = places;
    }

    public List<String> getGuides () {
        return guides;
    }

    public void setGuides (List<String> guides) {
        this.guides = guides;
    }

    public List<String> getStreets () {
        return streets;
    }

    public void setStreets (List<String> streets) {
        this.streets = streets;
    }

}
