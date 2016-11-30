package chi_software.citybase.ui;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.rengwuxian.materialedittext.MaterialEditText;

import chi_software.citybase.R;
import chi_software.citybase.core.BaseActivity;
import chi_software.citybase.core.api.Net;
import chi_software.citybase.data.login.LoginResponse;
import dmax.dialog.SpotsDialog;


/**
 * Created by Papin on 17.11.2016.
 */

public class RegistrationActivity extends BaseActivity implements View.OnClickListener {

    private MaterialEditText pass;
    private MaterialEditText phone;
    private MaterialEditText name;
    private String passS;
    private String phoneS;
    private String key, mId;
    private SpotsDialog dialogLoading;
    private int user_type;

    @Override
    protected void onCreate (@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration_layout);
        dialogLoading = new SpotsDialog(RegistrationActivity.this);
        pass = (MaterialEditText) findViewById(R.id.registPassET);
        phone = (MaterialEditText) findViewById(R.id.registNumberET);
        name = (MaterialEditText) findViewById(R.id.registNameET);
        Button butOk = (Button) findViewById(R.id.buttonReg);
        user_type = 0;
        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.radioGroup);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged (RadioGroup group, int checkedId) {
                switch ( checkedId ) {
                    case R.id.radioButton1:
                        user_type = 1;
                        break;
                    case R.id.radioButton2:
                        user_type = 2;
                        break;
                    case R.id.radioButton3:
                        user_type = 3;
                        break;
                }
            }
        });
        butOk.setOnClickListener(this);
    }

    @Override
    public void onClick (View v) {
        switch ( v.getId() ) {
            case R.id.buttonReg:
                passS = pass.getText().toString();
                phoneS = phone.getText().toString();
                String nameS = name.getText().toString();
                nameS = name.getText().toString();
                if ( user_type != 0 ) {
                    app.getNet().registration(phoneS, passS, nameS, user_type);
                    dialogLoading.show();
                }else
                    Toast.makeText(this, "Выберите один из типов использования", Toast.LENGTH_SHORT).show();
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
                Intent sms = new Intent(RegistrationActivity.this, SmsDialog.class);
                sms.putExtra("key", key);
                sms.putExtra("mId", mId);
                startActivity(sms);
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
            case Net.ACTIVATE_ACOUNT:
                Toast.makeText(this, "ERROR", Toast.LENGTH_SHORT).show();
        }
    }
}
