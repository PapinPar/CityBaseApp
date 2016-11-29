package chi_software.citybase.ui;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import chi_software.citybase.R;
import chi_software.citybase.core.BaseActivity;


/**
 * Created by Papin on 17.11.2016.
 */

public class SmsDialog extends BaseActivity {

    private EditText smsET;
    private Button butOK;
    private String mId, key, code;

    @Override
    protected void onCreate (@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sms_activate_layout);
        smsET = (EditText) findViewById(R.id.smsCodeET);
        butOK = (Button) findViewById(R.id.butConfirnSMS);
        key = getIntent().getStringExtra("key");
        mId = getIntent().getStringExtra(mId);

        butOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v) {

                code = smsET.getText().toString();
                if ( code.length() > 1 ) {
                    app.getNet().ActivateAcount(mId, key, code);
                    finish();
                }
                else
                    Toast.makeText(SmsDialog.this, "Введите код из СМС", Toast.LENGTH_SHORT).show();

            }
        });
    }
}
