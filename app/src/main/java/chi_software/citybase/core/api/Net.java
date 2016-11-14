package chi_software.citybase.core.api;

import android.support.annotation.IntDef;
import android.support.annotation.NonNull;

import chi_software.citybase.core.observer.Subject;

/**
 * Created by Papin on 08.11.2016.
 */
public interface Net extends Subject<NetSubscriber> {
    @IntDef({ SIGN_IN, GET_BASE, MENU_SEARC })
    @interface NetEvent {
    }

    int SIGN_IN = 101;
    int GET_BASE = 102;
    int MENU_SEARC = 103;

    //  ************* AUTH ************
    void login (@NonNull String login, @NonNull String password);

    //************** SEARCH ************
    void searchMenu (@NonNull String city, @NonNull String table, @NonNull String uid, @NonNull String key);
    void getBase (String search, String city,String table, String uid, String key);

}
