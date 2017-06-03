package com.example.android.yummy.DataManager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.example.android.yummy.MainActivities.Result;
import com.example.android.yummy.MainActivities.Main2Activity;
import com.example.android.yummy.MainActivities.MainActivity;

/**
 * Created by amitb on 2017-05-12.
 */

public class AvoidingErrors extends AppCompatActivity {
    public AvoidingErrors(Result object){
        Main2Activity.checker =true;
        object.checker = true;
        Toast.makeText(Main2Activity.object, "ENTER A VALID FOOD NAME", Toast.LENGTH_LONG).show();
        object.onBackPressed();
    }
}
