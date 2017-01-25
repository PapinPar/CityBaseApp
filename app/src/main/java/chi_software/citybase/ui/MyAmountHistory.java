package chi_software.citybase.ui;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

import chi_software.citybase.R;
import chi_software.citybase.core.BaseActivity;
import chi_software.citybase.core.api.Net;
import chi_software.citybase.data.history_amount.HistoryModel;
import chi_software.citybase.data.history_amount.HistoryResponse;
import chi_software.citybase.ui.adapter.HistoryAdapter;


/**
 * Created by user on 24.01.2017.
 */

public class MyAmountHistory extends BaseActivity  {

    public static final String KEY = "key";
    public static final String UID = "uid";

    private String mKey, mUid;
    private RecyclerView.LayoutManager mLayoutManager;
    private HistoryAdapter mHistoryAdapter;
    private ArrayList<HistoryModel> mHistoryList;
    private RecyclerView mHistoryRv;

    @Override
    protected void onCreate (@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.payments_history_layout);
        mUid = getIntent().getStringExtra(UID);
        mKey = getIntent().getStringExtra(KEY);
        mHistoryRv = (RecyclerView) findViewById(R.id.history_rv);

        mHistoryList = new ArrayList<>();
        mLayoutManager = new LinearLayoutManager(this);
        mHistoryAdapter = new HistoryAdapter(mHistoryList);
        mHistoryRv.setLayoutManager(mLayoutManager);
        mHistoryRv.setNestedScrollingEnabled(false);
        mHistoryRv.setAdapter(mHistoryAdapter);

        app.getNet().setGetHistoryAmount(mUid,mKey);
    }


    @Override
    public void onNetRequestDone (@Net.NetEvent int eventId, Object NetObjects) {
        super.onNetRequestDone(eventId, NetObjects);
        switch ( eventId ){
            case Net.GET_HISTORY_AMOUNT:
                fillHistoryList((HistoryResponse) NetObjects);
                break;
        }
    }

    private void fillHistoryList (HistoryResponse netObjects) {
        mHistoryList.addAll(netObjects.getResponse());
        mHistoryAdapter.notifyDataSetChanged();
    }

    @Override
    public void onNetRequestFail (@Net.NetEvent int eventId, Object NetObjects) {
        super.onNetRequestFail(eventId, NetObjects);
    }
}
