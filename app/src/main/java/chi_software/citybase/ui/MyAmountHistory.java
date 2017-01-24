package chi_software.citybase.ui;
import android.os.Bundle;
import android.support.annotation.Nullable;

import chi_software.citybase.R;
import chi_software.citybase.core.BaseActivity;


/**
 * Created by user on 24.01.2017.
 */

public class MyAmountHistory extends BaseActivity {
    @Override
    protected void onCreate (@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rv_payments_history_layout);
    }
}
