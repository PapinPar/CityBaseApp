package chi_software.citybase.data.history_amount;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;


/**
 * Created by user on 24.01.2017.
 */

public class HistoryResponse {

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