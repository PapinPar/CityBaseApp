package chi_software.citybase.ui;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.rengwuxian.materialedittext.MaterialEditText;

import chi_software.citybase.R;
import chi_software.citybase.core.BaseActivity;
import chi_software.citybase.core.api.Net;
import chi_software.citybase.data.login.LoginResponse;
import chi_software.citybase.ui.dialog.SmsDialog;
import dmax.dialog.SpotsDialog;


/**
 * Created by Papin on 17.11.2016.
 */

public class RegistrationActivity extends BaseActivity implements View.OnClickListener, SmsDialog.SmsListner {

    private MaterialEditText pass;
    private MaterialEditText phone;
    private MaterialEditText name;
    private Button butOk;
    private String passS, phoneS, nameS;
    private SmsDialog smsDialog;
    private String key, mId;
    private SpotsDialog dialogLoading;

    @Override
    protected void onCreate (@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration_layout);
        smsDialog = new SmsDialog();
        dialogLoading = new SpotsDialog(RegistrationActivity.this);
        pass = (MaterialEditText) findViewById(R.id.registPassET);
        phone = (MaterialEditText) findViewById(R.id.registNumberET);
        name = (MaterialEditText) findViewById(R.id.registNameET);
        butOk = (Button) findViewById(R.id.buttonReg);
        butOk.setOnClickListener(this);
    }

    @Override
    public void onClick (View v) {
        switch ( v.getId() ) {
            case R.id.buttonReg:
                passS = pass.getText().toString();
                phoneS = phone.getText().toString();
                nameS = name.getText().toString();
                nameS = name.getText().toString();
                app.getNet().registration(phoneS, passS, nameS);
                dialogLoading.show();
                break;
        }
    }

    @Override
    public void onNetRequestDone (@Net.NetEvent int eventId, Object NetObjects) {
        super.onNetRequestDone(eventId, NetObjects);
        switch ( eventId ) {
            case Net.REGISTRATION:
                app.getNet().login(phoneS, passS);
                break;
            case Net.SIGN_IN:
                LoginResponse loginResponse = (LoginResponse) NetObjects;
                key = loginResponse.getMyResponse().getKey();
                mId = loginResponse.getMyResponse().getId();
                app.getNet().sendSms(mId, key);
                break;
            case Net.SEND_SMS:
                Log.d("PAPIN_TAG", "sms");
                dialogLoading.dismiss();
                smsDialog.setGetSmsListnet(RegistrationActivity.this);
                smsDialog.setCancelable(false);
                smsDialog.show(getFragmentManager(), "");
                break;
            case Net.ACTIVATE_ACOUNT:
                dialogLoading.dismiss();
                Toast.makeText(this, "ok", Toast.LENGTH_SHORT).show();
                finish();
                break;
        }
    }

    @Override
    public void onNetRequestFail (@Net.NetEvent int eventId, Object NetObjects) {
        super.onNetRequestFail(eventId, NetObjects);
        switch ( eventId ) {
            case Net.REGISTRATION:
                dialogLoading.dismiss();
                Toast.makeText(this, "ERROR", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    public void getSmsListner (String answer) {
        dialogLoading.show();
        app.getNet().ActivateAcount(mId, key, answer);
    }
}
