package chi_software.citybase.ui;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import chi_software.citybase.R;
import chi_software.citybase.core.BaseActivity;
import chi_software.citybase.core.api.Net;
import chi_software.citybase.data.FieldResponse;
import chi_software.citybase.data.activ_service.ActivServicess;
import chi_software.citybase.data.activ_service.ServiceData;
import chi_software.citybase.data.activ_service.ServiceResponse;
import chi_software.citybase.data.login.UserResponse;
import chi_software.citybase.ui.adapter.ActiveServAdapter;
import dmax.dialog.SpotsDialog;


/**
 * Created by Papin on 30.11.2016.
 */

public class EditUserActivity extends BaseActivity implements View.OnClickListener {

    public static final String KEY = "key";
    public static final String UID = "uid";
    public static final String CITY = "city";

    private Toolbar toolbar;
    private DrawerLayout drawer;
    private ActionBarDrawerToggle toggle;
    private NavigationView navigationView;

    private TextView mPhone, mAmount, mCity;
    private EditText mLogin, mName, mEditMail, mNewPass, mEquelPass;
    private Button mSaveInfo, mMailBut, mChangePass;

    private String nameS, loginS, mKey, mUid, editMailS, mOldMailS, mNewPassS, mEqPassS, userCity;

    private List<ServiceData> serviceList;
    private ActiveServAdapter mAdapter;
    private RecyclerView rvService;
    private Button mHistoryBut;
    private SharedPreferences sPref;
    private SpotsDialog mDialog;

    @Override
    protected void onCreate (@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        mKey = getIntent().getStringExtra(KEY);
        mUid = getIntent().getStringExtra(UID);
        mDialog = new SpotsDialog(EditUserActivity.this);
        loadCity();
        mPhone = (TextView) findViewById(R.id.textPhone);
        mLogin = (EditText) findViewById(R.id.editPassET);
        mName = (EditText) findViewById(R.id.editNameET);
        mSaveInfo = (Button) findViewById(R.id.saveUserInfoBut);
        mEditMail = (EditText) findViewById(R.id.editEmailET);
        mMailBut = (Button) findViewById(R.id.editMailBut);
        mNewPass = (EditText) findViewById(R.id.new_pass_et);
        mEquelPass = (EditText) findViewById(R.id.new_equal_pass_et);
        mChangePass = (Button) findViewById(R.id.changePasBtn);
        mAmount = (TextView) findViewById(R.id.amount_tw);
        rvService = (RecyclerView) findViewById(R.id.rvActivServ);
        mCity = (TextView) findViewById(R.id.cityTv);
        mHistoryBut = (Button) findViewById(R.id.historyBut);
        serviceList = new ArrayList<>();

        mMailBut.setOnClickListener(this);
        mSaveInfo.setOnClickListener(this);
        mCity.setOnClickListener(this);
        mChangePass.setOnClickListener(this);
        mHistoryBut.setOnClickListener(this);
        mEditMail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged (CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged (CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged (Editable s) {
                if ( mEditMail.getText().toString().length() > 0 && mEditMail.getText().toString().contains("@") ) {
                    mMailBut.setAlpha(1);
                } else
                    mMailBut.setAlpha((float) 0.6);
            }
        });
        mEquelPass.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged (CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged (CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged (Editable s) {
                if ( mNewPass.getText().toString().length() == mEquelPass.getText().toString().length() ) {
                    mChangePass.setAlpha(1);
                } else
                    mChangePass.setAlpha((float) 0.6);
            }
        });

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        mAdapter = new ActiveServAdapter(serviceList);
        rvService.setAdapter(mAdapter);
        rvService.setLayoutManager(layoutManager);

        apiCalss();
    }

    private void apiCalss () {
        if ( !isNetworkConnected() )
            Toast.makeText(this, "Проверьте интернр соединение", Toast.LENGTH_SHORT).show();
        else {
            mDialog.show();
            app.getNet().getUser(mUid, mKey,"");
            app.getNet().getMyAmount(mUid, mKey);
        }
    }


    @Override
    public void onNetRequestDone (@Net.NetEvent int eventId, Object NetObjects) {
        super.onNetRequestDone(eventId, NetObjects);
        switch ( eventId ) {
            case Net.GET_USER:
                UserResponse userResponse = (UserResponse) NetObjects;
                filldata(userResponse);
                break;
            case Net.EDIT_USER_LOGIN:
                Toast.makeText(this, "Данные успешно изменены", Toast.LENGTH_SHORT).show();
                finish();
                break;
            case Net.DELETE_USER_EMAIL:
                editMailS = mEditMail.getText().toString();
                mDialog.show();
                app.getNet().addUserEmail(mUid, mKey, editMailS);
                break;
            case Net.ADD_USER_EMAIL:
                mDialog.dismiss();
                Toast.makeText(this, "Вам будет отправленно письмо на " + editMailS, Toast.LENGTH_SHORT).show();
                break;
            case Net.GET_MY_AMOUNT:
                FieldResponse myAmount = (FieldResponse) NetObjects;
                mAmount.setText(myAmount.getServerResponse() + " грн.");
                app.getNet().getActivService(userCity, mUid, mKey);
                break;
            case Net.GET_ACTIVE_SERVICE:
                mDialog.dismiss();
                ServiceResponse serviceResponse = (ServiceResponse) NetObjects;
                fillServiceData(serviceResponse);
                break;
        }
    }

    private void filldata (UserResponse userResponse) {
        mPhone.setText(userResponse.getResponse().getPhone());
        mName.setText(userResponse.getResponse().getName());
        mLogin.setText(userResponse.getResponse().getLogin());
        if ( userResponse.getResponse().getEmail() != null )
            mOldMailS = userResponse.getResponse().getEmail();
        if ( userResponse.getResponse().getEmail() == null || userResponse.getResponse().getEmail().toString().equals("") )
            mOldMailS = "NULL";
        mEditMail.setText(userResponse.getResponse().getEmail());
        if ( userCity.equals("_Kyiv") )
            mCity.setText("Киев");
        if ( userCity.equals("_Kharkov") )
            mCity.setText("Харьков");
        if ( userCity.equals("_Odessa") )
            mCity.setText("Одесса");
    }

    private void fillServiceData (ServiceResponse serviceResponse) {
        serviceList.clear();
        for ( ActivServicess serv : serviceResponse.getResponse() ) {
            serviceList.add(new ServiceData(serv.getDateFrom(), serv.getDateTo(), serv.getRusName()));
        }
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onClick (View v) {
        switch ( v.getId() ) {
            case R.id.saveUserInfoBut:
                nameS = mName.getText().toString().trim();
                loginS = mLogin.getText().toString();
                if ( isNetworkConnected() )
                    app.getNet().editUserLogin(mUid, mKey, nameS, loginS);
                break;
            case R.id.editMailBut:
                if ( !isNetworkConnected() )
                    Toast.makeText(this, "Проверьте интернр соединение", Toast.LENGTH_SHORT).show();
                else {
                    if ( mMailBut.getAlpha() == 1 ) {
                        if ( !mOldMailS.equals("NULL") ) {
                            app.getNet().deleteUserEmail(mUid, mKey);
                        } else {
                            editMailS = mEditMail.getText().toString();
                            app.getNet().addUserEmail(mUid, mKey, editMailS);
                        }
                    }
                }
                break;
            case R.id.changePasBtn:
                if ( !isNetworkConnected() )
                    Toast.makeText(this, "Проверьте интернр соединение", Toast.LENGTH_SHORT).show();
                else {
                    if ( mChangePass.getAlpha() == 1 ) {
                        mNewPassS = mNewPass.getText().toString();
                        mEqPassS = mEquelPass.getText().toString();
                        if ( mNewPassS.equals(mEqPassS) ) {
                            app.getNet().editUserPassword(mUid, mKey, mNewPassS, mEqPassS);
                        } else
                            Toast.makeText(this, "Пароли не воспадают", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
            case R.id.cityTv:
                showDialog(0);
                break;
            case R.id.historyBut:
                Intent showHistory = new Intent(EditUserActivity.this, MyAmountHistory.class);
                showHistory.putExtra(MyAmountHistory.UID, mUid);
                showHistory.putExtra(MyAmountHistory.KEY, mKey);
                startActivity(showHistory);
                break;
        }
    }


    // shared preferences
    private void saveCity () {
        sPref = getSharedPreferences(EditUserActivity.CITY, MODE_PRIVATE);
        SharedPreferences.Editor ed = sPref.edit();
        ed.putString(EditUserActivity.CITY, userCity);
        ed.apply();
    }

    private void loadCity () {
        sPref = getSharedPreferences(EditUserActivity.CITY, MODE_PRIVATE);
        userCity = sPref.getString(EditUserActivity.CITY, "_Kharkov");
    }
    // shared preferences


    @Override
    public void onBackPressed () {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_edit);
        if ( drawer.isDrawerOpen(GravityCompat.START) ) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            setResult(5);
            super.onBackPressed();
        }
    }

    @Override
    protected Dialog onCreateDialog (final int id) {
        switch ( id ) {
            case 0:
                AlertDialog.Builder builder = new AlertDialog.Builder(EditUserActivity.this);
                final String[] mCityChoose = { "Киев", "Харьков", "Одесса" };
                builder = new AlertDialog.Builder(this);
                builder.setTitle("Выберите Город");
                builder.setItems(mCityChoose, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick (DialogInterface dialog, int item) {
                        if ( item == 0 )
                            userCity = "_Kyiv";
                        if ( item == 1 )
                            userCity = "_Kharkov";
                        if ( item == 2 )
                            userCity = "_Odessa";
                        Toast.makeText(getApplicationContext(), "Выбранный город: " + mCityChoose[item], Toast.LENGTH_SHORT).show();
                        saveCity();
                        apiCalss();
                    }
                });
                builder.setCancelable(false);
                return builder.create();
        }
        return null;
    }

}
