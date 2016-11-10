package chi_software.citybase.ui;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

import chi_software.citybase.R;
import chi_software.citybase.core.BaseActivity;
import chi_software.citybase.core.api.Net;
import chi_software.citybase.data.BaseGetPhoto;

public class MainScreen extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    private Toolbar toolbar;
    private DrawerLayout drawer;
    ActionBarDrawerToggle toggle;
    NavigationView navigationView;
    private Button findBut;
    private RecyclerView myRecylcer;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);
        navigationInitial();
        findBut = (Button) findViewById(R.id.findButton);
        myRecylcer = (RecyclerView) findViewById(R.id.MyRecycle);
        findBut.setOnClickListener(this);
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
                BaseGetPhoto s = (BaseGetPhoto) NetObjects;
                ArrayList list = new ArrayList();
                for ( int i = 0 ; i < s.getMap().size() ; i++ ) {
                    String id = s.getBaseGet().getGetResponse().getGetMyObjects().get(i).getId();
                    if ( s.getMap().containsKey(id) ) {
                        list.add(s.getMap().get(id));
                    }
                }
                break;
        }
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
