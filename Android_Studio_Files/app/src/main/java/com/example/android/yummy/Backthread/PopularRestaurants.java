package com.example.android.yummy.Backthread;

import android.os.AsyncTask;
import android.util.Log;

import com.example.android.yummy.DataManager.GeoCoder;
import com.example.android.yummy.MainActivities.MainActivity;
import com.google.maps.GeoApiContext;
import com.google.maps.PlacesApi;
import com.google.maps.model.PlacesSearchResponse;

import java.util.Stack;

import static com.example.android.yummy.Backthread.restaurant_getter.city;

/**
 * Created by amitb on 2017-05-11.
 */

public class PopularRestaurants {
    private static String communityName;
    private static GeoApiContext context;
    public PlacesSearchResponse address;

    public PopularRestaurants(String communityName,GeoApiContext context){
        this.address = null;
        this.communityName = communityName;
        this.context = context;
        new geoCoding().execute();
    }
    class geoCoding extends AsyncTask<String, Void, PlacesSearchResponse> {


        @Override
        protected PlacesSearchResponse doInBackground(String... params) {

            try{
                address = PlacesApi.textSearchQuery(context, "Popular " + " restaurants in " + communityName).await();

            }
            catch (Exception e){
                Log.e("ERROR", e.getMessage(), e);
            }
            return address;

        }

    }
}
