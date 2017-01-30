package chi_software.citybase.ui;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.customtabs.CustomTabsIntent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import java.util.ArrayList;

import chi_software.citybase.R;
import chi_software.citybase.SharedCityBase;
import chi_software.citybase.core.BaseActivity;
import chi_software.citybase.core.api.Net;
import chi_software.citybase.data.FieldResponse;
import chi_software.citybase.data.tarif.Tariff;
import chi_software.citybase.data.tarif.TariffModel;
import chi_software.citybase.ui.adapter.TariffsAdapter;
import dmax.dialog.SpotsDialog;


/**
 * Created by user on 26.01.2017.
 */

public class TariffsListActivity extends BaseActivity implements TariffsAdapter.TariffClick {

    private String mKey, mUid, mCity;
    private RecyclerView.LayoutManager mLayoutManager;
    private TariffsAdapter mTariffAdapter;
    private ArrayList<TariffModel> mTariffList;
    private RecyclerView mTariffRv;
    private String ORDER_ACTION;
    private float mAmount;
    private SpotsDialog mDialog;


    @Override
    protected void onCreate (@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.payments_history_layout);

        mTariffRv = (RecyclerView) findViewById(R.id.history_rv);
        mTariffList = new ArrayList<>();
        mLayoutManager = new LinearLayoutManager(this);
        mTariffAdapter = new TariffsAdapter(mTariffList, this);
        mTariffRv.setLayoutManager(mLayoutManager);
        mTariffRv.setAdapter(mTariffAdapter);
        mDialog = new SpotsDialog(TariffsListActivity.this);
        mDialog.show();
        init();
        app.getNet().getTariffs(mCity, mUid, mKey);
    }

    private void init () {
        mCity = SharedCityBase.GetCity(this);
        mUid = SharedCityBase.GetUID(this);
        mKey = SharedCityBase.GetKey(this);
    }

    private void fillTariffs (Tariff netObjects) {
        mTariffList.addAll(netObjects.getResponse().getTariffModels());
        mTariffAdapter.notifyDataSetChanged();
    }

    @Override
    public void onNetRequestDone (@Net.NetEvent int eventId, Object NetObjects) {
        super.onNetRequestDone(eventId, NetObjects);
        switch ( eventId ) {
            case Net.GET_TARIFFS:
                fillTariffs((Tariff) NetObjects);
                mDialog.dismiss();
                break;
            case Net.CREATE_ORDER:
                if ( ORDER_ACTION.equals("ACTIVATE") ) {
                    activateOrder((FieldResponse) NetObjects);
                }
                if ( ORDER_ACTION.equals("BUY") ) {
                    buyOrder((FieldResponse) NetObjects);
                }
                break;
            case Net.ACTIVATE_ORDER:
                mDialog.dismiss();
                Toast.makeText(this, "Тариф успешно активирован", Toast.LENGTH_SHORT).show();
                finish();
                break;
            case Net.CREATE_PAYMENT:
                mDialog.dismiss();
                String fieldResponse = (String) NetObjects;
                openChromeTab(fieldResponse);
                break;
        }
    }

    private void openChromeTab (String link) {
        CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
        CustomTabsIntent customTabsIntent = builder.build();
        customTabsIntent.launchUrl(this, Uri.parse(link));
    }

    private void buyOrder (FieldResponse orderId) {
        app.getNet().createPayment(mUid, mKey, mAmount, "Оплата тарифа", "all", orderId.getServerResponse());
    }

    private void activateOrder (FieldResponse orderId) {
        app.getNet().activateOrder(orderId.getServerResponse(), mUid, mKey);
    }

    @Override
    public void onNetRequestFail (@Net.NetEvent int eventId, Object NetObjects) {
        super.onNetRequestFail(eventId, NetObjects);
        switch ( eventId ) {
            case Net.GET_TARIFFS:
                mDialog.dismiss();
                Toast.makeText(this, "Произошла ошибка.\nПроверьтье интернет соединине и попробуйте снова", Toast.LENGTH_SHORT).show();
                break;
            case Net.CREATE_ORDER:
                mDialog.dismiss();
                Toast.makeText(this, "Произошла ошибка.\nПроверьтье интернет соединине и попробуйте снова", Toast.LENGTH_SHORT).show();
                break;
        }
    }
    @Override
    public void activateOrder (String id) {
        mDialog.show();
        app.getNet().createOrder(id, "Созданно с приложения Android", mUid, mKey);
        ORDER_ACTION = "ACTIVATE";
    }

    @Override
    public void buyTariff (String id, float amount) {
        mDialog.show();
        ORDER_ACTION = "BUY";
        mAmount = amount;
        app.getNet().createOrder(id, "Созданно с приложения Android", mUid, mKey);
    }
}
