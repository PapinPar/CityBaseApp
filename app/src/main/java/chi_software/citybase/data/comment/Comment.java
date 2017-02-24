package chi_software.citybase.data.comment;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import chi_software.citybase.data.ErrorClass;

/**
 * Created by user on 21.02.2017.
 */

public class Comment  extends ErrorClass {

    @SerializedName("response")
    @Expose
    private ResponseCom response;

    public ResponseCom getResponse() {
        return response;
    }

    public void setResponse(ResponseCom response) {
        this.response = response;
    }

}