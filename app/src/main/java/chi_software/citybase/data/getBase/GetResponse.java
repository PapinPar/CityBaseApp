package chi_software.citybase.data.getBase;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class GetResponse implements Serializable {

    @SerializedName("objects")
    @Expose
    private List<MyObject> MyObjects = new ArrayList<MyObject>();
    @SerializedName("lastpage")
    @Expose
    private String lastpage;
    //@SerializedName("countphone")
    //@Expose
    //private List<Countphone> countphone = new ArrayList<Countphone>();
    //@SerializedName("address_count")
    //@Expose
    //private List<AddressCount> addressCount = new ArrayList<AddressCount>();

    public List<MyObject> getModel () {
        return MyObjects;
    }

    public void setMyObjects (List<MyObject> myObjects) {
        this.MyObjects = myObjects;
    }

    public String getLastpage () {
        return lastpage;
    }

    public void setLastpage (String lastpage) {
        this.lastpage = lastpage;
    }
}
