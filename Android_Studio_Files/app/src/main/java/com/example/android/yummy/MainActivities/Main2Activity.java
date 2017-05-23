package com.example.android.yummy.MainActivities;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.yummy.DataManager.Restaurant;
import com.example.android.yummy.R;
import com.example.android.yummy.DataManager.Result;
import com.google.maps.model.Photo;
import com.google.maps.model.PlacesSearchResponse;

public class Main2Activity extends AppCompatActivity {
    public static String Message;
    private static Restaurant[] popularRestaurants;
    public static Main2Activity object;
    public static double[] distances;
    public static double[] times;

    public static Boolean checker = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar3);
        progressBar.setVisibility(View.GONE);
        this.object = this;

        modifySearchBar();
        if( checker == true){
            Toast.makeText(this,"ENTER A VALID FOOD !",Toast.LENGTH_SHORT).show();
            checker=false;
        }
    }

    @Override
    public void onBackPressed(){
        this.finish();
        System.exit(0);
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
       popularRestaurants = new Restaurant[MainActivity.popularRestaurants.address.results.length];

        PlacesSearchResponse DATA = MainActivity.popularRestaurants.address;

        for (int i =0; i< popularRestaurants.length; i++){
            String name  = DATA.results[i].name;
            String address = DATA.results[i].formattedAddress;
            Double distance = distances[i];
            Double time = times[i];
            Double rating =(double) ((Math.round((double) DATA.results[i].rating * 1000000)) / 1000000) ;
            Boolean open;
            try{open = DATA.results[i].openingHours.openNow;}
            catch (Exception e){open = null;}
            Boolean permanentlyClosed =DATA.results[i].permanentlyClosed;
            popularRestaurants[i] = new Restaurant(name,address,time,distance,rating,null,null,open,permanentlyClosed, null);
        }

        object.PopularRestaurantsLayout();
    }

    public void PopularRestaurantsLayout(){
        TextView update = (TextView) findViewById(R.id.second_update);
        update.setVisibility(View.GONE);
        ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar2);
        progressBar.setVisibility(View.GONE);
        RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.relativePopularRestaurants);
        LinearLayout mainLinearLayout = new LinearLayout(this);
        LinearLayout.LayoutParams mainlayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        mainlayoutParams.setMargins(10,10,0,0);
        mainLinearLayout.setOrientation(LinearLayout.VERTICAL);
        for(int i =0; i<10; i++){
            RelativeLayout relativeLayout1 = new RelativeLayout(this);
            relativeLayout1.setBackgroundColor(getResources().getColor(R.color.BackGroundPopular));
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(1000,500);
            layoutParams.setMargins(0,40,0,0);
            relativeLayout1.setLayoutParams(layoutParams);

            ImageView imageView = new ImageView(this);
            imageView.setId(i);
          //  imageView.setImageDrawable(MainActivity.pictures.pictures.get(i)[0]);
              imageView.setImageBitmap(resizer(MainActivity.pictures.pictures.get(i),450,350));
            RelativeLayout.LayoutParams layoutParams1 = new RelativeLayout.LayoutParams(450,350);
            layoutParams1.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
            imageView.setLayoutParams(layoutParams1);

            TextView textView = new TextView(this);
            textView.setText(popularRestaurants[i].restuarant_name);
            textView.setTextColor(Color.WHITE);
            textView.setTextSize(16);
            textView.setAllCaps(true);
            RelativeLayout.LayoutParams layoutParams2 = new RelativeLayout.LayoutParams(550,RelativeLayout.LayoutParams.WRAP_CONTENT);
            layoutParams2.setMargins(460,0,0,0);
            layoutParams2.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            textView.setLayoutParams(layoutParams2);

            TextView textView2 = new TextView(this);
            textView2.setTextColor(getResources().getColor(R.color.SecondTextColor));
            textView2.setText(popularRestaurants[i].address);
            textView2.setTextSize(16);
            RelativeLayout.LayoutParams layoutParams3 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
            layoutParams3.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
            layoutParams3.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
            layoutParams3.setMargins(5,0,0,0);
            textView2.setLayoutParams(layoutParams3);

            relativeLayout1.addView(imageView);
            relativeLayout1.addView(textView);
            relativeLayout1.addView(textView2);
            mainLinearLayout.addView(relativeLayout1);
        }
        relativeLayout.addView(mainLinearLayout, mainlayoutParams);
    }
    public void nextActivity(String view){
        // food = view;
        ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar3);
        progressBar.setVisibility(View.VISIBLE);
        Message = view;
        Intent intent = new Intent(this, Result.class);
        ScrollView searchView = (ScrollView) findViewById(R.id.scrollview);
        searchView.setVisibility(View.GONE);
        TextView textView = (TextView) findViewById(R.id.PopularRestaurants);
        textView.setVisibility(View.GONE);
        startActivity(intent);
    }

}
