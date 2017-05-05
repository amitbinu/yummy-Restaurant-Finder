package com.example.android.yummy;

import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import java.util.Stack;


public class Result extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        Intent intent = getIntent();
        String user_input = intent.getStringExtra(MainActivity.Message);
        Stack<String> foods = new Stack<String>();
        foods.push(user_input);
        try{
            dataCollector(foods);
        }
        catch (Exception e){

        }
    }

    public void dataCollector(Stack<String> foods){

        try{
        //    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        //    StrictMode.setThreadPolicy(policy);
        yelp test = new yelp("Pizza Pizza", "2680 Erin Centre Blvd");

            TextView result = (TextView) findViewById(R.id.textview);
            result.setText("HEYYY");

        }



        catch (Exception e){
            TextView result = (TextView) findViewById(R.id.textview);
            result.setText(e.getMessage());
        }

    }
}
