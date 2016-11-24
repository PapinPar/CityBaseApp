package chi_software.citybase.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
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

    private MaterialEditText phoneLogin, passLogin;
    private Button butOk, butTryBase;
    private TextView registration;
    private String sEmai, sPass;

    @Override
    protected void onCreate (@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_screen_layout);
        phoneLogin = (MaterialEditText) findViewById(R.id.phoneLoginNew);
        passLogin = (MaterialEditText) findViewById(R.id.passwordLoginNew);
        butOk = (Button) findViewById(R.id.butOkNew);
        butTryBase = (Button) findViewById(R.id.butTryBaseNew);
        registration = (TextView) findViewById(R.id.registNewTW);

        butOk.setOnClickListener(this);
        butTryBase.setOnClickListener(this);
        registration.setOnClickListener(this);

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
                    startMainScreen.putExtra("_id", loginResponse.getMyResponse().getId());
                    startMainScreen.putExtra("_key", loginResponse.getMyResponse().getKey());
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
        sEmai = phoneLogin.getText().toString();
        sPass = passLogin.getText().toString();
        //app.getNet().login(sEmai, sPass);
        //app.getNet().login("0664382589", "test123456");
        app.getNet().login("0638367925", "papin0");
    }
}
