package chi_software.citybase.core.api;

import android.support.annotation.IntDef;
import android.support.annotation.NonNull;

import chi_software.citybase.core.observer.Subject;

/**
 * Created by Papin on 08.11.2016.
 */
public interface Net extends Subject<NetSubscriber> {
    @IntDef({SIGN_IN})
    @interface NetEvent{}

    int SIGN_IN = 101;

    //  ************* AUTH ************
    void login (@NonNull String login, @NonNull String password);

}
