package com.example.android.yummy.apiCalls;

import android.os.AsyncTask;
import android.util.Log;

import com.example.android.yummy.DataManager.GeoCoder;
import com.google.maps.GeoApiContext;
import com.google.maps.PlacesApi;
import com.google.maps.errors.InvalidRequestException;
import com.google.maps.model.PlaceDetails;
import com.google.maps.model.PlacesSearchResponse;

import java.util.ArrayList;

/**
 * Created by amitb on 2017-05-05.
 */

public class second_address {
    public static PlacesSearchResponse address;
    public static Exception exception;
    private static String nextPage;
    private static GeoApiContext context;
    private static String query;
    private static GeoCoder geoCoder;
    private static ArrayList<PlaceDetails> placeDetailses;
    public second_address(GeoApiContext context, String query, GeoCoder geoCoder){
        this.address = null;
        this.context = context;
        this.query = query;
        this.geoCoder = geoCoder;
        this.placeDetailses = new ArrayList<>();
        new call().execute();
    }

    public second_address(String nextPage, GeoApiContext context){
        this.address = null;
        this.nextPage = nextPage;
        this.context = context;
        this.placeDetailses = new ArrayList<>();
        new firstCall().execute();
    }

    class firstCall extends AsyncTask<Void, Void, Void>{

        @Override
        protected Void doInBackground(Void... params) {
            try {
                address = PlacesApi.textSearchNextPage(context, nextPage).await();
                for (int i =0; i < address.results.length ; i++){

                        PlaceDetails placeDetails = PlacesApi.placeDetails(context,address.results[i].placeId).await();
                        placeDetailses.add(placeDetails);
                        address.results[i].rating = placeDetails.rating;
                        try{
                            address.results[i].photos[0] = placeDetails.photos[0];
                        }
                        catch (Exception e){
                            address.results[i].photos[0] = placeDetails.photos[0];
                        }
                    }
            }
            catch (InvalidRequestException f){
                while (true){
                    try{
                        address = PlacesApi.textSearchNextPage(context, nextPage).await();
                        for (int i =0; i < address.results.length ; i++){

                                PlaceDetails placeDetails = PlacesApi.placeDetails(context,address.results[i].placeId).await();
                                placeDetailses.add(placeDetails);
                                address.results[i].rating = placeDetails.rating;
                                try{
                                    address.results[i].photos[0] = placeDetails.photos[0];
                                }
                                catch (Exception e){
                                    address.results[i].photos[0] = placeDetails.photos[0];
                                }
                                //  address.results[i].photos[0] = placeDetails.photos[0];
                            }
                        break;
                    }
                    catch (InvalidRequestException g){
                        continue;
                    }
                    catch (Exception all){
                        break;
                    }
                }
            }
            catch (Exception e) {
                Log.e("secindAddress", e.getMessage());
                address = null;
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            if(address.nextPageToken != null){
                geoCoder.afterAddress2(address,placeDetailses,false);}
            else{
                geoCoder.afterAddress2(address,placeDetailses,true);
            }
            super.onPostExecute(aVoid);
        }
    }
    class call extends AsyncTask<Void, Void, PlacesSearchResponse>{

        @Override
        protected PlacesSearchResponse doInBackground(Void... params) {

            try {
                address = PlacesApi.textSearchQuery(context, query).await();
                for (int i =0; i < address.results.length ; i++){
                        PlaceDetails placeDetails = PlacesApi.placeDetails(context,address.results[i].placeId).await();
                        placeDetailses.add(placeDetails);
                        address.results[i].rating = placeDetails.rating;
                        try{
                            address.results[i].photos[0] = placeDetails.photos[0];
                        }
                        catch (Exception e){
                            address.results[i].photos[0] = placeDetails.photos[0];
                        }
                    }

            }
            catch (InvalidRequestException f){
                while (true){
                    try{
                        address = PlacesApi.textSearchQuery(context, query).await();
                        for (int i =0; i < address.results.length ; i++){

                                PlaceDetails placeDetails = PlacesApi.placeDetails(context,address.results[i].placeId).await();
                                placeDetailses.add(placeDetails);
                                address.results[i].rating = placeDetails.rating;
                                try{
                                    address.results[i].photos[0] = placeDetails.photos[0];
                                }
                                catch (Exception e){
                                    address.results[i].photos[0] = placeDetails.photos[0];
                                }
                            }
                        break;
                    }
                    catch (InvalidRequestException g){
                        continue;
                    }
                    catch (Exception all){
                        break;
                    }
                }
            }
            catch (Exception e) {
                address = null;
            }

            return null;
        }

        @Override
        protected void onPostExecute(PlacesSearchResponse strings) {
            //Result.result.setText(strings[0] + " " + strings[1]);
            try{
                geoCoder.afterAddress(address,placeDetailses);
                ;}
            catch (Exception e){
            }
            super.onPostExecute(strings);
        }
    }
}
