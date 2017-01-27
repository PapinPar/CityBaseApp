package chi_software.citybase.ui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.util.ArrayMap;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import chi_software.citybase.R;
import chi_software.citybase.core.BaseActivity;
import chi_software.citybase.core.api.Net;
import chi_software.citybase.data.BaseResponse;
import chi_software.citybase.data.ModelData;
import chi_software.citybase.data.getBase.MyObject;
import chi_software.citybase.data.menuSearch.MenuSearch;
import chi_software.citybase.ui.adapter.PostAdapter;
import chi_software.citybase.ui.dialog.SearchDialog;
import chi_software.citybase.ui.pager.PagerViwer;
import dmax.dialog.SpotsDialog;


public class MainScreen extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener, SearchDialog.GetSpinnerListner, PostAdapter.PostAdapterCall {

    public static final String KEY = "key";
    public static final String UID = "uid";

    private Button mFindBut;
    private PostAdapter mAdapter;
    private List<ModelData> mModelDataList;
    private SearchDialog mSearchDialog;
    private MenuSearch mMyMenu;
    private BaseResponse mBaseResponse;
    private SpotsDialog mDialog;
    private String mTable, mKey, mUid, mSearch, mCity;
    private int mPage, mLastPosition;
    private RecyclerView mRecyclerView;
    private List<MyObject> mMyObject;
    private Map<String, List<String>> mKeysModel;
    private SharedPreferences sPref;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);
        navigationInitial();
        mFindBut = (Button) findViewById(R.id.findButton);
        mFindBut.setOnClickListener(this);
        mFindBut.setClickable(false);
        mFindBut.setAlpha((float) 0.4);
        mRecyclerView = (RecyclerView) findViewById(R.id.MyRecycle);
        mSearchDialog = new SearchDialog();
        mDialog = new SpotsDialog(MainScreen.this);
        init();
        loadCity();
        showDialog(0);
    }

    @Override
    protected void onRestart () {
        super.onRestart();
    }


    private void init () {
        mKey = getIntent().getStringExtra(KEY);
        mUid = getIntent().getStringExtra(UID);
        mSearch = "";
        mPage = 1;
        mLastPosition = 0;
        mModelDataList = new ArrayList<>();
        mMyObject = new ArrayList<>();
        mKeysModel = new ArrayMap<>();
        mTable = "rent_living";

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        mAdapter = new PostAdapter(mModelDataList, this);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(layoutManager);
    }

    private void apiCalls () {
        app.getNet().getBase(mSearch, mCity, mTable, mUid, mKey, mPage);
        app.getNet().searchMenu(mCity, mTable, mUid, mKey);
        mDialog.show();
        mDialog.setCancelable(false);

        mModelDataList.clear();
        mPage = 1;
        mLastPosition = 0;
        mKeysModel.clear();
        mMyObject.clear();
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onClick (View v) {
        switch ( v.getId() ) {
            case R.id.findButton:
                mSearchDialog.getListner(this, mMyMenu);
                mSearchDialog.show(getFragmentManager(), "Поиск");
                break;
        }
    }

    @Override
    public void getPhotoId (String id, int position) {
        String url = "http://api.citybase.in.ua/api/img/";
        ArrayList list = new ArrayList();
        if ( mKeysModel.containsKey(id) ) {
            list = (ArrayList) mKeysModel.get(id);
        }
        ArrayList<String> urlList = new ArrayList<>();
        for ( int i = 0 ; i < list.size() ; i++ ) {
            urlList.add(url + list.get(i));
        }

        Intent s = new Intent(MainScreen.this, PagerViwer.class);
        s.putExtra(PagerViwer.UID, mUid);
        s.putExtra(PagerViwer.KEY, mKey);
        s.putExtra(PagerViwer.TABLE, mTable);
        s.putExtra(PagerViwer.POSITION, position);
        s.putExtra(PagerViwer.SIZE, urlList.size());
        s.putStringArrayListExtra(PagerViwer.URL, (ArrayList<String>) urlList);
        //       List<MyObject> baseGets = mBaseResponse.getBaseGet().getGetResponse().getModel();
        s.putExtra(PagerViwer.MODEL, (Serializable) mMyObject);
        startActivity(s);
    }

    @Override
    protected void onActivityResult (int requestCode, int resultCode, Intent data) {
        switch ( resultCode ) {
            case 5:
                loadCity();
                apiCalls();
                Log.d("MainScreen", "lko");
                break;
            case 2:
                Log.d("MainScreen", "lkoasdsads");
                break;
        }
    }

    @Override
    public void getLastPost (int position, int size) {
        mLastPosition = size;
        mDialog.show();
        mPage++;
        app.getNet().getBase(mSearch, mCity, mTable, mUid, mKey, mPage);
    }

    @Override
    public void onNetRequestDone (@Net.NetEvent int eventId, Object NetObjects) {
        super.onNetRequestDone(eventId, NetObjects);
        switch ( eventId ) {
            case Net.GET_BASE:
                filldata(NetObjects);
                break;
            case Net.MENU_SEARC:
                fillsearch((MenuSearch) NetObjects);
        }
    }

    @Override
    public void getSpinner (String json) {
        mPage = 1;
        mLastPosition = 0;
        mSearch = json;
        mModelDataList.clear();
        mAdapter.notifyDataSetChanged();
        Log.d("MainScreen", mTable);
        app.getNet().getBase(mSearch, mCity, mTable, mUid, mKey, mPage);
        mDialog.show();
    }

    private void fillsearch (MenuSearch netObjects) {
        mMyMenu = netObjects;
        mFindBut.setClickable(true);
        mFindBut.setAlpha(1);
    }

    private void filldata (Object netObjects) {
        Log.d("MainScreenFill", mTable);
        if ( mPage == 1 )
            mModelDataList.clear();
        mBaseResponse = (BaseResponse) netObjects;
        mMyObject.addAll(mBaseResponse.getBaseGet().getGetResponse().getModel());
        mKeysModel.putAll(mBaseResponse.getMap());
        String url = "http://api.citybase.in.ua/api/img/";
        ArrayList list = new ArrayList();
        String info = "";
        for ( int i = 0 ; i < mBaseResponse.getBaseGet().getGetResponse().getModel().size() ; i++ ) {
            String id = mBaseResponse.getBaseGet().getGetResponse().getModel().get(i).getId();
            if ( mBaseResponse.getBaseGet().getGetResponse().getModel().get(i).getAdminRegion() != null && mBaseResponse.getBaseGet().getGetResponse().getModel().get(i).getAdminRegion().length() > 1 ) {
                info = mBaseResponse.getBaseGet().getGetResponse().getModel().get(i).getAdminRegion();
            } else
                if ( mBaseResponse.getBaseGet().getGetResponse().getModel().get(i).getPlace() != null && mBaseResponse.getBaseGet().getGetResponse().getModel().get(i).getPlace().length() > 1 ) {
                    info = mBaseResponse.getBaseGet().getGetResponse().getModel().get(i).getPlace();
                } else
                    info = mBaseResponse.getBaseGet().getGetResponse().getModel().get(i).getCity();
            if ( mBaseResponse.getMap().containsKey(id) ) {
                list = (ArrayList) mBaseResponse.getMap().get(id);
                mModelDataList.add(new ModelData(mBaseResponse.getBaseGet().getGetResponse().getModel().get(i).getPrice(), info,
                        //mBaseResponse.getBaseGet().getGetResponse().getModel().get(i).getUrl(),
                        "перейти на сайт", mBaseResponse.getBaseGet().getGetResponse().getModel().get(i).getType(), mBaseResponse.getBaseGet().getGetResponse().getModel().get(i).getText(), mBaseResponse.getBaseGet().getGetResponse().getModel().get(i).getId(), mBaseResponse.getBaseGet().getGetResponse().getModel().get(i).getColor(), url + list.get(0), mTable));
            } else
                mModelDataList.add(new ModelData(mBaseResponse.getBaseGet().getGetResponse().getModel().get(i).getPrice(), info,
                        //mBaseResponse.getBaseGet().getGetResponse().getModel().get(i).getUrl(),
                        "перейти на сайт", mBaseResponse.getBaseGet().getGetResponse().getModel().get(i).getType(), mBaseResponse.getBaseGet().getGetResponse().getModel().get(i).getText(), mBaseResponse.getBaseGet().getGetResponse().getModel().get(i).getId(), mBaseResponse.getBaseGet().getGetResponse().getModel().get(i).getColor(), null, mTable));
        }
        mDialog.dismiss();
        mAdapter.notifyDataSetChanged();
        if ( mPage != 1 )
            mRecyclerView.smoothScrollToPosition(mLastPosition - 1);
    }


    // shared preferences
    private void saveCity () {
        sPref = getSharedPreferences(EditUserActivity.CITY, MODE_PRIVATE);
        SharedPreferences.Editor ed = sPref.edit();
        ed.putString(EditUserActivity.CITY, mCity);
        ed.apply();
    }
    private void loadCity () {
        sPref = getSharedPreferences(EditUserActivity.CITY, MODE_PRIVATE);
        mCity = sPref.getString(EditUserActivity.CITY, "_Kharkov");
    }
    // shared preferences


    private void navigationInitial () {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected (MenuItem item) {
        switch ( item.getItemId() ) {
            case R.id.arenda:
                mTable = "rent_living";
                apiCalls();
                break;
            case R.id.sell:
                mTable = "sale_living";
                apiCalls();
                break;
            case R.id.arendaComer:
                mTable = "rent_not_living";
                apiCalls();
                break;
            case R.id.sellComer:
                mTable = "sale_not_living";
                apiCalls();
                break;
            case R.id.myProfile:
                Intent profile = new Intent(MainScreen.this, EditUserActivity.class);
                profile.putExtra(EditUserActivity.UID, mUid);
                profile.putExtra(EditUserActivity.KEY, mKey);
                startActivityForResult(profile, 1);
                break;
            case R.id.tarifs:
                Intent tariffs = new Intent(MainScreen.this, TariffsListActivity.class);
                tariffs.putExtra(TariffsListActivity.KEY, mKey);
                tariffs.putExtra(TariffsListActivity.UID, mUid);
                startActivity(tariffs);
                break;

        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed () {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if ( drawer.isDrawerOpen(GravityCompat.START) ) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected Dialog onCreateDialog (final int id) {
        switch ( id ) {
            case 0:
                AlertDialog.Builder builder = new AlertDialog.Builder(MainScreen.this);
                final String[] mCityChoose = { "Киев", "Харьков", "Одесса" };
                builder = new AlertDialog.Builder(this);
                builder.setTitle("Выберите Город");
                builder.setItems(mCityChoose, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick (DialogInterface dialog, int item) {
                        if ( item == 0 )
                            mCity = "_Kyiv";
                        if ( item == 1 )
                            mCity = "_Kharkov";
                        if ( item == 2 )
                            mCity = "_Odessa";
                        Toast.makeText(getApplicationContext(), "Выбранный город: " + mCityChoose[item], Toast.LENGTH_SHORT).show();
                        saveCity();
                        apiCalls();
                    }
                });
                builder.setCancelable(false);
                return builder.create();
        }
        return null;
    }

}
