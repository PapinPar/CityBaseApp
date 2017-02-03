package chi_software.citybase.ui;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

import chi_software.citybase.R;
import chi_software.citybase.SharedCityBase;
import chi_software.citybase.core.BaseActivity;
import chi_software.citybase.ui.fragment.EditUserFragment;
import chi_software.citybase.ui.fragment.MainFragment;


public class MainActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener, MainFragment.OpenTariffs {

    private boolean doubleBackToExitPressedOnce = false;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);
        navigationInitial();
        MainFragment mainFragment = new MainFragment();
        mainFragment.setOpenTarrif(this);
        getSupportFragmentManager().beginTransaction().replace(R.id.include_main, mainFragment, "mail_history_fragment").commit();

    }

    private void navigationInitial () {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        if ( navigationView != null ) {
            navigationView.setNavigationItemSelectedListener(this);
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected (MenuItem item) {
        MainFragment mainFragment = new MainFragment();
        mainFragment.setOpenTarrif(this);
        switch ( item.getItemId() ) {
            case R.id.arenda:
                SharedCityBase.SaveTable(this, "rent_living");
                getSupportFragmentManager().beginTransaction().replace(R.id.include_main, mainFragment, "mail_history_fragment").commit();
                break;
            case R.id.sell:
                SharedCityBase.SaveTable(this, "sale_living");
                getSupportFragmentManager().beginTransaction().replace(R.id.include_main, mainFragment, "mail_history_fragment").commit();
                break;
            case R.id.arendaComer:
                SharedCityBase.SaveTable(this, "rent_not_living");
                getSupportFragmentManager().beginTransaction().replace(R.id.include_main, mainFragment, "mail_history_fragment").commit();
                break;
            case R.id.sellComer:
                SharedCityBase.SaveTable(this, "sale_not_living");
                getSupportFragmentManager().beginTransaction().replace(R.id.include_main, mainFragment, "mail_history_fragment").commit();
                break;
            case R.id.myProfile:
                EditUserFragment mailFragment = new EditUserFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.include_main, mailFragment, "mail_history_fragment").commit();
                break;
            case R.id.tarifs:
                TariffsListActivity tariffsListActivity = new TariffsListActivity();
                getSupportFragmentManager().beginTransaction().replace(R.id.include_main, tariffsListActivity, "mail_history_fragment").commit();
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
            if ( doubleBackToExitPressedOnce ) {
                super.onBackPressed();
                return;
            }
            this.doubleBackToExitPressedOnce = true;
            Toast.makeText(this, "Нажмите снова для выхода", Toast.LENGTH_SHORT).show();

            new Handler().postDelayed(new Runnable() {

                @Override
                public void run () {
                    doubleBackToExitPressedOnce = false;
                }
            }, 2000);
        }

    }


    @Override
    public void openTariff () {
        TariffsListActivity tariffsListActivity = new TariffsListActivity();
        getSupportFragmentManager().beginTransaction().replace(R.id.include_main, tariffsListActivity, "mail_history_fragment").commit();
    }
}

