package com.example.android.yummy.apiCalls;

import
        android.os.AsyncTask;

import com.example.android.yummy.DataManager.GeoCoder;
import com.example.android.yummy.MainActivities.Main2Activity;
import com.google.maps.DistanceMatrixApi;
import com.google.maps.GeoApiContext;
import com.google.maps.model.DistanceMatrix;
import static com.example.android.yummy.MainActivities.Main2Activity.distances;

/**
 * Created by amitb on 2017-05-07.
 */

public class distanceTimeBackThread {
    private static GeoCoder object;
    public static String[] origin, destinations;
    private static GeoApiContext context;
    public static DistanceMatrix result;
    private Main2Activity object1;
    public distanceTimeBackThread(String[] origin, String[] destinations, GeoApiContext context, GeoCoder object){
        this.origin = origin;
        this.destinations = destinations;
        this.context = context;
        this.result = null;
        this.object = object;
        new secondCall().execute();

    }
    public distanceTimeBackThread(String[] origin, String[] destinations, GeoApiContext context, Main2Activity object1){
        this.object1 = object1;
        this.origin = origin;
        this.destinations = destinations;
        this.context = context;
        result =null;
        new call().execute();
    }

    class call extends AsyncTask<Void, Void,DistanceMatrix>{

        @Override
        protected DistanceMatrix doInBackground(Void... params) {
            try{
            result = DistanceMatrixApi.getDistanceMatrix(context, origin, destinations).await();

                object1.distances = new double[result.rows[0].elements.length];
                object1.times = new double[result.rows[0].elements.length];

                for (int i =0; i < distances.length; i++){
                    String distance = result.rows[0].elements[i].distance.humanReadable;
                    if(distance.contains("km")){
                        object1.distanceUnit = "km";
                        String[] dist = distance.split("km");
                        object1.distances[i] = Double.parseDouble(dist[0]);
                    }
                    if(distance.contains("miles")){
                        object1.distanceUnit = "miles";
                        String[] dist = distance.split("miles");
                        object1.distances[i] = Double.parseDouble(dist[0]);
                    }
                    String time;
                    try{
                        time = result.rows[0].elements[i].durationInTraffic.humanReadable;}
                    catch (Exception e){
                        time = result.rows[0].elements[i].duration.humanReadable;
                    }
                    String[] tim = time.split("min");
                    object1.times[i] = Double.parseDouble(tim[0]);
                }
                }
            catch (Exception e){
            }
            return null;
        }

        @Override
        protected void onPostExecute(DistanceMatrix distanceMatrix) {
            object1.popularRestaurants();
            super.onPostExecute(distanceMatrix);
        }
    }

    class secondCall extends AsyncTask<Void, Void, Void>{

        @Override
        protected Void doInBackground(Void... params) {
            try{
            result = DistanceMatrixApi.getDistanceMatrix(context, origin, destinations).await();
            }
            catch (Exception e){}
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            object.afterDist();
            super.onPostExecute(aVoid);
        }
    }
}
