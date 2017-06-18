package com.example.android.yummy.apiCalls;

import android.os.AsyncTask;
import com.example.android.yummy.DataManager.GeoCoder;
import com.google.maps.GeoApiContext;
import com.google.maps.PlacesApi;
import com.google.maps.model.PlacesSearchResponse;

/**
 * Created by amitb on 2017-05-05.
 */

public class restaurant_getter {
    public static String origin, city;
    public static GeoApiContext context;
    public static String food;
    public PlacesSearchResponse address;
    public static geoCoding running;
    private static GeoCoder object;

    public restaurant_getter(String origin, String city, GeoApiContext context, String food, GeoCoder test){
        this.origin = origin;
        this.city = city;
        this.context = context;
        this.food = food;
        this.object = test;
        running = new geoCoding();
        running.execute();
    }

    class geoCoding extends AsyncTask<String, Void, PlacesSearchResponse> {

        @Override
        protected PlacesSearchResponse doInBackground(String... params) {
                try{
                    address = PlacesApi.textSearchQuery(context, food + " restaurants in " + city).await();
                }
                catch (Exception e){
                }
                return address;

        }

        @Override
        protected void onPostExecute(PlacesSearchResponse strings) {
                try{
                    object.afterWAIT();}
                catch (Exception e){
                }

            super.onPostExecute(strings);
        }
    }
}
