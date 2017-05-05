package com.example.android.yummy;

import android.os.AsyncTask;
import android.util.Log;

import com.google.maps.GeoApiContext;
import com.google.maps.PlacesApi;
import com.google.maps.model.PlacesSearchResponse;

/**
 * Created by amitb on 2017-05-04.
 */

public class URL extends AsyncTask<Void, Void, PlacesSearchResponse> {
    public URL(String food, String city){
        this.f = food;
        this.city = city;
    }
    private String f = "";
    private String city = "";
    private Exception exception;
    protected PlacesSearchResponse doInBackground(Void... urls){

        try{
             GeoApiContext context = new GeoApiContext().setApiKey("AIzaSyB9RIqgSUPHG1eg182FbxOamicGXFgjBDA");
            PlacesSearchResponse address = PlacesApi.textSearchQuery(context, f + " restaurants in " + city).await();
            return address;
        }
        catch (Exception e){
            Log.e("ERROR", e.getMessage(), e);
            return null;
        }
    }
}

