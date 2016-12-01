package chi_software.citybase.ui.pager;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
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

    public static final String UID = "uid";
    public static final String URL = "url";
    public static final String SIZE = "size";
    public static final String POSITION = "position";
    public static final String MODEL = "model";
    public static final String KEY = "key";
    public static final String TABLE = "table";

    private ArrayList<String> mUrlList;
    private List<MyObject> mMyObjectsList;
    private int mPosition;
    private int mSize;
    private String mUid, mKey, mTable;
    private TextView mUpdData, mPublishedData, mPrice, mRoomsType, mAreaSize, mMetroName, mInfo, mAddress, mPhoneNmber;
    private LinearLayout mLine2, mLine3;

    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_info_layout);
        mUrlList = getIntent().getStringArrayListExtra(URL);
        mSize = getIntent().getIntExtra(SIZE, 0);
        mPosition = getIntent().getIntExtra(POSITION, 0);
        mMyObjectsList = (List<MyObject>) getIntent().getSerializableExtra(MODEL);
        mUid = getIntent().getStringExtra(UID);
        mKey = getIntent().getStringExtra(KEY);
        mTable = getIntent().getStringExtra(TABLE);

        ViewPager pager = (ViewPager) findViewById(R.id.myPager);
        PagerAdapter pagerAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager());
        pager.setAdapter(pagerAdapter);

        mLine2 = (LinearLayout) findViewById(R.id.line2);
        mLine3 = (LinearLayout) findViewById(R.id.line3);

        ImageView webLink = (ImageView) findViewById(R.id.imageWebLink);
        webLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.addCategory(Intent.CATEGORY_BROWSABLE);
                intent.setData(Uri.parse(mMyObjectsList.get(mPosition).getUrl()));
                startActivity(intent);
            }
        });

        mUpdData = (TextView) findViewById(R.id.updTW);
        mPublishedData = (TextView) findViewById(R.id.publichedTW);
        mPrice = (TextView) findViewById(R.id.priceTW);
        mRoomsType = (TextView) findViewById(R.id.roomsTypeTW);
        mAreaSize = (TextView) findViewById(R.id.areaSizeTW);
        mMetroName = (TextView) findViewById(R.id.metroNameTW);
        mInfo = (TextView) findViewById(R.id.infoAbout);
        mAddress = (TextView) findViewById(R.id.addressTW);
        mPhoneNmber = (TextView) findViewById(R.id.phoneNumberTW);

        mPhoneNmber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v) {
                if ( mMyObjectsList.get(mPosition).getPhone() != null )
                    if ( ActivityCompat.checkSelfPermission(PagerViwer.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED ) {
                        return;
                    }
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + mPhoneNmber.getText().toString()));
                startActivity(intent);
            }
        });

        setInfo();
        pager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageSelected (int position) {
            }

            @Override
            public void onPageScrolled (int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageScrollStateChanged (int state) {
            }
        });
    }

    private void setInfo () {
        mUpdData.setText(mMyObjectsList.get(mPosition).getDateUp());
        mPublishedData.setText(mMyObjectsList.get(mPosition).getDatePub());
        mPrice.setText(mMyObjectsList.get(mPosition).getPrice() + "грн");
        mRoomsType.setText(mMyObjectsList.get(mPosition).getType());
        String areaFloor = "";
        if ( mMyObjectsList.get(mPosition).getArea() != null )
            areaFloor = mMyObjectsList.get(mPosition).getArea() + "м^2, ";
        if ( mMyObjectsList.get(mPosition).getFloor() != null )
            areaFloor = areaFloor + mMyObjectsList.get(mPosition).getFloor() + "этаж";
        if ( !areaFloor.equals("") )
            mAreaSize.setText(areaFloor);
        else
            mLine2.setVisibility(View.GONE);

        String metroDistanse = "";
        if ( mMyObjectsList.get(mPosition).getGuide() != null )
            metroDistanse = "метро " + mMyObjectsList.get(mPosition).getGuide() + " ";
        if ( mMyObjectsList.get(mPosition).getDistanceMetro() != null )
            metroDistanse = metroDistanse + mMyObjectsList.get(mPosition).getDistanceMetro() + " м.";
        if ( !metroDistanse.equals("") )
            mMetroName.setText(metroDistanse);
        else
            mLine3.setVisibility(View.GONE);

        mInfo.setText(mMyObjectsList.get(mPosition).getText());
        String streetHouse = "";
        if ( mMyObjectsList.get(mPosition).getStreet() != null ) {
            streetHouse = mMyObjectsList.get(mPosition).getStreet() + " ";
            if ( mMyObjectsList.get(mPosition).getHouse() != null )
                streetHouse = streetHouse + mMyObjectsList.get(mPosition).getHouse();
        }
        if ( !streetHouse.equals("") )
            mAddress.setText(streetHouse);
        else
            mAddress.setVisibility(View.GONE);

        if ( mMyObjectsList.get(mPosition).getPhone() != null )
            mPhoneNmber.setText(mMyObjectsList.get(mPosition).getPhone());
        else
            mPhoneNmber.setVisibility(View.GONE);
    }

    private class MyFragmentPagerAdapter extends FragmentPagerAdapter {

        public MyFragmentPagerAdapter (FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem (int position) {
            return PageFragment.newInstance(position, mUrlList);
        }

        @Override
        public int getCount () {
            return mSize;
        }
    }

    @Override
    public boolean onCreateOptionsMenu (Menu menu) {
        getMenuInflater().inflate(R.menu.color_pick_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected (MenuItem item) {
        switch ( item.getItemId() ) {
            case R.id.action_green:
                app.getNet().setColor(mUid, mKey, "_Kharkov", mTable, mMyObjectsList.get(mPosition).getId(), "color", 1);
                break;
            case R.id.action_red:
                app.getNet().setColor(mUid, mKey, "_Kharkov", mTable, mMyObjectsList.get(mPosition).getId(), "color", 3);
                break;
            case R.id.action_yellow:
                app.getNet().setColor(mUid, mKey, "_Kharkov", mTable, mMyObjectsList.get(mPosition).getId(), "color", 2);
                break;
            case R.id.action_no_color:
                app.getNet().setColor(mUid, mKey, "_Kharkov", mTable, mMyObjectsList.get(mPosition).getId(), "color", 0);
                break;
            case R.id.action_comment:
                app.getNet().setComment(mUid, mKey, "_Kharkov", mTable, mMyObjectsList.get(mPosition).getId(), "comment", "Пробный комментарий");
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
