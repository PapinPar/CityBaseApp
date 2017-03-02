package chi_software.citybase.retrofit;

import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.v4.util.ArrayMap;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;

import chi_software.citybase.core.api.Net;
import chi_software.citybase.core.api.NetSubscriber;
import chi_software.citybase.data.BaseResponse;
import chi_software.citybase.data.ErrorClass;
import chi_software.citybase.data.FieldResponse;
import chi_software.citybase.data.activ_service.ServiceResponse;
import chi_software.citybase.data.comment.Comment;
import chi_software.citybase.data.getBase.BaseGet;
import chi_software.citybase.data.history_amount.HistoryResponse;
import chi_software.citybase.data.login.LoginResponse;
import chi_software.citybase.data.login.UserResponse;
import chi_software.citybase.data.menuSearch.MenuSearch;
import chi_software.citybase.data.payment.PaymentResponse;
import chi_software.citybase.data.tarif.Tariff;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static android.R.attr.value;


/**
 * Created by Papin on 08.11.2016.
 */

public class ConnectionManager implements Net {

    private final List<NetSubscriber> mObservers = new ArrayList<>();
    private final Handler mHandler = new Handler(Looper.getMainLooper());
    private final API api;

    public ConnectionManager(Executor mExecutor) {
        final Retrofit retrofit = RestClient.setUpClient(mExecutor);
        api = retrofit.create(API.class);
    }

    //  **************  IMPLEMENTATION METHODS
    @Override
    public void Subscribe(NetSubscriber observer) {
        if (!mObservers.contains(observer))
            mObservers.add(observer);
    }

    @Override
    public void UnSubscribe(NetSubscriber observer) {
        if (mObservers.contains(observer))
            mObservers.remove(observer);
    }

    @Override
    public boolean IsSubscribe(NetSubscriber observer) {
        return mObservers.contains(observer);
    }

    @Override
    public void notifySuccessSubscribers(final int eventId, final Object object) {
        for (final NetSubscriber observer : mObservers) {
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    observer.onNetRequestDone(eventId, object);
                }
            });
        }
    }

    @Override
    public void notifyErrorSubscribers(final int eventId, final Object object) {
        for (final NetSubscriber observer : mObservers) {
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    observer.onNetRequestFail(eventId, object);
                }
            });
        }
    }


    private static class BaseCallback<T> implements Callback<T> {
        private final Object locker = new Object();
        private final int value;
        private final Net net;

        BaseCallback(int value, Net net) {
            this.net = net;
            this.value = value;
        }

        @Override
        public void onResponse(Call<T> call, Response<T> response) {
            synchronized (locker) {
                ErrorClass resp = (ErrorClass) response.body();
                if (resp.getError() != null) {
                    if (!resp.getError().contains("access")) {
                        if (value == 106) {
                            if (resp.getError() != null) {
                                net.notifyErrorSubscribers(value, resp.getError());
                            } else {
                                net.notifySuccessSubscribers(value, resp);
                            }
                        }
                    } else {
                        net.notifyErrorSubscribers(MORE_USERS_ERROR, "Приложение не могут использовать больше двух пользователей!");
                    }
                } else {
                    net.notifySuccessSubscribers(value, response.body());
                }
            }
        }

        @Override
        public void onFailure(Call<T> call, Throwable t) {
            synchronized (locker) {
                net.notifyErrorSubscribers(value, t.getMessage());
            }
        }
    }

    // ************ AUTH *************
    @Override
    public void login(@NonNull final String login, @NonNull final String password) {
        api.login(login, password).enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                LoginResponse resp = (LoginResponse) response.body();
                if (resp.getMyResponse() != null) {
                    notifySuccessSubscribers(SIGN_IN, response.body());
                } else {
                    notifyErrorSubscribers(SIGN_IN, "Проверьте правильность введённых данных и повторите попытку.");
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                notifyErrorSubscribers(value, "Проверьте правильность введённых данных и повторите попытку.");
            }
        });
    }


    @Override
    public void registration(@NonNull final String phone, @NonNull final String pass, @NonNull final String name, @NonNull final Integer userType) {
        api.registration(phone, pass, name, userType).enqueue(new BaseCallback<FieldResponse>(REGISTRATION, this));
    }

    @Override
    public void ActivateAccount(@NonNull final String uid, @NonNull final String key, @NonNull final String code) {
        api.activationAccount(uid, key, code).enqueue(new BaseCallback<FieldResponse>(ACTIVATE_ACOUNT, this));
    }

    @Override
    public void getUser(@NonNull final String uid, @NonNull final String key, final String city) {
        api.getUser(uid, key, city).enqueue(new BaseCallback<UserResponse>(GET_USER, this));
    }

    // ************* EDIT USER **********
    @Override
    public void addUserEmail(@NonNull final String uid, @NonNull final String key, @NonNull final String email) {
        api.addUserEmail(uid, key, email).enqueue(new BaseCallback<FieldResponse>(ADD_USER_EMAIL, this));
    }

    @Override
    public void deleteUserEmail(@NonNull final String uid, @NonNull final String key) {
        api.deleteUserEmail(uid, key).enqueue(new BaseCallback<FieldResponse>(DELETE_USER_EMAIL, this));
    }

    @Override
    public void editUserLogin(@NonNull final String uid, @NonNull final String key, @NonNull final String name, @NonNull final String login,final String id) {
        api.editUserLogin(uid, key, name, login,id).enqueue(new BaseCallback<FieldResponse>(EDIT_USER_LOGIN, this));
    }

    @Override
    public void editUserPassword(@NonNull final String uid, @NonNull final String key, @NonNull final String password, @NonNull final String reenterpassword) {
        api.editUserPassword(uid, key, password, reenterpassword).enqueue(new BaseCallback<FieldResponse>(EDIT_USER_PASSWORD, this));
    }

    // ************ SEARCH ***************
    @Override
    public void searchMenu(@NonNull final String city, @NonNull final String table, @NonNull final String uid, @NonNull final String key) {
        api.getMenuSearch(city, table, uid, key).enqueue(new BaseCallback<MenuSearch>(MENU_SEARC, this));
    }

    @Override
    public void getBase(final String search, final String city, final String table, final String uid, final String key, final Integer page, String city_rus) {
        api.getBase(search, city, table, uid, key, page, city_rus).enqueue(new Callback<BaseGet>() {
            @Override
            public void onResponse(Call<BaseGet> call, Response<BaseGet> response) {
                ErrorClass bodyResponse = (ErrorClass) response.body();
                if (bodyResponse.getError() != null) {
                    if (!bodyResponse.getError().contains("access")) {
                        notifyErrorSubscribers(MORE_USERS_ERROR, "Приложение не могут использовать больше двух пользователей!");
                    }
                } else {
                    BaseGet baseGet = response.body();
                    Map s = getModel(baseGet);
                    BaseResponse photoResponse = new BaseResponse(baseGet, s);
                    notifySuccessSubscribers(GET_BASE, photoResponse);
                }
            }

            @Override
            public void onFailure(Call<BaseGet> call, Throwable t) {
                notifyErrorSubscribers(GET_BASE, "ERROR");
            }
        });
    }

    @Override
    public void tryBase(@NonNull final String city, @NonNull final String table, @NonNull final String ruscity, @NonNull final String type, @NonNull final String place, @NonNull final String basetype, @NonNull final String basetype2) {
        api.getTrialBase(city, table, ruscity, type, place, basetype, basetype2).enqueue(new BaseCallback<BaseGet>(TRIAL_BASE, this));
    }

    @Override
    public void getObjectinfo(@NonNull String search, @NonNull String city, @NonNull String table, @NonNull String uid, @NonNull String key, @NonNull Integer id) {
        api.getPostInfo(search, city, table, uid, key, id).enqueue(new BaseCallback<Comment>(GET_OBJ_INFO, this));
    }

    // ********************** SMS *************
    @Override
    public void sendSms(@NonNull final String uid, @NonNull final String key) {
        api.sendSms(uid, key).enqueue(new BaseCallback<FieldResponse>(SEND_SMS, this));
    }

    @Override
    public void smsReset(@NonNull final String phone) {
        api.smsReset(phone).enqueue(new BaseCallback<FieldResponse>(SMS_RESET, this));
    }

    @Override
    public void newResetPass(@NonNull final String code, @NonNull final String uid, @NonNull final String pass) {
        api.resetPass(code, uid, pass).enqueue(new BaseCallback<FieldResponse>(NEW_RESET_PASS, this));
    }

    // ******************** AMOUNT ********************
    @Override
    public void getMyAmount(@NonNull final String uid, @NonNull final String key) {
        api.getAmount(uid, key).enqueue(new BaseCallback<FieldResponse>(GET_MY_AMOUNT, this));
    }

    @Override
    public void getActivService(@NonNull final String city, @NonNull final String uid, @NonNull final String key) {
        api.getActivServise(city, uid, key).enqueue(new BaseCallback<ServiceResponse>(GET_ACTIVE_SERVICE, this));
    }

    @Override
    public void setGetHistoryAmount(@NonNull final String uid, @NonNull final String key) {
        api.getHistoryAmount(uid, key).enqueue(new BaseCallback<HistoryResponse>(GET_HISTORY_AMOUNT, this));
    }

    @Override
    public void getTariffs(@NonNull final String city, @NonNull final String uid, @NonNull final String key) {
        api.getTarifs(city, uid, key).enqueue(new BaseCallback<Tariff>(GET_TARIFFS, this));
    }

    @Override
    public void createOrder(@NonNull final String tariffID, @NonNull final String coment, @NonNull final String uid, @NonNull final String key) {
        api.createNewOrder(tariffID, coment, uid, key).enqueue(new BaseCallback<FieldResponse>(CREATE_ORDER, this));
    }

    @Override
    public void activateOrder(@NonNull final String orderId, @NonNull final String uid, @NonNull final String key) {
        api.activateOrder(orderId, uid, key).enqueue(new BaseCallback<FieldResponse>(ACTIVATE_ORDER, this));
    }

    @Override
    public void createPayment(@NonNull final String uid, @NonNull final String key, @NonNull final double amount, @NonNull final String operation, @NonNull final String pay_way, @NonNull final String orderId) {
        api.createPayment(uid, key, amount, operation, pay_way, orderId).enqueue(new BaseCallback<PaymentResponse>(CREATE_PAYMENT, this));
    }

    // *************** EDIT FIELD ****************
    @Override
    public void setColor(@NonNull final String uid, @NonNull final String key, @NonNull final String city, @NonNull final String table, @NonNull final String objId, @NonNull final String field, @NonNull final Integer color) {
        api.setColor(uid, key, city, table, objId, field, color).enqueue(new BaseCallback<FieldResponse>(SET_COLOR, this));
    }

    @Override
    public void setComment(@NonNull final String uid, @NonNull final String key, @NonNull final String city, @NonNull final String table, @NonNull final String objId, @NonNull final String field, @NonNull final String comment) {
        api.setComment(uid, key, city, table, objId, field, comment).enqueue(new BaseCallback<FieldResponse>(SET_COMMENT, this));
    }

    @Override
    public void setRieltor(@NonNull String uid, @NonNull String key, @NonNull String city, @NonNull String table, @NonNull String objId, @NonNull String field, @NonNull String number) {
        api.addRieltor(uid, key, city, table, objId, field, number).enqueue(new BaseCallback<FieldResponse>(ADD_RIELTOR, this));
    }

    // ************ HEPLERS METHODS ************
    private Map getModel(BaseGet baseGet) {
        Map<String, List<String>> keys = new ArrayMap<>();

        keys.clear();
        for (int i = 0; i < baseGet.getGetResponse().getModel().size(); i++) {
            String s = baseGet.getGetResponse().getModel().get(i).getFoto();
            if (s != null) {
                try {
                    JSONObject object = new JSONObject(s);
                    Iterator iter = object.keys();
                    List<String> values = new ArrayList<>();
                    while (iter.hasNext()) {
                        String key = (String) iter.next();
                        String value = object.getString(key);
                        values.add(value);
                    }
                    if (values.size() > 0) {
                        keys.put(baseGet.getGetResponse().getModel().get(i).getId(), values);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        return keys;
    }
}
