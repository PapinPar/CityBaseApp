package chi_software.citybase.retrofit;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * Created by Papin on 09.11.2016.
 */

class RestClient {
    static Retrofit setUpClient (Executor mExecutor) {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).connectTimeout(30, TimeUnit.SECONDS)
                .build();

        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").create();
        return new Retrofit.Builder()
                .callbackExecutor(mExecutor)
                .baseUrl("http://api.citybase.in.ua/api/app/")
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }
}
