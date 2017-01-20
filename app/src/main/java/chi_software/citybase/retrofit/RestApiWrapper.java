package chi_software.citybase.retrofit;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;

import chi_software.citybase.data.FieldResponse;
import chi_software.citybase.data.activ_service.ServiceResponse;
import chi_software.citybase.data.getBase.BaseGet;
import chi_software.citybase.data.login.LoginResponse;
import chi_software.citybase.data.login.UserResonse;
import chi_software.citybase.data.menuSearch.MenuSearch;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * Created by Papin on 09.11.2016.
 */

public class RestApiWrapper {

    private API api;
    private static RestApiWrapper instanse;

    public static RestApiWrapper getInstanse () {
        if ( instanse == null ) {
            instanse = new RestApiWrapper();
        }
        return instanse;
    }

    public RestApiWrapper () {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").create();
        Retrofit retrofit = new Retrofit.Builder().baseUrl("http://api.citybase.in.ua/api/app/").client(client).addConverterFactory(GsonConverterFactory.create(gson)).build();

        api = retrofit.create(API.class);
    }

    // **************** AUTH **************
    public Response<LoginResponse> login (String login, String password) throws IOException {
        Call<LoginResponse> myResponseCall = api.login(login, password);
        return myResponseCall.execute();
    }

    public Response<FieldResponse> registration (String phone, String pass, String name, Integer userType) throws IOException {
        Call<FieldResponse> callRegistration = api.registration(phone, pass, name, userType);
        return callRegistration.execute();
    }

    public Response<FieldResponse> activateAcount (String uid, String key, String code) throws IOException {
        Call<FieldResponse> callActivate = api.activationAccount(uid, key, code);
        return callActivate.execute();
    }

    public Response<UserResonse> getUser (String uid, String key) throws IOException {
        Call<UserResonse> userResonseCall = api.getUser(uid, key);
        return userResonseCall.execute();
    }


    // ******************** SMS *******************
    public Response<FieldResponse> sendSMS (String id, String key) throws IOException {
        Call<FieldResponse> callSendSMS = api.sendSms(id, key);
        return callSendSMS.execute();
    }


    // ******************* SEARCH ***************
    public Response<BaseGet> getBase (String search, String city, String table, String uid, String key) throws IOException {
        Call<BaseGet> baseGetCall = api.getBase(search, city, table, uid, key);
        return baseGetCall.execute();
    }

    public Response<MenuSearch> searchMenu (String city, String table, String uid, String key) throws IOException {
        Call<MenuSearch> menuSearchCall = api.getMenuSearch(city, table, uid, key);
        return menuSearchCall.execute();
    }

    public Response<BaseGet> getTrialBase (String city, String table, String ruscity, String type, String place, String basetype, String basetype2) throws IOException {
        Call<BaseGet> baseGetCall = api.getTrialBase(city, table, ruscity, type, place, basetype, basetype2);
        return baseGetCall.execute();
    }


    // *************** EDIT FIELD *******************
    public Response<FieldResponse> setColor (String uid, String key, String city, String table, String objId, String field, Integer color) throws IOException {
        Call<FieldResponse> callColor = api.setColor(uid, key, city, table, objId, field, color);
        return callColor.execute();
    }

    public Response<FieldResponse> setComment (String uid, String key, String city, String table, String objId, String field, String comment) throws IOException {
        Call<FieldResponse> calComment = api.setComment(uid, key, city, table, objId, field, comment);
        return calComment.execute();
    }


    // *************** EDIT USER *********************
    public Response<FieldResponse> addUserEmail (String uid, String key, String email) throws IOException {
        Call<FieldResponse> callAddEmail = api.addUserEmail(uid, key, email);
        return callAddEmail.execute();
    }

    public Response<FieldResponse> deleteUserEmail (String uid, String key) throws IOException {
        Call<FieldResponse> callDeleteEmail = api.deleteUserEmail(uid, key);
        return callDeleteEmail.execute();
    }

    public Response<FieldResponse> editUserLogin (String uid, String key, String name, String login) throws IOException {
        Call<FieldResponse> callEditLogin = api.editUserLogin(uid, key, name, login);
        return callEditLogin.execute();
    }

    public Response<FieldResponse> editUserPassword (String uid, String key, String password, String reenterpassword) throws IOException {
        Call<FieldResponse> callEditPassword = api.editUserPassword(uid, key, password, reenterpassword);
        return callEditPassword.execute();
    }

    // ******************** AMOUNT ********************
    public Response<FieldResponse> getAmount (String uid, String key) throws IOException {
        Call<FieldResponse> callGetAmount = api.getAmount(uid, key);
        return callGetAmount.execute();
    }

    public Response<ServiceResponse> getActivService (String city, String uid, String key) throws IOException {
        Call<ServiceResponse> serviceResponseCall = api.getActivServise(city, uid, key);
        return serviceResponseCall.execute();
    }
}
