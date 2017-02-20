package chi_software.citybase.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
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

public class RegistrationActivity extends BaseActivity implements View.OnClickListener, TextWatcher {

    private MaterialEditText metPass;
    private MaterialEditText metPhone;
    private MaterialEditText metName;
    private MaterialEditText metPassAgain;
    private String mPassAgain;
    private String mPass;
    private String mPhone;
    private String mName;
    private String mKey, mId;
    private SpotsDialog mDialogLoading;
    private int mUserType;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration_layout);
        mDialogLoading = new SpotsDialog(RegistrationActivity.this, "Загрузка");
        metPass = (MaterialEditText) findViewById(R.id.registPassET);
        metPhone = (MaterialEditText) findViewById(R.id.registNumberET);
        metName = (MaterialEditText) findViewById(R.id.registNameET);
        metPassAgain = (MaterialEditText) findViewById(R.id.registPassAgainET);
        metPhone.addTextChangedListener(this);
        Button butOk = (Button) findViewById(R.id.buttonReg);
        mUserType = 0;
        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.radioGroup);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
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
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonReg:
                mPass = metPass.getText().toString();
                mPhone = metPhone.getText().toString();
                mName = metName.getText().toString();
                mPassAgain = metPassAgain.getText().toString();
                boolean valid = isValid();
                if (valid) {
                    app.getNet().registration(mPhone, mPass, mName, mUserType);
                    mDialogLoading.show();
                }

        }
    }

    private boolean isValid() {
        if (mName.length() < 3) {
            Toast.makeText(this, "Введите не менее, чем 3 символа в поле имени", Toast.LENGTH_SHORT).show();
        } else {
            if (mPass.length() > 12 || mPass.length() < 6) {
                Toast.makeText(this, "Пароль должен быть не меньше 6 и не больше 12 символов", Toast.LENGTH_SHORT).show();
                return false;
            }
            if (mPhone.length() <=12) {
                Toast.makeText(this, "Введите действительный номер мобильного телефона", Toast.LENGTH_SHORT).show();
                return false;
            } else {
                if (mUserType != 0) {
                    if (mPass.length() > 0 && mPhone.length() > 0 && mName.length() > 0) {
                        if (mPass.equals(mPassAgain)) {
                            return true;
                        } else {
                            Toast.makeText(this, "Пароли должны совпадать", Toast.LENGTH_SHORT).show();
                            return false;
                        }
                    } else {
                        Toast.makeText(this, "Пожалуйста заполните все поля", Toast.LENGTH_SHORT).show();
                        return false;
                    }
                } else {
                    Toast.makeText(this, "Выберите один из типов использования", Toast.LENGTH_SHORT).show();
                    return false;
                }
            }
        }
        return false;
    }

    @Override
    public void onNetRequestDone(@Net.NetEvent int eventId, Object NetObjects) {
        super.onNetRequestDone(eventId, NetObjects);
        switch (eventId) {
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
                finish();
                break;
        }
    }

    @Override
    public void onNetRequestFail(@Net.NetEvent int eventId, Object NetObjects) {
        super.onNetRequestFail(eventId, NetObjects);
        switch (eventId) {
            case Net.REGISTRATION:
                mDialogLoading.dismiss();
                if (NetObjects.toString().contains("exists")) {
                    Toast.makeText(this, "Данный номер уже зарегестрирован", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Произошел сбой.Проверьте своё интернет подключение.", Toast.LENGTH_SHORT).show();
                }
                break;
            case Net.ACTIVATE_ACOUNT:
                mDialogLoading.dismiss();
                Toast.makeText(this, "Произошел сбой.Проверьте своё интернет подключение.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (s.toString().length() > 0) {
            if (s.charAt(0) != '+') {
                String change_text = s.toString().replaceAll("\\+", "");
                s = "+38" + change_text;
                metPhone.setText(s);
                metPhone.setSelection(4);
            }
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
