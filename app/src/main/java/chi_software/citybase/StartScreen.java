package chi_software.citybase;

import android.app.Activity;
import android.os.Bundle;
import android.os.PersistableBundle;

/**
 * Created by Papin on 08.11.2016.
 */

public class StartScreen extends Activity {
    @Override
    public void onCreate (Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.start_screen_layout);
    }


}
