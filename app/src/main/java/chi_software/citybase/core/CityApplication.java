package chi_software.citybase.core;

import android.app.Application;
import android.content.Context;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import chi_software.citybase.core.api.App;
import chi_software.citybase.core.api.Net;
import chi_software.citybase.retrofit.ConnectionManager;

/**
 * Created by Papin on 08.11.2016.
 */

public class CityApplication extends Application implements App {
    private Executor executor;
    private Net net;

    @Override
    public void onCreate () {
        super.onCreate();
        executor = Executors.newFixedThreadPool(2);
        net = new ConnectionManager(executor);
    }

    @Override
    public Net getNet () {
        return net;
    }

    @Override
    public Executor getExecutor () {
        return executor;
    }

    public static App getApp(Context context){
        return (App) context.getApplicationContext();}
}
