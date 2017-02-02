package chi_software.citybase.ui.forgotPass;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Toast;

import com.rengwuxian.materialedittext.MaterialEditText;

import chi_software.citybase.R;
import chi_software.citybase.core.BaseActivity;
import chi_software.citybase.core.api.Net;
import chi_software.citybase.data.FieldResponse;
import dmax.dialog.SpotsDialog;


@SuppressWarnings("ConstantConditions")
public class ForgotPassActivity extends BaseActivity implements View.OnClickListener {

    private MaterialEditText mPhone;
    private String phoneS;
    private SpotsDialog mDialog;


    @Override
    protected void onCreate (@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgot_pass_layout);
        findViewById(R.id.butForgetOK).setOnClickListener(this);
        mPhone = (MaterialEditText) findViewById(R.id.forgetNumberET);
        mDialog = new SpotsDialog(ForgotPassActivity.this);

    }

    @Override
    public void onClick (View v) {
        switch ( v.getId() ) {
            case R.id.butForgetOK:
                phoneS = mPhone.getText().toString();
                if ( isNetworkConnected() ) {
                    mDialog.show();
                    app.getNet().smsReset(phoneS);
                } else {
                    Toast.makeText(this, "Проверьте подключение к интернету", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    @Override
    public void onNetRequestDone (@Net.NetEvent int eventId, Object NetObjects) {
        super.onNetRequestDone(eventId, NetObjects);
        switch ( eventId ) {
            case Net.SMS_RESET:
                mDialog.dismiss();
                FieldResponse fieldResponse = (FieldResponse) NetObjects;
                Intent newPass = new Intent(ForgotPassActivity.this, NewPassActivity.class);
                newPass.putExtra(NewPassActivity.UID, fieldResponse.getServerResponse());
                startActivityForResult(newPass,1);
                break;
        }
    }

    @Override
    protected void onActivityResult (int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == 7 ){
            finish();
        }
    }

    @Override
    public void onNetRequestFail (@Net.NetEvent int eventId, Object NetObjects) {
        super.onNetRequestFail(eventId, NetObjects);
        switch ( eventId ) {
            case Net.SMS_RESET:
                mDialog.dismiss();
                String fieldResponse = (String) NetObjects;
                Toast.makeText(this, "" +  fieldResponse, Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    public boolean isNetworkConnected () {
        return super.isNetworkConnected();
    }
}
