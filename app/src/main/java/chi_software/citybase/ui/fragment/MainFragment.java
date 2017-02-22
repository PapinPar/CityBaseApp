package chi_software.citybase.ui.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.util.ArrayMap;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.tuyenmonkey.mkloader.MKLoader;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import chi_software.citybase.R;
import chi_software.citybase.SharedCityBase;
import chi_software.citybase.core.BaseFragment;
import chi_software.citybase.core.api.Net;
import chi_software.citybase.data.BaseResponse;
import chi_software.citybase.data.ModelData;
import chi_software.citybase.data.Search;
import chi_software.citybase.data.getBase.MyObject;
import chi_software.citybase.data.login.UserResponse;
import chi_software.citybase.data.menuSearch.MenuSearch;
import chi_software.citybase.ui.adapter.PostAdapter;
import chi_software.citybase.ui.dialog.SearchDialog;
import chi_software.citybase.ui.pager.DetailPostActivity;
import dmax.dialog.SpotsDialog;


public class MainFragment extends BaseFragment implements View.OnClickListener, SearchDialog.GetSpinnerListener, PostAdapter.PostAdapterCall, SwipeRefreshLayout.OnRefreshListener {


    private Button mFindBut;
    private PostAdapter mAdapter;
    private List<ModelData> mModelDataList;
    private SearchDialog mSearchDialog;
    private MenuSearch mMyMenu;
    private BaseResponse mBaseResponse;
    private SpotsDialog mDialog;
    private String mTable, mKey, mUid, mSearch, mCity, tmpCity, mRusCity;
    private int mPage, mLastPosition, tmpPosition;
    private RecyclerView mRecyclerView;
    private List<MyObject> mMyObject;
    private Map<String, List<String>> mKeysModel;
    private List<String> userInfo;
    private SharedPreferences sPref;
    private OpenTariffs openTariffs;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    private List<String> mTypeSelected;
    private List<String> mAreaSelected;
    private List<String> mPunktSelected;
    private MKLoader mLoader;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.content_main_screen, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mFindBut = (Button) view.findViewById(R.id.findButton);
        mLoader = (MKLoader) view.findViewById(R.id.MKLoader);
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.main_swap_refresh);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.MyRecycle);
        mFindBut.setOnClickListener(this);
        mFindBut.setClickable(false);
        mFindBut.setAlpha((float) 0.4);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSearchDialog = new SearchDialog();
        mDialog = new SpotsDialog(getActivity(), "Загрузка");
        init();
        loadShared();
        apiCalls();
    }


    private void init() {
        mSearch = getDate();
        mPage = 1;
        mLastPosition = 0;
        tmpPosition = 0;
        mModelDataList = new ArrayList<>();
        mMyObject = new ArrayList<>();
        mKeysModel = new ArrayMap<>();

        mTypeSelected = new ArrayList<>();
        mAreaSelected = new ArrayList<>();
        mPunktSelected = new ArrayList<>();

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mAdapter = new PostAdapter(mModelDataList, this);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(layoutManager);
    }

    private String getDate() {
        String year = new SimpleDateFormat("yyyy").format(new Date());
        int month = Integer.parseInt(new SimpleDateFormat("MM").format(new Date()));
        int day = Integer.parseInt(new SimpleDateFormat("dd").format(new Date()));
        if (day >= 8)
            day = day - 7;
        else {
            month = month - 1;
            day = 28;
        }
        String dateFrom = year + "-" + month + "-" + day;
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        Search searchJson = new Search();
        searchJson.setDatefrom(dateFrom);
        return gson.toJson(searchJson);
    }

    private void apiCalls() {
        app.getNet().searchMenu(mCity, mTable, mUid, mKey);
        app.getNet().getUser(mUid, mKey, mCity);
        mDialog.setCancelable(false);

        mModelDataList.clear();
        mPage = 1;
        mLastPosition = 0;
        mKeysModel.clear();
        mMyObject.clear();
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.findButton:
                mSearchDialog.getListener(this, mMyMenu);
                mSearchDialog.show(getActivity(), this, mMyMenu, mTypeSelected, mAreaSelected, mPunktSelected,mTable);
                break;
        }
    }

    @Override
    public void getPhotoId(String id, int position) {
        String url = "http://api.citybase.in.ua/api/img/";
        ArrayList list = new ArrayList();
        if (mKeysModel.containsKey(id)) {
            list = (ArrayList) mKeysModel.get(id);
        }
        ArrayList<String> urlList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            urlList.add(url + list.get(i));
        }

        Intent s = new Intent(getActivity(), DetailPostActivity.class);
        s.putExtra(DetailPostActivity.UID, mUid);
        s.putExtra(DetailPostActivity.KEY, mKey);
        s.putExtra(DetailPostActivity.TABLE, mTable);
        s.putExtra(DetailPostActivity.POSITION, position);
        s.putExtra(DetailPostActivity.SIZE, urlList.size());
        s.putStringArrayListExtra(DetailPostActivity.URL, (ArrayList<String>) urlList);
        //       List<MyObject> baseGets = mBaseResponse.getBaseGet().getGetResponse().getModel();
        s.putExtra(DetailPostActivity.MODEL, (Serializable) mMyObject);
        startActivity(s);
    }

    @Override
    public void getLastPost(int position, int size) {
        if (tmpPosition != position) {
            mLastPosition = size;
            mPage++;
            app.getNet().getBase(mSearch, mCity, mTable, mUid, mKey, mPage, mRusCity);
            mLoader.setVisibility(View.VISIBLE);
            tmpPosition = position;
        }
    }

    @Override
    public void onNetRequestDone(@Net.NetEvent int eventId, Object NetObjects) {
        super.onNetRequestDone(eventId, NetObjects);
        switch (eventId) {
            case Net.GET_BASE:
                filldata(NetObjects);
                mLoader.setVisibility(View.GONE);
                break;
            case Net.MENU_SEARC:
                fillsearch((MenuSearch) NetObjects);
                break;
            case Net.GET_USER:
                UserResponse userResponse = (UserResponse) NetObjects;
                userInfo = userResponse.getResponse().getOrders();
                getLevel();
                break;
        }
    }

    @Override
    public void onNetRequestFail(@Net.NetEvent int eventId, Object NetObjects) {
        super.onNetRequestFail(eventId, NetObjects);
        mDialog.dismiss();
        Toast.makeText(getActivity(), "Произошел сбой.Проверьте своё интернет подключение.", Toast.LENGTH_SHORT).show();
    }

    private void getLevel() {
        int count = 0;
        for (String s : userInfo) {
            if (mTable.contains(s)) {
                count++;
            }
        }
        if (count > 0) {
            app.getNet().getBase(mSearch, mCity, mTable, mUid, mKey, mPage, mRusCity);
            mDialog.show();
        } else {
            Toast.makeText(getActivity(), "У вас нет доустпа к этой базе", Toast.LENGTH_SHORT).show();
            openTariffs.openTariff();
        }
    }

    @Override
    public void getSpinner(String json, List<String> mTypeSelected, List<String> mAreaSelected, List<String> mPunktSelected) {
        mPage = 1;
        mLastPosition = 0;
        mSearch = json;
        mModelDataList.clear();
        mKeysModel.clear();
        mMyObject.clear();
        mAdapter.notifyDataSetChanged();
        app.getNet().getBase(mSearch, mCity, mTable, mUid, mKey, mPage, mRusCity);
        mDialog.show();

        this.mTypeSelected = mTypeSelected;
        this.mAreaSelected = mAreaSelected;
        this.mPunktSelected = mPunktSelected;
    }

    private void fillsearch(MenuSearch netObjects) {
        mMyMenu = netObjects;
        mFindBut.setClickable(true);
        mFindBut.setAlpha(1);
    }

    private void filldata(Object netObjects) {
        if (mPage == 1)
            mModelDataList.clear();
        mBaseResponse = (BaseResponse) netObjects;
        mMyObject.addAll(mBaseResponse.getBaseGet().getGetResponse().getModel());
        mKeysModel.putAll(mBaseResponse.getMap());
        String url = "http://api.citybase.in.ua/api/img/";
        ArrayList list = new ArrayList();
        String info = "";
        for (int i = 0; i < mBaseResponse.getBaseGet().getGetResponse().getModel().size(); i++) {
            String id = mBaseResponse.getBaseGet().getGetResponse().getModel().get(i).getId();
            if (mBaseResponse.getBaseGet().getGetResponse().getModel().get(i).getAdminRegion() != null && mBaseResponse.getBaseGet().getGetResponse().getModel().get(i).getAdminRegion().length() > 1) {
                info = mBaseResponse.getBaseGet().getGetResponse().getModel().get(i).getAdminRegion();
            } else if (mBaseResponse.getBaseGet().getGetResponse().getModel().get(i).getPlace() != null && mBaseResponse.getBaseGet().getGetResponse().getModel().get(i).getPlace().length() > 1) {
                info = mBaseResponse.getBaseGet().getGetResponse().getModel().get(i).getPlace();
            } else
                info = mBaseResponse.getBaseGet().getGetResponse().getModel().get(i).getCity();
            if (mBaseResponse.getMap().containsKey(id)) {
                list = (ArrayList) mBaseResponse.getMap().get(id);
                mModelDataList.add(new ModelData(mBaseResponse.getBaseGet().getGetResponse().getModel().get(i).getPrice(), info,
                        //mBaseResponse.getBaseGet().getGetResponse().getModel().get(i).getUrl(),
                        "перейти на сайт", mBaseResponse.getBaseGet().getGetResponse().getModel().get(i).getType(), mBaseResponse.getBaseGet().getGetResponse().getModel().get(i).getText(), mBaseResponse.getBaseGet().getGetResponse().getModel().get(i).getId(), mBaseResponse.getBaseGet().getGetResponse().getModel().get(i).getColor(), url + list.get(0), mTable));
            } else
                mModelDataList.add(new ModelData(mBaseResponse.getBaseGet().getGetResponse().getModel().get(i).getPrice(), info,
                        //mBaseResponse.getBaseGet().getGetResponse().getModel().get(i).getUrl(),
                        "перейти на сайт", mBaseResponse.getBaseGet().getGetResponse().getModel().get(i).getType(), mBaseResponse.getBaseGet().getGetResponse().getModel().get(i).getText(), mBaseResponse.getBaseGet().getGetResponse().getModel().get(i).getId(), mBaseResponse.getBaseGet().getGetResponse().getModel().get(i).getColor(), null, mTable));
        }
        mDialog.dismiss();
        mAdapter.notifyDataSetChanged();
        if (mPage != 1)
            mRecyclerView.smoothScrollToPosition(mLastPosition - 1);
    }

    private void loadShared() {
        mKey = SharedCityBase.GetKey(getActivity());
        mUid = SharedCityBase.GetUID(getActivity());
        mCity = SharedCityBase.GetCity(getActivity());
        mTable = SharedCityBase.GetTable(getActivity());
        mRusCity = SharedCityBase.GetCityRus(getActivity());
        tmpCity = mCity;
    }

    public void setOpenTarrif(OpenTariffs openTariffs) {
        this.openTariffs = openTariffs;
    }

    @Override
    public void onRefresh() {
        if (isNetworkConnected()) {
            apiCalls();
            mSwipeRefreshLayout.setRefreshing(false);
        } else {
            mSwipeRefreshLayout.setRefreshing(false);
            Toast.makeText(getActivity(), "Проверьте своё интернет соединение", Toast.LENGTH_SHORT).show();
        }
    }

    public interface OpenTariffs {
        void openTariff();
    }
}
