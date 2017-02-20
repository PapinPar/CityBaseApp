package chi_software.citybase.ui;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import chi_software.citybase.R;
import chi_software.citybase.core.BaseActivity;
import chi_software.citybase.core.api.Net;
import chi_software.citybase.data.FieldResponse;


/**
 * Created by Papin on 17.11.2016.
 */

public class SmsActivity extends BaseActivity {

    public static final String KEY = "key";
    public static final String MYID = "mId";

    private EditText smsET;
    private Button mButOK;
    private String mId, mKey, mCode;

    @Override
    protected void onCreate (@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sms_activate_layout);
        smsET = (EditText) findViewById(R.id.smsCodeET);
        mButOK = (Button) findViewById(R.id.butConfirnSMS);
        mKey = getIntent().getStringExtra(KEY);
        mId = getIntent().getStringExtra(MYID);

        mButOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v) {

                mCode = smsET.getText().toString();
                if ( mCode.length() > 1 ) {
                    app.getNet().ActivateAccount(mId, mKey, mCode);
                }
                else
                    Toast.makeText(SmsActivity.this, "Введите код из СМС", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onNetRequestDone(@Net.NetEvent int eventId, Object NetObjects) {
        super.onNetRequestDone(eventId, NetObjects);
        switch (eventId){
            case Net.ACTIVATE_ACOUNT:
                FieldResponse response = (FieldResponse)NetObjects;
                if(response.getServerResponse().contains("Ok")){
                    finish();
                }
                else{
                    Toast.makeText(this, "Введен непарвльный код", Toast.LENGTH_SHORT).show();
                }
        }
    }
}
