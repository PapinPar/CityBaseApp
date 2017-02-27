package chi_software.citybase.core;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.inputmethod.InputMethodManager;

import chi_software.citybase.utils.SharedCityBase;
import chi_software.citybase.core.api.App;
import chi_software.citybase.core.api.Net;
import chi_software.citybase.core.api.NetSubscriber;
import chi_software.citybase.ui.StartScreen;

/**
 * Created by Papin on 09.11.2016.
 */

public class BaseActivity extends AppCompatActivity implements NetSubscriber {

    protected App app;

    @Override
    protected void onCreate (@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        app = CityApplication.getApp(this);
        Log.d("PAPIN_TAG", "createBase");
    }

    @Override
    protected void onStart () {
        super.onStart();
        if ( !app.getNet().IsSubscribe(this) ) {
            app.getNet().Subscribe(this);
            Log.d("PAPIN_TAG","Subscribe");
        }
    }

    @Override
    protected void onStop () {
        super.onStop();
        app.getNet().UnSubscribe(this);
    }

    public boolean isNetworkConnected () {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }

    public void startScreen(){
        SharedCityBase.SetPassword(this,"");
        SharedCityBase.SetLogin(this,"");
        Intent startActivity = new Intent(BaseActivity.this, StartScreen.class);
        startActivity(startActivity);
    }

    public void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
    }

    @Override
    public void onNetRequestDone (@Net.NetEvent int eventId, Object NetObjects) {

    }

    @Override
    public void onNetRequestFail (@Net.NetEvent int eventId, Object NetObjects) {

    }
}
