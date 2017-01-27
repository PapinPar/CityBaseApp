package chi_software.citybase.data.tarif;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;


public class TarifReponse {

    @SerializedName("countusers")
    @Expose
    private String countusers;
    @SerializedName("tarifs")
    @Expose
    private List<TariffModel> tariffModels = null;

    public String getCountusers () {
        return countusers;
    }

    public void setCountusers (String countusers) {
        this.countusers = countusers;
    }

    public List<TariffModel> getTariffModels () {
        return tariffModels;
    }

    public void setTariffModels (List<TariffModel> tariffModels) {
        this.tariffModels = tariffModels;
    }
}

