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
    private ViewPager pager;
    private PagerAdapter pagerAdapter;
    private ArrayList<String> UrlList;
    private List<MyObject> myObjectsList;
    private int position;
    private int size;
    private String _uid, _key, table;
    private TextView updData, publishedDa, price, roomsType, areaSize, metroName, info, address, phoneNmber;
    private LinearLayout line2, line3;
    private ImageView webLink;

    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_info_layout);
        UrlList = getIntent().getStringArrayListExtra("test");
        size = getIntent().getIntExtra("size", 0);
        position = getIntent().getIntExtra("position", 0);
        myObjectsList = (List<MyObject>) getIntent().getSerializableExtra("model");
        _uid = getIntent().getStringExtra("uid");
        _key = getIntent().getStringExtra("key");
        table = getIntent().getStringExtra("table");

        pager = (ViewPager) findViewById(R.id.myPager);
        pagerAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager());
        pager.setAdapter(pagerAdapter);

        line2 = (LinearLayout) findViewById(R.id.line2);
        line3 = (LinearLayout) findViewById(R.id.line3);

        webLink = (ImageView) findViewById(R.id.imageWebLink);
        webLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.addCategory(Intent.CATEGORY_BROWSABLE);
                intent.setData(Uri.parse(myObjectsList.get(position).getUrl()));
                startActivity(intent);
            }
        });

        updData = (TextView) findViewById(R.id.updTW);
        publishedDa = (TextView) findViewById(R.id.publichedTW);
        price = (TextView) findViewById(R.id.priceTW);
        roomsType = (TextView) findViewById(R.id.roomsTypeTW);
        areaSize = (TextView) findViewById(R.id.areaSizeTW);
        metroName = (TextView) findViewById(R.id.metroNameTW);
        info = (TextView) findViewById(R.id.infoAbout);
        address = (TextView) findViewById(R.id.addressTW);
        phoneNmber = (TextView) findViewById(R.id.phoneNumberTW);

        phoneNmber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v) {
                if ( myObjectsList.get(position).getPhone() != null )
                    if ( ActivityCompat.checkSelfPermission(PagerViwer.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED ) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                        return;
                    }
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" +phoneNmber.getText().toString()));
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
        updData.setText(myObjectsList.get(position).getDateUp());
        publishedDa.setText(myObjectsList.get(position).getDatePub());
        price.setText(myObjectsList.get(position).getPrice() + "грн");
        roomsType.setText(myObjectsList.get(position).getType());
        String areaFloor = "";
        if ( myObjectsList.get(position).getArea() != null )
            areaFloor = myObjectsList.get(position).getArea() + "м^2, ";
        if ( myObjectsList.get(position).getFloor() != null )
            areaFloor = areaFloor + myObjectsList.get(position).getFloor() + "этаж";
        if ( !areaFloor.equals("") )
            areaSize.setText(areaFloor);
        else
            line2.setVisibility(View.GONE);

        String metroDistanse = "";
        if ( myObjectsList.get(position).getGuide() != null )
            metroDistanse = "метро " + myObjectsList.get(position).getGuide() + " ";
        if ( myObjectsList.get(position).getDistanceMetro() != null )
            metroDistanse = metroDistanse + myObjectsList.get(position).getDistanceMetro() + " м.";
        if ( !metroDistanse.equals("") )
            metroName.setText(metroDistanse);
        else
            line3.setVisibility(View.GONE);

        info.setText(myObjectsList.get(position).getText());
        String streetHouse = "";
        if ( myObjectsList.get(position).getStreet() != null ) {
            streetHouse = myObjectsList.get(position).getStreet() + " ";
            if ( myObjectsList.get(position).getHouse() != null )
                streetHouse = streetHouse + myObjectsList.get(position).getHouse();
        }
        if ( !streetHouse.equals("") )
            address.setText(streetHouse);
        else
            address.setVisibility(View.GONE);

        if ( myObjectsList.get(position).getPhone() != null )
            phoneNmber.setText(myObjectsList.get(position).getPhone());
        else
            phoneNmber.setVisibility(View.GONE);
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
    public boolean onCreateOptionsMenu (Menu menu) {
        getMenuInflater().inflate(R.menu.color_pick_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected (MenuItem item) {
        switch ( item.getItemId() ) {
            case R.id.action_green:
                app.getNet().setColor(_uid, _key, "_Kharkov", table, myObjectsList.get(position).getId(), "color", 1);
                break;
            case R.id.action_red:
                app.getNet().setColor(_uid, _key, "_Kharkov", table, myObjectsList.get(position).getId(), "color", 3);
                break;
            case R.id.action_yellow:
                app.getNet().setColor(_uid, _key, "_Kharkov", table, myObjectsList.get(position).getId(), "color", 2);
                break;
            case R.id.action_no_color:
                app.getNet().setColor(_uid, _key, "_Kharkov", table, myObjectsList.get(position).getId(), "color", 0);
                break;
            case R.id.action_comment:
                app.getNet().setComment(_uid, _key, "_Kharkov", table, myObjectsList.get(position).getId(), "comment", "Пробный комментарий");
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
