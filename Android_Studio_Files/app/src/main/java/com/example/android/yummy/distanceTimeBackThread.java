package com.example.android.yummy;

import android.os.AsyncTask;
import android.util.Log;

import com.google.maps.DistanceMatrixApi;
import com.google.maps.GeoApiContext;
import com.google.maps.model.DistanceMatrix;

/**
 * Created by amitb on 2017-05-07.
 */

public class distanceTimeBackThread {
    public static String[] origin, destinations;
    private static GeoApiContext context;
    private static GeoCoder object;
    public static DistanceMatrix result;
    public static call caller;
    public distanceTimeBackThread(String[] origin, String[] destinations, GeoApiContext context, GeoCoder object){
        this.origin = origin;
        this.destinations = destinations;
        this.context = context;
        this.object =object;
        result = null;
        caller = new call();
        caller.execute();

    }

    class call extends AsyncTask<Void, Void,DistanceMatrix>{

        @Override
        protected DistanceMatrix doInBackground(Void... params) {
            try{
            result = DistanceMatrixApi.getDistanceMatrix(context, origin, destinations).await();
                }
            catch (Exception e){
                Log.e("ERROR", e.getMessage(), e);
            }
            return null;
        }
    }
}
