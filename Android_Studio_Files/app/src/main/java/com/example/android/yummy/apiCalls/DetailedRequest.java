package com.example.android.yummy.apiCalls;

import android.os.AsyncTask;
import android.util.Log;

import com.example.android.yummy.DataManager.Constants;
import com.example.android.yummy.MainActivities.RestaurantActivity;
import com.example.android.yummy.R;
import com.google.maps.GeoApiContext;
import com.google.maps.PlacesApi;
import com.google.maps.model.PlaceDetails;
import com.google.maps.model.PlacesSearchResponse;

/**
 * Created by amitb on 2017-05-29.
 */

public class DetailedRequest {

    private static String placeId;
    public static PlaceDetails placeDetails;
    public DetailedRequest(String placeId){
        this.placeId = placeId;
        new detailedRequestCall().execute();
    }

    class detailedRequestCall extends AsyncTask<Void, Void, Void>{

        @Override
        protected Void doInBackground(Void... params) {
            GeoApiContext context = new GeoApiContext().setApiKey(Constants.ApiKey);
            try{
            placeDetails = PlacesApi.placeDetails(context,placeId).await();}

            catch (Exception e){
                Log.e("error-detailedRequest", e.getMessage());
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            RestaurantActivity.checker_for_placeDetails = true;
            if(RestaurantActivity.checker_for_yelp == true){
                RestaurantActivity.afterApiCall();
            }
            super.onPostExecute(aVoid);
        }
    }
}
