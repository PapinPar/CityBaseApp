package chi_software.citybase.ui.splash;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.widget.Toast;

import chi_software.citybase.R;
import chi_software.citybase.core.BaseActivity;
import chi_software.citybase.core.api.Net;
import chi_software.citybase.data.login.LoginResponse;
import chi_software.citybase.ui.MainActivity;
import chi_software.citybase.ui.StartScreen;
import chi_software.citybase.utils.SharedCityBase;

public class SplashActivity extends BaseActivity {

    private String mCity, mUser, mPass, mKey, mId;
    boolean isFirst;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_layout);
        isFirst = SharedCityBase.getFirst(SplashActivity.this);
        float dpi = getResources().getDisplayMetrics().density;
        SharedCityBase.setDPI(SplashActivity.this, dpi);
        start();

    }

    private void start() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (isFirst) {
                    SharedCityBase.setFirst(SplashActivity.this, false);
                    startActivity(TutorialActivity.class);
                } else {
                    loadUser();
                }
            }
        }, 2000);
    }

    private void loadUser() {
        mPass = SharedCityBase.GetPassword(this);
        mUser = SharedCityBase.GetLogin(this);
        if (mPass.length() > 1 && mUser.length() > 1) {
            app.getNet().login(mUser, mPass);
        } else {
            startActivity(StartScreen.class);
        }
    }

    @Override
    public void onNetRequestDone(@Net.NetEvent int eventId, Object NetObjects) {
        super.onNetRequestDone(eventId, NetObjects);
        switch (eventId) {
            case Net.SIGN_IN:
                LoginResponse loginResponse = (LoginResponse) NetObjects;
                if (loginResponse.getMyResponse().getActive().equals("1")) {
                    SharedCityBase.SetLogin(SplashActivity.this, mUser);
                    SharedCityBase.SetPassword(SplashActivity.this, mPass);
                    SharedCityBase.SaveUID(this, loginResponse.getMyResponse().getId());
                    SharedCityBase.SaveKey(this, loginResponse.getMyResponse().getKey());
                    startActivity(MainActivity.class);
                }
                break;
        }
    }

    @Override
    public void onNetRequestFail(@Net.NetEvent int eventId, Object NetObjects) {
        super.onNetRequestFail(eventId, NetObjects);
        String error = NetObjects.toString();
        if (error.contains("timed out")) {
            Toast.makeText(this, "Произошла ошибка. \n Пожалуйста попробуйте восстановить пароль через наш сайт citybase.com.ua", Toast.LENGTH_SHORT).show();
        }
        Toast.makeText(this, "Пожалуйста проверьте интернет соединение", Toast.LENGTH_SHORT).show();
        startActivity(StartScreen.class);
    }

    private void startActivity(Class starterClass) {
        Intent startMainScreen = new Intent(SplashActivity.this, starterClass);
        startActivity(startMainScreen);
        finish();
    }
}
