package com.example.android.yummy.apiCalls;

import android.os.AsyncTask;
import java.util.HashMap;
import java.util.Map;

import com.example.android.yummy.DataManager.Constants;
import com.example.android.yummy.DataManager.GeoCoder;
import com.example.android.yummy.MainActivities.RestaurantActivity;
import com.yelp.fusion.client.connection.YelpFusionApi;
import com.yelp.fusion.client.connection.YelpFusionApiFactory;
import com.yelp.fusion.client.models.SearchResponse;

/**
 * This class calls the yelp API with appropriate parameters to get the response.
 */
public class yelp {
    private static String item, address;
    private static GeoCoder geoCoder;
    public static retrofit2.Response<SearchResponse> response;

    /**
     * The constructor assigns the values in its parameters to appropriate global variables.
     * @param item The food user entered.
     * @param address The county/city where the user is currently in.
     * @param geoCoder A GeoCoder object that is used to call the method <i>afterWait()</i> after the background thread is executed.
     */
    public yelp(String item, String address, GeoCoder geoCoder){
        this.item = item;
        this.address = address;
        this.geoCoder = geoCoder;
        this.response = null;
        new firstCall().execute();
    }

    public yelp(String item, String address){
        this.item = item;
        this.address = address;
        this.response = null;
        new detailedCall().execute();
    }

    class firstCall extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... params) {
            try {
                YelpFusionApiFactory apiFactory = new YelpFusionApiFactory();
                YelpFusionApi yelpFusioinApi = apiFactory.createAPI(Constants.yelpAPi_AcessToken, Constants.yelpAApi_AccessSecret);
                Map<String, String> params1 = new HashMap<>();

                params1.put("term", item);
                params1.put("location", address);
                params1.put("limit", "50");
                retrofit2.Call<SearchResponse> call = yelpFusioinApi.getBusinessSearch(params1);
                response  = call.execute();

            } catch (Exception e) {
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            if(geoCoder.checker_for_restaurantgetter == true){
                try{
                geoCoder.afterWAIT();}
                catch (Exception e){
                }
            }
            super.onPostExecute(s);
        }
    }

    class detailedCall extends AsyncTask<Void,Void,Void>{

        @Override
        protected Void doInBackground(Void... params) {
            try {
                YelpFusionApiFactory apiFactory = new YelpFusionApiFactory();
                YelpFusionApi yelpFusioinApi = apiFactory.createAPI(Constants.yelpAPi_AcessToken, Constants.yelpAApi_AccessSecret);
                Map<String, String> params1 = new HashMap<>();

                params1.put("term", item);
                params1.put("location", address);
                params1.put("limit", "50");
                retrofit2.Call<SearchResponse> call = yelpFusioinApi.getBusinessSearch(params1);
                response  = call.execute();

            } catch (Exception e) {
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            RestaurantActivity.checker_for_yelp = true;
            if(RestaurantActivity.checker_for_placeDetails == true){
                RestaurantActivity.afterApiCall();
            }
            super.onPostExecute(aVoid);
        }
    }
    }



