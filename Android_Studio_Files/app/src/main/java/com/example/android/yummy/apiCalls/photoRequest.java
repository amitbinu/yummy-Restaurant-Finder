package com.example.android.yummy.apiCalls;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import com.example.android.yummy.DataManager.Constants;
import com.example.android.yummy.MainActivities.Result;
import com.example.android.yummy.MainActivities.MainActivity;
import com.example.android.yummy.MainActivities.photos;
import com.google.maps.GeoApiContext;
import com.google.maps.PhotoRequest;
import com.google.maps.PlacesApi;
import com.google.maps.model.Photo;
import com.google.maps.model.PhotoResult;
import com.google.maps.model.PlacesSearchResponse;
import com.google.maps.model.PlacesSearchResult;
import java.util.ArrayList;
import static com.example.android.yummy.apiCalls.restaurant_getter.city;

/**
 * Created by amitb on 2017-05-11.
 */

public class photoRequest {
    public static PhotoResult photoRESULT;
    private static Result object1;
    public static ArrayList<Bitmap> PopularPictures;
    public static ArrayList<Bitmap> pictures1;
    private static GeoApiContext context;
    private static ArrayList<PlacesSearchResult> placesSearchResult;
    private static int size;
    private static PlacesSearchResponse placesSearchResponse;
    private static Photo[] pictures;
    private static photos object;
    public photoRequest(Result object){
        this.object1 = object;
        new firtcall().execute();
    }

    public photoRequest(PlacesSearchResponse placesSearchResponse){
        this.context = new GeoApiContext().setApiKey(Constants.ApiKey);
        this.placesSearchResponse= placesSearchResponse;
        this.PopularPictures = new ArrayList<>();
        if(placesSearchResponse.results.length >= 10){
            this.size = 10;
        }
        else{
            this.size = placesSearchResponse.results.length;
        }
        new onecall().execute();

    }

    public  photoRequest(Photo[] photos, photos object){
        this.object = object;
        this.pictures1 = new ArrayList<>();
        this.pictures = photos;
        new fetchPhotos().execute();
    }

    public photoRequest(ArrayList<PlacesSearchResult> placesSearchResults){
        this.context = new GeoApiContext().setApiKey(Constants.ApiKey);
        this.placesSearchResult = placesSearchResults;
        this.pictures1 = new ArrayList<>();

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
                    byte[] bytes = photoRESULT.imageData;
                    BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inScaled = false;
                    PopularPictures.add(BitmapFactory.decodeByteArray(bytes,0,bytes.length,options));
                }
                catch (Exception e) {
                    PlacesSearchResponse address = null;
                    try{
                        address = PlacesApi.textSearchQuery(context, placesSearchResponse.results[i].name+ " in " + MainActivity.CITY + " " + MainActivity.STATE + " " + MainActivity.COUNTRY).await();}
                    catch (Exception f){};
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

                            PopularPictures.add(BitmapFactory.decodeByteArray(bytes,0,bytes.length,options));
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
    public class lastCall extends AsyncTask<Void, Void, PhotoResult>{

        @Override
        protected PhotoResult doInBackground(Void... params) {
            for (int i = 0; i < size; i++){
                try {
                    PhotoRequest photoRequests = new PhotoRequest(context);
                    photoRequests.maxHeight(100);
                    photoRequests.maxWidth(100);
                    photoRequests.photoReference(placesSearchResult.get(i).photos[0].photoReference);
                    photoRESULT = photoRequests.await();
                    byte[] bytes = photoRESULT.imageData;
                    BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inScaled = false;

                    pictures1.add(BitmapFactory.decodeByteArray(bytes,0,bytes.length,options));
                }
                catch (Exception e) {
                    PlacesSearchResponse testingaddress = null;
                    try{
                        testingaddress = PlacesApi.textSearchQuery(context, placesSearchResult.get(i).name+ " in " + city).await();
                        while(testingaddress.results == null){}}
                    catch (Exception f){};
                    int counter = 0;
                    for (int j=0; j < testingaddress.results.length; j++){
                        try{
                            PhotoRequest photoRequests = new PhotoRequest(context);
                            photoRequests.maxHeight(350);
                            photoRequests.maxWidth(450);
                            photoRequests.photoReference(testingaddress.results[j].photos[0].photoReference);
                            photoRESULT = photoRequests.await();
                            byte[] bytes = photoRESULT.imageData;
                            BitmapFactory.Options options = new BitmapFactory.Options();
                            options.inScaled = false;
                            pictures1.add(BitmapFactory.decodeByteArray(bytes,0,bytes.length,options));
                            break;
                        }
                        catch (Exception h){
                            counter++;
                            continue;
                        }
                    }
                    if(counter == testingaddress.results.length){
                       try{
                        testingaddress = PlacesApi.textSearchQuery(context, MainActivity.CITY+ " " + MainActivity.STATE).await();}
                        catch (Exception z){}

                            for (int j=0; j < testingaddress.results.length; j++){
                                try{
                                    PhotoRequest photoRequests = new PhotoRequest(context);
                                    photoRequests.maxHeight(350);
                                    photoRequests.maxWidth(450);
                                    photoRequests.photoReference(testingaddress.results[j].photos[0].photoReference);
                                    photoRESULT = photoRequests.await();
                                    byte[] bytes = photoRESULT.imageData;
                                    BitmapFactory.Options options = new BitmapFactory.Options();
                                    options.inScaled = false;
                                    pictures1.add(BitmapFactory.decodeByteArray(bytes,0,bytes.length,options));
                                    break;
                                }
                                catch (Exception h){
                                    counter++;
                                    continue;
                                }
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

    class fetchPhotos extends AsyncTask<Void,Void,Void>{

        @Override
        protected Void doInBackground(Void... params) {
            for(Photo photo : pictures){
                try{
                    PhotoRequest photoRequests = new PhotoRequest(context);
                    photoRequests.maxHeight(350);
                    photoRequests.maxWidth(450);
                    photoRequests.photoReference(photo.photoReference);
                    photoRESULT = photoRequests.await();
                    byte[] bytes = photoRESULT.imageData;
                    BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inScaled = false;
                    pictures1.add(BitmapFactory.decodeByteArray(bytes,0,bytes.length,options));}
                catch (Exception e){
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            object.afterRequest(pictures1);
            super.onPostExecute(aVoid);
        }
    }
}
