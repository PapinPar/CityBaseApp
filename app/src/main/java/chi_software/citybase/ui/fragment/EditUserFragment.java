package chi_software.citybase.ui.fragment;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import chi_software.citybase.R;
import chi_software.citybase.SharedCityBase;
import chi_software.citybase.core.BaseFragment;
import chi_software.citybase.core.api.Net;
import chi_software.citybase.data.FieldResponse;
import chi_software.citybase.data.activ_service.ActivServicess;
import chi_software.citybase.data.activ_service.ServiceData;
import chi_software.citybase.data.activ_service.ServiceResponse;
import chi_software.citybase.data.login.UserResponse;
import chi_software.citybase.ui.MyAmountHistory;
import chi_software.citybase.ui.StartScreen;
import chi_software.citybase.ui.adapter.ActiveServAdapter;
import dmax.dialog.SpotsDialog;


/**
 * Created by Papin on 27.01.2017.
 */

public class EditUserFragment extends BaseFragment implements View.OnClickListener {

    private TextView mPhone, mAmount, mCity;
    private EditText mLogin, mName, mEditMail, mNewPass, mEquelPass;
    private Button mSaveInfo, mMailBut, mChangePass;

    private String nameS, loginS, mKey, mUid, editMailS, mOldMailS, mNewPassS, mEqPassS, userCity;

    private List<ServiceData> serviceList;
    private ActiveServAdapter mAdapter;
    private RecyclerView rvService;
    private Button mHistoryBut;
    private SpotsDialog mDialog;
    private Button mSignOutBut;

    @Nullable
    @Override
    public View onCreateView (LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.edit_user_layout, container, false);
        return view;
    }

    @Override
    public void onViewCreated (View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        mDialog = new SpotsDialog(getActivity(),"Загрузка");
        mPhone = (TextView) view.findViewById(R.id.textPhone);
        mLogin = (EditText) view.findViewById(R.id.editPassET);
        mName = (EditText) view.findViewById(R.id.editNameET);
        mSaveInfo = (Button) view.findViewById(R.id.saveUserInfoBut);
        mEditMail = (EditText) view.findViewById(R.id.editEmailET);
        mMailBut = (Button) view.findViewById(R.id.editMailBut);
        mNewPass = (EditText) view.findViewById(R.id.new_pass_et);
        mEquelPass = (EditText) view.findViewById(R.id.new_equal_pass_et);
        mChangePass = (Button) view.findViewById(R.id.changePasBtn);
        mAmount = (TextView) view.findViewById(R.id.amount_tw);
        rvService = (RecyclerView) view.findViewById(R.id.rvActivServ);
        mCity = (TextView) view.findViewById(R.id.cityTv);
        mHistoryBut = (Button) view.findViewById(R.id.historyBut);
        mSignOutBut = (Button) view.findViewById(R.id.sigOutBut);
        serviceList = new ArrayList<>();

        mMailBut.setOnClickListener(this);
        mSaveInfo.setOnClickListener(this);
        mCity.setOnClickListener(this);
        mChangePass.setOnClickListener(this);
        mHistoryBut.setOnClickListener(this);
        mSignOutBut.setOnClickListener(this);
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

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mAdapter = new ActiveServAdapter(serviceList);
        rvService.setAdapter(mAdapter);
        rvService.setLayoutManager(layoutManager);
        loadShared();
        apiCalls();
    }

    private void apiCalls () {
        if ( !isNetworkConnected() )
            Toast.makeText(getActivity(), "Проверьте интернр соединение", Toast.LENGTH_SHORT).show();
        else {
            app.getNet().getUser(mUid, mKey, "");
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
                Toast.makeText(getActivity(), "Данные успешно изменены", Toast.LENGTH_SHORT).show();
                //finish();
                break;
            case Net.DELETE_USER_EMAIL:
                editMailS = mEditMail.getText().toString();
                mDialog.show();
                app.getNet().addUserEmail(mUid, mKey, editMailS);
                break;
            case Net.ADD_USER_EMAIL:
                mDialog.dismiss();
                Toast.makeText(getActivity(), "Вам будет отправленно письмо на " + editMailS, Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(getActivity(), "Проверьте интернр соединение", Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(getActivity(), "Проверьте интернр соединение", Toast.LENGTH_SHORT).show();
                else {
                    if ( mChangePass.getAlpha() == 1 ) {
                        mNewPassS = mNewPass.getText().toString();
                        mEqPassS = mEquelPass.getText().toString();
                        if ( mNewPassS.equals(mEqPassS) ) {
                            app.getNet().editUserPassword(mUid, mKey, mNewPassS, mEqPassS);
                        } else
                            Toast.makeText(getActivity(), "Пароли не воспадают", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
            case R.id.cityTv:
                onCreateDialog();
                break;
            case R.id.historyBut:
                Intent showHistory = new Intent(getContext(), MyAmountHistory.class);
                showHistory.putExtra(MyAmountHistory.UID, mUid);
                showHistory.putExtra(MyAmountHistory.KEY, mKey);
                startActivity(showHistory);
                break;
            case R.id.sigOutBut:
                SharedCityBase.SetPassword(getContext(), "");
                SharedCityBase.SetLogin(getContext(), "");
                Intent start = new Intent(getContext(), StartScreen.class);
                startActivity(start);
                getActivity().finish();
                break;
        }
    }


    private void saveShared () {
        SharedCityBase.SaveCity(getActivity(), userCity);
        SharedCityBase.SaveKey(getActivity(), mKey);
        SharedCityBase.SaveUID(getActivity(), mUid);
    }

    private void loadShared () {
        userCity = SharedCityBase.GetCity(getActivity());
        mCity.setText(userCity);
        mKey = SharedCityBase.GetKey(getActivity());
        mUid = SharedCityBase.GetUID(getActivity());
    }

    protected void onCreateDialog () {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        final String[] mCityChoose = { "Киев", "Харьков", "Одесса" };
        builder = new AlertDialog.Builder(getActivity());
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
                Toast.makeText(getContext(), "Местоположение изменено", Toast.LENGTH_SHORT).show();
                saveShared();
                apiCalls();
            }
        });
        builder.setCancelable(false);
        builder.show();
    }

}