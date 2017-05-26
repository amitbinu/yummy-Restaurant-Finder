package com.example.android.yummy.Backthread;

import android.os.AsyncTask;
import android.util.Log;
import java.util.HashMap;
import java.util.Map;
import com.example.android.yummy.DataManager.GeoCoder;
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

    class firstCall extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... params) {

            try {
                YelpFusionApiFactory apiFactory = new YelpFusionApiFactory();
                YelpFusionApi yelpFusioinApi = apiFactory.createAPI("");

                Map<String, String> params1 = new HashMap<>();

                params1.put("term", item);
                params1.put("location", address);
                params1.put("limit", "50");
                retrofit2.Call<SearchResponse> call = yelpFusioinApi.getBusinessSearch(params1);
                response  = call.execute();

            } catch (Exception e) {
                Log.e("ERROR", e.getMessage());
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            geoCoder.checker_for_yelp = true;
            if(geoCoder.checker_for_restaurantgetter == true){
                try{
                geoCoder.afterWAIT();}
                catch (Exception e){
                    Log.e("Error", e.getMessage());
                }
            }
            super.onPostExecute(s);
        }
    }
    }



