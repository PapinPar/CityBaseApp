package chi_software.citybase.data.history_amount;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


/**
 * Created by user on 24.01.2017.
 */
public class HistoryModel {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("amount")
    @Expose
    private String amount;
    @SerializedName("operation")
    @Expose
    private String operation;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("comment")
    @Expose
    private Object comment;
    @SerializedName("order_id")
    @Expose
    private Object orderId;
    @SerializedName("link_to_pay")
    @Expose
    private Object linkToPay;

    public String getId () {
        return id;
    }

    public void setId (String id) {
        this.id = id;
    }

    public String getUserId () {
        return userId;
    }

    public void setUserId (String userId) {
        this.userId = userId;
    }

    public String getDate () {
        return date;
    }

    public void setDate (String date) {
        this.date = date;
    }

    public String getAmount () {
        return amount;
    }

    public void setAmount (String amount) {
        this.amount = amount;
    }

    public String getOperation () {
        return operation;
    }

    public void setOperation (String operation) {
        this.operation = operation;
    }

    public String getStatus () {
        return status;
    }

    public void setStatus (String status) {
        this.status = status;
    }

    public Object getComment () {
        return comment;
    }

    public void setComment (Object comment) {
        this.comment = comment;
    }

    public Object getOrderId () {
        return orderId;
    }

    public void setOrderId (Object orderId) {
        this.orderId = orderId;
    }

    public Object getLinkToPay () {
        return linkToPay;
    }

    public void setLinkToPay (Object linkToPay) {
        this.linkToPay = linkToPay;
    }

}