package chi_software.citybase.ui;

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
import dmax.dialog.SpotsDialog;


/**
 * Created by Papin on 08.11.2016.
 */

public class StartScreen extends BaseActivity implements View.OnClickListener {

    private MaterialEditText mPhoneLoginEditText, mPassLoginEditText;
    private SpotsDialog mDialog;
    private String mCity;

    @Override
    protected void onCreate (@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_screen_layout);
        mPhoneLoginEditText = (MaterialEditText) findViewById(R.id.phoneLoginNew);
        mPassLoginEditText = (MaterialEditText) findViewById(R.id.passwordLoginNew);
        findViewById(R.id.butOkNew).setOnClickListener(this);
        findViewById(R.id.butTryBaseNew).setOnClickListener(this);
        findViewById(R.id.registNewTW).setOnClickListener(this);
        mDialog = new SpotsDialog(StartScreen.this);

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
                mDialog.dismiss();
                LoginResponse loginResponse = (LoginResponse) NetObjects;
                if ( loginResponse.getMyResponse().getActive().equals("1") ) {
                    Intent startMainScreen = new Intent(StartScreen.this, MainActivity.class);
                    SharedCityBase.SaveUID(this,loginResponse.getMyResponse().getId());
                    SharedCityBase.SaveKey(this,loginResponse.getMyResponse().getKey());
                    SharedCityBase.SaveCity(this, mCity);
                    startActivity(startMainScreen);
                    finish();
                } else
                    Toast.makeText(this, "Пожалуйста подвердите номер телефона", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    public void onNetRequestFail (@Net.NetEvent int eventId, Object NetObjects) {
        super.onNetRequestFail(eventId, NetObjects);
        switch ( eventId ) {
            case Net.SIGN_IN:
                mDialog.dismiss();
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
                if ( isNetworkConnected() ) {
                    mDialog.show();
                    onCreateDialog();
                }
                if ( !isNetworkConnected() )
                    Toast.makeText(this, "Проверьте интернр соединение", Toast.LENGTH_SHORT).show();
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
    protected void onCreateDialog () {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final String[] mCityChoose = { "Киев", "Харьков", "Одесса" };
        builder = new AlertDialog.Builder(this);
        builder.setTitle("Выберите Город");
        builder.setItems(mCityChoose, new DialogInterface.OnClickListener() {
            @Override
            public void onClick (DialogInterface dialog, int item) {
                if ( item == 0 )
                    mCity = "_Kyiv";
                if ( item == 1 )
                    mCity = "_Kharkov";
                if ( item == 2 )
                    mCity = "_Odessa";
                signIn();
            }
        });
        builder.setCancelable(false);
        builder.show();
    }
}
