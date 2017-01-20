package chi_software.citybase.data.activ_service;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


/**
 * Created by Papin on 19.01.2017.
 */

public class ActivServicess {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("date_from")
    @Expose
    private String dateFrom;
    @SerializedName("date_to")
    @Expose
    private String dateTo;
    @SerializedName("rus_name")
    @Expose
    private String rusName;

    public String getId () {
        return id;
    }

    public void setId (String id) {
        this.id = id;
    }

    public String getDateFrom () {
        return dateFrom;
    }

    public void setDateFrom (String dateFrom) {
        this.dateFrom = dateFrom;
    }

    public String getDateTo () {
        return dateTo;
    }

    public void setDateTo (String dateTo) {
        this.dateTo = dateTo;
    }

    public String getRusName () {
        return rusName;
    }

    public void setRusName (String rusName) {
        this.rusName = rusName;
    }

}