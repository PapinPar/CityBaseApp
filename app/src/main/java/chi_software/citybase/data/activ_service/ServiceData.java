package chi_software.citybase.data.activ_service;
/**
 * Created by Papin on 19.01.2017.
 */

public class ServiceData {
    public String dateFrom;
    public String dateTO;
    public String rusName;

    public ServiceData (String dateFrom, String dateTO, String rusName) {
        this.dateFrom = dateFrom;
        this.dateTO = dateTO;
        this.rusName = rusName;
    }

    public String getDateFrom () {
        return dateFrom;
    }

    public void setDateFrom (String dateFrom) {
        this.dateFrom = dateFrom;
    }

    public String getDateTO () {
        return dateTO;
    }

    public void setDateTO (String dateTO) {
        this.dateTO = dateTO;
    }

    public String getRusName () {
        return rusName;
    }

    public void setRusName (String rusName) {
        this.rusName = rusName;
    }
}
