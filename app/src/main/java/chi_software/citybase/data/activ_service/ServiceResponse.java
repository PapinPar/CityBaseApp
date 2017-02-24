package chi_software.citybase.data.activ_service;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import chi_software.citybase.data.ErrorClass;


/**
 * Created by Papin on 19.01.2017.
 */

public class ServiceResponse  extends ErrorClass {
    @SerializedName("response")
    @Expose
    private List<ActivServicess> response = null;

    public List<ActivServicess> getResponse () {
        return response;
    }

    public void setResponse (List<ActivServicess> response) {
        this.response = response;
    }
}
