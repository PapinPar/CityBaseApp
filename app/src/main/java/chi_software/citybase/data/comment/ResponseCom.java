package chi_software.citybase.data.comment;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import chi_software.citybase.data.getBase.UserParameters;

public class ResponseCom {
    @SerializedName("user_parameters")
    @Expose
    private UserParameters userParameters;

    public UserParameters getUserParameters() {
        return userParameters;
    }

    public void setUserParameters(UserParameters userParameters) {
        this.userParameters = userParameters;
    }

}