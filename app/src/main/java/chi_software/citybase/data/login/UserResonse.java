package chi_software.citybase.data.login;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


/**
 * Created by Papin on 19.12.2016.
 */

public class UserResonse {
    @SerializedName("response")
    @Expose
    private UserInfo response;

    public UserInfo getResponse () {
        return response;
    }

    public void setResponse (UserInfo response) {
        this.response = response;
    }

}
