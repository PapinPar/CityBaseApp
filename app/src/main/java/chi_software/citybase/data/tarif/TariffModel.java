package chi_software.citybase.data.tarif;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class TariffModel {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("city")
    @Expose
    private String city;
    @SerializedName("duration")
    @Expose
    private String duration;
    @SerializedName("rus_name")
    @Expose
    private String rusName;
    @SerializedName("cost")
    @Expose
    private String cost;
    @SerializedName("sort")
    @Expose
    private String sort;
    @SerializedName("class")
    @Expose
    private String _class;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("default_cost")
    @Expose
    private String defaultCost;
    @SerializedName("user_cost")
    @Expose
    private double userCost;

    public String getId () {
        return id;
    }

    public void setId (String id) {
        this.id = id;
    }

    public String getName () {
        return name;
    }

    public void setName (String name) {
        this.name = name;
    }

    public String getCity () {
        return city;
    }

    public void setCity (String city) {
        this.city = city;
    }

    public String getDuration () {
        return duration;
    }

    public void setDuration (String duration) {
        this.duration = duration;
    }

    public String getRusName () {
        return rusName;
    }

    public void setRusName (String rusName) {
        this.rusName = rusName;
    }

    public String getCost () {
        return cost;
    }

    public void setCost (String cost) {
        this.cost = cost;
    }

    public String getSort () {
        return sort;
    }

    public void setSort (String sort) {
        this.sort = sort;
    }

    public String getClass_ () {
        return _class;
    }

    public void setClass_ (String _class) {
        this._class = _class;
    }

    public String getDescription () {
        return description;
    }

    public void setDescription (String description) {
        this.description = description;
    }

    public String getDefaultCost () {
        return defaultCost;
    }

    public void setDefaultCost (String defaultCost) {
        this.defaultCost = defaultCost;
    }

    public double getUserCost () {
        return userCost;
    }

    public void setUserCost (double userCost) {
        this.userCost = userCost;
    }

}