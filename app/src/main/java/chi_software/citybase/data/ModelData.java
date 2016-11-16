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
    public String id;
    public String color;
    public String table;

    public ModelData (String price, String AdminArea, String data, String type, String info, String id, Object color, String url,String table) {
        this.AdminArea = AdminArea;
        this.price = price;
        this.data = data;
        this.url = url;
        this.type = type;
        this.id = id;
        this.color = (String) color;
        this.info = info;
        this.table = table;
    }
}
