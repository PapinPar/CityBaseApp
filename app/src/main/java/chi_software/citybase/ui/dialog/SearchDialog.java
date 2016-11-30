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
import android.widget.Toast;

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

public class SearchDialog extends DialogFragment implements MultiSelectionSpinner.OnMultipleItemsSelectedListener, View.OnClickListener {

    private View view;
    private MultiSelectionSpinner spinerType;
    private MultiSelectionSpinner spinerArea;
    private MultiSelectionSpinner spinerPunrkt;
    private EditText priceFrom;
    private EditText priceTo;
    private EditText text_phone;
    private EditText comment;
    private Button sell, sellComer, arenda, arendaComer;

    private String table;

    private List<String> typeSelected;
    private List<String> areaSelected;
    private List<String> punktSelected;

    private MenuSearch menuSearch;
    private GetSpinnerListner getSpinnerListner;


    public interface GetSpinnerListner {
        void getSpinner (String json, String table);
    }

    public void getListner (GetSpinnerListner getSpinnerListner, MenuSearch menuSearch) {
        this.getSpinnerListner = getSpinnerListner;
        this.menuSearch = menuSearch;
    }

    @Nullable
    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getDialog().setTitle("");
        table = "0";
        view = inflater.inflate(R.layout.search_dialog_layout, null);
        spinerType = (MultiSelectionSpinner) view.findViewById(R.id.spinnerTypeNew);
        spinerArea = (MultiSelectionSpinner) view.findViewById(R.id.spinnerAreaNew);
        spinerPunrkt = (MultiSelectionSpinner) view.findViewById(R.id.spinnerPunktNew);
        priceFrom = (EditText) view.findViewById(R.id.priceFromET);
        priceTo = (EditText) view.findViewById(R.id.priceToET);
        text_phone = (EditText) view.findViewById(R.id.phoneET);
        comment = (EditText) view.findViewById(R.id.comentET);
        Button button = (Button) view.findViewById(R.id.buttFind);

        arenda = (Button) view.findViewById(R.id.arendaBUT);
        arendaComer = (Button) view.findViewById(R.id.arendaComerBUT);
        sell = (Button) view.findViewById(R.id.sellBUTT);
        sellComer = (Button) view.findViewById(R.id.sellComerBUTT);

        sell.setOnClickListener(this);
        sellComer.setOnClickListener(this);
        arenda.setOnClickListener(this);
        arendaComer.setOnClickListener(this);

        ArrayList<String> listType = new ArrayList<>();
        ArrayList<String> listPunkt = new ArrayList<>();
        ArrayList<String> listArea = new ArrayList<>();
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
                if ( table.equals("0") ) {
                    Toast.makeText(view.getContext(), "Не выбран тип съема", Toast.LENGTH_SHORT).show();
                } else {
                    String dateFrom = new SimpleDateFormat("yyyy-MM").format(new Date());
                    int day = Integer.parseInt(new SimpleDateFormat("dd").format(new Date()));
                    day = day - 7;
                    dateFrom = dateFrom + "-" + day;
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
                    Log.d("SearchDialog", table);
                    getSpinnerListner.getSpinner(json, table);
                    dismiss();
                }
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

    @Override
    public void onClick (View v) {
        switch ( v.getId() ) {
            case R.id.sellBUTT:
                sell.setBackgroundResource(R.color.greenButton);
                sellComer.setBackgroundResource(R.color.backGray);
                arenda.setBackgroundResource(R.color.backGray);
                arendaComer.setBackgroundResource(R.color.backGray);
                table = "sale_living";
                break;
            case R.id.sellComerBUTT:
                sell.setBackgroundResource(R.color.backGray);
                sellComer.setBackgroundResource(R.color.greenButton);
                arenda.setBackgroundResource(R.color.backGray);
                arendaComer.setBackgroundResource(R.color.backGray);
                table = "sale_not_living";
                break;
            case R.id.arendaBUT:
                sell.setBackgroundResource(R.color.backGray);
                sellComer.setBackgroundResource(R.color.backGray);
                arenda.setBackgroundResource(R.color.greenButton);
                arendaComer.setBackgroundResource(R.color.backGray);
                table = "rent_living";
                break;
            case R.id.arendaComerBUT:
                sell.setBackgroundResource(R.color.backGray);
                sellComer.setBackgroundResource(R.color.backGray);
                arenda.setBackgroundResource(R.color.backGray);
                arendaComer.setBackgroundResource(R.color.greenButton);
                table = "rent_not_living";
                break;
        }
    }

}
