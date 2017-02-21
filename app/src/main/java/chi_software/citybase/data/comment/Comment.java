package chi_software.citybase.data.comment;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by user on 21.02.2017.
 */

public class Comment {

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