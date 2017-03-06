package chi_software.citybase.ui;

import android.app.AlertDialog;
import android.content.DialogInterface;
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
import chi_software.citybase.core.BaseActivity;
import chi_software.citybase.interfaces.OpenCloseTariff;
import chi_software.citybase.ui.fragment.EditUserFragment;
import chi_software.citybase.ui.fragment.InfoFragment;
import chi_software.citybase.ui.fragment.MainFragment;
import chi_software.citybase.ui.fragment.TariffsListFragment;
import chi_software.citybase.utils.SharedCityBase;


public class MainActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener, OpenCloseTariff {

    private boolean doubleBackToExitPressedOnce = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);
        navigationInitial();
        MainFragment mainFragment = new MainFragment();
        mainFragment.setOpenTarrif(this);
        getSupportFragmentManager().beginTransaction().replace(R.id.include_main, mainFragment, "mail_history_fragment").commit();

    }

    private void navigationInitial() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        if (navigationView != null) {
            navigationView.setNavigationItemSelectedListener(this);
        }
        switch (SharedCityBase.GetTable(this)) {
            case "rent_living":
                setTitle("Аренда жилой");
                break;
            case "sale_living":
                setTitle("Продажа жилой");
                break;
            case "rent_not_living":
                setTitle("Аренда коммерческой");
                break;
            case "sale_not_living":
                setTitle("Продажа коммерческой");
                break;
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        MainFragment mainFragment = new MainFragment();
        mainFragment.setOpenTarrif(this);
        switch (item.getItemId()) {
            case R.id.arenda:
                setTitle("Аренда жилой");
                SharedCityBase.SaveTable(this, "rent_living");
                getSupportFragmentManager().beginTransaction().replace(R.id.include_main, mainFragment, "rent_living").commit();
                break;
            case R.id.sell:
                setTitle("Продажа жилой");
                SharedCityBase.SaveTable(this, "sale_living");
                getSupportFragmentManager().beginTransaction().replace(R.id.include_main, mainFragment, "sale_living").commit();
                break;
            case R.id.arendaComer:
                setTitle("Аренда коммерческой");
                SharedCityBase.SaveTable(this, "rent_not_living");
                getSupportFragmentManager().beginTransaction().replace(R.id.include_main, mainFragment, "rent_not_living").commit();
                break;
            case R.id.sellComer:
                setTitle("Продажа коммерческой");
                SharedCityBase.SaveTable(this, "sale_not_living");
                getSupportFragmentManager().beginTransaction().replace(R.id.include_main, mainFragment, "sale_not_living").commit();
                break;
            case R.id.myProfile:
                setTitle("Личный кабинет");
                EditUserFragment editUserFragment = new EditUserFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.include_main, editUserFragment, "mail_history_fragment").commit();
                break;
            case R.id.tarifs:
                setTitle("Тарифы");
                TariffsListFragment tariffsListFragment = new TariffsListFragment();
                tariffsListFragment.setOpenTarrif(this);
                getSupportFragmentManager().beginTransaction().replace(R.id.include_main, tariffsListFragment, "tariffs_List_Fragment").commit();
                break;
            case R.id.study:
                setTitle("О нас");
                InfoFragment infoFragment = new InfoFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.include_main, infoFragment, "info_fragment").commit();
                break;

        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if (doubleBackToExitPressedOnce) {
                super.onBackPressed();
                return;
            }
            this.doubleBackToExitPressedOnce = true;
            Toast.makeText(this, "Нажмите снова для выхода", Toast.LENGTH_SHORT).show();

            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    doubleBackToExitPressedOnce = false;
                }
            }, 2000);
        }

    }

    protected void onCreateDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Внимание!")
                .setMessage("У вас нет доступа к этой базе, Вам необходимо активировать подписку в разделе тарифы.")
                .setCancelable(false)
                .setNegativeButton("Оk",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                setTitle("Тарифы");
                                TariffsListFragment tariffsListFragment = new TariffsListFragment();
                                getSupportFragmentManager().beginTransaction().replace(R.id.include_main, tariffsListFragment, "mail_history_fragment").commit();
                                dialog.cancel();
                            }
                        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    @Override
    public void openTariff() {
        onCreateDialog();
    }

    @Override
    public void closeTariff() {
        setTitle("Личный кабинет");
        EditUserFragment mailFragment = new EditUserFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.include_main, mailFragment, "mail_history_fragment").commit();
    }
}

