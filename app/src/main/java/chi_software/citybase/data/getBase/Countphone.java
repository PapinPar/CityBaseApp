
package chi_software.citybase.data.getBase;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Countphone {

    @SerializedName("value")
    @Expose
    private String value;
    @SerializedName("count")
    @Expose
    private Integer count;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

}
