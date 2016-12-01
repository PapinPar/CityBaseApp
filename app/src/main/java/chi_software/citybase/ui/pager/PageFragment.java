package chi_software.citybase.ui.pager;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import chi_software.citybase.R;


/**
 * Created by Papin on 15.11.2016.
 */

public class PageFragment extends Fragment {

    private final static String ARGUMENT_PAGE_NUMBER = "arg_page_number";
    private static ArrayList<String> mUrl;
    private int mPageNumber;
    static PageFragment newInstance (int page,ArrayList<String> url) {
        PageFragment pageFragment = new PageFragment();
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
    public View onCreateView (LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.pager_image_fragment, null);

        ImageView tvPage = (ImageView) view.findViewById(R.id.iwPage);
        Picasso.with(getContext())
                .load(mUrl.get(mPageNumber))
                .into(tvPage);
        //Pearl.imageLoader(getContext(),mUrl.get(mPageNumber),tvPage,R.color.black);
        return view;
    }
}