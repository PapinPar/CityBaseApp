package chi_software.citybase.ui.forgotPass;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Toast;

import com.rengwuxian.materialedittext.MaterialEditText;

import chi_software.citybase.R;
import chi_software.citybase.core.BaseActivity;
import chi_software.citybase.core.api.Net;
import dmax.dialog.SpotsDialog;


@SuppressWarnings("ConstantConditions")
public class NewPassActivity extends BaseActivity implements View.OnClickListener {

    public static final String UID = "UID";

    private String mUidS, mPassS, mCodeS;
    private MaterialEditText mCodeED;
    private MaterialEditText mPassED;
    private SpotsDialog mDialog;

    @Override
    protected void onCreate (@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activate_pass_layot);

        mUidS = getIntent().getStringExtra(UID);
        findViewById(R.id.newPassOK).setOnClickListener(this);
        mCodeED = (MaterialEditText) findViewById(R.id.newCodeET);
        mPassED = (MaterialEditText) findViewById(R.id.newPassET);
        mDialog = new SpotsDialog(NewPassActivity.this,"Загрузка");
    }

    @Override
    public void onNetRequestDone (@Net.NetEvent int eventId, Object NetObjects) {
        super.onNetRequestDone(eventId, NetObjects);
        switch ( eventId ) {
            case Net.NEW_RESET_PASS:
                mDialog.dismiss();
                Toast.makeText(this, "Пароль успешно изменен", Toast.LENGTH_SHORT).show();
                setResult(7);
                finish();
                break;
        }
    }

    @Override
    public void onNetRequestFail (@Net.NetEvent int eventId, Object NetObjects) {
        super.onNetRequestFail(eventId, NetObjects);
        switch ( eventId ) {
            case Net.NEW_RESET_PASS:
                mDialog.dismiss();
                Toast.makeText(this, "Произошел сбой.Проверьте своё интернет подключение.", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    public boolean isNetworkConnected () {
        return super.isNetworkConnected();
    }

    @Override
    public void onClick (View v) {
        mCodeS = mCodeED.getText().toString();
        mPassS = mPassED.getText().toString();
        if ( isNetworkConnected() ) {
            mDialog.show();
            app.getNet().newResetPass(mCodeS, mUidS, mPassS);
        } else {
            Toast.makeText(this, "Проверьте подключение к интернету", Toast.LENGTH_SHORT).show();
        }
    }
}
