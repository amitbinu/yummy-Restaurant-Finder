package com.example.android.yummy.apiCalls;

import android.os.AsyncTask;
import android.util.Log;

import com.example.android.yummy.DataManager.GeoCoder;
import com.example.android.yummy.MainActivities.MainActivity;
import com.google.maps.GeoApiContext;
import com.google.maps.PlacesApi;
import com.google.maps.model.PlacesSearchResponse;

import java.util.Stack;

import static com.example.android.yummy.apiCalls.restaurant_getter.city;

/**
 * Created by amitb on 2017-05-11.
 */

public class PopularRestaurants {
    private static String communityName;
    private static GeoApiContext context;
    public PlacesSearchResponse address;
    private static MainActivity object;
    public PopularRestaurants(String communityName,GeoApiContext context, MainActivity object){
        this.address = null;
        this.communityName = communityName;
        this.context = context;
        this.object = object;
        new geoCoding().execute();
    }
    class geoCoding extends AsyncTask<String, Void, PlacesSearchResponse> {


        @Override
        protected PlacesSearchResponse doInBackground(String... params) {

            try{
                address = PlacesApi.textSearchQuery(context, "Best " + " restaurants in " + communityName).await();
            }
            catch (Exception e){
                Log.e("ERROR IN POPULAR REST", e.getMessage(), e);
            }
            return address;

        }

        @Override
        protected void onPostExecute(PlacesSearchResponse placesSearchResponse) {
            object.startNextActivity();
            super.onPostExecute(placesSearchResponse);
        }
    }
}
