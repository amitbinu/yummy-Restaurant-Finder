package com.example.android.yummy.MainActivities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.android.yummy.Backthread.DetailedRequest;
import com.example.android.yummy.R;
import com.google.maps.model.PlaceDetails;

public class RestaurantActivity extends AppCompatActivity {

    public static String placeId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant);
        DetailedRequest detailedRequest = new DetailedRequest(placeId);
    }

    public static void afterApiCall(PlaceDetails placeDetails){

    }
}
