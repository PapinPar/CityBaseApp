
package chi_software.citybase.data.login;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MyResponse {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("key")
    @Expose
    private String key;
    @SerializedName("type")
    @Expose
    private String time;
    @SerializedName("active")
    @Expose
    private String active;

    public String getId () {
        return id;
    }

    public void setId (String id) {
        this.id = id;
    }

    public String getKey () {
        return key;
    }

    public void setKey (String key) {
        this.key = key;
    }

    public String getTime () {
        return time;
    }

    public void setTime (String time) {
        this.time = time;
    }

    public String getActive () {
        return active;
    }

    public void setActive (String active) {
        this.active = active;
    }

}
