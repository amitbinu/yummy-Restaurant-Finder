package com.example.android.yummy.MainActivities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.example.android.yummy.apiCalls.photoRequest;
import com.example.android.yummy.DataManager.data;
import com.example.android.yummy.DataManager.sort;
import com.example.android.yummy.R;
import java.util.ArrayList;
import static com.example.android.yummy.MainActivities.MainActivity.COMMUNITY;

public class Result extends AppCompatActivity {
    public static Boolean checker = false;
    public static Context contextforme;
    private static ProgressBar progressBar;
    public static RelativeLayout realOne;
    public static data test;
    public static ArrayList<String> prices = new ArrayList<>();
    public static Result object;
    private static Spinner spinner;
    private static TextView updateForUser;
    private static ArrayList<Integer[]> typesOfStars = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ResultRan = true;
        this.object = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        TextView textView = (TextView) findViewById(R.id.Header);
        textView.setText("Restaurants near " + COMMUNITY);
        if (checker == true) {
            super.onBackPressed();
        }
        realOne = (RelativeLayout) findViewById(R.id.RESULTrelative);
        checker = false;
        contextforme = this;
        progressBar = (ProgressBar) findViewById(R.id.progressBar2);
        spinner = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> arrayAdapter = ArrayAdapter.createFromResource(this, R.array.SpinnerSort, R.layout.spinner_list);
        arrayAdapter.setDropDownViewResource(R.layout.spinner_drop_down);
        spinner.setAdapter(arrayAdapter);
        updateForUser = (TextView) findViewById(R.id.update1);
        if(test==null){
        dataCollector();}
        else{
            finalRestaurants();
        }
    }

    public static boolean ResultRan = false;

    public static void dataCollector() {
        try {
            test = new data(Main2Activity.Message, MainActivity.latitude + " " + MainActivity.longitude, COMMUNITY + ", " + MainActivity.CITY + ", " + MainActivity.STATE);
        } catch (Exception e) {
        }
    }

    @Override
    public void onBackPressed() {
        this.finish();
        Intent intent = new Intent(this, Main2Activity.class);
        startActivity(intent);
    }

    public static void updating(String message) {
        updateForUser.setText(message);
    }

    public static void EXIT() {
        Main2Activity.checker = true;
        new com.example.android.yummy.DataManager.AvoidingErrors(object);
    }

    private static photoRequest pictures;

    public static void getter() {
        pictures = new photoRequest(object);
    }

    public void finalRestaurants() {
        int spacingBetweenStars = 75;
        int mTopStars =480;
        String text = spinner.getSelectedItem().toString();
        final sort sortedResult = new sort(test, text);

        if (sortedResult.checker == false) {
            Toast.makeText(object, "SOME PRICES ARE NOT AVAILABLE", Toast.LENGTH_LONG).show();
        }
        updateForUser.setVisibility(View.GONE);
        progressBar.setVisibility(View.GONE);
        RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.RESULTrelative);
        LinearLayout mainLinearLayout = new LinearLayout(contextforme);
        LinearLayout.LayoutParams mainlayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        mainlayoutParams.setMargins(10, 10, 0, 0);
        mainLinearLayout.setOrientation(LinearLayout.VERTICAL);

        TextView textView = (TextView) findViewById(R.id.dot1);
        textView.setVisibility(View.GONE);
        textView = (TextView) findViewById(R.id.dot2);
        textView.setVisibility(View.GONE);
        textView = (TextView) findViewById(R.id.dot3);
        textView.setVisibility(View.GONE);

        for (int i = 0; i < test.details.pictures.pictures1.size(); i++) {
            final RelativeLayout relativeLayout1 = new RelativeLayout(this);
            relativeLayout1.setBackgroundColor(getResources().getColor(R.color.BackGroundPopular));
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,570);
            layoutParams.setMargins(0,40,0,0);
            relativeLayout1.setLayoutParams(layoutParams);
            relativeLayout1.setId(i);
            relativeLayout1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(object, RestaurantActivity.class);
                    RestaurantActivity.placeId = sortedResult.Sortedrestaurants.get(relativeLayout1.getId()).restaurantId;
                    RestaurantActivity.ratingText =sortedResult.Sortedrestaurants.get(relativeLayout1.getId()).rating;
                    RestaurantActivity.distanceText = sortedResult.Sortedrestaurants.get(relativeLayout1.getId()).distance;
                    RestaurantActivity.timeText = sortedResult.Sortedrestaurants.get(relativeLayout1.getId()).time;
                    RestaurantActivity.typesOfStars = typesOfStars.get(relativeLayout1.getId());
                    RestaurantActivity.RestaurantName = sortedResult.Sortedrestaurants.get(relativeLayout1.getId()).restuarant_name;
                    RestaurantActivity.RestaurantAddress = sortedResult.Sortedrestaurants.get(relativeLayout1.getId()).address;
                    startActivity(intent);
                }
            });

            ImageView imageView = new ImageView(this);
            imageView.setImageBitmap(resizer(test.details.pictures.pictures1.get(i),450,350));
            RelativeLayout.LayoutParams layoutParams1 = new RelativeLayout.LayoutParams(450,350);
            layoutParams1.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
            imageView.setLayoutParams(layoutParams1);

            textView = new TextView(this);
            textView.setText(sortedResult.Sortedrestaurants.get(i).restuarant_name);
            textView.setTextColor(Color.WHITE);
            textView.setTextSize(17);
            textView.setAllCaps(true);
            RelativeLayout.LayoutParams layoutParams2 = new RelativeLayout.LayoutParams(550,RelativeLayout.LayoutParams.WRAP_CONTENT);
            layoutParams2.setMargins(460,0,0,0);
            textView.setLayoutParams(layoutParams2);

            TextView textView2 = new TextView(this);
            textView2.setTextColor(getResources().getColor(R.color.SecondTextColor));
            textView2.setText(sortedResult.Sortedrestaurants.get(i).address);
            textView2.setTextSize(16);
            RelativeLayout.LayoutParams layoutParams3 = new RelativeLayout.LayoutParams(700,RelativeLayout.LayoutParams.WRAP_CONTENT);
            layoutParams3.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
            //layoutParams3.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
            layoutParams3.setMargins(5, 360, 0, 0);
            textView2.setLayoutParams(layoutParams3);

            ImageView imageView1 = new ImageView(this);
            RelativeLayout.LayoutParams layoutParams4 = new RelativeLayout.LayoutParams(80,80);
            layoutParams4.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            layoutParams4.setMargins(0,40,200,0);
            imageView1.setLayoutParams(layoutParams4);

            TextView open = new TextView(this);
            RelativeLayout.LayoutParams layoutParams5 = new RelativeLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            open.setTextSize(16);
            open.setTextColor(getResources().getColor(R.color.SecondTextColor));
            layoutParams5.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            try{
                if(sortedResult.Sortedrestaurants.get(i).permanentlyClosed){
                    imageView1.setImageResource(R.drawable.permanentlyclosed);
                    layoutParams5.setMargins(0,40,20,0);
                    open.setLayoutParams(layoutParams5);
                    open.setText("Closed");
                }
                else{
                    if(sortedResult.Sortedrestaurants.get(i).open){
                        imageView1.setImageResource(R.drawable.open);
                        layoutParams5.setMargins(0,40,35 ,0);
                        open.setLayoutParams(layoutParams5);
                        open.setText("Open");
                    }
                    else{
                        imageView1.setImageResource(R.drawable.closed);
                        layoutParams5.setMargins(0,40,20,0);
                        open.setLayoutParams(layoutParams5);
                        open.setText("Closed");
                    }
                }
            }
            catch (Exception e){
                imageView1.setImageResource(R.drawable.permanentlyclosed);
                layoutParams5.setMargins(0,40,35,0);
                open.setLayoutParams(layoutParams5);
                open.setText("N/A");
            }

            ImageView distanceImage = new ImageView(this);
            distanceImage.setImageResource(R.drawable.distance);
            RelativeLayout.LayoutParams layoutParams6 = new RelativeLayout.LayoutParams(100,100);
            layoutParams6.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            layoutParams6.setMargins(0,190,230,0);
            distanceImage.setLayoutParams(layoutParams6);

            TextView distance = new TextView(this);
            distance.setText(sortedResult.Sortedrestaurants.get(i).distance + " " + Main2Activity.distanceUnit);
            RelativeLayout.LayoutParams layoutParams7 = new RelativeLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            distance.setTextSize(16);
            distance.setTextColor(getResources().getColor(R.color.SecondTextColor));
            layoutParams7.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            layoutParams7.setMargins(0,206,20,0);
            distance.setLayoutParams(layoutParams7);

            ImageView timeImage = new ImageView(this);
            timeImage.setImageResource(R.drawable.time);
            RelativeLayout.LayoutParams layoutParams8 = new RelativeLayout.LayoutParams(100, 100);
            layoutParams8.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            layoutParams8.setMargins(0,350,230,0);
            timeImage.setLayoutParams(layoutParams8);

            TextView time = new TextView(this);
            time.setText(sortedResult.Sortedrestaurants.get(i).time+ " min");
            RelativeLayout.LayoutParams layoutParams9 = new RelativeLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            time.setTextSize(16);
            time.setTextColor(getResources().getColor(R.color.SecondTextColor));
            layoutParams9.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            layoutParams9.setMargins(0,366,20,0);
            time.setLayoutParams(layoutParams9);

            int numberOfStars = numberOfStars(sortedResult.Sortedrestaurants.get(i).rating);
            Double rating =  sortedResult.Sortedrestaurants.get(i).rating;
            TextView ratingText = new TextView(this);
            ratingText.setText(rating+"");
            ratingText.setTextSize(16);
            ratingText.setTextColor(getResources().getColor(R.color.SecondTextColor));
            RelativeLayout.LayoutParams layoutParams20 = new RelativeLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
            layoutParams20.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            layoutParams20.setMargins(0,480,20,0);
            ratingText.setLayoutParams(layoutParams20);

            relativeLayout1.addView(ratingText);


            Log.e("Number of Stars", numberOfStars+"");
            Integer[] typesofstars = new Integer[5];
            switch (numberOfStars){
                case 0:
                    Log.e("Case #", "0");
                    ImageView star = new ImageView(this);
                    star.setImageResource(R.drawable.quarterstar);
                    RelativeLayout.LayoutParams layoutParams12 = new RelativeLayout.LayoutParams(100,100);
                    layoutParams12.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                    layoutParams12.setMargins(0,mTopStars,spacingBetweenStars*4,0);

                    ImageView starPict = new ImageView(this);
                    starPict.setImageResource(R.drawable.quarterstar);
                    RelativeLayout.LayoutParams emptyStarLayout = new RelativeLayout.LayoutParams(100,100);
                    emptyStarLayout.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                    emptyStarLayout.setMargins(0,mTopStars,100+spacingBetweenStars*3,0);
                    starPict.setLayoutParams(emptyStarLayout);

                    ImageView starPict2 = new ImageView(this);
                    starPict2.setImageResource(R.drawable.quarterstar);
                    RelativeLayout.LayoutParams emptyStarLayout2 = new RelativeLayout.LayoutParams(100,100);
                    emptyStarLayout2.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                    emptyStarLayout2.setMargins(0,mTopStars,100+spacingBetweenStars*2,0);
                    starPict2.setLayoutParams(emptyStarLayout2);

                    ImageView starPict3 = new ImageView(this);
                    starPict3.setImageResource(R.drawable.quarterstar);
                    RelativeLayout.LayoutParams emptyStarLayout3 = new RelativeLayout.LayoutParams(100,100);
                    emptyStarLayout3.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                    emptyStarLayout3.setMargins(0,mTopStars,100+spacingBetweenStars,0);
                    starPict3.setLayoutParams(emptyStarLayout3);

                    ImageView starPict4 = new ImageView(this);
                    starPict4.setImageResource(R.drawable.quarterstar);
                    RelativeLayout.LayoutParams emptyStarLayout4 = new RelativeLayout.LayoutParams(100,100);
                    emptyStarLayout4.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                    emptyStarLayout4.setMargins(0,mTopStars,100,0);
                    starPict4.setLayoutParams(emptyStarLayout4);

                    relativeLayout1.addView(star);
                    relativeLayout1.addView(starPict);
                    relativeLayout1.addView(starPict2);
                    relativeLayout1.addView(starPict3);
                    relativeLayout1.addView(starPict4);
                    typesofstars[0] = 0;
                    typesofstars[1] = 0;
                    typesofstars[2] = 0;
                    typesofstars[3] = 0;
                    typesofstars[4] = 0;
                    typesOfStars.add(typesofstars);
                    break;
                case 1:
                    Log.e("Case #", "1");
                    ImageView star1 = new ImageView(this);
                    RelativeLayout.LayoutParams layoutParams10 = new RelativeLayout.LayoutParams(100,100);
                    if(rating >= 0.75){
                        star1.setImageResource(R.drawable.fullstar);
                        layoutParams10.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                        layoutParams10.setMargins(0,mTopStars,100+spacingBetweenStars*4,0);
                        star1.setLayoutParams(layoutParams10);

                        starPict = new ImageView(this);
                        starPict.setImageResource(R.drawable.quarterstar);
                        emptyStarLayout = new RelativeLayout.LayoutParams(100,100);
                        emptyStarLayout.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                        emptyStarLayout.setMargins(0,mTopStars,100+spacingBetweenStars*3,0);
                        starPict.setLayoutParams(emptyStarLayout);

                        starPict2 = new ImageView(this);
                        starPict2.setImageResource(R.drawable.quarterstar);
                        emptyStarLayout2 = new RelativeLayout.LayoutParams(100,100);
                        emptyStarLayout2.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                        emptyStarLayout2.setMargins(0,mTopStars,100+spacingBetweenStars*2,0);
                        starPict2.setLayoutParams(emptyStarLayout2);

                        starPict3 = new ImageView(this);
                        starPict3.setImageResource(R.drawable.quarterstar);
                        emptyStarLayout3 = new RelativeLayout.LayoutParams(100,100);
                        emptyStarLayout3.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                        emptyStarLayout3.setMargins(0,mTopStars,100+spacingBetweenStars,0);
                        starPict3.setLayoutParams(emptyStarLayout3);

                        starPict4 = new ImageView(this);
                        starPict4.setImageResource(R.drawable.quarterstar);
                        emptyStarLayout4 = new RelativeLayout.LayoutParams(100,100);
                        emptyStarLayout4.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                        emptyStarLayout4.setMargins(0,mTopStars,100,0);
                        starPict4.setLayoutParams(emptyStarLayout4);

                        relativeLayout1.addView(starPict);
                        relativeLayout1.addView(starPict2);
                        relativeLayout1.addView(starPict3);
                        relativeLayout1.addView(starPict4);
                        relativeLayout1.addView(star1);
                        typesofstars[0] = 2;
                        typesofstars[1] = 0;
                        typesofstars[2] = 0;
                        typesofstars[3] = 0;
                        typesofstars[4] = 0;
                        typesOfStars.add(typesofstars);
                    }
                    else{
                        star1.setImageResource(R.drawable.halfstar);
                        layoutParams10.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                        layoutParams10.setMargins(0,mTopStars,100+spacingBetweenStars*4,0);
                        star1.setLayoutParams(layoutParams10);

                        starPict = new ImageView(this);
                        starPict.setImageResource(R.drawable.quarterstar);
                        emptyStarLayout = new RelativeLayout.LayoutParams(100,100);
                        emptyStarLayout.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                        emptyStarLayout.setMargins(0,mTopStars,100+spacingBetweenStars*3,0);
                        starPict.setLayoutParams(emptyStarLayout);

                        starPict2 = new ImageView(this);
                        starPict2.setImageResource(R.drawable.quarterstar);
                        emptyStarLayout2 = new RelativeLayout.LayoutParams(100,100);
                        emptyStarLayout2.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                        emptyStarLayout2.setMargins(0,mTopStars,100+spacingBetweenStars*2,0);
                        starPict2.setLayoutParams(emptyStarLayout2);

                        starPict3 = new ImageView(this);
                        starPict3.setImageResource(R.drawable.quarterstar);
                        emptyStarLayout3 = new RelativeLayout.LayoutParams(100,100);
                        emptyStarLayout3.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                        emptyStarLayout3.setMargins(0,mTopStars,100+spacingBetweenStars,0);
                        starPict3.setLayoutParams(emptyStarLayout3);

                        starPict4 = new ImageView(this);
                        starPict4.setImageResource(R.drawable.quarterstar);
                        emptyStarLayout4 = new RelativeLayout.LayoutParams(100,100);
                        emptyStarLayout4.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                        emptyStarLayout4.setMargins(0,mTopStars,100,0);
                        starPict4.setLayoutParams(emptyStarLayout4);

                        relativeLayout1.addView(starPict);
                        relativeLayout1.addView(starPict2);
                        relativeLayout1.addView(starPict3);
                        relativeLayout1.addView(starPict4);
                        relativeLayout1.addView(star1);
                        typesofstars[0] = 1;
                        typesofstars[1] = 0;
                        typesofstars[2] = 0;
                        typesofstars[3] = 0;
                        typesofstars[4] = 0;
                        typesOfStars.add(typesofstars);
                    }
                    break;

                case 2:
                    Log.e("Case #", "2");
                    ImageView star1_2 = new ImageView(this);
                    RelativeLayout.LayoutParams layoutParams13 = new RelativeLayout.LayoutParams(100,100);
                    if(rating >= 1.75){
                        star1_2.setImageResource(R.drawable.fullstar);
                        layoutParams13.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                        layoutParams13.setMargins(0,mTopStars,100+spacingBetweenStars*4,0);
                        star1_2.setLayoutParams(layoutParams13);

                        ImageView star2 = new ImageView(this);
                        RelativeLayout.LayoutParams layoutParams11= new RelativeLayout.LayoutParams(100,100);
                        layoutParams11.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                        layoutParams11.setMargins(0,mTopStars,100+spacingBetweenStars*3,0);
                        star2.setImageResource(R.drawable.fullstar);
                        star2.setLayoutParams(layoutParams11);

                        starPict = new ImageView(this);
                        starPict.setImageResource(R.drawable.quarterstar);
                        emptyStarLayout = new RelativeLayout.LayoutParams(100,100);
                        emptyStarLayout.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                        emptyStarLayout.setMargins(0,mTopStars,100+spacingBetweenStars*2,0);
                        starPict.setLayoutParams(emptyStarLayout);

                        starPict2 = new ImageView(this);
                        starPict2.setImageResource(R.drawable.quarterstar);
                        emptyStarLayout2 = new RelativeLayout.LayoutParams(100,100);
                        emptyStarLayout2.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                        emptyStarLayout2.setMargins(0,mTopStars,100+spacingBetweenStars,0);
                        starPict2.setLayoutParams(emptyStarLayout2);

                        starPict3 = new ImageView(this);
                        starPict3.setImageResource(R.drawable.quarterstar);
                        emptyStarLayout3 = new RelativeLayout.LayoutParams(100,100);
                        emptyStarLayout3.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                        emptyStarLayout3.setMargins(0,mTopStars,100,0);
                        starPict3.setLayoutParams(emptyStarLayout3);

                        relativeLayout1.addView(starPict);
                        relativeLayout1.addView(starPict2);
                        relativeLayout1.addView(starPict3);
                        relativeLayout1.addView(star1_2);
                        relativeLayout1.addView(star2);

                        typesofstars[0] = 2;
                        typesofstars[1] = 2;
                        typesofstars[2] = 0;
                        typesofstars[3] = 0;
                        typesofstars[4] = 0;
                        typesOfStars.add(typesofstars);
                    }
                    else{
                        star1_2.setImageResource(R.drawable.fullstar);
                        layoutParams13.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                        layoutParams13.setMargins(0,mTopStars,100+spacingBetweenStars*4,0);
                        star1_2.setLayoutParams(layoutParams13);

                        ImageView star2 = new ImageView(this);
                        star2.setImageResource(R.drawable.halfstar);
                        RelativeLayout.LayoutParams layoutParams11= new RelativeLayout.LayoutParams(100,100);
                        layoutParams11.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                        layoutParams11.setMargins(0,mTopStars,100+spacingBetweenStars*3,0);
                        star2.setLayoutParams(layoutParams11);

                        starPict = new ImageView(this);
                        starPict.setImageResource(R.drawable.quarterstar);
                        emptyStarLayout = new RelativeLayout.LayoutParams(100,100);
                        emptyStarLayout.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                        emptyStarLayout.setMargins(0,mTopStars,100+spacingBetweenStars*2,0);
                        starPict.setLayoutParams(emptyStarLayout);

                        starPict2 = new ImageView(this);
                        starPict2.setImageResource(R.drawable.quarterstar);
                        emptyStarLayout2 = new RelativeLayout.LayoutParams(100,100);
                        emptyStarLayout2.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                        emptyStarLayout2.setMargins(0,mTopStars,100+spacingBetweenStars,0);
                        starPict2.setLayoutParams(emptyStarLayout2);

                        starPict3 = new ImageView(this);
                        starPict3.setImageResource(R.drawable.quarterstar);
                        emptyStarLayout3 = new RelativeLayout.LayoutParams(100,100);
                        emptyStarLayout3.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                        emptyStarLayout3.setMargins(0,mTopStars,100,0);
                        starPict3.setLayoutParams(emptyStarLayout3);

                        relativeLayout1.addView(starPict);
                        relativeLayout1.addView(starPict2);
                        relativeLayout1.addView(starPict3);
                        relativeLayout1.addView(star1_2);
                        relativeLayout1.addView(star2);

                        typesofstars[0] = 2;
                        typesofstars[1] = 1;
                        typesofstars[2] = 0;
                        typesofstars[3] = 0;
                        typesofstars[4] = 0;
                        typesOfStars.add(typesofstars);
                    }
                    break;

                case 3:
                    Log.e("Case #", "3");
                    ImageView star1_3 = new ImageView(this);
                    RelativeLayout.LayoutParams layoutParams15 = new RelativeLayout.LayoutParams(100,100);
                    if(rating >= 2.75){
                        star1_3.setImageResource(R.drawable.fullstar);
                        layoutParams15.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                        layoutParams15.setMargins(0,mTopStars,100+spacingBetweenStars*4,0);
                        star1_3.setLayoutParams(layoutParams15);

                        ImageView star2 = new ImageView(this);
                        RelativeLayout.LayoutParams layoutParams11= new RelativeLayout.LayoutParams(100,100);
                        layoutParams11.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                        layoutParams11.setMargins(0,mTopStars,100+spacingBetweenStars*3,0);
                        star2.setImageResource(R.drawable.fullstar);
                        star2.setLayoutParams(layoutParams11);


                        ImageView star3 = new ImageView(this);
                        star3.setImageResource(R.drawable.fullstar);
                        RelativeLayout.LayoutParams layoutParams14 = new RelativeLayout.LayoutParams(100,100);
                        layoutParams14.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                        layoutParams14.setMargins(0,mTopStars,100+spacingBetweenStars*2,0);
                        star3.setLayoutParams(layoutParams14);

                        starPict = new ImageView(this);
                        starPict.setImageResource(R.drawable.quarterstar);
                        emptyStarLayout = new RelativeLayout.LayoutParams(100,100);
                        emptyStarLayout.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                        emptyStarLayout.setMargins(0,mTopStars,100+spacingBetweenStars,0);
                        starPict.setLayoutParams(emptyStarLayout);

                        starPict2 = new ImageView(this);
                        starPict2.setImageResource(R.drawable.quarterstar);
                        emptyStarLayout2 = new RelativeLayout.LayoutParams(100,100);
                        emptyStarLayout2.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                        emptyStarLayout2.setMargins(0,mTopStars,100,0);
                        starPict2.setLayoutParams(emptyStarLayout2);

                        relativeLayout1.addView(starPict);
                        relativeLayout1.addView(starPict2);
                        relativeLayout1.addView(star3);
                        relativeLayout1.addView(star1_3);
                        relativeLayout1.addView(star2);

                        typesofstars[0] = 2;
                        typesofstars[1] = 2;
                        typesofstars[2] = 2;
                        typesofstars[3] = 0;
                        typesofstars[4] = 0;
                        typesOfStars.add(typesofstars);
                    }
                    else {
                        star1_3.setImageResource(R.drawable.fullstar);
                        layoutParams15.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                        layoutParams15.setMargins(0,mTopStars,100+spacingBetweenStars*4,0);
                        star1_3.setLayoutParams(layoutParams15);

                        ImageView star2 = new ImageView(this);
                        RelativeLayout.LayoutParams layoutParams11= new RelativeLayout.LayoutParams(100,100);
                        layoutParams11.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                        layoutParams11.setMargins(0,mTopStars,100+spacingBetweenStars*3,0);
                        star2.setImageResource(R.drawable.fullstar);
                        star2.setLayoutParams(layoutParams11);

                        ImageView star3 = new ImageView(this);
                        star3.setImageResource(R.drawable.halfstar);
                        RelativeLayout.LayoutParams layoutParams14 = new RelativeLayout.LayoutParams(100,100);
                        layoutParams14.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                        layoutParams14.setMargins(0,mTopStars,100+spacingBetweenStars*2,0);
                        star3.setLayoutParams(layoutParams14);

                        ImageView star4 = new ImageView(this);
                        star4.setImageResource(R.drawable.quarterstar);
                        RelativeLayout.LayoutParams layoutParams16 = new RelativeLayout.LayoutParams(100,100);
                        layoutParams16.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                        layoutParams16.setMargins(0,mTopStars,100+spacingBetweenStars,0);
                        star4.setLayoutParams(layoutParams16);

                        starPict = new ImageView(this);
                        starPict.setImageResource(R.drawable.quarterstar);
                        emptyStarLayout = new RelativeLayout.LayoutParams(100,100);
                        emptyStarLayout.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                        emptyStarLayout.setMargins(0,mTopStars,100,0);
                        starPict.setLayoutParams(emptyStarLayout);

                        relativeLayout1.addView(starPict);
                        relativeLayout1.addView(star4);
                        relativeLayout1.addView(star3);
                        relativeLayout1.addView(star1_3);
                        relativeLayout1.addView(star2);

                        typesofstars[0] = 2;
                        typesofstars[1] = 2;
                        typesofstars[2] = 2;
                        typesofstars[3] = 1;
                        typesofstars[4] = 0;
                        typesOfStars.add(typesofstars);
                    }
                    break;
                case 4 :
                    Log.e("Case #", "4");
                    ImageView star1_4 = new ImageView(this);
                    RelativeLayout.LayoutParams layoutParams17 = new RelativeLayout.LayoutParams(100,100);
                    if(rating >= 3.75){
                        star1_4.setImageResource(R.drawable.fullstar);
                        layoutParams17.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                        layoutParams17.setMargins(0,mTopStars,100+spacingBetweenStars*4,0);
                        star1_4.setLayoutParams(layoutParams17);

                        ImageView star2 = new ImageView(this);
                        RelativeLayout.LayoutParams layoutParams11= new RelativeLayout.LayoutParams(100,100);
                        layoutParams11.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                        layoutParams11.setMargins(0,mTopStars,100+spacingBetweenStars*3,0);
                        star2.setImageResource(R.drawable.fullstar);
                        star2.setLayoutParams(layoutParams11);

                        ImageView star3 = new ImageView(this);
                        star3.setImageResource(R.drawable.fullstar);
                        RelativeLayout.LayoutParams layoutParams14 = new RelativeLayout.LayoutParams(100,100);
                        layoutParams14.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                        layoutParams14.setMargins(0,mTopStars,100+spacingBetweenStars*2,0);
                        star3.setLayoutParams(layoutParams14);

                        ImageView star4 = new ImageView(this);
                        star4.setImageResource(R.drawable.fullstar);
                        RelativeLayout.LayoutParams layoutParams16 = new RelativeLayout.LayoutParams(100,100);
                        layoutParams16.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                        layoutParams16.setMargins(0,mTopStars,100+spacingBetweenStars,0);
                        star4.setLayoutParams(layoutParams16);

                        starPict = new ImageView(this);
                        starPict.setImageResource(R.drawable.quarterstar);
                        emptyStarLayout = new RelativeLayout.LayoutParams(100,100);
                        emptyStarLayout.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                        emptyStarLayout.setMargins(0,mTopStars,100,0);
                        starPict.setLayoutParams(emptyStarLayout);

                        relativeLayout1.addView(starPict);
                        relativeLayout1.addView(star4);
                        relativeLayout1.addView(star3);
                        relativeLayout1.addView(star1_4);
                        relativeLayout1.addView(star2);

                        typesofstars[0] = 2;
                        typesofstars[1] = 2;
                        typesofstars[2] = 2;
                        typesofstars[3] = 2;
                        typesofstars[4] = 0;
                        typesOfStars.add(typesofstars);
                    }
                    else {
                        star1_4.setImageResource(R.drawable.fullstar);
                        layoutParams17.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                        layoutParams17.setMargins(0,mTopStars,100+spacingBetweenStars*4,0);
                        star1_4.setLayoutParams(layoutParams17);

                        ImageView star2 = new ImageView(this);
                        RelativeLayout.LayoutParams layoutParams11= new RelativeLayout.LayoutParams(100,100);
                        layoutParams11.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                        layoutParams11.setMargins(0,mTopStars,100+spacingBetweenStars*3,0);
                        star2.setImageResource(R.drawable.fullstar);
                        star2.setLayoutParams(layoutParams11);

                        ImageView star3 = new ImageView(this);
                        star3.setImageResource(R.drawable.fullstar);
                        RelativeLayout.LayoutParams layoutParams14 = new RelativeLayout.LayoutParams(100,100);
                        layoutParams14.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                        layoutParams14.setMargins(0,mTopStars,100+spacingBetweenStars*2,0);
                        star3.setLayoutParams(layoutParams14);

                        ImageView star4 = new ImageView(this);
                        star4.setImageResource(R.drawable.halfstar);
                        RelativeLayout.LayoutParams layoutParams16 = new RelativeLayout.LayoutParams(100,100);
                        layoutParams16.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                        layoutParams16.setMargins(0,mTopStars,100+spacingBetweenStars,0);
                        star4.setLayoutParams(layoutParams16);

                        starPict = new ImageView(this);
                        starPict.setImageResource(R.drawable.quarterstar);
                        emptyStarLayout = new RelativeLayout.LayoutParams(100,100);
                        emptyStarLayout.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                        emptyStarLayout.setMargins(0,mTopStars,100,0);
                        starPict.setLayoutParams(emptyStarLayout);

                        relativeLayout1.addView(starPict);
                        relativeLayout1.addView(star4);
                        relativeLayout1.addView(star3);
                        relativeLayout1.addView(star1_4);
                        relativeLayout1.addView(star2);

                        typesofstars[0] = 2;
                        typesofstars[1] = 2;
                        typesofstars[2] = 2;
                        typesofstars[3] = 1;
                        typesofstars[4] = 0;
                        typesOfStars.add(typesofstars);
                    }
                    break;
                case 5 :
                    Log.e("Case #", "5");
                    if(rating < 4.75){
                        ImageView star1_5 = new ImageView(this);
                        RelativeLayout.LayoutParams layoutParams19 = new RelativeLayout.LayoutParams(100,100);
                        star1_5.setImageResource(R.drawable.fullstar);
                        layoutParams19.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                        layoutParams19.setMargins(00,mTopStars,100+spacingBetweenStars*4,0);
                        star1_5.setLayoutParams(layoutParams19);

                        ImageView star2 = new ImageView(this);
                        RelativeLayout.LayoutParams layoutParams11= new RelativeLayout.LayoutParams(100,100);
                        layoutParams11.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                        layoutParams11.setMargins(0,mTopStars,100+spacingBetweenStars*3,0);
                        star2.setImageResource(R.drawable.fullstar);
                        star2.setLayoutParams(layoutParams11);

                        ImageView star3 = new ImageView(this);
                        star3.setImageResource(R.drawable.fullstar);
                        RelativeLayout.LayoutParams layoutParams14 = new RelativeLayout.LayoutParams(100,100);
                        layoutParams14.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                        layoutParams14.setMargins(0,mTopStars,100+spacingBetweenStars*2,0);
                        star3.setLayoutParams(layoutParams14);

                        ImageView star4 = new ImageView(this);
                        star4.setImageResource(R.drawable.fullstar);
                        RelativeLayout.LayoutParams layoutParams16 = new RelativeLayout.LayoutParams(100,100);
                        layoutParams16.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                        layoutParams16.setMargins(0,mTopStars,100+spacingBetweenStars,0);
                        star4.setLayoutParams(layoutParams16);

                        ImageView star5 = new ImageView(this);
                        star5.setImageResource(R.drawable.halfstar);
                        RelativeLayout.LayoutParams layoutParams18 = new RelativeLayout.LayoutParams(100,100);
                        layoutParams18.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                        layoutParams18.setMargins(0,mTopStars,100,0);
                        star5.setLayoutParams(layoutParams18);

                        relativeLayout1.addView(star5);
                        relativeLayout1.addView(star4);
                        relativeLayout1.addView(star3);
                        relativeLayout1.addView(star1_5);
                        relativeLayout1.addView(star2);

                        typesofstars[0] = 2;
                        typesofstars[1] = 2;
                        typesofstars[2] = 2;
                        typesofstars[3] = 2;
                        typesofstars[4] = 1;
                        typesOfStars.add(typesofstars);
                    }
                    else{
                        ImageView star1_5 = new ImageView(this);
                        RelativeLayout.LayoutParams layoutParams19 = new RelativeLayout.LayoutParams(100,100);
                        star1_5.setImageResource(R.drawable.fullstar);
                        layoutParams19.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                        layoutParams19.setMargins(0,mTopStars,100+spacingBetweenStars*4,0);
                        star1_5.setLayoutParams(layoutParams19);

                        ImageView star2 = new ImageView(this);
                        RelativeLayout.LayoutParams layoutParams11= new RelativeLayout.LayoutParams(100,100);
                        layoutParams11.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                        layoutParams11.setMargins(0,mTopStars,100+spacingBetweenStars*3,0);
                        star2.setImageResource(R.drawable.fullstar);
                        star2.setLayoutParams(layoutParams11);

                        ImageView star3 = new ImageView(this);
                        star3.setImageResource(R.drawable.fullstar);
                        RelativeLayout.LayoutParams layoutParams14 = new RelativeLayout.LayoutParams(100,100);
                        layoutParams14.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                        layoutParams14.setMargins(0,mTopStars,100+spacingBetweenStars*2,0);
                        star3.setLayoutParams(layoutParams14);

                        ImageView star4 = new ImageView(this);
                        star4.setImageResource(R.drawable.fullstar);
                        RelativeLayout.LayoutParams layoutParams16 = new RelativeLayout.LayoutParams(100,100);
                        layoutParams16.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                        layoutParams16.setMargins(0,mTopStars,100+spacingBetweenStars,0);
                        star4.setLayoutParams(layoutParams16);

                        ImageView star5 = new ImageView(this);
                        star5.setImageResource(R.drawable.fullstar);
                        RelativeLayout.LayoutParams layoutParams18 = new RelativeLayout.LayoutParams(100,100);
                        layoutParams18.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                        layoutParams18.setMargins(0,mTopStars,100,0);
                        star5.setLayoutParams(layoutParams18);

                        relativeLayout1.addView(star5);
                        relativeLayout1.addView(star4);
                        relativeLayout1.addView(star3);
                        relativeLayout1.addView(star1_5);
                        relativeLayout1.addView(star2);

                        typesofstars[0] = 2;
                        typesofstars[1] = 2;
                        typesofstars[2] = 2;
                        typesofstars[3] = 2;
                        typesofstars[4] = 2;
                        typesOfStars.add(typesofstars);
                    }
                    break;

            }
            relativeLayout1.addView(time);
            relativeLayout1.addView(timeImage);
            relativeLayout1.addView(distanceImage);
            relativeLayout1.addView(distance);
            relativeLayout1.addView(open);
            relativeLayout1.addView(imageView1);
            relativeLayout1.addView(imageView);
            relativeLayout1.addView(textView);
            relativeLayout1.addView(textView2);
            mainLinearLayout.addView(relativeLayout1);
        }
        relativeLayout.addView(mainLinearLayout, mainlayoutParams);
    }

    private static int numberOfStars(double rating){
        if(0 <= rating && rating < 0.25){
            return 0;
        }
        if(0.25 <= rating && rating <= 1.25){
            return 1;
        }
        if(1.25 < rating && rating <= 2.25){
            return 2;
        }
        if(2.25 < rating && rating <= 3.25){
            return 3;
        }
        if(3.25 < rating && rating <= 4.25){
            return 4;
        }
        else{
            return 5;
        }
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
}
