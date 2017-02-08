package chi_software.citybase.ui.dialog;

import android.app.Activity;
import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import chi_software.citybase.R;
import chi_software.citybase.data.Search;
import chi_software.citybase.data.menuSearch.MenuSearch;
import chi_software.citybase.multiSpinner.MultiSelectionSpinner;


/**
 * Created by Papin on 10.11.2016.
 */

public class SearchDialog extends DialogFragment implements MultiSelectionSpinner.OnMultipleItemsSelectedListener {

    private View view;
    private MultiSelectionSpinner mSpinerType;
    private MultiSelectionSpinner mSpinerArea;
    private MultiSelectionSpinner mSpinerPunrkt;
    private EditText priceFromET;
    private EditText priceToET;
    private EditText textPhoneET;
    private EditText commentET;

    private List<String> mTypeSelected;
    private List<String> mAreaSelected;
    private List<String> mPunktSelected;

    private CheckBox mRadioLong;
    private CheckBox mRadioShort;

    private MenuSearch menuSearch;
    private GetSpinnerListener getSpinnerListener;

    public void show (Activity activity, GetSpinnerListener getSpinnerListener, MenuSearch menuSearch, List<String> mTypeSelected, List<String> mAreaSelected, List<String> mPunktSelected) {
        this.mTypeSelected = mTypeSelected;
        this.mAreaSelected = mAreaSelected;
        this.mPunktSelected = mPunktSelected;
        getListener(getSpinnerListener, menuSearch);
        show(activity.getFragmentManager(), "Поиск");
    }



    public interface GetSpinnerListener {
        void getSpinner (String json, List<String> mTypeSelected, List<String> mAreaSelected, List<String> mPunktSelected);
    }

    public void getListener (GetSpinnerListener getSpinnerListener, MenuSearch menuSearch) {
        this.getSpinnerListener = getSpinnerListener;
        this.menuSearch = menuSearch;
    }

    @Nullable
    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getDialog().setTitle("");
        view = inflater.inflate(R.layout.search_dialog_layout, null);

        mSpinerType = (MultiSelectionSpinner) view.findViewById(R.id.spinnerTypeNew);
        mSpinerArea = (MultiSelectionSpinner) view.findViewById(R.id.spinnerAreaNew);
        mSpinerPunrkt = (MultiSelectionSpinner) view.findViewById(R.id.spinnerPunktNew);
        priceFromET = (EditText) view.findViewById(R.id.priceFromET);
        priceToET = (EditText) view.findViewById(R.id.priceToET);
        textPhoneET = (EditText) view.findViewById(R.id.phoneET);
        commentET = (EditText) view.findViewById(R.id.comentET);
        Button button = (Button) view.findViewById(R.id.buttFind);

        final List<String> mListTerm = new ArrayList<>();
        mRadioLong = (CheckBox) view.findViewById(R.id.radioLong);
        mRadioShort = (CheckBox) view.findViewById(R.id.radioShort);

        mRadioLong.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged (CompoundButton buttonView, boolean isChecked) {
                if ( isChecked )
                    mListTerm.add("Д");
                else
                    mListTerm.remove("Д");
            }
        });

        mRadioShort.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged (CompoundButton buttonView, boolean isChecked) {
                if ( isChecked ) {
                    mListTerm.add("С");
                } else {
                    mRadioShort.setChecked(false);
                    mListTerm.remove("С");
                }
            }
        });

        ArrayList<String> listType = new ArrayList<>();
        ArrayList<String> listPunkt = new ArrayList<>();
        ArrayList<String> listArea = new ArrayList<>();

        for ( int i = 0 ; i < menuSearch.getMenuResponse().getTypes().size() ; i++ ) {
            listType.add(menuSearch.getMenuResponse().getTypes().get(i));
        }
        for ( int i = 0 ; i < menuSearch.getMenuResponse().getCities().size() ; i++ ) {
            listPunkt.add(menuSearch.getMenuResponse().getCities().get(i));
        }
        for ( int i = 0 ; i < menuSearch.getMenuResponse().getPlaces().size() ; i++ ) {
            listArea.add(menuSearch.getMenuResponse().getPlaces().get(i));
        }

        mSpinerType.setItems(listType);
        mSpinerType.setListener(this);

        mSpinerArea.setItems(listArea);
        mSpinerArea.setListener(this);

        mSpinerPunrkt.setItems(listPunkt);
        mSpinerPunrkt.setListener(this);

        mSpinerType.setSelection(mTypeSelected);
        mSpinerArea.setSelection(mAreaSelected);
        mSpinerPunrkt.setSelection(mPunktSelected);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v) {
                String year = new SimpleDateFormat("yyyy").format(new Date());
                int month = Integer.parseInt(new SimpleDateFormat("MM").format(new Date()));
                int day = Integer.parseInt(new SimpleDateFormat("dd").format(new Date()));
                if ( day >= 8 )
                    day = day - 7;
                else {
                    month = month - 1;
                    day = 28;
                }
                String dateFrom = year + "-" + month + "-" + day;
                Search searchJson = new Search();
                if ( mListTerm.size() >= 1 )
                    searchJson.setTerm(mListTerm);
                searchJson.setCity(mPunktSelected);
                searchJson.setPlace(mAreaSelected);
                searchJson.setTypes(mTypeSelected);
                searchJson.setDatefrom(dateFrom);
                if ( priceFromET.getText().toString().length() > 0 )
                    searchJson.setPricefrom(priceFromET.getText().toString());
                if ( priceToET.getText().toString().length() > 0 )
                    searchJson.setPriceto(priceToET.getText().toString());
                if ( commentET.getText().toString().length() > 0 )
                    searchJson.setComment(commentET.getText().toString());
                if ( textPhoneET.getText().toString().length() > 0 )
                    searchJson.setTextorphone(textPhoneET.getText().toString());
                GsonBuilder builder = new GsonBuilder();
                Gson gson = builder.create();
                Log.i("GSON", gson.toJson(searchJson));
                String json = gson.toJson(searchJson);
                getSpinnerListener.getSpinner(json, mTypeSelected, mPunktSelected, mAreaSelected);
                dismiss();
            }
        });
        return view;
    }

    @Override
    public void selectedIndices (List<Integer> indices) {
        Log.d("SearchDialog", "indices:" + indices);
    }

    @Override
    public void selectedStrings (List<String> strings) {
        Log.d("SearchDialog", "strings:" + strings);
        mTypeSelected = mSpinerType.getSelectedStrings();
        mSpinerType.setSelection(mTypeSelected);

        mAreaSelected = mSpinerArea.getSelectedStrings();
        mSpinerArea.setSelection(mAreaSelected);

        mPunktSelected = mSpinerPunrkt.getSelectedStrings();
        mSpinerPunrkt.setSelection(mPunktSelected);

    }

}
