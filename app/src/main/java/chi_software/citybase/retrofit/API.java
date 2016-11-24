package chi_software.citybase.retrofit;

import chi_software.citybase.data.FieldResponse;
import chi_software.citybase.data.getBase.BaseGet;
import chi_software.citybase.data.login.LoginResponse;
import chi_software.citybase.data.menuSearch.MenuSearch;
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
    Call<FieldResponse> registration (@Query("phone") String phone, @Query("pass") String pass, @Query("name") String name);

    @GET("login.ActivationAccount")
    Call<FieldResponse> activationAccount (@Query("uid") String uid, @Query("key") String key, @Query("code") String code);

    // ************* EDIT USER ****************
    @GET("login.EditUserLogin")
    Call<FieldResponse> editUserLogin (@Query("uid") String uid, @Query("key") String key, @Query("name") String name, @Query("login") String login);

    @GET("login.EditUserPassword")
    Call<FieldResponse> editUserPassword (@Query("uid") String uid, @Query("key") String key, @Query("password") String password, @Query("reenterpassword") String reenterpassword);

    @GET("login.AddUserEmail")
    Call<FieldResponse> addUserEmail (@Query("uid") String uid, @Query("key") String key, @Query("email") String email);

    @GET("login.DeleteUserEmail")
    Call<FieldResponse> deleteUserEmail (@Query("uid")String uid,@Query("key")String key);

    // ************ SEARCH ****************
    @GET("Base.get")
    Call<BaseGet> getBase (@Query("search") String search, @Query("city") String city,
                           @Query("table") String table, @Query("uid") String uid, @Query("key") String key);

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
}
