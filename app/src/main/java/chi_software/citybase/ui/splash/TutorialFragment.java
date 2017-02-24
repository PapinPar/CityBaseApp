package chi_software.citybase.ui.splash;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class TutorialFragment extends Fragment {

    private final static String ARGUMENT_PAGE_NUMBER = "arg_page_number";
    private int mPageNumber;
    private static ArrayList<Integer> mListInfo;

    static TutorialFragment newInstance (int page, ArrayList<Integer> info) {
        TutorialFragment pageFragment = new TutorialFragment();
        mListInfo = info;
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
        View view = inflater.inflate(mListInfo.get(mPageNumber), null);
        return view;
    }

}