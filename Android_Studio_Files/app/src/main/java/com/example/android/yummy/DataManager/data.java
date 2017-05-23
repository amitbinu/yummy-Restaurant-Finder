package com.example.android.yummy.DataManager;
import android.graphics.Bitmap;
import android.util.Log;

import java.util.Stack;

import com.google.maps.model.Photo;

public class data {
	public static GeoCoder details;
	private static String[] temp;
    private static Stack<String> foods;
	public data(Stack<String> foods, String origin, String city_name) throws Exception{
        this.foods = foods;
		temp = new String[foods.size()];
		for (int i =0; i < foods.size(); i++){
			temp[i] = foods.get(i);
		}
		details = new GeoCoder(city_name , foods, origin, this);
	}

    public static Boolean checker = false;
	public static void results(){
        if(checker == true){
			Log.e("IN","the if statement");
            Result.EXIT();
        }
        else{
			Log.e("IN", "the else statement");
			Result.getter();}
		}
		
	
}
