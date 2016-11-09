package chi_software.citybase.retrofit;

import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;

import chi_software.citybase.core.api.Net;
import chi_software.citybase.core.api.NetSubscriber;
import chi_software.citybase.data.login.LoginResponse;
import retrofit2.Response;

/**
 * Created by Papin on 08.11.2016.
 */

public class ConnectionManager implements Net {

    private final List<NetSubscriber> observers = new ArrayList<>();
    private Handler mHandler;
    Executor executor;

    public ConnectionManager (Executor executor) {
        this.executor = executor;
        mHandler = new Handler(Looper.getMainLooper());
    }

    //  **************  IMPLEMENTATION METHODS
    @Override
    public void Subscribe (NetSubscriber observer) {
        if ( !observers.contains(observer) )
            observers.add(observer);
    }

    @Override
    public void UnSubscribe (NetSubscriber observer) {
        if ( observers.contains(observer) )
            observers.remove(observer);
    }

    @Override
    public boolean IsSubscribe (NetSubscriber observer) {
        return observers.contains(observer);
    }

    @Override
    public void notifySuccessSubscribers (final int eventId, final Object object) {
        for ( final NetSubscriber observer : observers ) {
            mHandler.post(new Runnable() {
                @Override
                public void run () {
                    observer.onNetRequestDone(eventId, object);
                }
            });
        }
    }

    @Override
    public void notifyErrorSubscribers (final int eventId, final Object object) {
        for ( final NetSubscriber observer : observers ) {
            mHandler.post(new Runnable() {
                @Override
                public void run () {
                    observer.onNetRequestFail(eventId, object);
                }
            });
        }
    }

    @Override
    public void login (@NonNull final String login, @NonNull final String password) {
        executor.execute(new Runnable() {
            @Override
            public void run () {
                try {
                    Response<LoginResponse> response = RestApiWrapper.getInstanse().login(login, password);
                    LoginResponse loginResponse = response.body();
                    if ( response.isSuccessful() ) {
                        if ( loginResponse.getMyResponse() != null )
                            notifySuccessSubscribers(SIGN_IN, loginResponse);
                    }
                    if ( loginResponse.getMyResponse() == null )
                        notifyErrorSubscribers(SIGN_IN, "Error");
                } catch ( IOException e ) {
                    e.printStackTrace();
                }
            }
        });
    }
}
