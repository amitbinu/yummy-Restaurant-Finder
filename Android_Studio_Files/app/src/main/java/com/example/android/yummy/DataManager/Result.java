package com.example.android.yummy.DataManager;

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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.android.yummy.Backthread.AvoidingErrors;
import com.example.android.yummy.Backthread.photoRequest;
import com.example.android.yummy.MainActivities.Main2Activity;
import com.example.android.yummy.MainActivities.MainActivity;
import com.example.android.yummy.R;

import java.util.ArrayList;
import java.util.Stack;

import static com.example.android.yummy.MainActivities.MainActivity.COMMUNITY;

public class Result extends AppCompatActivity {
    public static Boolean checker = false;
    public static Context contextforme;
    private static ProgressBar progressBar;
    public static RelativeLayout realOne;
    public static data test;
    public static ArrayList<String> prices = new ArrayList<>();
    public static  Result object;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.object = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        TextView textView = (TextView) findViewById(R.id.Header);
        textView.setText("Restaurants near " + COMMUNITY);
        if(checker == true){super.onBackPressed();}
        realOne = (RelativeLayout) findViewById(R.id.RESULTrelative);
        checker = false;
        contextforme = this;
        progressBar = (ProgressBar) findViewById(R.id.progressBar2);
        dataCollector();
    }
    public static void dataCollector(){
        try{
            Stack<String> food = new Stack<>();
            food.push(Main2Activity.Message);
            Log.d("FOOD NAME:", Main2Activity.Message);
            test = new data(food, MainActivity.latitude + " " + MainActivity.longitude, COMMUNITY+", "+ MainActivity.CITY + ", " +MainActivity.STATE );
           // if(test.checker == true){super.onBackPressed();}
        }
        catch (Exception e){
            Log.e("ERROR", e.getMessage(), e);
        }
    }

    @Override
    public void onBackPressed(){
        this.finish();
      //  MainActivity.finalCall();
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }

    public static void EXIT(){
        Log.e("in", "EXITT");
        Main2Activity.checker = true;
        new AvoidingErrors(object);
    }

    private static photoRequest pictures;
    public static void getter() {
        pictures = new photoRequest(object);
    }

    public void finalRestaurants(){

        progressBar.setVisibility(View.GONE);
        RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.RESULTrelative);
        LinearLayout mainLinearLayout = new LinearLayout(contextforme);
        LinearLayout.LayoutParams mainlayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        mainlayoutParams.setMargins(10, 10, 0, 0);
        mainLinearLayout.setOrientation(LinearLayout.VERTICAL);
        for (int i = 0; i < test.restaurants.length; i++) {
            RelativeLayout relativeLayout1 = new RelativeLayout(this);
            relativeLayout1.setBackgroundColor(getResources().getColor(R.color.BackGroundPopular));
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(1000, 500);
            layoutParams.setMargins(0, 40, 0, 0);
            relativeLayout1.setLayoutParams(layoutParams);

            ImageView imageView = new ImageView(this);
            imageView.setId(i);
            imageView.setImageBitmap(resizer(test.restaurants[i].photos, 450, 350));
            RelativeLayout.LayoutParams layoutParams1 = new RelativeLayout.LayoutParams(450, 350);
            layoutParams1.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
            imageView.setLayoutParams(layoutParams1);

            TextView textView = new TextView(this);
            textView.setText(test.restaurants[i].restuarant_name);
            textView.setTextColor(Color.WHITE);
            textView.setTextSize(16);
            textView.setAllCaps(true);
            RelativeLayout.LayoutParams layoutParams2 = new RelativeLayout.LayoutParams(550, RelativeLayout.LayoutParams.WRAP_CONTENT);
            layoutParams2.setMargins(460, 0, 0, 0);
            layoutParams2.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            textView.setLayoutParams(layoutParams2);

            TextView textView2 = new TextView(this);
            textView2.setTextColor(getResources().getColor(R.color.SecondTextColor));
            textView2.setText(test.restaurants[i].address);
            textView2.setTextSize(16);
            RelativeLayout.LayoutParams layoutParams3 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            layoutParams3.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
            layoutParams3.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
            layoutParams3.setMargins(5, 0, 0, 0);
            textView2.setLayoutParams(layoutParams3);

            relativeLayout1.addView(imageView);
            relativeLayout1.addView(textView);
            relativeLayout1.addView(textView2);
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
    public static void setter(String value){
        prices.add(value);
    }
}
