package com.example.android.yummy.DataManager;

import com.example.android.yummy.MainActivities.Result;

/**
 * This class calls the GeoCoder class and makes sure what to do based on the results got from GeoCoder class.
 */
public class data {
	public static GeoCoder details;
    public static Boolean checker = false;
	/**
	 * The constructor calls the GeoCoder class with appropriate parameters.
	 * @param foods The food the user entered.
	 * @param origin User's location in the form of coordinates.
	 * @param city_name The name of the city / county where the user is currently in.
	 * @throws Exception Exception thrown for invalid requests to Google API and / or Yelp API.
	 */
	public data(String foods, String origin, String city_name) throws Exception{
		details = new GeoCoder(city_name , foods, origin, this);
	}
    /**
     * This method is called by the GeoCoder class after all the data are collected.
     * It executes a conditional statement based on whether the user entered a valid food or not.
     */
	public static void results(){
        if(checker == true){
            Result.EXIT();
        }
        else{
			Result.getter();}
		}
}
