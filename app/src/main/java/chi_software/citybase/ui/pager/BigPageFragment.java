package chi_software.citybase.ui.pager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alexvasilkov.gestures.Settings;
import com.alexvasilkov.gestures.views.GestureImageView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import chi_software.citybase.R;


public class BigPageFragment extends Fragment {

    private final static String ARGUMENT_PAGE_NUMBER = "arg_page_number";
    private static ArrayList<String> mUrl;
    private int mPageNumber;

    static BigPageFragment newInstance (int page, ArrayList<String> url) {
        BigPageFragment pageFragment = new BigPageFragment();
        mUrl = url;
        Bundle arguments = new Bundle();
        arguments.putInt(ARGUMENT_PAGE_NUMBER, page);
        pageFragment.setArguments(arguments);
        return pageFragment;
    }

    @Override
    public void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPageNumber = getArguments().getInt(ARGUMENT_PAGE_NUMBER);
    }

    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.big_pager_image_fragment, null);

        GestureImageView tvPage = (GestureImageView) view.findViewById(R.id.iwPageBig);
        tvPage.getController().getSettings()
                .setMaxZoom(2f)
                .setPanEnabled(true)
                .setZoomEnabled(true)
                .setDoubleTapEnabled(true)
                .setRotationEnabled(false)
                .setRestrictRotation(false)
                .setOverscrollDistance(0f, 0f)
                .setOverzoomFactor(2f)
                .setFillViewport(false)
                .setFitMethod(Settings.Fit.INSIDE)
                .setGravity(Gravity.CENTER);
        Picasso.with(getContext()).load(mUrl.get(mPageNumber)).into(tvPage);
        return view;
    }

}