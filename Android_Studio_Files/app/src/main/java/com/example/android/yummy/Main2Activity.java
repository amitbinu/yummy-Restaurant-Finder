package com.example.android.yummy;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.View;
import android.widget.SearchView;

public class Main2Activity extends AppCompatActivity {
    public static String Message;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        modifySearchBar();
    }
    public void modifySearchBar(){
        final SearchView searchbar = (SearchView) findViewById(R.id.SearchBar);
        searchbar.setQueryHint(Html.fromHtml("<font color = #ffffff>" + " Type the food here " + "</font>"));
        searchbar.onActionViewExpanded();
        searchbar.setIconified(false);
        searchbar.setOnQueryTextListener(new SearchView.OnQueryTextListener(){
            @Override
            public boolean onQueryTextSubmit(String query){
                nextActivity(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String change){
                return false;
            }
        });
        searchbar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                searchbar.setIconified(false);

            }
        });
    }
    public void nextActivity(String view){
        // food = view;
        Message = view;
        Intent intent = new Intent(this, Result.class);
        intent.putExtra(Message, view);
        startActivity(intent);
    }

}
