package chi_software.citybase.data;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


/**
 * Created by Papin on 16.11.2016.
 */

public class FieldResponse {
    @SerializedName("response")
    @Expose
    private String serverResponse;
    @SerializedName("errno")
    @Expose
    private String errorNo;
    @SerializedName("error")
    @Expose
    private String error;

    public String getServerResponse () {
        return serverResponse;
    }
    public void getColorResponse (String colorResponse) {
        this.serverResponse = colorResponse;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getErrorNo() {
        return errorNo;
    }

    public void setErrorNo(String errorNo) {
        this.errorNo = errorNo;
    }
}
