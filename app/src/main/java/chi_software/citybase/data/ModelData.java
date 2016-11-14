package chi_software.citybase.data;

/**
 * Created by Papin on 11.11.2016.
 */

public class ModelData {
    public String AdminArea;
    public String data;
    public String type;
    public String price;
    public String url;
    public String info;

    public ModelData (String price, String AdminArea, String data, String type, String info, String url) {
        this.AdminArea = AdminArea;
        this.price = price;
        this.data = data;
        this.url = url;
        this.type = type;
        this.info = info;
    }
}
