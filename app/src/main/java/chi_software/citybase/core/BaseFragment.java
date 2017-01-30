package chi_software.citybase.core;
import android.content.Context;
import android.net.ConnectivityManager;
import android.support.v4.app.Fragment;
import android.util.Log;

import chi_software.citybase.core.api.App;
import chi_software.citybase.core.api.Net;
import chi_software.citybase.core.api.NetSubscriber;


/**
 * Created by user on 27.01.2017.
 */

public class BaseFragment extends Fragment implements NetSubscriber {

    protected App app;

    @Override
    public void onAttach (Context context) {
        super.onAttach(context);
        app = CityApplication.getApp(context);
        if ( !app.getNet().IsSubscribe(this) ) {
            app.getNet().Subscribe(this);
            Log.d("PAPIN_TAG", "Subscribe");
        }
    }

    @Override
    public void onDetach () {
        super.onDetach();
        app.getNet().UnSubscribe(this);
    }

    @Override
    public void onNetRequestDone (@Net.NetEvent int eventId, Object NetObjects) {

    }

    @Override
    public void onNetRequestFail (@Net.NetEvent int eventId, Object NetObjects) {

    }

    public boolean isNetworkConnected () {
        ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }
}
