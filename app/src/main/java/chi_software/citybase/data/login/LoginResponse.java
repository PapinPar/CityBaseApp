package chi_software.citybase.data.login;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import chi_software.citybase.data.ErrorClass;

public class LoginResponse extends ErrorClass {

    @SerializedName("response")
    @Expose
    private MyResponse myResponse;

    public MyResponse getMyResponse () {
        return myResponse;
    }

    public void setMyResponse (MyResponse myResponse) {
        this.myResponse = myResponse;
    }

}
