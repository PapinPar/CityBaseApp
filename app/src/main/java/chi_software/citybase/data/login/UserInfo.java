package chi_software.citybase.data.login;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;


/**
 * Created by Papin on 19.12.2016.
 */
public class UserInfo {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("login")
    @Expose
    private String login;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("date_registration")
    @Expose
    private String dateRegistration;
    @SerializedName("admin")
    @Expose
    private String admin;
    @SerializedName("deleted")
    @Expose
    private String deleted;
    @SerializedName("access_level")
    @Expose
    private String accessLevel;
    @SerializedName("agency_id")
    @Expose
    private String agencyId;
    @SerializedName("active")
    @Expose
    private String active;
    @SerializedName("parameters")
    @Expose
    private String parameters;
    @SerializedName("orders")
    @Expose
    private List<String> orders = null;

    public List<String> getOrders () {
        return orders;
    }

    public void setOrders (List<String> orders) {
        this.orders = orders;
    }

    public String getId () {
        return id;
    }

    public void setId (String id) {
        this.id = id;
    }

    public String getLogin () {
        return login;
    }

    public void setLogin (String login) {
        this.login = login;
    }

    public String getEmail () {
        return email;
    }

    public void setEmail (String email) {
        this.email = email;
    }

    public String getPhone () {
        return phone;
    }

    public void setPhone (String phone) {
        this.phone = phone;
    }

    public String getName () {
        return name;
    }

    public void setName (String name) {
        this.name = name;
    }

    public String getDateRegistration () {
        return dateRegistration;
    }

    public void setDateRegistration (String dateRegistration) {
        this.dateRegistration = dateRegistration;
    }

    public String getAdmin () {
        return admin;
    }

    public void setAdmin (String admin) {
        this.admin = admin;
    }

    public String getDeleted () {
        return deleted;
    }

    public void setDeleted (String deleted) {
        this.deleted = deleted;
    }

    public String getAccessLevel () {
        return accessLevel;
    }

    public void setAccessLevel (String accessLevel) {
        this.accessLevel = accessLevel;
    }

    public String getAgencyId () {
        return agencyId;
    }

    public void setAgencyId (String agencyId) {
        this.agencyId = agencyId;
    }

    public String getActive () {
        return active;
    }

    public void setActive (String active) {
        this.active = active;
    }

    public String getParameters () {
        return parameters;
    }

    public void setParameters (String parameters) {
        this.parameters = parameters;
    }

}