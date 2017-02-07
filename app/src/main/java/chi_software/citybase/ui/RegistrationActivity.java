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

    private MaterialEditText metPass;
    private MaterialEditText metPhone;
    private MaterialEditText metName;
    private String mPass;
    private String mPhone;
    private String mName;
    private String mKey, mId;
    private SpotsDialog mDialogLoading;
    private int mUserType;

    @Override
    protected void onCreate (@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration_layout);
        mDialogLoading = new SpotsDialog(RegistrationActivity.this,"Загрузка");
        metPass = (MaterialEditText) findViewById(R.id.registPassET);
        metPhone = (MaterialEditText) findViewById(R.id.registNumberET);
        metName = (MaterialEditText) findViewById(R.id.registNameET);
        Button butOk = (Button) findViewById(R.id.buttonReg);
        mUserType = 0;
        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.radioGroup);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged (RadioGroup group, int checkedId) {
                switch ( checkedId ) {
                    case R.id.radioButton1:
                        mUserType = 1;
                        break;
                    case R.id.radioButton2:
                        mUserType = 2;
                        break;
                    case R.id.radioButton3:
                        mUserType = 3;
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
                mPass = metPass.getText().toString();
                mPhone = metPhone.getText().toString();
                mName = metName.getText().toString();
                if ( mUserType != 0 ) {
                    app.getNet().registration(mPhone, mPass, mName, mUserType);
                    mDialogLoading.show();
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
                app.getNet().login(mPhone, mPass);
                break;
            case Net.SIGN_IN:
                LoginResponse loginResponse = (LoginResponse) NetObjects;
                mKey = loginResponse.getMyResponse().getKey();
                mId = loginResponse.getMyResponse().getId();
                app.getNet().sendSms(mId, mKey);
                break;
            case Net.SEND_SMS:
                Log.d("PAPIN_TAG", "sms");
                mDialogLoading.dismiss();
                Intent sms = new Intent(RegistrationActivity.this, SmsActivity.class);
                sms.putExtra(SmsActivity.KEY, mKey);
                sms.putExtra(SmsActivity.MYID, mId);
                startActivity(sms);
                break;
            case Net.ACTIVATE_ACOUNT:
                mDialogLoading.dismiss();
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
                mDialogLoading.dismiss();
                Toast.makeText(this, "ERROR", Toast.LENGTH_SHORT).show();
                break;
            case Net.ACTIVATE_ACOUNT:
                Toast.makeText(this, "ERROR", Toast.LENGTH_SHORT).show();
        }
    }
}
