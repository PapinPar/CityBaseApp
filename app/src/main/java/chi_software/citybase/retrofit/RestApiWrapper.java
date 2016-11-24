package chi_software.citybase.retrofit;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;

import chi_software.citybase.data.FieldResponse;
import chi_software.citybase.data.getBase.BaseGet;
import chi_software.citybase.data.login.LoginResponse;
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
        Response response = myResponseCall.execute();
        return response;
    }

    public Response<FieldResponse> registration (String phone, String pass, String name) throws IOException{
        Call<FieldResponse> callRegistration = api.registration(phone, pass, name);
        Response response = callRegistration.execute();
        return response;
    }

    public Response<FieldResponse> activateAcount (String uid, String key, String code) throws IOException {
        Call<FieldResponse> callActivate = api.activationAccount(uid, key, code);
        Response response = callActivate.execute();
        return response;
    }



    // ******************** SMS *******************
    public Response<FieldResponse> sendSMS (String id, String key) throws IOException {
        Call<FieldResponse> callSendSMS = api.sendSms(id, key);
        Response response = callSendSMS.execute();
        return response;
    }



    // ******************* SEARCH ***************
    public Response<BaseGet> getBase (String search, String city, String table, String uid, String key) throws IOException {
        Call<BaseGet> baseGetCall = api.getBase(search, city, table, uid, key);
        Response response = baseGetCall.execute();
        return response;
    }

    public Response<MenuSearch> searchMenu (String city, String table, String uid, String key) throws IOException {
        Call<MenuSearch> menuSearchCall = api.getMenuSearch(city, table, uid, key);
        Response response = menuSearchCall.execute();
        return response;
    }

    public Response<BaseGet> getTrialBase(String city,  String table,  String ruscity,String type,  String place,  String basetype,  String basetype2) throws IOException{
        Call<BaseGet> baseGetCall = api.getTrialBase(city, table, ruscity, type, place, basetype, basetype2);
        Response response = baseGetCall.execute();
        return response;
    }



    // *************** EDIT FIELD *******************
    public Response<FieldResponse> setColor (String uid, String key, String city, String table, String objId, String field, Integer color) throws  IOException {
        Call<FieldResponse> callColor = api.setColor(uid, key, city, table, objId, field, color);
        Response response = callColor.execute();
        return response;
    }

    public Response<FieldResponse> setComment(String uid, String key, String city, String table, String objId, String field, String comment) throws  IOException {
        Call<FieldResponse> calComment = api.setComment(uid,key,city,table,objId,field,comment);
        Response response = calComment.execute();
        return response;
    }


    // *************** EDIT USER *********************
    public Response<FieldResponse> addUserEmail (String uid, String key, String email) throws IOException {
        Call<FieldResponse> callAddEmail = api.addUserEmail(uid, key, email);
        Response response = callAddEmail.execute();
        return response;
    }

    public Response<FieldResponse> deleteUserEmail (String uid, String key)  throws IOException{
        Call<FieldResponse> callDeleteEmail = api.deleteUserEmail(uid, key);
        Response response = callDeleteEmail.execute();
        return response;
    }

    public Response<FieldResponse> editUserLogin (String uid, String key, String name, String login) throws IOException{
        Call<FieldResponse> callEditLogin = api.editUserLogin(uid, key, name, login);
        Response response = callEditLogin.execute();
        return response;
    }

    public Response<FieldResponse> editUserPassword (String uid, String key, String password, String reenterpassword) throws IOException{
        Call<FieldResponse> callEditPassword = api.editUserPassword(uid, key, password, reenterpassword);
        Response response = callEditPassword.execute();
        return response;
    }
}
