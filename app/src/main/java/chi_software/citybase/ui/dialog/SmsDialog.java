package chi_software.citybase.ui.dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import chi_software.citybase.R;


/**
 * Created by Papin on 17.11.2016.
 */

public class SmsDialog extends DialogFragment {

    private EditText smsET;
    private Button butOK;

    private SmsListner smsListner;


    public interface SmsListner {
        void getSmsListner (String answer);
    }

    public void setGetSmsListnet (SmsListner smsListner) {
        this.smsListner = smsListner;
    }

    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.sms_activate_layout, null);
        smsET = (EditText) v.findViewById(R.id.smsCodeET);
        butOK = (Button) v.findViewById(R.id.butSms);

        butOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v) {
                smsListner.getSmsListner(smsET.getText().toString());
            }
        });
        return v;
    }

}
