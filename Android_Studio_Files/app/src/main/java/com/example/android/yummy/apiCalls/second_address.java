package com.example.android.yummy.apiCalls;

import android.os.AsyncTask;
import android.util.Log;

import com.example.android.yummy.DataManager.GeoCoder;
import com.google.maps.GeoApiContext;
import com.google.maps.PlacesApi;
import com.google.maps.errors.InvalidRequestException;
import com.google.maps.model.PlacesSearchResponse;

/**
 * Created by amitb on 2017-05-05.
 */

public class second_address {
    public static PlacesSearchResponse address;
    public static Exception exception;
    private static String nextPage;
    private static GeoApiContext context;
    private static GeoCoder object;
    public second_address(String nextPage, GeoApiContext context, GeoCoder object){
        this.address = null;
        this.nextPage = nextPage;
        this.context = context;
        this.object = object;
        new call().execute();
    }

    public second_address(String nextPage, GeoApiContext context){
        this.address = null;
        this.nextPage = nextPage;
        this.context = context;
        new firstCall().execute();
    }

    class firstCall extends AsyncTask<Void, Void, Void>{

        @Override
        protected Void doInBackground(Void... params) {
            try {
                address = PlacesApi.textSearchNextPage(context, nextPage).await();

            }
            catch (InvalidRequestException f){
             //   Log.d("error", f.getMessage(), f);
                while (true){
                    try{
                        address = PlacesApi.textSearchNextPage(context, nextPage).await();
                        break;
                    }
                    catch (InvalidRequestException g){
                  //      Log.d("error", f.getMessage(), f);
                        continue;
                    }
                    catch (Exception all){
                        Log.e("ERROR", all.getMessage(), all);
                        break;
                    }
                }
            }
            catch (Exception e) {
                address = null;
                Log.e("ERROR SECOND GEO", e.getMessage(), e);
            }
            return null;
        }
    }
    class call extends AsyncTask<Void, Void, PlacesSearchResponse>{

        @Override
        protected PlacesSearchResponse doInBackground(Void... params) {

                try {
                    address = PlacesApi.textSearchNextPage(context, nextPage).await();

                }
                catch (InvalidRequestException f){
                    Log.e("error", f.getMessage(), f);
                    while (true){
                        try{
                            address = PlacesApi.textSearchNextPage(context, nextPage).await();
                            break;
                        }
                        catch (InvalidRequestException g){
                            Log.e("error", f.getMessage(), f);
                            continue;
                        }
                        catch (Exception all){
                            Log.e("ERROR", all.getMessage(), all);
                            break;
                        }
                    }
                }
                catch (Exception e) {
                    address = null;
                    Log.e("ERROR SECOND GEO", e.getMessage(), e);
                }

            return null;
        }

        protected void onPostExecute(PlacesSearchResponse strings) {
            //Result.result.setText(strings[0] + " " + strings[1]);
            try{
     //          object.get_address()
               ;}
            catch (Exception e){
                Log.e("ERROR IN doInBACKGROUND", e.getMessage(), e);
            }
            super.onPostExecute(strings);
        }
    }
}
