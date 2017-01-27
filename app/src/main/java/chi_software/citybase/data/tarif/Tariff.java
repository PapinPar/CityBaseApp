package chi_software.citybase.data.tarif;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Tariff {

    @SerializedName("response")
    @Expose
    private TarifReponse response;

    public TarifReponse getResponse () {
        return response;
    }

    public void setResponse (TarifReponse response) {
        this.response = response;
    }

}