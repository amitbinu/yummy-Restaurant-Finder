package com.example.android.yummy.DataManager;

import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.ArrayList;

import java.util.Stack;
import com.example.android.yummy.MainActivities.MainActivity;
import com.example.android.yummy.apiCalls.distanceTimeBackThread;
import com.example.android.yummy.apiCalls.photoRequest;
import com.example.android.yummy.apiCalls.restaurant_getter;
import com.example.android.yummy.apiCalls.second_address;
import com.example.android.yummy.apiCalls.yelp;
import com.google.maps.*;
import com.google.maps.model.DistanceMatrix;
import com.google.maps.model.PlacesSearchResponse;
import com.google.maps.model.PlacesSearchResult;
import com.yelp.fusion.client.models.Business;


/**
 * This class calls the Google Api and yelp Api and then finds the common Restaurants both APIs returns.
 * @author Amit Binu
 *
 */
public class GeoCoder extends AppCompatActivity {
	/**
	 * This is the food user entered.
	 */
	public static String  food;
	/**
	 * The name of the city the user is in.
	 */
	private static String city;
	/**
	 * Creates a context that sets the Api key. This context is used to communicate with various google Api(s).
	 */
	private static GeoApiContext context;
	/**
	 * After executing the distance_getter() method, this ArrayList will contain the distance from user's location to each restaurant.
	 */
	private static Stack<Double> distances;

	/**
	 * After executing the time_getter() method, this ArrayList will contain the time it takes to go from user's location to each restaurant.
	 */
	private static Stack<Double> times;

	/**
	 * This Stack will contain the google ratings of all the restaurants that the user wants to go to.
	 */
	private static Stack<Double> ratings;
	/**
	 * A GeoCoder object that is passed to other classes.
	 */
	private static GeoCoder object;
	/**
	 * photoRequest object that is used to request pictures from Google Photos Api.
	 */
    public static photoRequest pictures;
	/**
	 * The PlaceSearchResults of all the restaurants returned by Google maps API and / or Yelp API.
	 */
    public static ArrayList<PlacesSearchResult> commonRestaurants_Google ;
	/**
	 * The PlaceSearchResults of all the restaurants returened by both Google Maps API and Yelp API.
	 */
	private static ArrayList<PlacesSearchResult> Google1;
	/**
	 * <i>data</i> object that is used to call the <i>results</i> method in <i>data</i> class, after all the information is collected.
	 */
	private static data Dataobject;
	/**
	 * The user's location in the form of coordinates.
	 */
    private static String origin;
	/**
	 * <i>restaurant_getter</i> object that calls the google maps API to get the information.
	 */
	public static restaurant_getter test1;
	/**
	 * A boolean variable that will be changed to true once the Google Maps's response is gotten.
	 */
	public static Boolean checker_for_restaurantgetter = false;
    /**
     * An array of all restaurants' addresses.
     */
    private static String[] destinations;
    /**
     * A <i>distanceTimeBackThread</i> object that calls the Google Maps Api to get the distance and time it takes to go from user's location and each restaurant.
     */
    private static distanceTimeBackThread fetcher;
    /**
     * This will contain what unit to use for displaying distance.
     */
    public static String distanceUnit;
	/**
	 * The constructor assigns the parameters to appropriate global variables,initializes certain public Stack and then calls the Restaurant_getter() method.
	 * @param city_name This is the name of the city the user is currently in.
	 * @param foods_from_user The food the user entered.
	 * @param origin The user's location in the form of coordinates.
	 * @throws Exception Throws exceptions mainly for invalid requests made to google.
	 */
	public GeoCoder (String city_name, String foods_from_user, String origin, data object) throws Exception{
		this.origin = origin;
		this.city = city_name;
		this.food = foods_from_user;
		this.object = this;
		this.Dataobject = object;
		context = new GeoApiContext().setApiKey(Constants.ApiKey);
		distances = new Stack<>();
		times = new Stack<>();
		ratings = new Stack<>();
			Restaurant_getter(origin, city, foods_from_user);
	}
	/**
	 * This method gets the information of each restaurant that offers the food the user wants by calling Google maps API and Yelp API.
	 * @param origin This is the exact location of user.
	 * @param city This is the name of the city the user is currently in.
	 * @param FOOD TStack that contains the food(s) user wants
	 * @throws Exception Throws exceptions mainly for invalid requests made to google.
	 */
	private static void Restaurant_getter(String origin, String city, String FOOD) throws Exception {
			test1 = new restaurant_getter(origin,city,context,FOOD,object);

	}
	/**
	 * This method is called after both yelp's and google maps' response is got.
	 * This method finds as many common restaurants in google maps' and yelp's response.
	 * @throws Exception
	 */
	public static void afterWAIT() throws Exception{
		PlacesSearchResponse address = test1.address;
        commonRestaurants_Google = new ArrayList<>();
		Google1 = new ArrayList<>();
        CommonFinder(address);
		if(address.nextPageToken == null){
			second_address SecondAddress = new second_address(context, food + " restaurants in " + MainActivity.CITY, object);
		}
		else{
            second_address SecondAddress = new second_address(address.nextPageToken,context);
        }

		}

    public static void afterAddress2(PlacesSearchResponse address,boolean value){
        CommonFinder(address);
        if(value==true || Google1.size() >=40){
            organizeAllData();
        }
        else {
            second_address SecondAddress = new second_address(address.nextPageToken,context);
        }
    }
    public static void afterAddress(PlacesSearchResponse address){
        CommonFinder(address);
        organizeAllData();
    }

    private static void organizeAllData(){
        commonRestaurants_Google = Google1;

        com.example.android.yummy.MainActivities.Result.updating("Gathering restaurants' pictures");
        pictures = new photoRequest(commonRestaurants_Google);
        try{
            DataOrganizer();}
        catch (Exception e){
        }
    }
    /**
     * This method finds the common Restaurants in Google's and Yelp's response.
     * @param address The PlaseSearchResponse returned by the Google Maps API after a request was made.
     */

    private static void CommonFinder(PlacesSearchResponse address) {
        for(int j =0; j < address.results.length; j++){
				if(! Google1.contains(address.results[j])){
					Google1.add(address.results[j]);
                }

            }
    }
    /**
     * This method organizes all the data and puts certain information in appropriate stacks.
     * @throws Exception throws an Exception when invalid request is made to get the distance and time.
     */
	private static void DataOrganizer() throws Exception{
        destinations = new String[commonRestaurants_Google.size()];
		for (int i =0; i < commonRestaurants_Google.size(); i++){
            destinations[i] = commonRestaurants_Google.get(i).formattedAddress;
			double rating = (double) commonRestaurants_Google.get(i).rating*1000000;
			rating = Math.round(rating);
			ratings.push(rating/1000000); //This is done so that the rating will have only one decimal value.
		}
        if(ratings.size() == 1){
            Dataobject.checker = true;
            Dataobject.results();
        }
        else{
            Dataobject.checker = false;
            distanceAndTime(origin, destinations);}
	}
	/**
	 *
	 * This method gets the distance and time it takes to go from user's location to each restaurant.
	 * @param origin This is the exact location of user.
	 * @param destinations This Stack contains the address of each restaurant that has the food user wants.
	 * @throws Exception Throws exceptions mainly for invalid requests made to google.
	 */
	private static void distanceAndTime(String origin, String[] destinations) throws Exception{
		String[] temp = {origin};
		fetcher = new distanceTimeBackThread(temp, destinations, context, object);
    }

    /**
     * This method is called after the distance and time data are obtained from <i>distanceTimeBackThread</i>.
     *
     */
    public static void afterDist(){

        DistanceMatrix dist = fetcher.result;
        for(int i =0; i < dist.rows[0].elements.length ; i++){
            try{
                String[] removeUnit = null;
                if(dist.rows[0].elements[i].distance.toString().contains("km")){
                    distanceUnit = "km";
                    removeUnit =  dist.rows[0].elements[i].distance.toString().split("km");
                }
                if(dist.rows[0].elements[i].distance.toString().contains("miles")){
                    distanceUnit = "miles";
                    removeUnit =  dist.rows[0].elements[i].distance.toString().split("miles");
                }
                String[] removeMINS;
                if(dist.rows[0].elements[i].durationInTraffic != null){
                    if(dist.rows[0].elements[i].durationInTraffic.humanReadable.contains("mins")){
                        removeMINS = dist.rows[0].elements[i].durationInTraffic.toString().split("mins");
                    }
                    else{
                        removeMINS = dist.rows[0].elements[i].durationInTraffic.toString().split("min");
                    }
                }
                else{
                    if(dist.rows[0].elements[i].duration.humanReadable.contains("mins")){
                        removeMINS = dist.rows[0].elements[i].duration.toString().split("mins");
                    }
                    else{
                        removeMINS = dist.rows[0].elements[i].duration.toString().split("min");
                    }
                }
                distances.push(Double.parseDouble(removeUnit[0]));
                times.push(Double.parseDouble(removeMINS[0]));
            }
            catch (NullPointerException f){
                continue;
            }
            catch (NumberFormatException e){
                distances.push(null);
                times.push(null);
            }
        }
        Dataobject.results();

    }
	/**
	 *
	 * @return a stack that contains the distance between user's location and each restaurant's location.
	 */
	public Stack<Double> distance_getter(){
		return distances;
	}
	/**
	 *
	 * @return a stack that contains the time it takes to go from user's location and each restuarant's location.
	 */
	public Stack<Double> time_getter(){
		return times;
	}
	/**
	 *
	 * @return a stack that contains the google ratings of all the restaurants that have the food user wants.
	 */
	public Stack<Double> ratings(){
		return ratings;
	}

	}

