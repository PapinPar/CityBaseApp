package chi_software.citybase.data.getBase;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class GetResponse {

    @SerializedName("objects")
    @Expose
    private List<getMyObject> getMyObjects = new ArrayList<getMyObject>();
    @SerializedName("lastpage")
    @Expose
    private String lastpage;
    //@SerializedName("countphone")
    //@Expose
    //private List<Countphone> countphone = new ArrayList<Countphone>();
    //@SerializedName("address_count")
    //@Expose
    //private List<AddressCount> addressCount = new ArrayList<AddressCount>();

    public List<getMyObject> getModel () {
        return getMyObjects;
    }

    public void setGetMyObjects (List<getMyObject> getMyObjects) {
        this.getMyObjects = getMyObjects;
    }

    public String getLastpage () {
        return lastpage;
    }

    public void setLastpage (String lastpage) {
        this.lastpage = lastpage;
    }
}
