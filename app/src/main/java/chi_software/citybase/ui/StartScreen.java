package chi_software.citybase.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import chi_software.citybase.R;
import chi_software.citybase.core.BaseActivity;
import chi_software.citybase.core.api.Net;
import chi_software.citybase.data.getBase.BaseGet;


/**
 * Created by Papin on 08.11.2016.
 */

public class StartScreen extends BaseActivity implements View.OnClickListener {

    private Button signInBut, getAccessBut, tryAccess;

    @Override
    protected void onCreate (@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_screen_layout);
        signInBut = (Button) findViewById(R.id.SignInBut);
        signInBut.setOnClickListener(this);
        getAccessBut = (Button) findViewById(R.id.GetAccessBut);
        getAccessBut.setOnClickListener(this);
        tryAccess = (Button) findViewById(R.id.TryBaseBut);
        tryAccess.setOnClickListener(this);
    }

    @Override
    public void onNetRequestDone (@Net.NetEvent int eventId, Object NetObjects) {
        super.onNetRequestDone(eventId, NetObjects);
        switch ( eventId ) {
            case Net.TRIAL_BASE:
                BaseGet baseGet = (BaseGet) NetObjects;
                Log.d("StartScreen", "baseGet.getGetResponse().getModel():" + baseGet.getGetResponse().getTrialObjects());
        }
    }

    @Override
    public void onClick (View v) {
        switch ( v.getId() ) {
            case R.id.SignInBut:
                Intent startLogin = new Intent(StartScreen.this, LoginSrcreen.class);
                startActivity(startLogin);
                break;
            case R.id.GetAccessBut:
                Intent register = new Intent(StartScreen.this, RegistrationActivity.class);
                startActivity(register);
                break;
            case R.id.TryBaseBut:
                app.getNet().tryBase("_Kharkov", "rent_living", "Харьков", "1 к", "Салтовка", "rent", "living");
                break;

        }
    }
}
