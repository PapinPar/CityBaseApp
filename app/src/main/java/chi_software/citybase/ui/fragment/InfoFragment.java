package chi_software.citybase.ui.fragment;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.customtabs.CustomTabsIntent;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import chi_software.citybase.R;
import chi_software.citybase.core.BaseFragment;


/**
 * Created by user on 07.02.2017.
 */

public class InfoFragment extends BaseFragment implements View.OnClickListener {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.info_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.findViewById(R.id.imageChi).setOnClickListener(this);
        view.findViewById(R.id.first_phone_tv).setOnClickListener(this);
        view.findViewById(R.id.second_phone_tv).setOnClickListener(this);
        view.findViewById(R.id.email_tv).setOnClickListener(this);
    }

    private void openChromeTab() {
        CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
        CustomTabsIntent customTabsIntent = builder.build();
        customTabsIntent.launchUrl(getActivity(), Uri.parse("https://chisw.com/"));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imageChi:
                openChromeTab();
                break;
            case R.id.first_phone_tv:
                callPhone("0664382589");
                break;
            case R.id.second_phone_tv:
                callPhone("0969134433");
                break;
            case R.id.email_tv:
                sendEmail();
                break;
        }
    }

    private void callPhone(String phone) {
        int permissionCheck = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CALL_PHONE);
        if (permissionCheck == 0) {
            if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.CALL_PHONE)) {
                } else {
                    ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_CONTACTS}, 1);
                }
            }
        }
        Log.d("InfoFragment", "permissionCheck:" + permissionCheck);
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + phone));
        startActivity(intent);
    }

    private void sendEmail() {
        String mailTo = "mailto:admin@citybase.in.ua";
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
        emailIntent.setData(Uri.parse(mailTo));

        try {
            startActivity(emailIntent);
        } catch (ActivityNotFoundException e) {
            //TODO: Handle case where no email app is available
        }
    }
}
