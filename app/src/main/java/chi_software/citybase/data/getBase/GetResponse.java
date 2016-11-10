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

    /**
     * 
     * @return
     *     The objects
     */
    public List<getMyObject> getGetMyObjects () {
        return getMyObjects;
    }

    /**
     * 
     * @param getMyObjects
     *     The objects
     */
    public void setGetMyObjects (List<getMyObject> getMyObjects) {
        this.getMyObjects = getMyObjects;
    }

    /**
     * 
     * @return
     *     The lastpage
     */
    public String getLastpage() {
        return lastpage;
    }

    /**
     * 
     * @param lastpage
     *     The lastpage
     */
    public void setLastpage(String lastpage) {
        this.lastpage = lastpage;
    }

    /**
     * 
     * @return
     *     The countphone
     */

}
