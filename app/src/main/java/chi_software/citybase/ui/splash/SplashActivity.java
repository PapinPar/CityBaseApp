package chi_software.citybase.ui.splash;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;

import chi_software.citybase.R;
import chi_software.citybase.SharedCityBase;
import chi_software.citybase.core.BaseActivity;
import chi_software.citybase.ui.StartScreen;

public class SplashActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_layout);
        boolean isFirst = SharedCityBase.getFirst(SplashActivity.this);
        if (isFirst) {
            SharedCityBase.setFirst(SplashActivity.this,false);
            start(TutorialActivity.class);
        } else {
            start(StartScreen.class);
        }

    }

    private void start(final Class tutorialActivityClass) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent mainIntent = new Intent(SplashActivity.this, tutorialActivityClass);
                startActivity(mainIntent);
                finish();
            }
        }, 2000);
    }
}
