package chi_software.citybase.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

import chi_software.citybase.R;
import chi_software.citybase.core.BaseActivity;

/**
 * Created by Papin on 08.11.2016.
 */

public class StartScreen extends BaseActivity implements View.OnClickListener {

    private Button signInBut;

    @Override
    protected void onCreate (@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_screen_layout);
        signInBut = (Button) findViewById(R.id.SignInBut);
        signInBut.setOnClickListener(this);
    }

    @Override
    public void onClick (View v) {
        switch ( v.getId() ) {
            case R.id.SignInBut:
                Intent startLogin = new Intent(StartScreen.this, LoginSrcreen.class);
                startActivity(startLogin);
                break;
        }
    }
}
