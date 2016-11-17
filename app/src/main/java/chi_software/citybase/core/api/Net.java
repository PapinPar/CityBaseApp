package chi_software.citybase.core.api;

import android.support.annotation.IntDef;
import android.support.annotation.NonNull;

import chi_software.citybase.core.observer.Subject;


/**
 * Created by Papin on 08.11.2016.
 */
public interface Net extends Subject<NetSubscriber> {
    @IntDef({ SIGN_IN, GET_BASE, MENU_SEARC, SET_COLOR,SET_COMMENT,
            REGISTRATION,SEND_SMS,ACTIVATE_ACOUNT})
    @interface NetEvent {}


    int SIGN_IN = 101;
    int GET_BASE = 102;
    int MENU_SEARC = 103;
    int SET_COLOR = 104;
    int SET_COMMENT = 105;
    int REGISTRATION = 106;
    int SEND_SMS = 107;
    int ACTIVATE_ACOUNT = 108;

    //  ************* AUTH ************
    void login (@NonNull String login, @NonNull String password);
    void registration (@NonNull String phone, @NonNull String pass, @NonNull String name);
    void ActivateAcount (@NonNull String uid, @NonNull String key, @NonNull String code);

    //************** SEARCH ************
    void searchMenu (@NonNull String city, @NonNull String table, @NonNull String uid, @NonNull String key);
    void getBase (String search, String city, String table, String uid, String key);

    // *********** SMS *************
    void sendSms (@NonNull String uid, @NonNull String key);

    // ********* SET COLOR ***************
    void setColor (@NonNull String uid, @NonNull String key, @NonNull String city, @NonNull String table, @NonNull String objId, @NonNull String field, @NonNull Integer color);

    // ********* SET COMMENT ***************
    void setComment (@NonNull String uid, @NonNull String key, @NonNull String city, @NonNull String table, @NonNull String objId, @NonNull String field, @NonNull String comment);
}
