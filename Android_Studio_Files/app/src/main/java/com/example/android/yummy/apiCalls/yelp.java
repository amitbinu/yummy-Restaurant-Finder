package com.example.android.yummy.Backthread;

import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


import com.example.android.yummy.MainActivities.MainActivity;
import com.google.maps.model.PlacesSearchResponse;
import com.yelp.fusion.client.connection.YelpFusionApi;
import com.yelp.fusion.client.connection.YelpFusionApiFactory;
import com.yelp.fusion.client.models.Business;
import com.yelp.fusion.client.models.SearchResponse;


public class yelp {
    private static String item, address;
    public static retrofit2.Response<SearchResponse> response;
    public yelp(String item, String address){
        this.item = item;
        this.address = address;
        this.response = null;
        new firstCall().execute();
    }

    class firstCall extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... params) {

            try {
                YelpFusionApiFactory apiFactory = new YelpFusionApiFactory();
                YelpFusionApi yelpFusioinApi = apiFactory.createAPI("EKQF7reSwACI45yGJiBhlg", "FqRhQWJCvmzontKsjalqqd7eb9EnnNv95TEO3hVGdRfkH3CoMSBlUR0NWHbnAEyi");

                Map<String, String> params1 = new HashMap<>();

                params1.put("term", item);
                params1.put("location", address);
                //params.put("longitude", "-79.716575");
                params1.put("limit", "50");
                retrofit2.Call<SearchResponse> call = yelpFusioinApi.getBusinessSearch(params1);
                response  = call.execute();

            } catch (Exception e) {
                Log.e("ERROR", e.getMessage());
            }
            return null;
        }
    }

    }



