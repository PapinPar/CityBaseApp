package chi_software.citybase.retrofit;

import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.v4.util.ArrayMap;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;

import chi_software.citybase.core.api.Net;
import chi_software.citybase.core.api.NetSubscriber;
import chi_software.citybase.data.BaseResponse;
import chi_software.citybase.data.getBase.BaseGet;
import chi_software.citybase.data.login.LoginResponse;
import chi_software.citybase.data.menuSearch.MenuSearch;
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

    @Override
    public void searchMenu (@NonNull final String city, @NonNull final String table, @NonNull final String uid, @NonNull final String key) {
        executor.execute(new Runnable() {
            @Override
            public void run () {
                try {
                    Response<MenuSearch> response = RestApiWrapper.getInstanse().searchMenu(city, table, uid, key);
                    MenuSearch menuSearch = response.body();
                    if ( menuSearch != null )
                        notifySuccessSubscribers(MENU_SEARC, menuSearch);
                    else
                        notifyErrorSubscribers(MENU_SEARC, "ERROR");
                } catch ( IOException e ) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void getBase (final String search, final String city, final String table, final String uid, final String key) {
        executor.execute(new Runnable() {
            @Override
            public void run () {
                try {
                    Response<BaseGet> response = RestApiWrapper.getInstanse().getBase(search, city, table, uid, key);
                    BaseGet baseGet = response.body();
                    Map s = getModel(baseGet);
                    BaseResponse photoResponse = new BaseResponse(baseGet, s);
                    if ( baseGet.getGetResponse() != null ) {
                        notifySuccessSubscribers(GET_BASE, photoResponse);
                    } else notifyErrorSubscribers(GET_BASE, "ERROR");

                } catch ( IOException e ) {
                    e.printStackTrace();
                }
            }
        });

    }

    private Map getModel (BaseGet baseGet) {
        Map<String, List<String>> keys = new ArrayMap<>();

        keys.clear();
        for ( int i = 0 ; i < baseGet.getGetResponse().getModel().size() ; i++ ) {
            String s = baseGet.getGetResponse().getModel().get(i).getFoto();
            try {
                JSONObject object = new JSONObject(s);
                Iterator iter = object.keys();
                List<String> values = new ArrayList<>();
                while ( iter.hasNext() ) {
                    String key = (String) iter.next();
                    String value = object.getString(key);
                    values.add(value);
                }
                if ( values.size() > 0 ) {
                    keys.put(baseGet.getGetResponse().getModel().get(i).getId(), values);
                }
            } catch ( JSONException e ) {
                e.printStackTrace();
            }
        }
        return keys;
    }
}
