package com.example.android.yummy.Backthread;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import com.example.android.yummy.DataManager.Restaurant;
import com.example.android.yummy.DataManager.Result;
import com.example.android.yummy.MainActivities.Main2Activity;
import com.example.android.yummy.MainActivities.MainActivity;
import com.google.maps.GeoApiContext;
import com.google.maps.PhotoRequest;
import com.google.maps.PlacesApi;
import com.google.maps.model.PhotoResult;
import com.google.maps.model.PlacesSearchResponse;
import com.google.maps.model.PlacesSearchResult;

import java.util.ArrayList;

import static com.example.android.yummy.Backthread.restaurant_getter.city;

/**
 * Created by amitb on 2017-05-11.
 */

public class photoRequest {
    public static PhotoResult photoRESULT;
    private static Result object1;
    public static ArrayList<Bitmap> pictures;
    public static ArrayList<Bitmap> pictures1;
    private static GeoApiContext context;
    private static ArrayList<PlacesSearchResult> placesSearchResult;
    private static int size;
    private static PlacesSearchResponse placesSearchResponse;

    public photoRequest(Result object){
        this.object1 = object;
        new firtcall().execute();
    }

    public photoRequest(PlacesSearchResponse placesSearchResponse){
        this.context = new GeoApiContext().setApiKey("AIzaSyB9RIqgSUPHG1eg182FbxOamicGXFgjBDA");
        this.placesSearchResponse= placesSearchResponse;
        this.pictures = new ArrayList<>();
        if(placesSearchResponse.results.length >= 10){
            this.size = 10;
        }
        else{
            this.size = placesSearchResponse.results.length;
        }
        new onecall().execute();

    }

    public photoRequest(ArrayList<PlacesSearchResult> placesSearchResults){
        this.context = new GeoApiContext().setApiKey("AIzaSyB9RIqgSUPHG1eg182FbxOamicGXFgjBDA");
        this.placesSearchResult = placesSearchResults;
        this.pictures1 = new ArrayList<>();
       // Log.e("placeSearchResponse", placesSearchResponse.results[0].photos[0].toString());

            this.size = placesSearchResults.size();

        new lastCall().execute();
    }

    class onecall extends AsyncTask<Void, Void, Void>{

        @Override
        protected Void doInBackground(Void... params) {
            PlacesSearchResult[] result = placesSearchResponse.results;
            for (int i = 0; i < size; i++){
                try {
                    PhotoRequest photoRequests = new PhotoRequest(context);
                    photoRequests.maxHeight(100);
                    photoRequests.maxWidth(100);
                    photoRequests.photoReference(result[i].photos[0].photoReference);
                    photoRESULT = photoRequests.await();
                 //   Log.d("i value", i+"");
                    byte[] bytes = photoRESULT.imageData;
                    BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inScaled = false;
                    pictures.add(BitmapFactory.decodeByteArray(bytes,0,bytes.length,options));
                }
                catch (Exception e) {
                 //   Log.d("photoRequest", "ERROR ERROR");
                 //   Log.e("ERROR", e.getMessage(), e);
                    PlacesSearchResponse address = null;
                    try{
                  //      Log.d("Number", i +"");
                        address = PlacesApi.textSearchQuery(context, placesSearchResponse.results[i].name+ " in " + MainActivity.CITY + " " + MainActivity.STATE + " " + MainActivity.COUNTRY).await();}
                    catch (Exception f){Log.d("Second Error", f.getMessage());};
                    for (int j=0; j < address.results.length; j++){
                        try{
                            PhotoRequest photoRequests = new PhotoRequest(context);
                            photoRequests.maxHeight(100);
                            photoRequests.maxWidth(100);
                            photoRequests.photoReference(address.results[j].photos[0].photoReference);
                            photoRESULT = photoRequests.await();
                            byte[] bytes = photoRESULT.imageData;
                            BitmapFactory.Options options = new BitmapFactory.Options();
                            options.inScaled = false;

                            pictures.add(BitmapFactory.decodeByteArray(bytes,0,bytes.length,options));
                            break;
                        }
                        catch (Exception h){
                            continue;
                        }
                    }
                }
            }
            return null;
        }
    }
    class lastCall extends AsyncTask<Void, Void, PhotoResult>{

        @Override
        protected PhotoResult doInBackground(Void... params) {
            for (int i = 0; i < size; i++){
                try {
                    PhotoRequest photoRequests = new PhotoRequest(context);
                    photoRequests.maxHeight(100);
                    photoRequests.maxWidth(100);
                    photoRequests.photoReference(placesSearchResult.get(i).photos[0].photoReference);
                 //   Log.d("photoReference", i+"");
                    photoRESULT = photoRequests.await();
                    byte[] bytes = photoRESULT.imageData;
                    BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inScaled = false;

                    pictures1.add(BitmapFactory.decodeByteArray(bytes,0,bytes.length,options));
                }
                catch (Exception e) {
                 //   Log.d("photoRequest", "ERROR ERROR");
                  //  Log.d("ERROR", e.getMessage(), e);
                    PlacesSearchResponse address = null;
                    try{
                  //      Log.d("Number", i +"");
                        address = PlacesApi.textSearchQuery(context, placesSearchResult.get(i).name+ " in " + city).await();}
                    catch (Exception f){Log.d("Second Error", f.getMessage());};
                    for (int j=0; j < address.results.length; j++){
                        try{
                            PhotoRequest photoRequests = new PhotoRequest(context);
                            photoRequests.maxHeight(100);
                            photoRequests.maxWidth(100);
                            photoRequests.photoReference(address.results[j].photos[0].photoReference);
                            photoRESULT = photoRequests.await();
                            byte[] bytes = photoRESULT.imageData;
                            BitmapFactory.Options options = new BitmapFactory.Options();
                            options.inScaled = false;

                            pictures1.add(BitmapFactory.decodeByteArray(bytes,0,bytes.length,options));
                            break;
                        }
                        catch (Exception h){
                            continue;
                        }
                    }
                }
            }
            return null;
        }
    }



    class firtcall extends AsyncTask<Void, Void, PhotoResult>{

        @Override
        protected PhotoResult doInBackground(Void... params) {

            return null;
        }

        @Override
        protected void onPostExecute(PhotoResult photoResult) {
            object1.finalRestaurants();
            super.onPostExecute(photoResult);
        }
    }
}
