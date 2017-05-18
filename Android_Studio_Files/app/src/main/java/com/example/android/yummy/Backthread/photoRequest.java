package com.example.android.yummy.Backthread;

import android.os.AsyncTask;
import android.util.Log;

import com.example.android.yummy.DataManager.Restaurant;
import com.example.android.yummy.MainActivities.Main2Activity;
import com.google.maps.DistanceMatrixApi;
import com.google.maps.GeoApiContext;
import com.google.maps.PhotoRequest;
import com.google.maps.model.DistanceMatrix;
import com.google.maps.model.PhotoResult;

import static com.example.android.yummy.Backthread.distanceTimeBackThread.destinations;
import static com.example.android.yummy.Backthread.distanceTimeBackThread.origin;
import static com.example.android.yummy.Backthread.restaurant_getter.context;

/**
 * Created by amitb on 2017-05-11.
 */

public class photoRequest {
    public static PhotoResult photoRESULT;
    private static Main2Activity object;
    private static Restaurant[] popular;
    public photoRequest( Main2Activity object, Restaurant[] popular){

        this.photoRESULT = null;
        this.object = object;
        this.popular =popular;

        new call().execute();
    }

    class call extends AsyncTask<Void, Void,PhotoResult> {

        @Override
        protected PhotoResult doInBackground(Void... params) {

                try {
                    for (int i = 0; i < popular.length; i++){
                        PhotoRequest photoRequests = new PhotoRequest(new GeoApiContext().setApiKey("AIzaSyB9RIqgSUPHG1eg182FbxOamicGXFgjBDA"));
                        photoRequests.maxHeight(100);
                        photoRequests.maxWidth(100);
                        photoRequests.photoReference(popular[i].photos[0].photoReference);
                        Log.e("photoReference", popular[i].photos.length+"");
                        photoRESULT = photoRequests.await();
                        Log.e("photoResult", photoRESULT.toString());
                        object.photo(photoRESULT,i);
                    }
                } catch (Exception e) {
                    Log.e("photoRequest", "ERROR ERROR");
                    Log.e("ERROR", e.getMessage(), e);
                }
            return null;
        }
    }
}
