package chi_software.citybase.core.api;

import android.support.annotation.IntDef;
import android.support.annotation.NonNull;

import chi_software.citybase.core.observer.Subject;


/**
 * Created by Papin on 08.11.2016.
 */
public interface Net extends Subject<NetSubscriber> {
    @IntDef({ SIGN_IN, GET_BASE, MENU_SEARC, SET_COLOR, SET_COMMENT, REGISTRATION, SEND_SMS, ACTIVATE_ACOUNT, TRIAL_BASE, EDIT_USER_LOGIN,
            EDIT_USER_PASSWORD, ADD_USER_EMAIL, DELETE_USER_EMAIL, GET_USER, GET_MY_AMOUNT, GET_ACTIVE_SERVICE, GET_TARIFFS, CREATE_ORDER,
            ACTIVATE_ORDER, CREATE_PAYMENT ,SMS_RESET,NEW_RESET_PASS})
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
    int EDIT_USER_LOGIN = 201;
    int EDIT_USER_PASSWORD = 202;
    int ADD_USER_EMAIL = 203;
    int DELETE_USER_EMAIL = 204;
    int GET_USER = 205;
    int GET_MY_AMOUNT = 206;
    int GET_ACTIVE_SERVICE = 207;
    int GET_HISTORY_AMOUNT = 208;
    int GET_TARIFFS = 209;
    int CREATE_ORDER = 301;
    int ACTIVATE_ORDER = 302;
    int CREATE_PAYMENT = 303;
    int SMS_RESET = 304;
    int NEW_RESET_PASS= 305;

    //  ************* AUTH ************
    void login (@NonNull String login, @NonNull String password);
    void registration (@NonNull String phone, @NonNull String pass, @NonNull String name, @NonNull Integer user_type);
    void ActivateAccount (@NonNull String uid, @NonNull String key, @NonNull String code);
    void getUser (@NonNull String uid, @NonNull String key, String city);

    // ************** EDIT USER **********
    void editUserLogin (@NonNull String uid, @NonNull String key, @NonNull String name, @NonNull String login);
    void editUserPassword (@NonNull String uid, @NonNull String key, @NonNull String password, @NonNull String reenterpassword);
    void addUserEmail (@NonNull String uid, @NonNull String key, @NonNull String email);
    void deleteUserEmail (@NonNull String uid, @NonNull String key);

    //************** SEARCH ************
    void searchMenu (@NonNull String city, @NonNull String table, @NonNull String uid, @NonNull String key);
    void getBase (@NonNull String search, @NonNull String city, @NonNull String table, @NonNull String uid, @NonNull String key, @NonNull Integer page);
    void tryBase (@NonNull String city, @NonNull String table, @NonNull String ruscity, @NonNull String type, @NonNull String place, @NonNull String basetype, @NonNull String basetype2);
    // *********** SMS *************
    void sendSms (@NonNull String uid, @NonNull String key);
    void smsReset(@NonNull String phone);
    void newResetPass (@NonNull String code, @NonNull String uid, @NonNull String pass);

    // ********* SET COLOR ***************
    void setColor (@NonNull String uid, @NonNull String key, @NonNull String city, @NonNull String table, @NonNull String objId, @NonNull String field, @NonNull Integer color);

    // ********* SET COMMENT ***************
    void setComment (@NonNull String uid, @NonNull String key, @NonNull String city, @NonNull String table, @NonNull String objId, @NonNull String field, @NonNull String comment);

    // ******************** AMOUNT ********************
    void getMyAmount (@NonNull String uid, @NonNull String key);
    void getActivService (@NonNull String city, @NonNull String uid, @NonNull String key);
    void setGetHistoryAmount (@NonNull String uid, @NonNull String key);
    void getTariffs (@NonNull String city, @NonNull String uid, @NonNull String key);
    void createOrder (@NonNull String tariffID, @NonNull String coment, @NonNull String uid, @NonNull String key);
    void activateOrder (@NonNull String orderId, @NonNull String uid, @NonNull String key);
    void createPayment (@NonNull String uid, @NonNull String key, @NonNull Float amount, @NonNull String operation, @NonNull String pay_way, @NonNull String orderId);
}
