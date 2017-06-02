package com.example.android.yummy.MainActivities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.android.yummy.Backthread.DetailedRequest;
import com.example.android.yummy.Backthread.yelp;
import com.example.android.yummy.DataManager.Restaurant;
import com.example.android.yummy.R;
import com.google.maps.model.Photo;

import edu.princeton.cs.algs4.LinearRegression;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.L;
import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;
import static com.example.android.yummy.R.id.Sunday;
import static com.example.android.yummy.R.id.relativeLayout;
import static com.example.android.yummy.R.id.textView;

public class RestaurantActivity extends AppCompatActivity {
    private static TextView distance, restaurantName, time, price, priceText, rating, address;
    private static ScrollView scrollView;
    private static ProgressBar progressBar;
    private static ImageView star1, star2, star3, star4, star5;
    private static RelativeLayout review_text;
    private static RestaurantActivity object;
    public static Boolean checker_for_yelp, checker_for_placeDetails;
    private static yelp yelpCaller;
    public static Photo[] photos;
    private static DetailedRequest detailedRequest;
    public static String placeId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant);

        object = this;
        distance = (TextView) findViewById(R.id.Distance_text);
        restaurantName = (TextView) findViewById(R.id.RestaurantName);
        scrollView = (ScrollView) findViewById(R.id.ScrollView);
        time = (TextView) findViewById(R.id.time_text);
        price = (TextView) findViewById(R.id.price_pict);
        priceText = (TextView) findViewById(R.id.price_text);
        progressBar = (ProgressBar) findViewById(R.id.ProgressBar);
        star1 = (ImageView) findViewById(R.id.Star1);
        star2 = (ImageView) findViewById(R.id.Star2);
        star3 = (ImageView) findViewById(R.id.Star3);
        star4 = (ImageView) findViewById(R.id.Star4);
        star5 = (ImageView) findViewById(R.id.Star5);
        rating = (TextView) findViewById(R.id.rating_text);
        address = (TextView) findViewById(R.id.Address_text);
        review_text = (RelativeLayout) findViewById(R.id.reviews_text);

        restaurantName.setVisibility(View.GONE);
        scrollView.setVisibility(View.GONE);

        checker_for_yelp = false;
        checker_for_placeDetails = false;
        yelpCaller = new yelp(Main2Activity.popularRestaurants[Main2Activity.index_value].restuarant_name, Main2Activity.popularRestaurants[Main2Activity.index_value].address);
        detailedRequest = new DetailedRequest(placeId);
    }



    public static void afterApiCall() {
        progressBar.setVisibility(View.GONE);
        restaurantName.setVisibility(View.VISIBLE);
        scrollView.setVisibility(View.VISIBLE);
        restaurantName.setText(Main2Activity.popularRestaurants[Main2Activity.index_value].restuarant_name);
        address.setText(Main2Activity.popularRestaurants[Main2Activity.index_value].address);
        try {
            price.setText(yelpCaller.response.body().getBusinesses().get(0).getPrice());

            switch (yelpCaller.response.body().getBusinesses().get(0).getPrice().length()) {
                case 1:
                    priceText.setText("   < 10");
                    break;
                case 2:
                    priceText.setText("$ 11 - $ 30");
                    break;
                case 3:
                    priceText.setText("$ 31 - $ 60");
                    break;
                case 4:
                    priceText.setText("   > 61");
                    break;
            }
        } catch (Exception e) {
            price.setText("N/A");
            priceText.setText("     - -");
        }
        distance.setText(Main2Activity.popularRestaurants[Main2Activity.index_value].distance + " " + Main2Activity.distanceUnit);
        time.setText(Main2Activity.popularRestaurants[Main2Activity.index_value].time + " " + "min");
        setRatings();
        object.review_setter();
        object.openingHours();
    }

    public static void setRatings() {

        switch (Main2Activity.typesOfStars.get(Main2Activity.index_value)[0]) {
            case 0:
                star1.setImageResource(R.drawable.emptystar_restaurant_activity);
                break;
            case 1:
                star1.setImageResource(R.drawable.halfstar_restaurant_activity);
                break;
            case 2:
                star1.setImageResource(R.drawable.fullstar_restaurant_activity);
                break;
        }
        switch (Main2Activity.typesOfStars.get(Main2Activity.index_value)[1]) {
            case 0:
                star2.setImageResource(R.drawable.emptystar_restaurant_activity);
                break;
            case 1:
                star2.setImageResource(R.drawable.halfstar_restaurant_activity);
                break;
            case 2:
                star2.setImageResource(R.drawable.fullstar_restaurant_activity);
                break;
        }
        switch (Main2Activity.typesOfStars.get(Main2Activity.index_value)[2]) {
            case 0:
                star3.setImageResource(R.drawable.emptystar_restaurant_activity);
                break;
            case 1:
                star3.setImageResource(R.drawable.halfstar_restaurant_activity);
                break;
            case 2:
                star3.setImageResource(R.drawable.fullstar_restaurant_activity);
                break;
        }
        switch (Main2Activity.typesOfStars.get(Main2Activity.index_value)[3]) {
            case 0:
                star4.setImageResource(R.drawable.emptystar_restaurant_activity);
                break;
            case 1:
                star4.setImageResource(R.drawable.halfstar_restaurant_activity);
                break;
            case 2:
                star4.setImageResource(R.drawable.fullstar_restaurant_activity);
                break;
        }
        switch (Main2Activity.typesOfStars.get(Main2Activity.index_value)[4]) {
            case 0:
                star5.setImageResource(R.drawable.emptystar_restaurant_activity);
                break;
            case 1:
                star5.setImageResource(R.drawable.halfstar_restaurant_activity);
                break;
            case 2:
                star5.setImageResource(R.drawable.fullstar_restaurant_activity);
                break;
        }
        rating.setText("(" + Main2Activity.popularRestaurants[Main2Activity.index_value].rating + ")");
    }

    public void GoogleMaps(View view) {
        Uri gmmIntentUri = Uri.parse("google.navigation:q="+detailedRequest.placeDetails.name+",+"+detailedRequest.placeDetails.formattedAddress);
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        if (mapIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(mapIntent);
        }
    }

    public void Website(View view) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.addCategory(Intent.CATEGORY_BROWSABLE);
        intent.setData(Uri.parse(detailedRequest.placeDetails.website.toString()));
        startActivity(intent);
    }

    public void Photos(View view) {
        Intent intent = new Intent(this, photos.class);
        this.photos = detailedRequest.placeDetails.photos;
        startActivity(intent);
    }

    public void Phone(View view) {
        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:" + detailedRequest.placeDetails.formattedPhoneNumber));
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        startActivity(intent);
    }


    private void review_setter(){
        LinearLayout Vertical = new LinearLayout(this);
        Vertical.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams VerticalParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        Vertical.setLayoutParams(VerticalParams);
        LinearLayout HorizontalLinearLayout ;
        for(int i =0; i < detailedRequest.placeDetails.reviews.length; i++){
            LinearLayout linearLayout = new LinearLayout(this);
            LinearLayout.LayoutParams LinearlayoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            LinearlayoutParams.setMargins(0,10,0,0);
            linearLayout.setOrientation(LinearLayout.VERTICAL);
            linearLayout.setLayoutParams(LinearlayoutParams);

            HorizontalLinearLayout = new LinearLayout(this);
            LinearLayout.LayoutParams HorizontalLayoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
            HorizontalLayoutParams.setMargins(0,0,0,0);
            HorizontalLinearLayout.setOrientation(LinearLayout.HORIZONTAL);
            HorizontalLinearLayout.setLayoutParams(HorizontalLayoutParams);
            HorizontalLinearLayout.setId(i);

            if(i == 0){
            TextView userName = new TextView(this);
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.setMargins(10,5,0,0);
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
            userName.setTextColor(getResources().getColor(R.color.SecondTextColor));
            userName.setTextSize(15);
            userName.setLayoutParams(layoutParams);
            userName.setText(detailedRequest.placeDetails.reviews[i].authorName);
            linearLayout.addView(userName);}
            else{
                TextView userName = new TextView(this);
                RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                layoutParams.setMargins(10,20,0,0);
                layoutParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
                userName.setTextColor(getResources().getColor(R.color.SecondTextColor));
                userName.setTextSize(15);
                userName.setLayoutParams(layoutParams);
                userName.setText(detailedRequest.placeDetails.reviews[i].authorName);
                linearLayout.addView(userName);
            }


            LinearLayout ratingLayout = new LinearLayout(this);
            LinearLayout.LayoutParams ratingParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            ratingLayout.setOrientation(LinearLayout.HORIZONTAL);
            ratingLayout.setLayoutParams(ratingParams);

            switch (detailedRequest.placeDetails.reviews[i].rating){
                case 0:
                    ImageView star = new ImageView(this);
                    star.setImageResource(R.drawable.emptystar_restaurant_activity);
                    RelativeLayout.LayoutParams layoutParams12 = new RelativeLayout.LayoutParams(60,60);
                    layoutParams12.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
                    layoutParams12.setMargins(5,25,0,0);
                    star.setLayoutParams(layoutParams12);

                    ImageView starPict = new ImageView(this);
                    starPict.setImageResource(R.drawable.emptystar_restaurant_activity);
                    RelativeLayout.LayoutParams emptyStarLayout = new RelativeLayout.LayoutParams(60,60);
                    // emptyStarLayout.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
                    emptyStarLayout.setMargins(25,25,0,0);
                    starPict.setLayoutParams(emptyStarLayout);

                    ImageView starPict2 = new ImageView(this);
                    starPict2.setImageResource(R.drawable.emptystar_restaurant_activity);
                    RelativeLayout.LayoutParams emptyStarLayout2 = new RelativeLayout.LayoutParams(60,60);
                    //  emptyStarLayout2.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
                    emptyStarLayout2.setMargins(25,25,0,0);
                    starPict2.setLayoutParams(emptyStarLayout2);

                    ImageView starPict3 = new ImageView(this);
                    starPict3.setImageResource(R.drawable.emptystar_restaurant_activity);
                    RelativeLayout.LayoutParams emptyStarLayout3 = new RelativeLayout.LayoutParams(60,60);
                    // emptyStarLayout3.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
                    emptyStarLayout3.setMargins(25,25,0,0);
                    starPict3.setLayoutParams(emptyStarLayout3);

                    ImageView starPict4 = new ImageView(this);
                    starPict4.setImageResource(R.drawable.emptystar_restaurant_activity);
                    RelativeLayout.LayoutParams emptyStarLayout4 = new RelativeLayout.LayoutParams(60,60);
                    // emptyStarLayout4.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
                    emptyStarLayout4.setMargins(25,25,0,0);
                    starPict4.setLayoutParams(emptyStarLayout4);

                    TextView rating = new TextView(this);
                     RelativeLayout.LayoutParams layoutParams2 = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    layoutParams2.setMargins(30,25,0,0);
                    layoutParams2.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
                    rating.setTextColor(getResources().getColor(R.color.SecondTextColor));
                    rating.setTextSize(13);
                    rating.setText(0+"");
                    rating.setLayoutParams(layoutParams2);

                    ratingLayout.addView(star);
                    ratingLayout.addView(starPict);
                    ratingLayout.addView(starPict2);
                    ratingLayout.addView(starPict3);
                    ratingLayout.addView(starPict4);
                    ratingLayout.addView(rating);
                    linearLayout.addView(ratingLayout);
                case 1:
                    star = new ImageView(this);
                    star.setImageResource(R.drawable.fullstar_restaurant_activity);
                    layoutParams12 = new RelativeLayout.LayoutParams(60,60);
                    layoutParams12.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
                    layoutParams12.setMargins(5,25,0,0);
                    star.setLayoutParams(layoutParams12);

                    starPict = new ImageView(this);
                    starPict.setImageResource(R.drawable.emptystar_restaurant_activity);
                    emptyStarLayout = new RelativeLayout.LayoutParams(60,60);
                    // emptyStarLayout.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
                    emptyStarLayout.setMargins(25,25,0,0);
                    starPict.setLayoutParams(emptyStarLayout);

                    starPict2 = new ImageView(this);
                    starPict2.setImageResource(R.drawable.emptystar_restaurant_activity);
                    emptyStarLayout2 = new RelativeLayout.LayoutParams(60,60);
                    //  emptyStarLayout2.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
                    emptyStarLayout2.setMargins(25,25,0,0);
                    starPict2.setLayoutParams(emptyStarLayout2);

                    starPict3 = new ImageView(this);
                    starPict3.setImageResource(R.drawable.emptystar_restaurant_activity);
                    emptyStarLayout3 = new RelativeLayout.LayoutParams(60,60);
                    // emptyStarLayout3.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
                    emptyStarLayout3.setMargins(25,25,0,0);
                    starPict3.setLayoutParams(emptyStarLayout3);

                    starPict4 = new ImageView(this);
                    starPict4.setImageResource(R.drawable.emptystar_restaurant_activity);
                    emptyStarLayout4 = new RelativeLayout.LayoutParams(60,60);
                    // emptyStarLayout4.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
                    emptyStarLayout4.setMargins(25,25,0,0);
                    starPict4.setLayoutParams(emptyStarLayout4);

                    rating = new TextView(this);
                    layoutParams2 = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    layoutParams2.setMargins(30,25,0,0);
                    layoutParams2.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
                    rating.setTextColor(getResources().getColor(R.color.SecondTextColor));
                    rating.setTextSize(13);
                    rating.setText(1.0+"");
                    rating.setLayoutParams(layoutParams2);

                    ratingLayout.addView(star);
                    ratingLayout.addView(starPict);
                    ratingLayout.addView(starPict2);
                    ratingLayout.addView(starPict3);
                    ratingLayout.addView(starPict4);
                    ratingLayout.addView(rating);
                    linearLayout.addView(ratingLayout);
                    break;
                case 2:
                    star = new ImageView(this);
                    star.setImageResource(R.drawable.fullstar_restaurant_activity);
                    layoutParams12 = new RelativeLayout.LayoutParams(60,60);
                    layoutParams12.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
                    layoutParams12.setMargins(5,25,0,0);
                    star.setLayoutParams(layoutParams12);

                    starPict = new ImageView(this);
                    starPict.setImageResource(R.drawable.fullstar_restaurant_activity);
                    emptyStarLayout = new RelativeLayout.LayoutParams(60,60);
                    // emptyStarLayout.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
                    emptyStarLayout.setMargins(25,25,0,0);
                    starPict.setLayoutParams(emptyStarLayout);

                    starPict2 = new ImageView(this);
                    starPict2.setImageResource(R.drawable.emptystar_restaurant_activity);
                    emptyStarLayout2 = new RelativeLayout.LayoutParams(60,60);
                    //  emptyStarLayout2.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
                    emptyStarLayout2.setMargins(25,25,0,0);
                    starPict2.setLayoutParams(emptyStarLayout2);

                    starPict3 = new ImageView(this);
                    starPict3.setImageResource(R.drawable.emptystar_restaurant_activity);
                    emptyStarLayout3 = new RelativeLayout.LayoutParams(60,60);
                    // emptyStarLayout3.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
                    emptyStarLayout3.setMargins(25,25,0,0);
                    starPict3.setLayoutParams(emptyStarLayout3);

                    starPict4 = new ImageView(this);
                    starPict4.setImageResource(R.drawable.emptystar_restaurant_activity);
                    emptyStarLayout4 = new RelativeLayout.LayoutParams(60,60);
                    // emptyStarLayout4.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
                    emptyStarLayout4.setMargins(25,25,0,0);
                    starPict4.setLayoutParams(emptyStarLayout4);

                    rating = new TextView(this);
                    layoutParams2 = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    layoutParams2.setMargins(30,25,0,0);
                    layoutParams2.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
                    rating.setTextColor(getResources().getColor(R.color.SecondTextColor));
                    rating.setTextSize(13);
                    rating.setText(2.0+"");
                    rating.setLayoutParams(layoutParams2);

                    ratingLayout.addView(star);
                    ratingLayout.addView(starPict);
                    ratingLayout.addView(starPict2);
                    ratingLayout.addView(starPict3);
                    ratingLayout.addView(starPict4);
                    ratingLayout.addView(rating);
                    linearLayout.addView(ratingLayout);
                    break;
                case 3:
                    Log.e("Case #", 3+"");
                    star = new ImageView(this);
                    star.setImageResource(R.drawable.fullstar_restaurant_activity);
                    layoutParams12 = new RelativeLayout.LayoutParams(60,60);
                    layoutParams12.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
                    layoutParams12.setMargins(5,25,0,0);
                    star.setLayoutParams(layoutParams12);

                    starPict = new ImageView(this);
                    starPict.setImageResource(R.drawable.fullstar_restaurant_activity);
                    emptyStarLayout = new RelativeLayout.LayoutParams(60,60);
                   // emptyStarLayout.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
                    emptyStarLayout.setMargins(25,25,0,0);
                    starPict.setLayoutParams(emptyStarLayout);

                    starPict2 = new ImageView(this);
                    starPict2.setImageResource(R.drawable.fullstar_restaurant_activity);
                    emptyStarLayout2 = new RelativeLayout.LayoutParams(60,60);
                  //  emptyStarLayout2.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
                    emptyStarLayout2.setMargins(25,25,0,0);
                    starPict2.setLayoutParams(emptyStarLayout2);

                    starPict3 = new ImageView(this);
                    starPict3.setImageResource(R.drawable.emptystar_restaurant_activity);
                    emptyStarLayout3 = new RelativeLayout.LayoutParams(60,60);
                   // emptyStarLayout3.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
                    emptyStarLayout3.setMargins(25,25,0,0);
                    starPict3.setLayoutParams(emptyStarLayout3);

                    starPict4 = new ImageView(this);
                    starPict4.setImageResource(R.drawable.emptystar_restaurant_activity);
                    emptyStarLayout4 = new RelativeLayout.LayoutParams(60,60);
                   // emptyStarLayout4.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
                    emptyStarLayout4.setMargins(25,25,0,0);
                    starPict4.setLayoutParams(emptyStarLayout4);

                    rating = new TextView(this);
                    layoutParams2 = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    layoutParams2.setMargins(30,25,0,0);
                    layoutParams2.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
                    rating.setTextColor(getResources().getColor(R.color.SecondTextColor));
                    rating.setTextSize(13);
                    rating.setText(3.0+"");
                    rating.setLayoutParams(layoutParams2);

                    ratingLayout.addView(star);
                    ratingLayout.addView(starPict);
                    ratingLayout.addView(starPict2);
                    ratingLayout.addView(starPict3);
                    ratingLayout.addView(starPict4);
                    ratingLayout.addView(rating);
                    linearLayout.addView(ratingLayout);
                    break;
                case 4:
                    star = new ImageView(this);
                    star.setImageResource(R.drawable.fullstar_restaurant_activity);
                    layoutParams12 = new RelativeLayout.LayoutParams(60,60);
                    layoutParams12.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
                    layoutParams12.setMargins(5,25,0,0);
                    star.setLayoutParams(layoutParams12);

                    starPict = new ImageView(this);
                    starPict.setImageResource(R.drawable.fullstar_restaurant_activity);
                    emptyStarLayout = new RelativeLayout.LayoutParams(60,60);
                    // emptyStarLayout.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
                    emptyStarLayout.setMargins(25,25,0,0);
                    starPict.setLayoutParams(emptyStarLayout);

                    starPict2 = new ImageView(this);
                    starPict2.setImageResource(R.drawable.fullstar_restaurant_activity);
                    emptyStarLayout2 = new RelativeLayout.LayoutParams(60,60);
                    //  emptyStarLayout2.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
                    emptyStarLayout2.setMargins(25,25,0,0);
                    starPict2.setLayoutParams(emptyStarLayout2);

                    starPict3 = new ImageView(this);
                    starPict3.setImageResource(R.drawable.fullstar_restaurant_activity);
                    emptyStarLayout3 = new RelativeLayout.LayoutParams(60,60);
                    // emptyStarLayout3.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
                    emptyStarLayout3.setMargins(25,25,0,0);
                    starPict3.setLayoutParams(emptyStarLayout3);

                    starPict4 = new ImageView(this);
                    starPict4.setImageResource(R.drawable.emptystar_restaurant_activity);
                    emptyStarLayout4 = new RelativeLayout.LayoutParams(60,60);
                    // emptyStarLayout4.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
                    emptyStarLayout4.setMargins(25,25,0,0);
                    starPict4.setLayoutParams(emptyStarLayout4);

                    rating = new TextView(this);
                    layoutParams2 = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    layoutParams2.setMargins(30,25,0,0);
                    layoutParams2.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
                    rating.setTextColor(getResources().getColor(R.color.SecondTextColor));
                    rating.setTextSize(13);
                    rating.setText(4.0+"");
                    rating.setLayoutParams(layoutParams2);

                    ratingLayout.addView(star);
                    ratingLayout.addView(starPict);
                    ratingLayout.addView(starPict2);
                    ratingLayout.addView(starPict3);
                    ratingLayout.addView(starPict4);
                    ratingLayout.addView(rating);
                    linearLayout.addView(ratingLayout);
                    break;
                case 5:
                    star = new ImageView(this);
                    star.setImageResource(R.drawable.fullstar_restaurant_activity);
                    layoutParams12 = new RelativeLayout.LayoutParams(60,60);
                    layoutParams12.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
                    layoutParams12.setMargins(5,25,0,0);
                    star.setLayoutParams(layoutParams12);

                    starPict = new ImageView(this);
                    starPict.setImageResource(R.drawable.fullstar_restaurant_activity);
                    emptyStarLayout = new RelativeLayout.LayoutParams(60,60);
                    // emptyStarLayout.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
                    emptyStarLayout.setMargins(25,25,0,0);
                    starPict.setLayoutParams(emptyStarLayout);

                    starPict2 = new ImageView(this);
                    starPict2.setImageResource(R.drawable.fullstar_restaurant_activity);
                    emptyStarLayout2 = new RelativeLayout.LayoutParams(60,60);
                    //  emptyStarLayout2.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
                    emptyStarLayout2.setMargins(25,25,0,0);
                    starPict2.setLayoutParams(emptyStarLayout2);

                    starPict3 = new ImageView(this);
                    starPict3.setImageResource(R.drawable.fullstar_restaurant_activity);
                    emptyStarLayout3 = new RelativeLayout.LayoutParams(60,60);
                    // emptyStarLayout3.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
                    emptyStarLayout3.setMargins(25,25,0,0);
                    starPict3.setLayoutParams(emptyStarLayout3);

                    starPict4 = new ImageView(this);
                    starPict4.setImageResource(R.drawable.fullstar_restaurant_activity);
                    emptyStarLayout4 = new RelativeLayout.LayoutParams(60,60);
                    // emptyStarLayout4.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
                    emptyStarLayout4.setMargins(25,25,0,0);
                    starPict4.setLayoutParams(emptyStarLayout4);

                    rating = new TextView(this);
                    layoutParams2 = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    layoutParams2.setMargins(30,25,0,0);
                    layoutParams2.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
                    rating.setTextColor(getResources().getColor(R.color.SecondTextColor));
                    rating.setTextSize(13);
                    rating.setText(5.0+"");
                    rating.setLayoutParams(layoutParams2);

                    ratingLayout.addView(star);
                    ratingLayout.addView(starPict);
                    ratingLayout.addView(starPict2);
                    ratingLayout.addView(starPict3);
                    ratingLayout.addView(starPict4);
                    ratingLayout.addView(rating);
                    linearLayout.addView(ratingLayout);
                    break;
            }

            String[] dateComponents = detailedRequest.placeDetails.reviews[i].time.toDate().toString().split(" ");
            TextView date = new TextView(this);
            date.setTextSize(13);
            date.setTextColor(getResources().getColor(R.color.SecondTextColor));
            date.setTypeface(date.getTypeface(),Typeface.ITALIC);
            LinearLayout.LayoutParams dateParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            dateParams.setMargins(20,10,0,0);
            date.setText(dateComponents[2] + " , " + dateComponents[1] + " , " + dateComponents[dateComponents.length-1]);
            date.setLayoutParams(dateParams);
            linearLayout.addView(date);

            TextView redLine = new TextView(this);
            LinearLayout.LayoutParams redLineParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,1);
            redLine.setBackgroundColor(getResources().getColor(R.color.YummyColor));
            redLine.setLayoutParams(redLineParams);
          //  linearLayout.addView(redLine);

            HorizontalLinearLayout.addView(linearLayout);

            TextView verticalRedLine = new TextView(this);
            LinearLayout.LayoutParams verticalRedLineParams = new LinearLayout.LayoutParams(1, ViewGroup.LayoutParams.MATCH_PARENT);
            verticalRedLineParams.setMargins(30,0,0,0);
            verticalRedLine.setBackgroundColor(getResources().getColor(R.color.YummyColor));
            verticalRedLine.setLayoutParams(verticalRedLineParams);

            HorizontalLinearLayout.addView(verticalRedLine);

            TextView reviewText = new TextView(this);
            reviewText.setTextColor(getResources().getColor(R.color.SecondTextColor));
            reviewText.setTextSize(13);
            reviewText.setTypeface(reviewText.getTypeface(),Typeface.ITALIC);
            LinearLayout.LayoutParams reviewTextParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            reviewTextParams.setMargins(40,30,0,30);
            reviewText.setText('"'+ detailedRequest.placeDetails.reviews[i].text+'"');
            reviewText.setLayoutParams(reviewTextParams);

            HorizontalLinearLayout.addView(reviewText);
            Vertical.addView(HorizontalLinearLayout);
            Vertical.addView(redLine);
        }

         review_text.addView(Vertical);
    }

    private void openingHours(){
        TextView Sunday = (TextView) findViewById(R.id.Sunday);
        Sunday.setText(detailedRequest.placeDetails.openingHours.weekdayText[0]);

        TextView Monday = (TextView) findViewById(R.id.Monday);
        Monday.setText(detailedRequest.placeDetails.openingHours.weekdayText[1]);

        TextView Tuesday = (TextView) findViewById(R.id.Tuesday);
        Tuesday.setText(detailedRequest.placeDetails.openingHours.weekdayText[2]);

        TextView Wednesday = (TextView) findViewById(R.id.Wednesday);
        Wednesday.setText(detailedRequest.placeDetails.openingHours.weekdayText[3]);

        TextView Thursday = (TextView) findViewById(R.id.Thursday);
        Thursday.setText(detailedRequest.placeDetails.openingHours.weekdayText[4]);

        TextView Friday = (TextView) findViewById(R.id.Friday);
        Friday.setText(detailedRequest.placeDetails.openingHours.weekdayText[5]);

        TextView Saturday = (TextView) findViewById(R.id.Saturday);
        Saturday.setText(detailedRequest.placeDetails.openingHours.weekdayText[6]);


    }
}
