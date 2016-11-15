package chi_software.citybase.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Papin on 09.11.2016.
 */

public class Search {
    @SerializedName("typedate")
    @Expose
    private String typedate;
    @SerializedName("time")
    @Expose
    private String time;
    @SerializedName("datefrom")
    @Expose
    private String datefrom;
    @SerializedName("dateto")
    @Expose
    private Date dateto;
    @SerializedName("areafrom")
    @Expose
    private String areafrom;
    @SerializedName("areato")
    @Expose
    private String areato;
    @SerializedName("floor")
    @Expose
    private String floor;
    @SerializedName("house")
    @Expose
    private String house;
    @SerializedName("street")
    @Expose
    private List<String> street = new ArrayList<String>();
    @SerializedName("guide")
    @Expose
    private List<String> guide = new ArrayList<String>();
    @SerializedName("place")
    @Expose
    private List<String> place = new ArrayList<String>();
    @SerializedName("admin_region")
    @Expose
    private  List<String> admin_region = new ArrayList<String>();
    @SerializedName("city")
    @Expose
    private List<String> city = new ArrayList<String>();
    @SerializedName("term")
    @Expose
    private List<String> term = new ArrayList<String>();
    @SerializedName("comment")
    @Expose
    private String comment;
    @SerializedName("textorphone")
    @Expose
    private String textorphone;
    @SerializedName("fill")
    @Expose
    private List<String> fill = new ArrayList<String>();
    @SerializedName("type")
    @Expose
    private List<String> type = new ArrayList<String>();
    @SerializedName("pricefrom")
    @Expose
    private String pricefrom;
    @SerializedName("priceto")
    @Expose
    private String priceto;




    // ****************************************************************

    public String getPricefrom () {
        return pricefrom;
    }

    public void setPricefrom (String pricefrom) {
        this.pricefrom = pricefrom;
    }

    public String getPriceto () {
        return priceto;
    }

    public void setPriceto (String priceto) {
        this.priceto = priceto;
    }

    public List<String> getTypes () {
        return type;
    }

    public void setTypes (List<String> types) {
        this.type = types;
    }

    public List<String> getAdmin_region () {
        return admin_region;
    }

    public void setAdmin_region (List<String> admin_region) {
        this.admin_region = admin_region;
    }

    public String getAreafrom () {
        return areafrom;
    }

    public void setAreafrom (String areafrom) {
        this.areafrom = areafrom;
    }

    public String getAreato () {
        return areato;
    }

    public void setAreato (String areato) {
        this.areato = areato;
    }

    public List<String> getCity () {
        return city;
    }

    public void setCity (List<String> city) {
        this.city = city;
    }

    public String getComment () {
        return comment;
    }

    public void setComment (String comment) {
        this.comment = comment;
    }

    public String getDatefrom () {
        return datefrom;
    }

    public void setDatefrom (String datefrom) {
        this.datefrom = datefrom;
    }

    public Date getDateto () {
        return dateto;
    }

    public void setDateto (Date dateto) {
        this.dateto = dateto;
    }

    public List<String> getFill () {
        return fill;
    }

    public void setFill (List<String> fill) {
        this.fill = fill;
    }

    public String getFloor () {
        return floor;
    }

    public void setFloor (String floor) {
        this.floor = floor;
    }

    public List<String> getGuide () {
        return guide;
    }

    public void setGuide (List<String> guide) {
        this.guide = guide;
    }

    public String getHouse () {
        return house;
    }

    public void setHouse (String house) {
        this.house = house;
    }

    public List<String> getPlace () {
        return place;
    }

    public void setPlace (List<String> place) {
        this.place = place;
    }

    public List<String> getStreet () {
        return street;
    }

    public void setStreet (List<String> street) {
        this.street = street;
    }

    public List<String> getTerm () {
        return term;
    }

    public void setTerm (List<String> term) {
        this.term = term;
    }

    public String getTextorphone () {
        return textorphone;
    }

    public void setTextorphone (String textorphone) {
        this.textorphone = textorphone;
    }

    public String getTime () {
        return time;
    }

    public void setTime (String time) {
        this.time = time;
    }

    public String getTypedate () {
        return typedate;
    }

    public void setTypedate (String typedate) {
        this.typedate = typedate;
    }
}
