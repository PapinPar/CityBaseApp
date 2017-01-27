package chi_software.citybase.retrofit;

import chi_software.citybase.data.FieldResponse;
import chi_software.citybase.data.activ_service.ServiceResponse;
import chi_software.citybase.data.getBase.BaseGet;
import chi_software.citybase.data.history_amount.HistoryResponse;
import chi_software.citybase.data.login.LoginResponse;
import chi_software.citybase.data.login.UserResonse;
import chi_software.citybase.data.menuSearch.MenuSearch;
import chi_software.citybase.data.tarif.Tariff;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Papin on 09.11.2016.
 */

public interface API {

    // ************** AUTH *****************
    @GET("login.auth")
    Call<LoginResponse> login (@Query("login") String login, @Query("pass") String password);

    @GET("login.registration")
    Call<FieldResponse> registration (@Query("phone") String phone, @Query("pass") String pass, @Query("name") String name, @Query("user_type") Integer userType);

    @GET("login.ActivationAccount")
    Call<FieldResponse> activationAccount (@Query("uid") String uid, @Query("key") String key, @Query("code") String code);

    @GET("login.getUser")
    Call<UserResonse> getUser (@Query("uid") String uid, @Query("key") String key);


    //login.getUser?uid=126&key=8ef5951de284b0046b9f79ca09b1de771e09e6b9

    // ************* EDIT USER ****************
    @GET("login.EDIT_USER_LOGIN")
    Call<FieldResponse> editUserLogin (@Query("uid") String uid, @Query("key") String key, @Query("name") String name, @Query("login") String login);

    @GET("login.EDIT_USER_PASSWORD")
    Call<FieldResponse> editUserPassword (@Query("uid") String uid, @Query("key") String key, @Query("password") String password, @Query("reenterpassword") String reenterpassword);

    @GET("login.ADD_USER_EMAIL")
    Call<FieldResponse> addUserEmail (@Query("uid") String uid, @Query("key") String key, @Query("email") String email);

    @GET("login.DELETE_USER_EMAIL")
    Call<FieldResponse> deleteUserEmail (@Query("uid")String uid,@Query("key")String key);

    // ************ SEARCH ****************
    @GET("Base.get")
    Call<BaseGet> getBase (@Query("search") String search, @Query("city") String city,
                           @Query("table") String table, @Query("uid") String uid, @Query("key") String key,@Query("page") Integer page);

    @GET("Base.searchmenu")
    Call<MenuSearch> getMenuSearch (@Query("city") String city, @Query("table") String table, @Query("uid") String iod, @Query("key") String key);

    @GET("Base.getobjforindex")
    Call<BaseGet> getTrialBase(@Query("city") String city, @Query("table") String table, @Query("ruscity") String ruscity,
                               @Query("type") String type, @Query("place") String place, @Query("basetype") String basetype, @Query("basetype2") String basetype2);


    // ************* EDIT FIELD ****************
    @GET("Base.editfield")
    Call<FieldResponse> setColor (@Query("uid") String uid, @Query("key") String key,
                                  @Query("city") String city, @Query("table") String table,
                                  @Query("objid") String objId, @Query("field") String field, @Query("value") Integer color);

    @GET("Base.editfield")
    Call<FieldResponse> setComment (@Query("uid") String uid, @Query("key") String key,
                                    @Query("city") String city, @Query("table") String table,
                                    @Query("objid") String objId, @Query("field") String field, @Query("value") String color);


    // **************** SMS ********************
    @GET("sms.smsregistration")
    Call<FieldResponse> sendSms (@Query("uid") String id, @Query("key") String key);

    // ***************** AMOUNT ***************
    @GET("payments.getAmount")
    Call<FieldResponse> getAmount (@Query("uid") String id, @Query("key") String key);

    @GET("orders.getactivityorders")
    Call<ServiceResponse> getActivServise (@Query("citysite") String city, @Query("uid") String uid, @Query("key") String key);

    @GET("payments.getHistoryAmount")
    Call<HistoryResponse> getHistoryAmount (@Query("uid") String uid, @Query("key") String key);

    @GET("orders.gettarifs")
    Call<Tariff> getTarifs (@Query("city") String city, @Query("uid") String uid, @Query("key") String key);
}
