package com.restaurant.android.yummy.MainActivities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.restaurant.android.yummy.apiCalls.photoRequest;
import com.restaurant.android.yummy.R;
import com.google.maps.model.Photo;


import java.util.ArrayList;

public class photos extends AppCompatActivity {
    private static ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photos);
        Photo[] photos = RestaurantActivity.placeDetails.photos;
        progressBar = (ProgressBar) findViewById(R.id.PhotoProgressBar);
        if(photos == null){
            progressBar.setVisibility(View.GONE);
        }
        else{
            TextView errorMessage = (TextView) findViewById(R.id.ErrorMessage);
            errorMessage.setVisibility(View.GONE);
            if(isOnline() == true){
                new photoRequest(photos, this);
            }
            else{
                Toast.makeText(this,"Need Internet to work",Toast.LENGTH_LONG).show();
                Intent settingsIntent = new Intent(Settings.ACTION_WIFI_SETTINGS);
                startActivityForResult(settingsIntent, 9003);
                onBackPressed();
            }
        }
    }


    private Boolean isOnline(){
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }

    public void afterRequest(ArrayList<Bitmap> pictures){
        LinearLayout mainView = (LinearLayout) findViewById(R.id.MainView);
        for(int i =0; i < pictures.size(); i++){
            ImageView imageView1 = new ImageView(this);
            LinearLayout.LayoutParams layoutParams1 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 1000);
            layoutParams1.setMargins(0,20,0,0);
            imageView1.setImageBitmap(pictures.get(i));
            imageView1.setLayoutParams(layoutParams1);
            mainView.addView(imageView1);
        }
        progressBar.setVisibility(View.GONE);
    }
}
