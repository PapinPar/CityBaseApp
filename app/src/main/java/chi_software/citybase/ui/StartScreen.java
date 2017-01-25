package chi_software.citybase.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.rengwuxian.materialedittext.MaterialEditText;

import chi_software.citybase.R;
import chi_software.citybase.core.BaseActivity;
import chi_software.citybase.core.api.Net;
import chi_software.citybase.data.getBase.BaseGet;
import chi_software.citybase.data.login.LoginResponse;


/**
 * Created by Papin on 08.11.2016.
 */

public class StartScreen extends BaseActivity implements View.OnClickListener {

    private MaterialEditText mPhoneLoginEditText, mPassLoginEditText;

    @Override
    protected void onCreate (@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_screen_layout);
        mPhoneLoginEditText = (MaterialEditText) findViewById(R.id.phoneLoginNew);
        mPassLoginEditText = (MaterialEditText) findViewById(R.id.passwordLoginNew);
        findViewById(R.id.butOkNew).setOnClickListener(this);
        findViewById(R.id.butTryBaseNew).setOnClickListener(this);
        findViewById(R.id.registNewTW).setOnClickListener(this);
    }

    @Override
    public void onNetRequestDone (@Net.NetEvent int eventId, Object NetObjects) {
        super.onNetRequestDone(eventId, NetObjects);
        switch ( eventId ) {
            case Net.TRIAL_BASE:
                BaseGet baseGet = (BaseGet) NetObjects;
                Log.d("StartScreen", "baseGet.getGetResponse().getModel():" + baseGet.getGetResponse().getTrialObjects());
                break;
            case Net.SIGN_IN:
                LoginResponse loginResponse = (LoginResponse) NetObjects;
                if ( loginResponse.getMyResponse().getActive().equals("1") ) {
                    Intent startMainScreen = new Intent(StartScreen.this, MainScreen.class);
                    startMainScreen.putExtra(MainScreen.UID, loginResponse.getMyResponse().getId());
                    startMainScreen.putExtra(MainScreen.KEY, loginResponse.getMyResponse().getKey());
                    startActivity(startMainScreen);
                    finish();
                }
                else
                    Toast.makeText(this, "Пожалуйста подвердите номер телефона", Toast.LENGTH_SHORT).show();
                break;
        }
    }
    @Override
    public void onNetRequestFail (@Net.NetEvent int eventId, Object NetObjects) {
        super.onNetRequestFail(eventId, NetObjects);
        switch ( eventId ) {
            case Net.SIGN_IN:
                if ( !isNetworkConnected() )
                    Toast.makeText(this, "Проверьте интернр соединение", Toast.LENGTH_SHORT).show();
                if ( isNetworkConnected() )
                    Toast.makeText(this, "Введен не правильный логин или пароль", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    public void onClick (View v) {
        switch ( v.getId() ) {
            case R.id.butOkNew:
                signIn();
                break;
            case R.id.registNewTW:
                Intent register = new Intent(StartScreen.this, RegistrationActivity.class);
                startActivity(register);
                break;
            case R.id.butTryBaseNew:
                app.getNet().tryBase("_Kharkov", "rent_living", "Харьков", "1 к", "Салтовка", "rent", "living");
                break;

        }
    }

    private void signIn () {
        String email = mPhoneLoginEditText.getText().toString();
        String password = mPassLoginEditText.getText().toString();
        //app.getNet().login(sEmai, sPass);
        //app.getNet().login("0664382589", "test123456");
        //app.getNet().login("0638367925", "papin1");
        app.getNet().login("0506803241", "123456");
    }
}
