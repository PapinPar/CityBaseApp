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
    private static ArrayList<String> url1;
    private int pageNumber;
    static PageFragment newInstance (int page,ArrayList<String> url) {
        PageFragment pageFragment = new PageFragment();
        url1 = url;
        Bundle arguments = new Bundle();
        arguments.putInt(ARGUMENT_PAGE_NUMBER, page);
        pageFragment.setArguments(arguments);
        return pageFragment;
    }

    @Override
    public void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pageNumber = getArguments().getInt(ARGUMENT_PAGE_NUMBER);
    }

    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.pager_image_fragment, null);

        ImageView tvPage = (ImageView) view.findViewById(R.id.iwPage);
        Picasso.with(getContext())
                .load(url1.get(pageNumber))
                .into(tvPage);
        //Pearl.imageLoader(getContext(),url1.get(pageNumber),tvPage,R.color.black);
        return view;
    }
}