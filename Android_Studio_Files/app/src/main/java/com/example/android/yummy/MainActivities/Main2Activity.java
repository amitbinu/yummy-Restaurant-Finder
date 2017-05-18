package com.example.android.yummy.MainActivities;

import android.app.VoiceInteractor;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.TextView;


import com.example.android.yummy.Backthread.PopularRestaurants;
import com.example.android.yummy.Backthread.photoRequest;
import com.example.android.yummy.DataManager.Restaurant;
import com.example.android.yummy.R;
import com.example.android.yummy.DataManager.Result;
import com.google.maps.GeoApiContext;
import com.google.maps.PhotoRequest;
import com.google.maps.model.Photo;
import com.google.maps.model.PhotoResult;
import com.google.maps.model.PlacesSearchResponse;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.security.PrivateKey;
import java.util.ArrayList;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.V;
import static com.example.android.yummy.Backthread.second_address.address;

public class Main2Activity extends AppCompatActivity {
    public static String Message;
    private static Restaurant[] popularRestaurants;
    public static Bitmap[] bitmap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        object = this;
        modifySearchBar();
       new caller().execute();
    }

    private class caller extends AsyncTask<Void, Void, String>{

        @Override
        protected String doInBackground(Void... params) {
            popularRestaurants();

            return null;
        }
    }
    public void modifySearchBar(){
        final SearchView searchbar = (SearchView) findViewById(R.id.SearchBar);
        searchbar.setQueryHint(Html.fromHtml("<font color = #ffffff>" + " Type the food here " + "</font>"));
        searchbar.onActionViewExpanded();
        searchbar.setIconified(false);
        searchbar.setQueryHint(Html.fromHtml("<font color = #ffffff>" + " Type the food here " + "</font>"));
        searchbar.setOnQueryTextListener(new SearchView.OnQueryTextListener(){
            @Override
            public boolean onQueryTextSubmit(String query){
                nextActivity(query);
                return false;
            }
            @Override
            public boolean onQueryTextChange(String change){
                return false;
            }
        });
        searchbar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                searchbar.setIconified(false);

            }
        });
        searchbar.setIconified(false);
        searchbar.setIconified(true);
    }

    private void PopularRestaurantsLayout(){
        RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.relativePopularRestaurants);
        LinearLayout mainLinearLayout = new LinearLayout(this);
        LinearLayout.LayoutParams mainlayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        mainlayoutParams.setMargins(10,10,0,0);
        mainLinearLayout.setOrientation(LinearLayout.VERTICAL);
        for(int i =0; i<bitmap.length; i++){
            RelativeLayout relativeLayout1 = new RelativeLayout(this);
            relativeLayout1.setBackgroundColor(getResources().getColor(R.color.BackGroundSearch));
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
            layoutParams.setMargins(0,40,0,0);
            relativeLayout1.setLayoutParams(layoutParams);

            ImageView imageView = new ImageView(this);
            imageView.setImageBitmap(resizer(bitmap[i],500,300));
            RelativeLayout.LayoutParams layoutParams1 = new RelativeLayout.LayoutParams(500,300);
            layoutParams1.setMargins(0,0,0,0);


            TextView textView = new TextView(this);
            textView.setText(popularRestaurants[i].restuarant_name);
            textView.setTextColor(Color.WHITE);
            textView.setTextSize(15);
            RelativeLayout.LayoutParams layoutParams2 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
            layoutParams2.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            layoutParams2.setMargins(0,10,0,0);
            textView.setLayoutParams(layoutParams2);

           // textView.setLayoutParams(layoutParams1);

            imageView.setLayoutParams(layoutParams1);
            TextView textView2 = new TextView(this);
          //  textView.setText("B");
          //  textView.setTextColor(Color.WHITE);
            textView2.setBackgroundColor(ResourcesCompat.getColor(getResources(),R.color.SearchColor, null));
            RelativeLayout.LayoutParams layoutParams3 = new RelativeLayout.LayoutParams(30,80);
            layoutParams2.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
           // layoutParams2.
            layoutParams2.setMargins(0,10,10,0);
            textView2.setLayoutParams(layoutParams2);

            relativeLayout1.addView(imageView,0);
            relativeLayout1.addView(textView,1);
            mainLinearLayout.addView(relativeLayout1);
        }
        relativeLayout.addView(mainLinearLayout, mainlayoutParams);
    }

    public static Bitmap resizer(Bitmap bitmap, int newWidth, int newHeight){
        Bitmap scaledBitmap = Bitmap.createBitmap(newWidth, newHeight, Bitmap.Config.ARGB_8888);

        float ratioX = newWidth / (float) bitmap.getWidth();
        float ratioY = newHeight / (float) bitmap.getHeight();
        float middleX = newWidth / 2.0f;
        float middleY = newHeight / 2.0f;

        Matrix scaleMatrix = new Matrix();
        scaleMatrix.setScale(ratioX, ratioY, middleX, middleY);

        Canvas canvas = new Canvas(scaledBitmap);
        canvas.setMatrix(scaleMatrix);
        canvas.drawBitmap(bitmap, middleX - bitmap.getWidth() / 2, middleY - bitmap.getHeight() / 2, new Paint(Paint.FILTER_BITMAP_FLAG));

        return scaledBitmap;

    }
    public static void popularRestaurants(){
        if(MainActivity.popularRestaurants.address.results.length >= 10){
            popularRestaurants = new Restaurant[10];
        }
        else{
            popularRestaurants = new Restaurant[MainActivity.popularRestaurants.address.results.length];
        }

        Log.e("Number of Restaurants", popularRestaurants.length+")=");
        PlacesSearchResponse DATA = MainActivity.popularRestaurants.address;

        for (int i =0; i< popularRestaurants.length; i++){
            String name  = DATA.results[i].name;
            String address = DATA.results[i].formattedAddress;
            Double distance = MainActivity.distances[i];
            Double time = MainActivity.times[i];
            Double rating =(double) ((Math.round((double) DATA.results[i].rating * 1000000)) / 1000000) ;
            Photo[] pictures = DATA.results[i].photos;
            Boolean open;
            try{open = DATA.results[i].openingHours.openNow;}
            catch (Exception e){open = null;}
            Boolean permanentlyClosed =DATA.results[i].permanentlyClosed;
            java.net.URL url = DATA.results[i].icon;
            popularRestaurants[i] = new Restaurant(name,address,time,distance,rating,null,pictures,open,permanentlyClosed,url);
        }
        bitmap = new Bitmap[popularRestaurants.length];
            getPhotos(popularRestaurants);
    }
    public static Main2Activity object;

    private static void getPhotos(Restaurant[] popularRestaurants){

        photoRequest pict = new photoRequest(object,popularRestaurants);
    }

    public static void photo(PhotoResult pict, int i){
        Log.e("Number", i +"");
        byte[] bytes = pict.imageData;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inScaled = false;
        bitmap[i] = BitmapFactory.decodeByteArray(bytes,0,bytes.length,options);
    }
    public void nextActivity(String view){
        // food = view;
        Message = view;
        Intent intent = new Intent(this, Result.class);
        intent.putExtra(Message, view);
        startActivity(intent);
    }

}
