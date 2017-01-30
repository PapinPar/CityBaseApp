package chi_software.citybase.data.payment;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


/**
 * Created by user on 27.01.2017.
 */
public class PaymentModel {

    @SerializedName("link")
    @Expose
    private String link;
    @SerializedName("data")
    @Expose
    private String data;
    @SerializedName("signature")
    @Expose
    private String signature;
    @SerializedName("payment_id")
    @Expose
    private Integer paymentId;

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public Integer getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(Integer paymentId) {
        this.paymentId = paymentId;
    }
}