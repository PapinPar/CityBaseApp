package chi_software.citybase.ui;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.rengwuxian.materialedittext.MaterialEditText;

import chi_software.citybase.R;
import chi_software.citybase.SharedCityBase;
import chi_software.citybase.core.BaseActivity;
import chi_software.citybase.core.api.Net;
import chi_software.citybase.data.getBase.BaseGet;
import chi_software.citybase.data.login.LoginResponse;
import chi_software.citybase.ui.forgotPass.ForgotPassActivity;
import dmax.dialog.SpotsDialog;


/**
 * Created by Papin on 08.11.2016.
 */

public class StartScreen extends BaseActivity implements View.OnClickListener {

    private MaterialEditText mPhoneLoginEditText, mPassLoginEditText;
    private SpotsDialog mDialog;
    private String mCity, mUser, mPass;
    private boolean mState = false;

    @SuppressWarnings("ConstantConditions")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_screen_layout);
        mPhoneLoginEditText = (MaterialEditText) findViewById(R.id.phoneLoginNew);
        mPassLoginEditText = (MaterialEditText) findViewById(R.id.passwordLoginNew);
        findViewById(R.id.butOkNew).setOnClickListener(this);
        findViewById(R.id.butTryBaseNew).setOnClickListener(this);
        findViewById(R.id.registNewTW).setOnClickListener(this);
        findViewById(R.id.forgotTV).setOnClickListener(this);
        mDialog = new SpotsDialog(StartScreen.this, "Загрузка");

        loadUser();

    }

    private void loadUser() {
        mPass = SharedCityBase.GetPassword(this);
        mUser = SharedCityBase.GetLogin(this);
        if (mPass.length() > 1 && mUser.length() > 1) {
            mDialog.show();
            mState = true;
            app.getNet().login(mUser, mPass);
        }
    }

    @SuppressLint("SwitchIntDef")
    @Override
    public void onNetRequestDone(@Net.NetEvent int eventId, Object NetObjects) {
        super.onNetRequestDone(eventId, NetObjects);
        switch (eventId) {
            case Net.TRIAL_BASE:
                BaseGet baseGet = (BaseGet) NetObjects;
                Log.d("StartScreen", "baseGet.getGetResponse().getModel():" + baseGet.getGetResponse().getTrialObjects());
                break;
            case Net.SIGN_IN:
                LoginResponse loginResponse = (LoginResponse) NetObjects;
                if (loginResponse.getMyResponse().getActive().equals("1")) {
                    SharedCityBase.SetLogin(StartScreen.this, mUser);
                    SharedCityBase.SetPassword(StartScreen.this, mPass);
                    SharedCityBase.SaveUID(this, loginResponse.getMyResponse().getId());
                    SharedCityBase.SaveKey(this, loginResponse.getMyResponse().getKey());
                    mDialog.dismiss();
                    if (!mState) {
                        SharedCityBase.SaveCity(this, mCity);
                        onCreateDialog();
                    }else {
                        startMainActivity();
                    }
                } else
                    Toast.makeText(this, "Пожалуйста подвердите номер телефона", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @SuppressLint("SwitchIntDef")
    @Override
    public void onNetRequestFail(@Net.NetEvent int eventId, Object NetObjects) {
        super.onNetRequestFail(eventId, NetObjects);
        switch (eventId) {
            case Net.SIGN_IN:
                mDialog.dismiss();
                if (!mState) {
                    if (!isNetworkConnected())
                        Toast.makeText(this, "Проверьте интернет соединение", Toast.LENGTH_SHORT).show();
                    if (isNetworkConnected())
                        Toast.makeText(this, "Введен не правильный логин или пароль", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.butOkNew:
                if (isNetworkConnected()) {
                    mDialog.show();
                    signIn();
                }
                if (!isNetworkConnected())
                    Toast.makeText(this, "Проверьте интернр соединение", Toast.LENGTH_SHORT).show();
                break;
            case R.id.registNewTW:
                startNewActivity(RegistrationActivity.class);
                break;
            case R.id.butTryBaseNew:
                app.getNet().tryBase("_Kharkov", "rent_living", "Харьков", "1 к", "Салтовка", "rent", "living");
                break;
            case R.id.forgotTV:
                startNewActivity(ForgotPassActivity.class);
                break;
        }
    }

    private void startNewActivity(Class registrationActivityClass) {
        Intent register = new Intent(StartScreen.this, registrationActivityClass);
        startActivity(register);
    }

    private void signIn() {
        //app.getNet().login(sEmai, sPass);
        //app.getNet().login("0664382589", "test123456");
        //app.getNet().login("0638367925", "papin1");
        mUser = mPhoneLoginEditText.getText().toString();
        mPass = mPassLoginEditText.getText().toString();
        //mUser = "0506803241";
        //mPass = "123456";
        if (mUser.length() > 0 && mPass.length() > 0) {
            app.getNet().login(mUser, mPass);
        } else {
            mDialog.dismiss();
            Toast.makeText(this, "Пожалуйста введите свои данные", Toast.LENGTH_SHORT).show();
        }
    }

    protected void onCreateDialog() {
        AlertDialog.Builder builder;
        final String[] mCityChoose = {"Киев", "Харьков", "Одесса"};
        builder = new AlertDialog.Builder(this);
        builder.setTitle("Выберите Город");
        builder.setItems(mCityChoose, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (item == 0)
                    mCity = "_Kyiv";
                if (item == 1)
                    mCity = "_Kharkov";
                if (item == 2)
                    mCity = "_Odessa";
                startMainActivity();
            }
        });
        builder.setCancelable(false);
        builder.show();
    }

    private void startMainActivity() {
        Intent startMainScreen = new Intent(StartScreen.this, MainActivity.class);
        startActivity(startMainScreen);
        finish();
    }
}
