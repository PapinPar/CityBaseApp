package chi_software.citybase.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Base64;


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
    private static final String PREFS_IS_FIRST = "IS_FIRST";
    private static final String PREFS_RUS_CITY = "RUS_CITY";

    private static SharedPreferences getSharedPreferences(Context context) {
        return context.getSharedPreferences(SHARED_PREFERENCES_CITY_BASE, Context.MODE_PRIVATE);
    }

    public static void clear(Context context) {
        getSharedPreferences(context).edit().clear().commit();
    }

    // KYE
    public static void SaveKey(Context context, String s) {
        saveString(context, s, PREFS_KEY);
    }

    public static String GetKey(Context context) {
        return getString(context, PREFS_KEY, "");
    }


    // UID
    public static void SaveUID(Context context, String s) {
        saveString(context, s, PREFS_UID);
    }

    public static String GetUID(Context context) {
        return getString(context, PREFS_UID, "");
    }

    // CITY
    public static void SaveCity(Context context, String s) {
        saveString(context, s, PREFS_CITY);
    }

    public static String GetCity(Context context) {
        return getString(context, PREFS_CITY, "_Kharkov");

    }

    // RUS_CITY
    public static void SaveCityRus(Context context, String s) {
        saveString(context, s, PREFS_RUS_CITY);
    }

    public static String GetCityRus(Context context) {
        return getString(context, PREFS_RUS_CITY, "Харьков");

    }

    // TABLE
    public static void SaveTable(Context context, String s) {
        saveString(context, s, PREFS_TABLE);
    }

    public static String GetTable(Context context) {
        return getString(context, PREFS_TABLE, "rent_living");

    }

    // ACCESS LEVEL
    public static void SaveLevel(Context context, String s) {
        saveString(context, s, PREFS_ACCESS_LEVEL);
    }

    public static String GetLevel(Context context) {
        return getString(context, PREFS_ACCESS_LEVEL);
    }

    // PASSWORD
    public static void SetPassword(Context context, String s) {
        saveString(context, encrypt(s), PREFS_PASSWORD);
    }

    public static String GetPassword(Context contextc) {
        return decrypt(getString(contextc, PREFS_PASSWORD, ""));
    }

    // LOGIN
    public static void SetLogin(Context context, String s) {

        saveString(context, encrypt(s), PREFS_LOGIN);
    }

    public static String GetLogin(Context context) {
        return decrypt(getString(context, PREFS_LOGIN, ""));
    }

    // IS FIRST
    public static void setFirst(Context context, boolean b) {
        saveBoolean(context, b, PREFS_IS_FIRST);
    }

    public static boolean getFirst(Context context) {
        return getBoolean(context, PREFS_IS_FIRST, true);
    }

    private static void saveString(Context context, String s, String key) {
        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putString(key, s);
        editor.apply();
    }

    private static String getString(Context context, String key) {
        return getString(context, key, "");
    }

    private static String getString(Context context, String key, String defValue) {
        return getSharedPreferences(context).getString(key, defValue);
    }

    private static void saveBoolean(Context context, boolean b, String key) {
        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putBoolean(key, b);
        editor.apply();
    }

    private static boolean getBoolean(Context context, String key) {
        return getBoolean(context, key);
    }

    private static boolean getBoolean(Context context, String key, boolean defValue) {
        return getSharedPreferences(context).getBoolean(key, defValue);
    }

    private static String encrypt(String input) {
        return Base64.encodeToString(input.getBytes(), Base64.DEFAULT);
    }

    private static String decrypt(String input) {
        return new String(Base64.decode(input, Base64.DEFAULT));
    }

}
