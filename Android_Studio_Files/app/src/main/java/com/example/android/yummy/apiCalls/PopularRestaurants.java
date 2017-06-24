package com.restaurant.android.yummy.apiCalls;

import android.os.AsyncTask;
import com.restaurant.android.yummy.MainActivities.MainActivity;
import com.google.maps.GeoApiContext;
import com.google.maps.PlacesApi;
import com.google.maps.model.PlaceDetails;
import com.google.maps.model.PlacesSearchResponse;

import java.util.ArrayList;

/**
 * Created by amitb on 2017-05-11.
 */

public class PopularRestaurants {
    private static String communityName;
    private static GeoApiContext context;
    public PlacesSearchResponse address;
    private static MainActivity object;
    private static int size =0;
    public static ArrayList<PlaceDetails> placeDetailses;
    public PopularRestaurants(String communityName,GeoApiContext context, MainActivity object){
        this.address = null;
        this.communityName = communityName;
        this.context = context;
        this.object = object;
        this.placeDetailses = new ArrayList<>();

        new geoCoding().execute();
    }
    class geoCoding extends AsyncTask<String, Void, PlacesSearchResponse> {


        @Override
        protected PlacesSearchResponse doInBackground(String... params) {

            try{
                address = PlacesApi.textSearchQuery(context, "Popular Restaurants in " + communityName).await();
                if(address.results.length >= 10){
                    size = 10;
                }
                else{
                    size = address.results.length;
                }
                for (int i =0; i < size ; i++){
                    PlaceDetails placeDetails = PlacesApi.placeDetails(context,address.results[i].placeId).await();
                    placeDetailses.add(placeDetails);
                    address.results[i].rating = placeDetails.rating;
                    try{
                        address.results[i].photos[0] = placeDetails.photos[0];
                    }
                    catch (Exception e) {}
                }
            }
            catch (Exception e){
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
