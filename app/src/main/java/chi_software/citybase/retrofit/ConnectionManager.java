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
import chi_software.citybase.data.FieldResponse;
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


    // ************ AUTH *************
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
    public void registration (@NonNull final String phone, @NonNull final String pass, @NonNull final String name) {
        executor.execute(new Runnable() {
            @Override
            public void run () {
                try {
                    Response<FieldResponse> response = RestApiWrapper.getInstanse().registration(phone, pass, name);
                    FieldResponse body = response.body();

                    if ( body.getServerResponse() != null )
                        notifySuccessSubscribers(REGISTRATION, body.getServerResponse().toString());
                    else
                        notifyErrorSubscribers(REGISTRATION, "ERROR");
                } catch ( IOException e ) {
                }

            }
        });
    }

    @Override
    public void ActivateAcount (@NonNull final String uid, @NonNull final String key, @NonNull final String code) {
        executor.execute(new Runnable() {
            @Override
            public void run () {
                try {
                    Response<FieldResponse> response = RestApiWrapper.getInstanse().activateAcount(uid, key, code);
                    FieldResponse serverResponse = response.body();
                    if ( serverResponse.getServerResponse() != null )
                        notifySuccessSubscribers(ACTIVATE_ACOUNT, serverResponse);
                } catch ( IOException e ) {
                }
            }
        });
    }

    // ************* EDIT USER **********


    @Override
    public void addUserEmail (@NonNull final String uid, @NonNull final String key, @NonNull final String email) {
        executor.execute(new Runnable() {
            @Override
            public void run () {
                try {
                    Response<FieldResponse> response = RestApiWrapper.getInstanse().addUserEmail(uid, key, email);
                    FieldResponse fieldResponse = response.body();
                    if ( fieldResponse.getServerResponse() != null ) {
                        notifySuccessSubscribers(AddUserEmail, fieldResponse);
                    } else
                        notifyErrorSubscribers(AddUserEmail, "ERROR");
                } catch ( IOException e ) {
                }
            }
        });
    }

    @Override
    public void deleteUserEmail (@NonNull final String uid, @NonNull final String key) {
        executor.execute(new Runnable() {
            @Override
            public void run () {
                try {
                    Response<FieldResponse> response = RestApiWrapper.getInstanse().deleteUserEmail(uid, key);
                    FieldResponse fieldResponse = response.body();
                    if ( fieldResponse.getServerResponse() != null ) {
                        notifySuccessSubscribers(DeleteUserEmail, fieldResponse);
                    } else
                        notifyErrorSubscribers(DeleteUserEmail, "ERROR");

                } catch ( IOException e ) {
                }
            }
        });
    }

    @Override
    public void editUserLogin (@NonNull final String uid, @NonNull final String key, @NonNull final String name, @NonNull final String login) {
        executor.execute(new Runnable() {
            @Override
            public void run () {
                try {
                    Response<FieldResponse> response = RestApiWrapper.getInstanse().editUserLogin(uid, key, name, login);
                    FieldResponse fieldResponse = response.body();
                    if ( fieldResponse.getServerResponse() != null ) {
                        notifySuccessSubscribers(EditUserLogin, fieldResponse);
                    } else
                        notifyErrorSubscribers(EditUserLogin, "ERROR");
                } catch ( IOException e ) {
                }
            }
        });
    }

    @Override
    public void editUserPassword (@NonNull final String uid, @NonNull final String key, @NonNull final String password, @NonNull final String reenterpassword) {
        executor.execute(new Runnable() {
            @Override
            public void run () {
                try {
                    Response<FieldResponse> response = RestApiWrapper.getInstanse().editUserPassword(uid, key, password, reenterpassword);
                    FieldResponse fieldResponse = response.body();
                    if ( fieldResponse.getServerResponse() != null ) {
                        notifySuccessSubscribers(EditUserPassword, fieldResponse);
                    } else
                        notifyErrorSubscribers(EditUserPassword, "ERROR");
                } catch ( IOException e ) {
                }
            }
        });

    }

    // ************ SEARCH ***************
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
                    } else
                        notifyErrorSubscribers(GET_BASE, "ERROR");

                } catch ( IOException e ) {
                    e.printStackTrace();
                }
            }
        });

    }

    @Override
    public void tryBase (@NonNull final String city, @NonNull final String table, @NonNull final String ruscity, @NonNull final String type, @NonNull final String place, @NonNull final String basetype, @NonNull final String basetype2) {
        executor.execute(new Runnable() {
            @Override
            public void run () {
                try {
                    Response<BaseGet> response = RestApiWrapper.getInstanse().getTrialBase(city, table, ruscity, type, place, basetype, basetype2);
                    BaseGet baseGet = response.body();
                    if ( baseGet.getGetResponse() != null ) {
                        notifySuccessSubscribers(TRIAL_BASE, baseGet);
                    } else
                        notifyErrorSubscribers(TRIAL_BASE, "ERROR");

                } catch ( IOException e ) {
                    e.printStackTrace();
                }
            }
        });
    }

    // ********************** SMS *************


    @Override
    public void sendSms (@NonNull final String uid, @NonNull final String key) {
        executor.execute(new Runnable() {
            @Override
            public void run () {
                try {
                    Response<FieldResponse> response = RestApiWrapper.getInstanse().sendSMS(uid, key);
                    FieldResponse serverResponse = response.body();
                    if ( serverResponse.getServerResponse() != null )
                        notifySuccessSubscribers(SEND_SMS, serverResponse);
                    else
                        notifyErrorSubscribers(SEND_SMS, "ERROR");
                } catch ( IOException e ) {
                }
            }
        });
    }

    // *************** EDIT FIELD ****************
    @Override
    public void setColor (@NonNull final String uid, @NonNull final String key, @NonNull final String city, @NonNull final String table, @NonNull final String objId, @NonNull final String field, @NonNull final Integer color) {
        executor.execute(new Runnable() {
            @Override
            public void run () {
                try {
                    Response<FieldResponse> response = RestApiWrapper.getInstanse().setColor(uid, key, city, table, objId, field, color);
                    notifySuccessSubscribers(SET_COLOR, "OK");
                } catch ( IOException e ) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void setComment (@NonNull final String uid, @NonNull final String key, @NonNull final String city, @NonNull final String table, @NonNull final String objId, @NonNull final String field, @NonNull final String comment) {
        executor.execute(new Runnable() {
            @Override
            public void run () {
                try {
                    Response<FieldResponse> response = RestApiWrapper.getInstanse().setComment(uid, key, city, table, objId, field, comment);
                    notifySuccessSubscribers(SET_COMMENT, "OK");
                } catch ( IOException e ) {
                }
            }
        });
    }


    // ************ HEPLERS METHODS ************
    private Map getModel (BaseGet baseGet) {
        Map<String, List<String>> keys = new ArrayMap<>();

        keys.clear();
        for ( int i = 0 ; i < baseGet.getGetResponse().getModel().size() ; i++ ) {
            String s = baseGet.getGetResponse().getModel().get(i).getFoto();
            if ( s != null ) {
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
        }
        return keys;
    }
}
