package com.example.android.yummy;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.TextView;

import org.w3c.dom.Text;

import static android.R.attr.id;
import static android.R.id.message;
import static android.os.Build.VERSION_CODES.N;

public class MainActivity extends AppCompatActivity {
    public static final String Message = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        modifySearchBar();
    }

    public void modifySearchBar(){
        final SearchView searchbar = (SearchView) findViewById(R.id.SearchBar);
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
        Intent intent = new Intent(this, Result.class);
        intent.putExtra(Message, view);
        startActivity(intent);
        SearchView searchbar = (SearchView) findViewById(R.id.SearchBar);
      //  int text22 = searchbar.g
        TextView test = (TextView) findViewById(R.id.textview);
        test.setText(view);
    }

}
