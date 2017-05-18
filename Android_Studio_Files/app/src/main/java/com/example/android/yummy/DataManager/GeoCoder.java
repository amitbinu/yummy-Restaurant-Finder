package com.example.android.yummy.DataManager;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import java.net.URL;
import java.util.ArrayList;
import java.util.Stack;


import com.example.android.yummy.Backthread.distanceTimeBackThread;
import com.example.android.yummy.Backthread.photoRequest;
import com.example.android.yummy.Backthread.restaurant_getter;
import com.example.android.yummy.Backthread.second_address;
import com.example.android.yummy.Backthread.yelp;
import com.example.android.yummy.MainActivities.Main2Activity;
import com.google.maps.*;
import com.google.maps.model.DistanceMatrix;
import com.google.maps.model.Photo;
import com.google.maps.model.PlacesSearchResponse;
import com.google.maps.model.PlacesSearchResult;
import com.yelp.fusion.client.models.Business;

import static android.R.attr.y;
import static com.example.android.yummy.Backthread.distanceTimeBackThread.destinations;
import static com.example.android.yummy.Backthread.restaurant_getter.origin;
import static com.example.android.yummy.Backthread.yelp.response;


/**
 * This class has one constructor, 2 methods and 6 getter methods. 
 * @author Amit Binu
 *
 */
public class GeoCoder extends AppCompatActivity {
    public Context context2 = this;

	/**
	 * This ArrayList will contain the food(s) user wants. 
	 */
	public static Stack  food;
	
	/**
	 * This <i> city </i> variable has the name of the city the user is in.
	 */
	private static String city;
	
	/**
	 * Creates a context that sets the Api key. This context is used to communicate with various google Api(s).
	 */
	private static GeoApiContext context;
	
	/**
	 * After executing the restaurant_getter() method, this ArrayList will have all the restaurants in the user's city that has the food(s) user want.
	 */
	private static Stack<String> restaurants;
	
	/**
	 * This stack will contain the addresses of all the restaurants that the user wants to go to. 
	 */
	private static Stack<String> addresses;
	
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
	 * This stack contains the photos of each restaurant. 
	 */
	private static Stack<Photo[]> photos;
	private static yelp yelpCaller;
	private static GeoCoder object;
	private static Stack<Boolean> open;
    public static photoRequest pictures;
    public static ArrayList<PlacesSearchResult> commonRestaurants_Google ;
    public static ArrayList<Business> commonRestaurants_Yelp;
	private static Stack<Boolean> permanentlyClosed;
	private static data Dataobject;
    private static String origin;
	/**
	 * The constructor assigns the parameters to appropriate global variables and then calls the restaurant_getter() method. 
	 * @param city_name This is the name of the city the user is currently in. 
	 * @param foods_from_user Stack that contains the food(s) user wants
	 * @param origin This is the exact location of user. 
	 * @throws Exception Throws exceprions mainly for invalid requests made to google. 
	 */
	public GeoCoder (String city_name, Stack<String> foods_from_user, String origin, data object) throws Exception{
        this.origin = origin;
		this.city = city_name;
		this.food = foods_from_user;
		this.object = this;
		this.Dataobject = object;
		context = new GeoApiContext().setApiKey("AIzaSyB9RIqgSUPHG1eg182FbxOamicGXFgjBDA");
		restaurants = new Stack<String>();
		addresses = new Stack<String>();
		distances = new Stack<Double>();
		times = new Stack<Double>();
		ratings = new Stack<Double>();
		photos = new Stack<Photo[]>();
		open = new Stack<Boolean>();
		permanentlyClosed = new Stack<Boolean>();
			Restaurant_getter(origin, city, foods_from_user);


	}
	/**
	 * This method gets the name, address, rating and photo of each restaurant that offers the food user wants. Then it puts this information to appropriate <i> Stacks </i>. 
	 * @param origin This is the exact location of user. 
	 * @param city This is the name of the city the user is currently in.
	 * @param FOOD TStack that contains the food(s) user wants
	 * @throws Exception Throws exceptions mainly for invalid requests made to google. 
	 */
	public static restaurant_getter test1;
	private static void Restaurant_getter(String origin, String city, Stack<String> FOOD) throws Exception {

		if (!FOOD.isEmpty()) {
			String f = FOOD.pop();
			yelpCaller = new yelp(f + " Restaurants ",city);
			test1 = new restaurant_getter(origin,city, FOOD,context,f,object);
		}
	}
	public static void afterWAIT() throws Exception{
		PlacesSearchResponse address = test1.address;
        commonRestaurants_Google = new ArrayList<>();
        commonRestaurants_Yelp = new ArrayList<>();
        CommonFinder(address);
        while(address.nextPageToken != null){
            if(commonRestaurants_Google.size()>40){break;}
            second_address secondAddress = new second_address(address.nextPageToken,new GeoApiContext().setApiKey("AIzaSyB9RIqgSUPHG1eg182FbxOamicGXFgjBDA"));
            while(secondAddress.address ==null){}
            address = secondAddress.address;
            CommonFinder(address);
        }
        pictures = new photoRequest(commonRestaurants_Google);
        DataOrganizer();
		}

    private static void CommonFinder(PlacesSearchResponse address){
        for (int i =0; i < yelpCaller.response.body().getBusinesses().size(); i++){
            for (int j =0; j < address.results.length; j++){
                if(yelpCaller.response.body().getBusinesses().get(i).getName().equals(address.results[j].name) && (!(commonRestaurants_Google.contains(address.results[j])) )){
                     //       Log.e("CHECK G", address.results[j].name + " " + address.results[j].geometry.location.lat + " " + address.results[j].geometry.location.lng);
                     //       Log.e("CHECK Y",yelpCaller.response.body().getBusinesses().get(i).getName()+ " " + yelpCaller.response.body().getBusinesses().get(i).getCoordinates().getLatitude()+" " + yelpCaller.response.body().getBusinesses().get(i).getCoordinates().getLongitude() );
                            commonRestaurants_Google.add(address.results[j]);
                            commonRestaurants_Yelp.add( yelpCaller.response.body().getBusinesses().get(i));
                }
            }
        }
    }

	private static void DataOrganizer() throws Exception{
        String[] destinations = new String[commonRestaurants_Google.size()];
		for (int i =0; i < commonRestaurants_Google.size(); i++){
			try{
				open.push(commonRestaurants_Google.get(i).openingHours.openNow);}
			catch(NullPointerException e){
				open.push(null);
			}
			permanentlyClosed.push(commonRestaurants_Google.get(i).permanentlyClosed);
			restaurants.push(commonRestaurants_Google.get(i).name);
            destinations[i] = commonRestaurants_Google.get(i).formattedAddress;
			addresses.push(commonRestaurants_Google.get(i).formattedAddress);
			photos.push(commonRestaurants_Google.get(i).photos);
			double rating = (double) commonRestaurants_Google.get(i).rating*1000000;
			rating = Math.round(rating);
			ratings.push(rating/1000000); //This is done so that the rating will have only one decimal value.
		}
		permanentlyClosed.push(null);
		open.push(null);
		restaurants.push(null);
		addresses.push(null);
		ratings.push(null);
		photos.push(null);
		if(restaurants.size() == 1){
			Dataobject.checker = true;
            Dataobject.results();
		}
		else{
			Dataobject.checker = false;
			distanceAndTime(origin, destinations);}
	}

	private static distanceTimeBackThread fetcher;
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

    public static void afterDist(){
        DistanceMatrix dist = fetcher.result;

        for(int i =0; i < dist.rows[0].elements.length ; i++){
            try{
                String[] removeKM =  dist.rows[0].elements[i].distance.toString().split("km");
                String[] removeMINS = dist.rows[0].elements[i].duration.toString().split("mins");

                distances.push(Double.parseDouble(removeKM[0]));
                times.push(Double.parseDouble(removeMINS[0]));}
            catch (NullPointerException f){
                continue;
            }
            catch (NumberFormatException e){
                distances.push(null);
                times.push(null);
            }
        }

        distances.push(null);
        times.push(null);
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
	 * @return a stack that contains the names of all the restaurants that have the food user wants. 
	 */
	public Stack<String> restaurant_names(){
		return restaurants;
	}
	/**
	 * 
	 * @return a stack that contains the addresses of all the restaurants that have the food user wants. 
	 */
	public Stack<String> restaurant_addresses(){
		return addresses;
	}
	/**
	 * 
	 * @return a stack that contains the google ratings of all the restaurants that have the food user wants. 
	 */
	public Stack<Double> ratings(){
		return ratings;
	}
	/**
	 * 
	 * @return a stack that contians the photos of all the restaurants that have the food user wants. 
	 */
	
	public Stack<Boolean> Open(){
		return open;
	}

	public Stack<Boolean> PermanentlyClosed(){
		return permanentlyClosed;
	}
	}

