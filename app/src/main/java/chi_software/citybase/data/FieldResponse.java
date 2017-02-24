package chi_software.citybase.data;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


/**
 * Created by Papin on 16.11.2016.
 */

public class FieldResponse extends ErrorClass {
    @SerializedName("response")
    @Expose
    private String serverResponse;


    public String getServerResponse () {
        return serverResponse;
    }
    public void getColorResponse (String colorResponse) {
        this.serverResponse = colorResponse;
    }
}
