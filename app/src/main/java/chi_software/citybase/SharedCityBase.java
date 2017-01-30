package chi_software.citybase;
import android.content.Context;
import android.content.SharedPreferences;


/**
 * Created by user on 27.01.2017.
 */

public class SharedCityBase {


    private static final String SHARED_PREFERENCES_CITY_BASE = "preferences_city_base";
    private static final String PREFS_SETTING_CITY = "CITY";
    private static final String PREFS_SETTING_UID = "UID";
    private static final String PREFS_SETTING_KEY = "KEY";
    private static final String PREFS_SETTING_TABLE = "TABLE";
    private static final String PREFS_SETTING_ACCESS_LEVEL= "ACCESS_LEVEL";

    private static SharedPreferences getSharedPreferences (Context context) {
        return context.getSharedPreferences(SHARED_PREFERENCES_CITY_BASE, Context.MODE_PRIVATE);
    }

    public static void clear (Context context) {
        getSharedPreferences(context).edit().clear().commit();
    }

    // KYE
    public static void SaveKey (Context context, String s) {
        saveString(context, s, PREFS_SETTING_KEY);
    }
    public static String GetKey (Context context) {
        return getString(context, PREFS_SETTING_KEY, "");
    }


    // UID
    public static void SaveUID (Context context, String s) {
        saveString(context, s, PREFS_SETTING_UID);
    }
    public static String GetUID (Context context) {
        return getString(context, PREFS_SETTING_UID, "");
    }

    // CITY
    public static void SaveCity (Context context, String s) {
        saveString(context, s, PREFS_SETTING_CITY);
    }
    public static String GetCity (Context context) {
        return getString(context, PREFS_SETTING_CITY, "_Kharkov");

    }

    // TABLE
    public static void SaveTable (Context context, String s) {
        saveString(context, s, PREFS_SETTING_TABLE);
    }
    public static String GetTable (Context context) {
        return getString(context, PREFS_SETTING_TABLE, "rent_living");

    }

    // ACCESS LEVEL
    public static void SaveLevel (Context context,String s){
        saveString(context,s,PREFS_SETTING_ACCESS_LEVEL);
    }
    public static String GetLevel (Context context) {
        return getString(context, PREFS_SETTING_ACCESS_LEVEL);
    }

    private static void saveString (Context context, String s, String key) {
        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putString(key, s);
        editor.commit();
    }

    private static String getString (Context context, String key) {
        return getString(context, key, "");
    }

    private static String getString (Context context, String key, String defValue) {
        return getSharedPreferences(context).getString(key, defValue);
    }

}
