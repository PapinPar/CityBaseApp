package chi_software.citybase;
import android.content.Context;
import android.content.SharedPreferences;


/**
 * Created by user on 27.01.2017.
 */

public class SharedCityBase {


    private static final String SHARED_PREFERENCES_CITY_BASE = "preferences_city_base";
    private static final String PREFS_CITY = "CITY";
    private static final String PREFS_UID = "UID";
    private static final String PREFS_KEY = "KEY";
    private static final String PREFS_TABLE = "TABLE";
    private static final String PREFS_ACCESS_LEVEL = "ACCESS_LEVEL";
    private static final String PREFS_PASSWORD = "PASSWORD";
    private static final String PREFS_LOGIN = "LOGIN";

    private static SharedPreferences getSharedPreferences (Context context) {
        return context.getSharedPreferences(SHARED_PREFERENCES_CITY_BASE, Context.MODE_PRIVATE);
    }

    public static void clear (Context context) {
        getSharedPreferences(context).edit().clear().commit();
    }

    // KYE
    public static void SaveKey (Context context, String s) {
        saveString(context, s, PREFS_KEY);
    }
    public static String GetKey (Context context) {
        return getString(context, PREFS_KEY, "");
    }


    // UID
    public static void SaveUID (Context context, String s) {
        saveString(context, s, PREFS_UID);
    }
    public static String GetUID (Context context) {
        return getString(context, PREFS_UID, "");
    }

    // CITY
    public static void SaveCity (Context context, String s) {
        saveString(context, s, PREFS_CITY);
    }
    public static String GetCity (Context context) {
        return getString(context, PREFS_CITY, "_Kharkov");

    }

    // TABLE
    public static void SaveTable (Context context, String s) {
        saveString(context, s, PREFS_TABLE);
    }
    public static String GetTable (Context context) {
        return getString(context, PREFS_TABLE, "rent_living");

    }

    // ACCESS LEVEL
    public static void SaveLevel (Context context,String s){
        saveString(context,s, PREFS_ACCESS_LEVEL);
    }
    public static String GetLevel (Context context) {
        return getString(context, PREFS_ACCESS_LEVEL);
    }

    // PASSWORD
    public static void SetPassword(Context context,String s){
        saveString(context,s,PREFS_PASSWORD);
    }
    public static String GetPassword(Context contextc){
        return getString(contextc, PREFS_PASSWORD, "");
    }

    // LOGIN
    public static void SetLogin(Context context,String s){
        saveString(context,s,PREFS_LOGIN);
    }
    public static String GetLogin(Context contextc){
        return getString(contextc, PREFS_LOGIN, "");
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
