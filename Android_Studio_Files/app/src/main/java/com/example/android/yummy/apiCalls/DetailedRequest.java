package com.restaurant.android.yummy.apiCalls;

import android.os.AsyncTask;
import com.restaurant.android.yummy.DataManager.Constants;
import com.restaurant.android.yummy.MainActivities.RestaurantActivity;
import com.google.maps.GeoApiContext;
import com.google.maps.PlacesApi;
import com.google.maps.model.PlaceDetails;

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
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            RestaurantActivity.checker_for_placeDetails = true;
            if(RestaurantActivity.checker_for_yelp == true){
               // RestaurantActivity.afterApiCall();
            }
            super.onPostExecute(aVoid);
        }
    }
}
