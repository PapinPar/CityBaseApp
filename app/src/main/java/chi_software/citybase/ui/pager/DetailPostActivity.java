package chi_software.citybase.ui.pager;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.customtabs.CustomTabsIntent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import chi_software.citybase.R;
import chi_software.citybase.core.BaseActivity;
import chi_software.citybase.core.api.Net;
import chi_software.citybase.data.comment.Comment;
import chi_software.citybase.data.getBase.MyObject;
import chi_software.citybase.interfaces.SetCommentListener;
import chi_software.citybase.ui.dialog.CommentDialog;
import chi_software.citybase.utils.SharedCityBase;


/**
 * Created by Papin on 15.11.2016.
 */

@SuppressWarnings("ConstantConditions")
public class DetailPostActivity extends BaseActivity implements PageFragment.ShowBigImageListener, SetCommentListener {

    public static final String UID = "uid";
    public static final String URL = "url";
    public static final String SIZE = "size";
    public static final String POSITION = "position";
    public static final String POSITION_PHOTO = "position_photo";
    public static final String MODEL = "model";
    public static final String KEY = "key";
    public static final String TABLE = "table";

    private ArrayList<String> mUrlList;
    private List<MyObject> mMyObjectsList;
    private int mPosition;
    private int mSize, mPhotoId;
    private String mUid, mKey, mTable, mCommentStr;
    private TextView mUpdData, mPublishedData, mPrice, mRoomsType, mAreaSize, mMetroName, mInfo, mAddress, mPhoneNumber, mTermType;
    private LinearLayout mLine2, mLine3, mLine4;
    private EditText mComment;
    private RelativeLayout mRelativeImage;
    private CommentDialog setCommentIF;

    protected void onCreate(Bundle savedInstanceState) {
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
        if (pager != null) {
            pager.setAdapter(pagerAdapter);
        }


        ImageView webLink = (ImageView) findViewById(R.id.imageWebLink);
        webLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openChromeTab(mMyObjectsList.get(mPosition).getUrl());
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
        mPhoneNumber = (TextView) findViewById(R.id.phoneNumberTW);
        mComment = (EditText) findViewById(R.id.comment_et);
        mRelativeImage = (RelativeLayout) findViewById(R.id.ImageRelative);
        mTermType = (TextView) findViewById(R.id.termTV);
        mLine2 = (LinearLayout) findViewById(R.id.line2);
        mLine3 = (LinearLayout) findViewById(R.id.line3);
        mLine4 = (LinearLayout) findViewById(R.id.line4);
        // Say about rieltor
        findViewById(R.id.realtor_tw).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog alertDialog = new AlertDialog.Builder(DetailPostActivity.this).create();
                alertDialog.setTitle("Попали на посредника?");
                alertDialog.setMessage("Если вы попали на посредника и хотите нам сообщить, то нажмите кнопку ДА и в будущем объявления с данным номером не будут вам отображаться.");
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "ДА",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                app.getNet().setRieltor(mUid, mKey, SharedCityBase.GetCity(DetailPostActivity.this),
                                        mTable, mMyObjectsList.get(mPosition).getId(), "addrieltor", mMyObjectsList.get(mPosition).getPhone());
                                dialog.dismiss();
                            }
                        });
                alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Нет",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                alertDialog.show();
            }
        });

        // Call owner
        mPhoneNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mMyObjectsList.get(mPosition).getPhone() != null) {
                    Intent intent = new Intent(Intent.ACTION_DIAL);
                    intent.setData(Uri.parse("tel:" + mPhoneNumber.getText().toString()));
                    startActivity(intent);
                }
            }
        });

        // SEND COMMENT
        mComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendComment();
            }
        });
        app.getNet().getObjectinfo("", SharedCityBase.GetCity(this), mTable, mUid, mKey, Integer.valueOf(mMyObjectsList.get(mPosition).getId()));
        setInfo();
        pager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                mPhotoId = position;
            }

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    private void sendComment() {
        setCommentIF = new CommentDialog();
        setCommentIF.show(DetailPostActivity.this, this, mComment.getText().toString());
    }


    private void setInfo() {
        if (mUrlList.size() <= 0 || mUrlList == null) {
            mRelativeImage.setVisibility(View.GONE);
        }
        mUpdData.setText(mMyObjectsList.get(mPosition).getDateUp());
        mPublishedData.setText(mMyObjectsList.get(mPosition).getDatePub());
        String currency;
        if (mTable.equals("rent_living") || mTable.equals("rent_not_living"))
            currency = " грн";
        else
            currency = " $";
        if (mMyObjectsList.get(mPosition).getPrice() != null) {
            mPrice.setText("Цена:" + mMyObjectsList.get(mPosition).getPrice() + currency);
        } else {
            mPrice.setText("Цена:?" + currency);
        }
        mRoomsType.setText(mMyObjectsList.get(mPosition).getType());
        String areaFloor = "";
        if (mMyObjectsList.get(mPosition).getArea() != null && !mMyObjectsList.get(mPosition).getArea().equals("") && !mMyObjectsList.get(mPosition).getArea().equals("0")) {
            areaFloor = mMyObjectsList.get(mPosition).getArea() + " м.кв.  ";
        }
        if (mMyObjectsList.get(mPosition).getFloor() != null && !mMyObjectsList.get(mPosition).getFloor().equals("") && !mMyObjectsList.get(mPosition).getFloor().equals("0")) {
            areaFloor = areaFloor + mMyObjectsList.get(mPosition).getFloor();
            if (mMyObjectsList.get(mPosition).getFloorNumb() != null) {
                areaFloor = areaFloor + " этаж из " + mMyObjectsList.get(mPosition).getFloorNumb();
            }
        }

        if (!areaFloor.equals("")) {
            mAreaSize.setText(areaFloor);
        } else {
            mLine2.setVisibility(View.GONE);
        }

        if (mMyObjectsList.get(mPosition).getTerm() != null) {
            if (mMyObjectsList.get(mPosition).getTerm().equals("Д")) {
                mTermType.setText("Долгосрочно");
            } else {
                mTermType.setText("Посуточно");
            }
        } else {
            mLine4.setVisibility(View.GONE);
        }


        String metroDistanse = "";
        if (mMyObjectsList.get(mPosition).getGuide() != null && !mMyObjectsList.get(mPosition).getGuide().equals("") && !mMyObjectsList.get(mPosition).getGuide().equals("0"))
            metroDistanse = mMyObjectsList.get(mPosition).getGuide() + " ";
        if (mMyObjectsList.get(mPosition).getDistanceMetro() != null && !mMyObjectsList.get(mPosition).getDistanceMetro().equals("") && !mMyObjectsList.get(mPosition).getDistanceMetro().equals("0"))
            metroDistanse = metroDistanse + mMyObjectsList.get(mPosition).getDistanceMetro() + " м.";
        if (!metroDistanse.equals(""))
            mMetroName.setText(metroDistanse);
        else
            mLine3.setVisibility(View.GONE);

        mInfo.setText(mMyObjectsList.get(mPosition).getText());
        String streetHouse = "";
        if (mMyObjectsList.get(mPosition).getStreet() != null) {
            streetHouse = mMyObjectsList.get(mPosition).getStreet() + " ";
            if (mMyObjectsList.get(mPosition).getHouse() != null)
                streetHouse = streetHouse + mMyObjectsList.get(mPosition).getHouse();
        }
        if (streetHouse.length() > 5) {
            mAddress.setText(streetHouse);
            findViewById(R.id.textView19).setVisibility(View.VISIBLE);
        } else {
            mAddress.setVisibility(View.GONE);
            findViewById(R.id.textView19).setVisibility(View.GONE);
        }

        if (mMyObjectsList.get(mPosition).getPhone() != null) {
            mPhoneNumber.setText(mMyObjectsList.get(mPosition).getPhone());
            findViewById(R.id.textView21).setVisibility(View.VISIBLE);
        } else {
            mPhoneNumber.setVisibility(View.GONE);
            findViewById(R.id.textView21).setVisibility(View.GONE);
        }

        if (mMyObjectsList.get(mPosition).getComment() != null) {
            mComment.setText(mMyObjectsList.get(mPosition).getComment().toString());
        }
    }


    @Override
    public void showImage() {
        Intent s = new Intent(DetailPostActivity.this, BigViwerActivity.class);
        s.putExtra(DetailPostActivity.UID, mUid);
        s.putExtra(DetailPostActivity.KEY, mKey);
        s.putExtra(DetailPostActivity.TABLE, mTable);
        s.putExtra(DetailPostActivity.POSITION, mPosition);
        s.putExtra(DetailPostActivity.POSITION_PHOTO, mPhotoId);
        s.putExtra(DetailPostActivity.SIZE, mSize);
        s.putStringArrayListExtra(DetailPostActivity.URL, (ArrayList<String>) mUrlList);
        s.putExtra(DetailPostActivity.MODEL, (Serializable) mMyObjectsList);
        startActivity(s);
    }

    @Override
    public void onNetRequestDone(@Net.NetEvent int eventId, Object NetObjects) {
        super.onNetRequestDone(eventId, NetObjects);
        switch (eventId) {
            case Net.GET_OBJ_INFO:
                Comment baseGet = (Comment) NetObjects;
                if (baseGet.getResponse().getUserParameters() != null)
                    mComment.setText(baseGet.getResponse().getUserParameters().getComment());
                break;
            case Net.ADD_RIELTOR:
                Toast.makeText(this, "Спасибо что помогаете нам улучшить сервис.", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    public void onNetRequestFail(@Net.NetEvent int eventId, Object NetObjects) {
        super.onNetRequestFail(eventId, NetObjects);
        switch (eventId) {
            case Net.MORE_USERS_ERROR:
                Toast.makeText(DetailPostActivity.this, (String) NetObjects, Toast.LENGTH_SHORT).show();
                startScreen();
                break;
        }
    }

    @Override
    public void getBackComment(String comment) {
        mComment.setText(comment);
        if (comment.length() > 0) {
            Toast.makeText(this, "Комментарий сохранен.", Toast.LENGTH_SHORT).show();
            mComment.clearFocus();
        } else {
            Toast.makeText(this, "Комментарий удалён.", Toast.LENGTH_SHORT).show();
            mComment.clearFocus();
        }
        app.getNet().setComment(mUid, mKey, SharedCityBase.GetCity(this), mTable, mMyObjectsList.get(mPosition).getId(), "comment", comment);
    }

    private class MyFragmentPagerAdapter extends FragmentPagerAdapter {

        public MyFragmentPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            PageFragment fragment = PageFragment.newInstance(position, mUrlList);
            fragment.setListener(DetailPostActivity.this);
            return fragment;
        }

        @Override
        public int getCount() {
            return mSize;
        }
    }

    private void openChromeTab(String url) {
        CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
        CustomTabsIntent customTabsIntent = builder.build();
        customTabsIntent.launchUrl(DetailPostActivity.this, Uri.parse(url));
    }

}
