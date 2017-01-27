package chi_software.citybase.ui;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

import chi_software.citybase.R;
import chi_software.citybase.core.BaseActivity;
import chi_software.citybase.core.api.Net;
import chi_software.citybase.data.tarif.Tariff;
import chi_software.citybase.data.tarif.TariffModel;
import chi_software.citybase.ui.adapter.TariffsAdapter;


/**
 * Created by user on 26.01.2017.
 */

public class TariffsListActivity extends BaseActivity {

    public static final String KEY = "key";
    public static final String UID = "uid";

    private String mKey,mUid,mCity;
    private RecyclerView.LayoutManager mLayoutManager;
    private TariffsAdapter mTariffAdapter;
    private ArrayList<TariffModel> mTariffList;
    private RecyclerView mTariffRv;

    @Override
    protected void onCreate (@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.payments_history_layout);

        mTariffRv = (RecyclerView) findViewById(R.id.history_rv);
        mTariffList = new ArrayList<>();
        mLayoutManager = new LinearLayoutManager(this);
        mTariffAdapter = new TariffsAdapter(mTariffList);
        mTariffRv.setLayoutManager(mLayoutManager);
        mTariffRv.setAdapter(mTariffAdapter);

        init();
        app.getNet().getTariffs(mCity,mUid,mKey);
    }

    private void init () {
        mKey = getIntent().getStringExtra(KEY);
        mUid = getIntent().getStringExtra(UID);
        loadCity();
    }

    private void fillTariffs (Tariff netObjects) {
        mTariffList.addAll(netObjects.getResponse().getTariffModels());
        mTariffAdapter.notifyDataSetChanged();
    }

    @Override
    public void onNetRequestDone (@Net.NetEvent int eventId, Object NetObjects) {
        super.onNetRequestDone(eventId, NetObjects);
        switch ( eventId ){
            case Net.GET_TARIFFS:
                fillTariffs((Tariff) NetObjects);
                break;
        }
    }

    @Override
    public void onNetRequestFail (@Net.NetEvent int eventId, Object NetObjects) {
        super.onNetRequestFail(eventId, NetObjects);
        switch ( eventId ){
            case Net.GET_TARIFFS:
                break;
        }
    }

    private void loadCity () {
        SharedPreferences sPref;
        sPref = getSharedPreferences(EditUserActivity.CITY, MODE_PRIVATE);
        mCity = sPref.getString(EditUserActivity.CITY, "_Kharkov");
    }
}
