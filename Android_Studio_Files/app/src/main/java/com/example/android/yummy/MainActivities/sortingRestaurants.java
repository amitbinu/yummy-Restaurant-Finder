package com.restaurant.android.yummy.MainActivities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.restaurant.android.yummy.DataManager.data;
import com.restaurant.android.yummy.DataManager.sort;
import com.restaurant.android.yummy.R;
public class sortingRestaurants extends AppCompatActivity {
    public static String sortValue;
    public static data info;
    public static sort restaurants;
    public static int position;
    private static sortingRestaurants object;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sorting_restaurants);
      //  info = Result.test;
        if(sortValue != null){
            restaurants = new sort(info,sortValue);
            Result.defaultItem = false;
        }
        else{
            position = 0;
            restaurants = new sort(info,"Distance(low-high)");
        }
        object = this;
        Intent intent = new Intent(this,Result.class);
        startActivity(intent);

    }
    @Override
    public void onBackPressed() {
        this.finish();
        Intent intent = new Intent(this, Result.class);
        startActivity(intent);
    }


}
