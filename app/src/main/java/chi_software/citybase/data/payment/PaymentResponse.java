package chi_software.citybase.data.payment;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import chi_software.citybase.data.ErrorClass;


public class PaymentResponse extends ErrorClass {

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
