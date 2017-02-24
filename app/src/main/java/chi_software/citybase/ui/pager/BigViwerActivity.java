package chi_software.citybase.ui.pager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;

import java.util.ArrayList;
import java.util.List;

import chi_software.citybase.R;
import chi_software.citybase.core.BaseActivity;
import chi_software.citybase.data.getBase.MyObject;


/**
 * Created by user on 01.02.2017.
 */

public class BigViwerActivity extends BaseActivity {

    public static final String URL = "url";
    public static final String SIZE = "size";
    public static final String POSITION = "position";
    public static final String MODEL = "model";

    private ArrayList<String> mUrlList;
    private List<MyObject> mMyObjectsList;
    private int mSize;
    private int mPosition;
    private int mPhotoId;


    @Override
    protected void onCreate (@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.big_viwer_layout);
        init();
    }

    private void init () {

        mUrlList = getIntent().getStringArrayListExtra(URL);
        mSize = getIntent().getIntExtra(SIZE, 0);
        mPosition = getIntent().getIntExtra(POSITION, 0);
        mPhotoId = getIntent().getIntExtra(DetailPostActivity.POSITION_PHOTO, 0);
        mMyObjectsList = (List<MyObject>) getIntent().getSerializableExtra(MODEL);

        ViewPager pager = (ViewPager) findViewById(R.id.myBigPager);

        PagerAdapter pagerAdapter = new BigViwerActivity.MyFragmentPagerAdapter(getSupportFragmentManager());
        if ( pager != null ) {
            pager.setAdapter(pagerAdapter);
            pager.setCurrentItem(mPhotoId,true);
        }
    }

    private class MyFragmentPagerAdapter extends FragmentPagerAdapter {

        public MyFragmentPagerAdapter (FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem (int position) {
            return BigPageFragment.newInstance(position, mUrlList);
        }

        @Override
        public int getCount () {
            return mSize;
        }
    }
}
