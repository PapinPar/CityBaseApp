package chi_software.citybase.core;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.inputmethod.InputMethodManager;

import chi_software.citybase.utils.SharedCityBase;
import chi_software.citybase.core.api.App;
import chi_software.citybase.core.api.Net;
import chi_software.citybase.core.api.NetSubscriber;
import chi_software.citybase.ui.StartScreen;


/**
 * Created by user on 27.01.2017.
 */

public class BaseFragment extends Fragment implements NetSubscriber {

    protected App app;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        app = CityApplication.getApp(context);
        if (!app.getNet().IsSubscribe(this)) {
            app.getNet().Subscribe(this);
            Log.d("PAPIN_TAG", "Subscribe");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        app.getNet().UnSubscribe(this);
    }

    @Override
    public void onNetRequestDone(@Net.NetEvent int eventId, Object NetObjects) {

    }

    @Override
    public void onNetRequestFail(@Net.NetEvent int eventId, Object NetObjects) {

    }

    public void startScreen() {
        SharedCityBase.SetPassword(getContext(), "");
        SharedCityBase.SetLogin(getContext(), "");
        Intent startActivity = new Intent(getContext(), StartScreen.class);
        startActivity(startActivity);
    }

    public boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }

    public void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
    }
}
