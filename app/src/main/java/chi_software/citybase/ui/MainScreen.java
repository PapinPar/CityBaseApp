package chi_software.citybase.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import chi_software.citybase.R;
import chi_software.citybase.core.BaseActivity;
import chi_software.citybase.core.api.Net;
import chi_software.citybase.data.BaseResponse;
import chi_software.citybase.data.ModelData;
import chi_software.citybase.data.getBase.MyObject;
import chi_software.citybase.data.menuSearch.MenuSearch;
import chi_software.citybase.ui.adapter.MyAdapter;
import chi_software.citybase.ui.dialog.SearchDialog;
import chi_software.citybase.ui.pager.PagerViwer;
import dmax.dialog.SpotsDialog;


public class MainScreen extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener, SearchDialog.GetSpinnerListner, MyAdapter.photoListener {

    public static final String KEY = "key";
    public static final String UID = "uid";

    private Button mFindBut;
    private MyAdapter mAdapter;
    private List<ModelData> mModelDataList;
    private SearchDialog mSearchDialog;
    private MenuSearch mMyMenu;
    private BaseResponse mBaseResponse;
    private SpotsDialog mDialog;
    private String mTable, mKey, mUid, mSearch;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);
        mSearch = "";
        mModelDataList = new ArrayList<>();
        mKey = getIntent().getStringExtra(KEY);
        mUid = getIntent().getStringExtra(UID);
        mTable = "rent_living";
        navigationInitial();
        mSearchDialog = new SearchDialog();
        mFindBut = (Button) findViewById(R.id.findButton);
        mFindBut.setOnClickListener(this);
        mFindBut.setClickable(false);
        mFindBut.setAlpha((float) 0.4);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.MyRecycle);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        mAdapter = new MyAdapter(mModelDataList, this);
        recyclerView.setAdapter(mAdapter);
        recyclerView.setLayoutManager(layoutManager);
        mTable = "rent_living";
        mDialog = new SpotsDialog(MainScreen.this);
        apiCalls();

    }

    private void apiCalls () {
        app.getNet().getBase(mSearch, "_Kharkov", mTable, mUid, mKey);
        app.getNet().searchMenu("_Kharkov", mTable, mUid, mKey);
        mDialog.show();
        mDialog.setCancelable(false);
        mModelDataList.clear();
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
        if ( mBaseResponse.getMap().containsKey(id) )
            list = (ArrayList) mBaseResponse.getMap().get(id);
        ArrayList<String> urlList = new ArrayList<>();
        for ( int i = 0 ; i < list.size() ; i++ )
            urlList.add(url + list.get(i));

        Intent s = new Intent(MainScreen.this, PagerViwer.class);
        s.putExtra(PagerViwer.UID, mUid);
        s.putExtra(PagerViwer.KEY, mKey);
        s.putExtra(PagerViwer.TABLE, mTable);
        s.putExtra(PagerViwer.POSITION, position);
        s.putExtra(PagerViwer.SIZE, urlList.size());
        s.putStringArrayListExtra(PagerViwer.URL, (ArrayList<String>) urlList);
        List<MyObject> baseGets = mBaseResponse.getBaseGet().getGetResponse().getModel();
        s.putExtra(PagerViwer.MODEL, (Serializable) baseGets);
        startActivity(s);
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
        mSearch = json;
        mModelDataList.clear();
        mAdapter.notifyDataSetChanged();
        Log.d("MainScreen", mTable);
        app.getNet().getBase(mSearch, "_Kharkov", mTable, mUid, mKey);
        mDialog.show();
    }

    private void fillsearch (MenuSearch netObjects) {
        mMyMenu = netObjects;
        mFindBut.setClickable(true);
        mFindBut.setAlpha(1);
    }

    private void filldata (Object netObjects) {
        Log.d("MainScreenFill", mTable);
        mModelDataList.clear();
        mBaseResponse = (BaseResponse) netObjects;
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
    }

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
                startActivity(profile);
                break;
            case R.id.tarifs:
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
}
