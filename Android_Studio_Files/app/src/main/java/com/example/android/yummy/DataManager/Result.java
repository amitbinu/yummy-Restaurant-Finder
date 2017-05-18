package com.example.android.yummy.DataManager;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.android.yummy.DataManager.data;
import com.example.android.yummy.MainActivities.Main2Activity;
import com.example.android.yummy.MainActivities.MainActivity;
import com.example.android.yummy.R;

import java.util.ArrayList;
import java.util.Stack;

public class Result extends AppCompatActivity {
    public static Context contextforme;
    private static ProgressBar progressBar;
    public static LinearLayout realOne;
    public static data test;
    public static ArrayList<String> prices = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        realOne = (LinearLayout) findViewById(R.id.linear);

        contextforme = this;
        progressBar = (ProgressBar) findViewById(R.id.progressBar2);

        try{
            dataCollector();
        }
        catch (Exception e){}
    }
    public void dataCollector(){
        try{
            Stack<String> food = new Stack<>();
            food.push(Main2Activity.Message);
            Log.e("FOOD NAME:", Main2Activity.Message);
            test = new data(food, MainActivity.latitude + " " + MainActivity.longitude, MainActivity.CITY + MainActivity.STATE + MainActivity.COUNTRY);
        }

        catch (Exception e){
            Log.e("ERROR", e.getMessage(), e);
        }

    }


    public static void getter()
    {   progressBar.setVisibility(View.GONE);
        Log.e("IT WORKED", "LITT");
        for (int i =0; i < test.restaurants.length; i++){

            TextView text = new TextView(contextforme);
            text.setTextColor(Color.WHITE);
           // text.setLayoutParams(new ActionBar.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT));
            text.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20);
            text.setText(test.restaurants[i].restuarant_name + "                   "+ test.restaurants[i].distance + "km");
            LinearLayout.LayoutParams newLayout = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            newLayout.setMargins(10,10,0,0);
            text.setLayoutParams(newLayout);
            Log.e("NAMES:  ", test.restaurants[i].restuarant_name + "   ");
            realOne.addView(text);
        }
    }

    public static void setter(String value){
        prices.add(value);
    }


}
