package chi_software.citybase.data;

import java.util.Map;

import chi_software.citybase.data.getBase.BaseGet;

/**
 * Created by Papin on 10.11.2016.
 */

public class BaseResponse {
    private BaseGet baseGet;
    private Map hashMap;

    public BaseResponse (BaseGet baseGet, Map hashMap) {
        this.baseGet = baseGet;
        this.hashMap = hashMap;
    }

    public BaseGet getBaseGet () {
        return baseGet;
    }

    public void setBaseGet (BaseGet baseGet) {
        this.baseGet = baseGet;
    }

    public Map getMap () {
        return hashMap;
    }

    public void setMap (Map hashMap) {
        this.hashMap = hashMap;
    }
}
