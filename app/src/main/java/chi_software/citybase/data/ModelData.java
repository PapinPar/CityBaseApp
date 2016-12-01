package chi_software.citybase.data;

/**
 * Created by Papin on 11.11.2016.
 */

public class ModelData {
    private String AdminArea;
    private String data;
    private String type;
    private String price;
    private String url;
    private String info;
    private String id;
    private String color;
    private String table;

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

    public String getAdminArea () {
        return AdminArea;
    }

    public void setAdminArea (String adminArea) {
        AdminArea = adminArea;
    }

    public String getColor () {
        return color;
    }

    public void setColor (String color) {
        this.color = color;
    }

    public String getData () {
        return data;
    }

    public void setData (String data) {
        this.data = data;
    }

    public String getId () {
        return id;
    }

    public void setId (String id) {
        this.id = id;
    }

    public String getInfo () {
        return info;
    }

    public void setInfo (String info) {
        this.info = info;
    }

    public String getPrice () {
        return price;
    }

    public void setPrice (String price) {
        this.price = price;
    }

    public String getTable () {
        return table;
    }

    public void setTable (String table) {
        this.table = table;
    }

    public String getType () {
        return type;
    }

    public void setType (String type) {
        this.type = type;
    }

    public String getUrl () {
        return url;
    }

    public void setUrl (String url) {
        this.url = url;
    }
}
