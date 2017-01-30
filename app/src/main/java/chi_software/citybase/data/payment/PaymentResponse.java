package chi_software.citybase.data.payment;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class PaymentResponse {

    @SerializedName("response")
    @Expose
    private  PaymentModel response;

    public PaymentModel getResponse() {
        return response;
    }

    public void setResponse(PaymentModel response) {
        this.response = response;
    }

}
