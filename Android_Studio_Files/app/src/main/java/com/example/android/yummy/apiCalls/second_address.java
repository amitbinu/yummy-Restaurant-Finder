package com.restaurant.android.yummy.apiCalls;

import android.os.AsyncTask;

import com.restaurant.android.yummy.DataManager.GeoCoder;
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
    private static int size;
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
                        catch (Exception e){ }
                    }
            }
            catch (InvalidRequestException f){
                while (true){
                    try{
                        address = PlacesApi.textSearchNextPage(context, nextPage).await();
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
                                catch (Exception e){ }
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
                        catch (Exception e){
                            address.results[i].photos[0] = placeDetails.photos[0];
                        }
                    }

            }
            catch (InvalidRequestException f){
                while (true){
                    try{
                        address = PlacesApi.textSearchQuery(context, query).await();
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
            try{
                geoCoder.afterAddress(address,placeDetailses);
                ;}
            catch (Exception e){
            }
            super.onPostExecute(strings);
        }
    }
}
