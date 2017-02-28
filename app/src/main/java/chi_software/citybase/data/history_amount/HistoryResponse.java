package chi_software.citybase.data.history_amount;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import chi_software.citybase.data.ErrorClass;


/**
 * Created by user on 24.01.2017.
 */

public class HistoryResponse  extends ErrorClass{

    @SerializedName("response")
    @Expose
    private List<HistoryModel> response = null;

    public List<HistoryModel> getResponse() {
        return response;
    }

    public void setResponse(List<HistoryModel> response) {
        this.response = response;
    }

}