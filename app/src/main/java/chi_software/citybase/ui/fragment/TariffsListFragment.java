package chi_software.citybase.ui.fragment;

import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.customtabs.CustomTabsIntent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;

import chi_software.citybase.R;
import chi_software.citybase.core.BaseFragment;
import chi_software.citybase.core.api.Net;
import chi_software.citybase.data.FieldResponse;
import chi_software.citybase.data.payment.PaymentResponse;
import chi_software.citybase.data.tarif.Tariff;
import chi_software.citybase.data.tarif.TariffModel;
import chi_software.citybase.interfaces.OpenCloseTariff;
import chi_software.citybase.ui.adapter.TariffsAdapter;
import chi_software.citybase.utils.SharedCityBase;
import dmax.dialog.SpotsDialog;


/**
 * Created by user on 26.01.2017.
 */

public class TariffsListFragment extends BaseFragment implements TariffsAdapter.TariffClick {

    private String mKey, mUid, mCity;
    private RecyclerView.LayoutManager mLayoutManager;
    private TariffsAdapter mTariffAdapter;
    private ArrayList<TariffModel> mTariffList;
    private RecyclerView mTariffRv;
    private String ORDER_ACTION;
    private double mAmount;
    private SpotsDialog mDialog;
    private OpenCloseTariff openCloseTariff;


    @Nullable
    @Override
    public View onCreateView (LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.payments_history_layout, container, false);
    }

    @Override
    public void onViewCreated (View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mTariffRv = (RecyclerView) view.findViewById(R.id.history_rv);
        mTariffList = new ArrayList<>();
        mLayoutManager = new LinearLayoutManager(getContext());
        mTariffAdapter = new TariffsAdapter(mTariffList, this);
        mTariffRv.setLayoutManager(mLayoutManager);
        mTariffRv.setAdapter(mTariffAdapter);
        mDialog = new SpotsDialog(getContext(),"Загрузка");
        mDialog.show();
        init();
        app.getNet().getTariffs(mCity, mUid, mKey);
    }

    private void init () {
        mCity = SharedCityBase.GetCity(getContext());
        mUid = SharedCityBase.GetUID(getContext());
        mKey = SharedCityBase.GetKey(getContext());
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
                SuccessActivate();
                break;
            case Net.CREATE_PAYMENT:
                mDialog.dismiss();
                PaymentResponse fieldResponse = (PaymentResponse) NetObjects;
                openChromeTab(fieldResponse.getResponse().getLink());
                break;
        }
    }

    private void SuccessActivate() {
        mDialog.dismiss();
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Поздравляю!")
                .setMessage("Тариф успешно активирован")
                .setCancelable(false)
                .setNegativeButton("Ok",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                openCloseTariff.closeTariff();
                                dialog.cancel();
                            }
                        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    private void openChromeTab (String link) {
        CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
        CustomTabsIntent customTabsIntent = builder.build();
        customTabsIntent.launchUrl(getActivity(), Uri.parse(link));
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
                Toast.makeText(getContext(), "Произошла ошибка.\nПроверьтье интернет соединине и попробуйте снова", Toast.LENGTH_SHORT).show();
                break;
            case Net.CREATE_ORDER:
                mDialog.dismiss();
                Toast.makeText(getContext(), "Произошла ошибка.\nПроверьтье интернет соединине и попробуйте снова", Toast.LENGTH_SHORT).show();
                break;
            case Net.MORE_USERS_ERROR:
                Toast.makeText(getActivity(), (String) NetObjects, Toast.LENGTH_SHORT).show();
                startScreen();
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
    public void buyTariff (String id, double amount) {
        mDialog.show();
        ORDER_ACTION = "BUY";
        mAmount = amount;
        app.getNet().createOrder(id, "Созданно с приложения Android", mUid, mKey);
    }

    public void setOpenTarrif(OpenCloseTariff openCloseTariff) {
        this.openCloseTariff = openCloseTariff;
    }
}
