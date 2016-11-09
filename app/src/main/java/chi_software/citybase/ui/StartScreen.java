package chi_software.citybase.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import chi_software.citybase.R;
import chi_software.citybase.core.BaseActivity;
import chi_software.citybase.data.Search;

/**
 * Created by Papin on 08.11.2016.
 */

public class StartScreen extends BaseActivity implements View.OnClickListener {

    private Button signInBut;

    @Override
    protected void onCreate (@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_screen_layout);
        signInBut = (Button) findViewById(R.id.SignInBut);
        signInBut.setOnClickListener(this);
        Search search = new Search();
        List<String> city = new ArrayList<>();
        city.add("Харьков");
        city.add("Аксютовка");
        List<String> term = new ArrayList<>();
        term.add("Д");
        List<String> fill = new ArrayList<>();
        fill.add("1");
        fill.add("2");
        fill.add("3");
        fill.add("0");

        search.setCity(city);
        search.setTerm(term);
        search.setFill(fill);

        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        Log.i("GSON", gson.toJson(search));

    }

    @Override
    public void onClick (View v) {
        switch ( v.getId() ) {
            case R.id.SignInBut:
                Intent startLogin = new Intent(StartScreen.this, LoginSrcreen.class);
                startActivity(startLogin);
                break;
        }
    }
}
