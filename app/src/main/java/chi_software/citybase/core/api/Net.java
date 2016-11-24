package chi_software.citybase.core.api;

import android.support.annotation.IntDef;
import android.support.annotation.NonNull;

import chi_software.citybase.core.observer.Subject;


/**
 * Created by Papin on 08.11.2016.
 */
public interface Net extends Subject<NetSubscriber> {
    @IntDef({ SIGN_IN, GET_BASE, MENU_SEARC, SET_COLOR, SET_COMMENT, REGISTRATION, SEND_SMS, ACTIVATE_ACOUNT,TRIAL_BASE,
            EditUserLogin,EditUserPassword,AddUserEmail,DeleteUserEmail})
    @interface NetEvent {}


    int SIGN_IN = 101;
    int GET_BASE = 102;
    int MENU_SEARC = 103;
    int SET_COLOR = 104;
    int SET_COMMENT = 105;
    int REGISTRATION = 106;
    int SEND_SMS = 107;
    int ACTIVATE_ACOUNT = 108;
    int TRIAL_BASE = 109;
    int EditUserLogin  = 201;
    int EditUserPassword = 202;
    int AddUserEmail = 203;
    int DeleteUserEmail = 204;

    //  ************* AUTH ************
    void login (@NonNull String login, @NonNull String password);
    void registration (@NonNull String phone, @NonNull String pass, @NonNull String name);
    void ActivateAcount (@NonNull String uid, @NonNull String key, @NonNull String code);

    // ************** EDIT USER **********
    void editUserLogin (@NonNull String uid, @NonNull String key, @NonNull String name, @NonNull String login);
    void editUserPassword (@NonNull String uid, @NonNull String key, @NonNull String password, @NonNull String reenterpassword);
    void addUserEmail (@NonNull String uid, @NonNull String key, @NonNull String email);
    void deleteUserEmail (@NonNull String uid, @NonNull String key);

    //************** SEARCH ************
    void searchMenu (@NonNull String city, @NonNull String table, @NonNull String uid, @NonNull String key);
    void getBase (@NonNull String search, @NonNull String city, @NonNull String table, @NonNull String uid, @NonNull String key);
    void tryBase (@NonNull String city, @NonNull String table, @NonNull String ruscity,
                  @NonNull String type, @NonNull String place, @NonNull String basetype, @NonNull String basetype2);
    // *********** SMS *************
    void sendSms (@NonNull String uid, @NonNull String key);

    // ********* SET COLOR ***************
    void setColor (@NonNull String uid, @NonNull String key, @NonNull String city, @NonNull String table, @NonNull String objId, @NonNull String field, @NonNull Integer color);

    // ********* SET COMMENT ***************
    void setComment (@NonNull String uid, @NonNull String key, @NonNull String city, @NonNull String table, @NonNull String objId, @NonNull String field, @NonNull String comment);
}
