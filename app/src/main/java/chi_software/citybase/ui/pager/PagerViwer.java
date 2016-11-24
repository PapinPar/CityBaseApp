package chi_software.citybase.ui.pager;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import chi_software.citybase.R;
import chi_software.citybase.core.BaseActivity;
import chi_software.citybase.data.getBase.MyObject;

/**
 * Created by Papin on 15.11.2016.
 */

public class PagerViwer extends BaseActivity {
    private ViewPager pager;
    private PagerAdapter pagerAdapter;
    private ArrayList<String> UrlList;
    private List<MyObject> myObjectsList;
    private int position;
    private int size;
    private String _uid, _key,table;

    TextView price,text, city;

    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_info_layout);
        UrlList = getIntent().getStringArrayListExtra("test");
        size = getIntent().getIntExtra("size", 0);
        position = getIntent().getIntExtra("position", 0);
        myObjectsList = (List<MyObject>) getIntent().getSerializableExtra("model");
        _uid = getIntent().getStringExtra("uid") ;
        _key = getIntent().getStringExtra("key");
        table = getIntent().getStringExtra("table");

        pager = (ViewPager) findViewById(R.id.myPager);
        pagerAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager());
        pager.setAdapter(pagerAdapter);

        price = (TextView) findViewById(R.id.priceInfo);
        text = (TextView) findViewById(R.id.textInfo);
        city = (TextView) findViewById(R.id.cityInfo);

        text.setMovementMethod(new ScrollingMovementMethod());

        price.setText(myObjectsList.get(position).getPrice());
        text.setText(myObjectsList.get(position).getText());
        city.setText(myObjectsList.get(position).getCity());

        pager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageSelected (int position) {
            }

            @Override
            public void onPageScrolled (int position, float positionOffset,
                                        int positionOffsetPixels) {
            }

            @Override
            public void onPageScrollStateChanged (int state) {
            }
        });
    }

    private class MyFragmentPagerAdapter extends FragmentPagerAdapter {

        public MyFragmentPagerAdapter (FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem (int position) {
            return PageFragment.newInstance(position, UrlList);
        }

        @Override
        public int getCount () {
            return size;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.color_pick_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch ( item.getItemId() )
        {
            case R.id.action_green:
                app.getNet().setColor(_uid,_key,"_Kharkov",table,myObjectsList.get(position).getId(),"color",1);
                break;
            case R.id.action_red:
                app.getNet().setColor(_uid,_key,"_Kharkov",table,myObjectsList.get(position).getId(),"color",3);
                break;
            case R.id.action_yellow:
                app.getNet().setColor(_uid,_key,"_Kharkov",table,myObjectsList.get(position).getId(),"color",2);
                break;
            case R.id.action_no_color:
                app.getNet().setColor(_uid,_key,"_Kharkov",table,myObjectsList.get(position).getId(),"color",0);
                break;
            case R.id.action_comment:
                app.getNet().setComment(_uid,_key,"_Kharkov",table,myObjectsList.get(position).getId(),"comment","Пробный комментарий");
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
