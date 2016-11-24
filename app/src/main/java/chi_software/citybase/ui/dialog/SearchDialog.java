package chi_software.citybase.ui.dialog;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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

    View view;
    private MultiSelectionSpinner spinerType;
    private MultiSelectionSpinner spinerArea;
    private MultiSelectionSpinner spinerPunrkt;
    private EditText priceFrom;
    private EditText priceTo;
    private EditText text_phone;
    private EditText comment;
    private Button button;
    private ArrayList<String> listType;
    private ArrayList<String> listArea;
    private ArrayList<String> listPunkt;
    private ArrayList<Integer> type;
    private ArrayList<Integer> area;
    private ArrayList<Integer> punkt;
    List<String> typeSelected;
    List<String> areaSelected;
    List<String> punktSelected;

    MenuSearch menuSearch;
    GetSpinnerListner getSpinnerListner;

    public interface GetSpinnerListner {
        void getSpinner (String json);
    }

    public void getListner (GetSpinnerListner getSpinnerListner, MenuSearch menuSearch) {
        this.getSpinnerListner = getSpinnerListner;
        this.menuSearch = menuSearch;
    }

    @Nullable
    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getDialog().setTitle("");
        view = inflater.inflate(R.layout.search_dialog_layout, null);
        spinerType = (MultiSelectionSpinner) view.findViewById(R.id.spinnerType);
        spinerArea = (MultiSelectionSpinner) view.findViewById(R.id.spinnerArea);
        spinerPunrkt = (MultiSelectionSpinner) view.findViewById(R.id.spinnerPunkt);
        priceFrom = (EditText) view.findViewById(R.id.priceFromET);
        priceTo = (EditText) view.findViewById(R.id.priceToET);
        text_phone = (EditText) view.findViewById(R.id.phoneET);
        comment = (EditText) view.findViewById(R.id.comentET);
        button = (Button) view.findViewById(R.id.buttFind);

        listType = new ArrayList<>(); //тип
        listPunkt = new ArrayList<>(); // населенный пункт
        listArea = new ArrayList<>();// район
        typeSelected = new ArrayList<>();
        areaSelected = new ArrayList<>();
        punktSelected = new ArrayList<>();
        typeSelected.clear();
        areaSelected.clear();
        punktSelected.clear();

        listType.add("Все");
        listPunkt.add("Все");
        listArea.add("Все");

        for ( int i = 0 ; i < menuSearch.getMenuResponse().getTypes().size() ; i++ ) {
            listType.add(menuSearch.getMenuResponse().getTypes().get(i));
        }
        for ( int i = 0 ; i < menuSearch.getMenuResponse().getCities().size() ; i++ ) {
            listPunkt.add(menuSearch.getMenuResponse().getCities().get(i));
        }
        for ( int i = 0 ; i < menuSearch.getMenuResponse().getPlaces().size() ; i++ ) {
            listArea.add(menuSearch.getMenuResponse().getPlaces().get(i));
        }

        spinerType.setItems(listType);
        spinerType.setListener(this);

        spinerArea.setItems(listArea);
        spinerArea.setListener(this);

        spinerPunrkt.setItems(listPunkt);
        spinerPunrkt.setListener(this);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v) {
                String dateFrom = new SimpleDateFormat("yyyy-MM").format(new Date());
                int day = Integer.parseInt(new SimpleDateFormat("dd").format(new Date()));
                day = day-7;
                dateFrom = dateFrom+"-"+day;
                Search searchJson = new Search();
                searchJson.setCity(punktSelected);
                searchJson.setPlace(areaSelected);
                searchJson.setTypes(typeSelected);
                searchJson.setDatefrom(dateFrom);
                if ( priceFrom.getText().toString().length() > 0 )
                    searchJson.setPricefrom(priceFrom.getText().toString());
                if ( priceTo.getText().toString().length() > 0 )
                    searchJson.setPriceto(priceTo.getText().toString());
                if ( comment.getText().toString().length() > 0 )
                    searchJson.setComment(comment.getText().toString());
                if ( text_phone.getText().toString().length() > 0 )
                    searchJson.setTextorphone(text_phone.getText().toString());
                GsonBuilder builder = new GsonBuilder();
                Gson gson = builder.create();
                Log.i("GSON", gson.toJson(searchJson));
                String json = gson.toJson(searchJson);
                getSpinnerListner.getSpinner(json);
                dismiss();
            }
        });

        return view;
    }

    @Override
    public void selectedIndices (List<Integer> indices) {
    }

    @Override
    public void selectedStrings (List<String> strings) {
        if ( spinerType.getSelectedStrings().contains("Все") && spinerType.getSelectedStrings().size() > 1 ) {
            typeSelected = spinerType.getSelectedStrings();
            typeSelected.remove(0);
            spinerType.setSelection(typeSelected);
        }
        if ( spinerArea.getSelectedStrings().contains("Все") && spinerArea.getSelectedStrings().size() > 1 ) {
            areaSelected = spinerArea.getSelectedStrings();
            areaSelected.remove(0);
            spinerArea.setSelection(areaSelected);
        }
        if ( spinerPunrkt.getSelectedStrings().contains("Все") && spinerPunrkt.getSelectedStrings().size() > 1 ) {
            punktSelected = spinerPunrkt.getSelectedStrings();
            punktSelected.remove(0);
            spinerPunrkt.setSelection(punktSelected);
        }

        if ( spinerType.getSelectedStrings().size() == 0 )
            spinerType.setSelection(0);

        if ( spinerArea.getSelectedStrings().size() == 0 )
            spinerArea.setSelection(0);

        if ( spinerPunrkt.getSelectedStrings().size() == 0 )
            spinerPunrkt.setSelection(0);
    }

}
