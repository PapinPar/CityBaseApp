package chi_software.citybase.ui;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

import chi_software.citybase.R;
import chi_software.citybase.core.BaseActivity;
import chi_software.citybase.core.api.Net;
import chi_software.citybase.data.BaseGetPhoto;
import chi_software.citybase.data.ObjectModel;
import chi_software.citybase.ui.adapter.MyAdapter;

public class MainScreen extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    private Toolbar toolbar;
    private DrawerLayout drawer;
    ActionBarDrawerToggle toggle;
    NavigationView navigationView;
    private Button findBut;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private MyAdapter adapter;
    private List<ObjectModel> objectModelList = new ArrayList<>();

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);
        navigationInitial();
        findBut = (Button) findViewById(R.id.findButton);
        findBut.setOnClickListener(this);
        recyclerView = (RecyclerView) findViewById(R.id.MyRecycle);
        layoutManager = new LinearLayoutManager(this);
        adapter = new MyAdapter(objectModelList);
        recyclerView.setAdapter(adapter);

        recyclerView.setLayoutManager(layoutManager);

        app.getNet().getBase("", "_Kharkov", "rent_living", "4698", "05d7762e5ac636443a36a2cd6160111c6877f03f");
    }

    @Override
    public void onClick (View v) {
        switch ( v.getId() ) {
            case R.id.findButton:
                break;
        }
    }

    @Override
    public void onNetRequestDone (@Net.NetEvent int eventId, Object NetObjects) {
        super.onNetRequestDone(eventId, NetObjects);
        switch ( eventId ) {
            case Net.GET_BASE:
                filldata(NetObjects);
                break;
        }
    }

    private void filldata (Object netObjects) {
        BaseGetPhoto s = (BaseGetPhoto) netObjects;
        String url = "http://api.citybase.in.ua/api/img/";
        ArrayList list = new ArrayList();
        String info;
        for ( int i = 0 ; i < s.getBaseGet().getGetResponse().getGetMyObjects().size() ; i++ ) {
            String id = s.getBaseGet().getGetResponse().getGetMyObjects().get(i).getId();
            if ( s.getBaseGet().getGetResponse().getGetMyObjects().get(i).getAdminRegion().length() > 1 )
                info = s.getBaseGet().getGetResponse().getGetMyObjects().get(i).getAdminRegion();
            else if ( s.getBaseGet().getGetResponse().getGetMyObjects().get(i).getPlace().length() > 1 )
                info = s.getBaseGet().getGetResponse().getGetMyObjects().get(i).getPlace();
            else info = s.getBaseGet().getGetResponse().getGetMyObjects().get(i).getCity();
            if ( s.getMap().containsKey(id) ) {
                list = (ArrayList) s.getMap().get(id);
                objectModelList.add(new ObjectModel(s.getBaseGet().getGetResponse().getGetMyObjects().get(i).getPrice(),
                        info,
                        s.getBaseGet().getGetResponse().getGetMyObjects().get(i).getDateUp(),
                        s.getBaseGet().getGetResponse().getGetMyObjects().get(i).getType(),
                        url + list.get(0)));
            } else
                objectModelList.add(new ObjectModel(s.getBaseGet().getGetResponse().getGetMyObjects().get(i).getPrice(),
                        info,
                        s.getBaseGet().getGetResponse().getGetMyObjects().get(i).getDateUp(),
                        s.getBaseGet().getGetResponse().getGetMyObjects().get(i).getType(),
                        null));
        }
        adapter.notifyDataSetChanged();
    }

    private void navigationInitial () {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
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
                break;
            case R.id.sell:
                break;
            case R.id.arendaComer:
                break;
            case R.id.sellComer:
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
    public void onBackPressed () {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if ( drawer.isDrawerOpen(GravityCompat.START) ) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}
