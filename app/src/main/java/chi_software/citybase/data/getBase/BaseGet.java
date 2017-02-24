
package chi_software.citybase.data.getBase;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import chi_software.citybase.data.ErrorClass;

public class BaseGet extends ErrorClass implements Serializable {

    @SerializedName("response")
    @Expose
    private GetResponse getResponse;

    public GetResponse getGetResponse() {
        return getResponse;
    }

    public void setGetResponse(GetResponse getResponse) {
        this.getResponse = getResponse;
    }

}
