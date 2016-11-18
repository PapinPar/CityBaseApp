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


public class MainScreen extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener, SearchDialog.GetSpinnerListner, MyAdapter.photoListner {

    private Toolbar toolbar;
    private DrawerLayout drawer;
    ActionBarDrawerToggle toggle;
    NavigationView navigationView;
    private Button findBut;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private MyAdapter adapter;
    private List<ModelData> modelDataList = new ArrayList<>();
    private SearchDialog searchDialog;
    private MenuSearch myMenu;
    private BaseResponse baseResponse;
    private SpotsDialog dialog;
    private String table, _key, _id,search;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);
        search = "";
        _key = getIntent().getStringExtra("_key");
        _id = getIntent().getStringExtra("_id");
        table = "rent_living";
        navigationInitial();
        searchDialog = new SearchDialog();
        findBut = (Button) findViewById(R.id.findButton);
        findBut.setOnClickListener(this);
        findBut.setClickable(false);
        findBut.setAlpha((float) 0.4);
        recyclerView = (RecyclerView) findViewById(R.id.MyRecycle);
        layoutManager = new LinearLayoutManager(this);
        adapter = new MyAdapter(modelDataList, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);
        table = "rent_living";
        dialog = new SpotsDialog(MainScreen.this);
        apiCalls();

    }

    private void apiCalls () {
        Log.d("PAPIN_TAG","search"+search);
        app.getNet().getBase(search, "_Kharkov", table, _id, _key);
        app.getNet().searchMenu("_Kharkov", table, _id, _key);
        dialog.show();
        dialog.setCancelable(false);
        modelDataList.clear();
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onClick (View v) {
        switch ( v.getId() ) {
            case R.id.findButton:
                searchDialog.getListner(this, myMenu);
                searchDialog.show(getFragmentManager(), "Поиск");
                break;
        }
    }

    @Override
    public void getPhotoId (String id, int position) {
        String url = "http://api.citybase.in.ua/api/img/";
        ArrayList list = new ArrayList();
        if ( baseResponse.getMap().containsKey(id) )
            list = (ArrayList) baseResponse.getMap().get(id);
        ArrayList<String> urlList = new ArrayList<>();
        for ( int i = 0 ; i < list.size() ; i++ )
            urlList.add(url + list.get(i));

        Intent s = new Intent(MainScreen.this, PagerViwer.class);
        s.putExtra("uid", _id);
        s.putExtra("key", _key);
        s.putExtra("table", table);
        s.putExtra("position", position);
        s.putExtra("size", urlList.size());
        s.putStringArrayListExtra("test", (ArrayList<String>) urlList);
        List<MyObject> baseGets = baseResponse.getBaseGet().getGetResponse().getModel();
        s.putExtra("model", (Serializable) baseGets);
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
        search = json;
        modelDataList.clear();
        adapter.notifyDataSetChanged();
        app.getNet().getBase(search, "_Kharkov", "rent_living", _id, _key);
        dialog.show();
    }

    private void fillsearch (MenuSearch netObjects) {
        myMenu = netObjects;
        findBut.setClickable(true);
        findBut.setAlpha(1);
    }

    private void filldata (Object netObjects) {
        modelDataList.clear();
        baseResponse = (BaseResponse) netObjects;
        String url = "http://api.citybase.in.ua/api/img/";
        ArrayList list = new ArrayList();
        String info = "";
        for ( int i = 0 ; i < baseResponse.getBaseGet().getGetResponse().getModel().size() ; i++ ) {
            String id = baseResponse.getBaseGet().getGetResponse().getModel().get(i).getId();
            if ( baseResponse.getBaseGet().getGetResponse().getModel().get(i).getAdminRegion() != null ) {
                if ( baseResponse.getBaseGet().getGetResponse().getModel().get(i).getAdminRegion().length() > 1 )
                    info = baseResponse.getBaseGet().getGetResponse().getModel().get(i).getAdminRegion();
            } else
                if ( baseResponse.getBaseGet().getGetResponse().getModel().get(i).getPlace() != null ) {
                    if ( baseResponse.getBaseGet().getGetResponse().getModel().get(i).getPlace().length() > 1 )
                        info = baseResponse.getBaseGet().getGetResponse().getModel().get(i).getPlace();
                } else
                    info = baseResponse.getBaseGet().getGetResponse().getModel().get(i).getCity();
            if ( baseResponse.getMap().containsKey(id) ) {
                list = (ArrayList) baseResponse.getMap().get(id);
                modelDataList.add(new ModelData(baseResponse.getBaseGet().getGetResponse().getModel().get(i).getPrice(), info, baseResponse.getBaseGet().getGetResponse().getModel().get(i).getDateUp(), baseResponse.getBaseGet().getGetResponse().getModel().get(i).getType(), baseResponse.getBaseGet().getGetResponse().getModel().get(i).getText(), baseResponse.getBaseGet().getGetResponse().getModel().get(i).getId(), baseResponse.getBaseGet().getGetResponse().getModel().get(i).getColor(), url + list.get(0), table));
            } else
                modelDataList.add(new ModelData(baseResponse.getBaseGet().getGetResponse().getModel().get(i).getPrice(), info, baseResponse.getBaseGet().getGetResponse().getModel().get(i).getDateUp(), baseResponse.getBaseGet().getGetResponse().getModel().get(i).getType(), baseResponse.getBaseGet().getGetResponse().getModel().get(i).getText(), baseResponse.getBaseGet().getGetResponse().getModel().get(i).getId(), baseResponse.getBaseGet().getGetResponse().getModel().get(i).getColor(), null, table));
        }
        dialog.dismiss();
        adapter.notifyDataSetChanged();
    }

    private void navigationInitial () {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected (MenuItem item) {
        switch ( item.getItemId() ) {
            case R.id.arenda:
                table = "rent_living";
                search = "";
                apiCalls();
                break;
            case R.id.sell:
                table = "sale_living";
                search = "";
                apiCalls();
                break;
            case R.id.arendaComer:
                table = "rent_not_living";
                search = "";
                apiCalls();
                break;
            case R.id.sellComer:
                table = "sale_not_living";
                search = "";
                apiCalls();
                break;
            case R.id.myProfile:
                break;
            case R.id.tarifs:
                break;
            case R.id.study:
                break;
            case R.id.contacts:
                break;

        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onRestart () {
        super.onRestart();
        apiCalls();
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
