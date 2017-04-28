package yummy;
import java.io.InputStreamReader
;

import java.io.Reader;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Queue;
import java.util.Stack;

import com.google.maps.*;
import com.google.maps.model.DistanceMatrix;
import com.google.maps.model.GeocodingResult;
import com.google.maps.model.PlacesSearchResponse;
import com.google.maps.model.PlacesSearchResult;


public class GeoCoder {
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
	private static GeoApiContext context = new GeoApiContext().setApiKey("AIzaSyBTliUEXQUj2RpsAACAcu6dnXWdgIKrc_w");
	
	/**
	 * After executing the restaurant_getter() method, this ArrayList will have all the restaurants in the user's city that has the food(s) user want.
	 */
	private static Stack<String> restaurants = new Stack<String>();
	
	/**
	 * This stack will contain the addresses of all the restaurants that the user wants to go to. 
	 */
	private static Stack<String> addresses = new Stack<String>();
	
	/**
	 * After executing the distance_getter() method, this ArrayList will contain the distance from user's location to each restaurant.
	 */
	private static Stack<Double> distances = new Stack<Double>();
	
	/**
	 * After executing the time_getter() method, this ArrayList will contain the time it takes to go from user's location to each restaurant.
	 */
	private static Stack<Double> times = new Stack<Double>();
	
	/**
	 * This Stack will contain the google ratings of all the restaurants that the user wants to go to. 
	 */
	private static Stack<Double> ratings = new Stack<Double>();

	
	public GeoCoder(String city_name, Stack<String> foods_from_user, String origin) throws Exception{
		this.city = city_name;
		this.food = foods_from_user;
		
		for(int i = 0; i < food.size(); i++){ //does it for each food the user wants
			
			restaurant_getter(city, (String) food.get(i));
			distanceAndTime(origin);
		}
	}
	
	public static void restaurant_getter(String location, String food) throws Exception{
		PlacesSearchResponse address = PlacesApi.textSearchQuery(context, food + " in " + location).await();		
		for (int i =0; i < address.results.length; i++){
			restaurants.push(address.results[i].name);
			addresses.push(address.results[i].formattedAddress);
			double rating = (double) address.results[i].rating*1000000;
			rating = Math.round(rating);
			ratings.push(rating/1000000); //This is done so that the rating will have only one decimal value.
		}
		restaurants.push(null);
		addresses.push(null);
		ratings.push(null);
	}
	
	public static void distanceAndTime(String origin) throws Exception{
		String[] temp = {origin};
		
		double start = System.currentTimeMillis();
		
		for(String dest : addresses){
			if (dest == null)
				break;
	//		if (System.currentTimeMillis() > start +10000) // If it takes more than 10 seconds, the search ends.
	//			break;
			String[] finalDest = {dest};
			DistanceMatrix dist = DistanceMatrixApi.getDistanceMatrix(context, temp, finalDest).await();
			String[] removeKM =  dist.rows[0].elements[0].distance.humanReadable.split("km");
			String[] removeMINS = dist.rows[0].elements[0].duration.humanReadable.split("mins");
			
			distances.push(Double.parseDouble(removeKM[0]));
			times.push(Double.parseDouble(removeMINS[0]));
		}
		distances.push(null);
		times.push(null);
	}
	
	public static Stack<Double> distance_getter(){
		return distances;
	}
	
	public static Stack<Double> time_getter(){
		return times;
	}
	
	public static Stack<String> restaurant_names(){
		return restaurants;
	}
	
	public static Stack<String> restaurant_addresses(){
		return addresses;
	}

	public static Stack<Double> ratings(){
		return ratings;
	}
	
	}

