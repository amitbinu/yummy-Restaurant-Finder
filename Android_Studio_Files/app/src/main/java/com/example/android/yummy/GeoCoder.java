package com.example.android.yummy;


import android.os.AsyncTask;
import android.util.Log;

import java.net.URL;
import java.util.Stack;




import com.google.maps.*;
import com.google.maps.errors.InvalidRequestException;
import com.google.maps.model.DistanceMatrix;
import com.google.maps.model.Photo;
import com.google.maps.model.PlacesSearchResponse;

import com.google.maps.model.PlacesSearchResponse;


/**
 * This class has one constructor, 2 methods and 6 getter methods. 
 * @author Amit Binu
 *
 */
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
	private static GeoApiContext context = new GeoApiContext().setApiKey("AIzaSyB9RIqgSUPHG1eg182FbxOamicGXFgjBDA");
	
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
	/**
	 * This stack contains the photos of each restaurant. 
	 */
	private static Stack<Photo[]> photos = new Stack<Photo[]>();
	
	private static Stack<Boolean> open = new Stack<Boolean>();
	
	private static Stack<Boolean> permanentlyClosed = new Stack<Boolean>();
	
	private static Stack<URL> urls = new Stack<URL>();
	/**
	 * The constructor assigns the parameters to appropriate global variables and then calls the restaurant_getter() method. 
	 * @param city_name This is the name of the city the user is currently in. 
	 * @param foods_from_user Stack that contains the food(s) user wants
	 * @param origin This is the exact location of user. 
	 * @throws Exception Throws exceprions mainly for invalid requests made to google. 
	 */
	public GeoCoder(String city_name, Stack<String> foods_from_user, String origin) throws Exception{
		this.city = city_name;
		this.food = foods_from_user;

			restaurant_getter(origin, city, foods_from_user);

	}
	
	/**
	 * This method gets the name, address, rating and photo of each restaurant that offers the food user wants. Then it puts this information to appropriate <i> Stacks </i>. 
	 * @param origin This is the exact location of user. 
	 * @param city This is the name of the city the user is currently in.
	 * @param FOOD TStack that contains the food(s) user wants
	 * @throws Exception Throws exceptions mainly for invalid requests made to google. 
	 */
	private static void restaurant_getter(String origin, String city, Stack<String> FOOD) throws Exception{
		Stack<String> tempAdd = new Stack<String>();
		if (! FOOD.isEmpty()){
			String f = FOOD.pop();
		//PlacesSearchResponse address = PlacesApi.textSearchQuery(context, f + " restaurants in " + city).await();
			com.example.android.yummy.URL test = new com.example.android.yummy.URL(f, city);
			PlacesSearchResponse address = test.doInBackground();

		for (int i =0; i < address.results.length; i++){
			permanentlyClosed.push(address.results[i].permanentlyClosed);
			restaurants.push(address.results[i].name);
			open.push(address.results[i].openingHours.openNow);
			urls.push(address.results[i].icon);
			addresses.push(address.results[i].formattedAddress);
			tempAdd.push(address.results[i].formattedAddress); //This is done so when there are more than one food in the Stack, distanceANdTime() method will use the addresses for only one food. 
			photos.push(address.results[i].photos);
			double rating = (double) address.results[i].rating*1000000;
			rating = Math.round(rating);
			ratings.push(rating/1000000); //This is done so that the rating will have only one decimal value.
		}
		
		while(address.nextPageToken != null){
			
			try{
			address = PlacesApi.textSearchNextPage(context, address.nextPageToken).await();}
			catch(InvalidRequestException e){
				while(true){
					try{
						address = PlacesApi.textSearchNextPage(context, address.nextPageToken).await();
						break;
					}
					catch(InvalidRequestException f1){
						continue;
					}
				}
				
			}
			for (int i =0; i < address.results.length; i++){
				try{
				open.push(address.results[i].openingHours.openNow);}
				catch(NullPointerException e){
					open.push(null);
				}
				permanentlyClosed.push(address.results[i].permanentlyClosed);
				urls.push(address.results[i].icon);
				restaurants.push(address.results[i].name);
				addresses.push(address.results[i].formattedAddress);
				tempAdd.push(address.results[i].formattedAddress); //This is done so when there are more than one food in the Stack, distanceANdTime() method will use the addresses for only one food. 
				photos.push(address.results[i].photos);
				double rating = (double) address.results[i].rating*1000000;
				rating = Math.round(rating);
				ratings.push(rating/1000000); //This is done so that the rating will have only one decimal value.
			}
		}
		permanentlyClosed.push(null);
		open.push(null);
		urls.push(null);
		restaurants.push(null);
		addresses.push(null);
		ratings.push(null);
		photos.push(null);
		distanceAndTime(origin, tempAdd);
		restaurant_getter(origin, city,FOOD);
		}
		}
		
	
	/**
	 * This method gets the distance and time it takes to go from user's location to each restaurant. 
	 * @param origin This is the exact location of user. 
	 * @param destinations This Stack contains the address of each restaurant that has the food user wants.
	 * @throws Exception Throws exceptions mainly for invalid requests made to google. 
	 */
	private static void distanceAndTime(String origin, Stack<String> destinations) throws Exception{
		String[] temp = {origin};
		String[] realAdress= new String[destinations.size()]; 
			
		for(int i =0; i < realAdress.length ; i++){
			realAdress[i] = destinations.get(i);
		}
		
		DistanceMatrix dist = DistanceMatrixApi.getDistanceMatrix(context, temp, realAdress).await();
		
		for(int i =0; i < destinations.size() ; i++){						
			String[] removeKM =  dist.rows[0].elements[i].distance.humanReadable.split("km");
			String[] removeMINS = dist.rows[0].elements[i].duration.humanReadable.split("mins");
					
			distances.push(Double.parseDouble(removeKM[0]));
			times.push(Double.parseDouble(removeMINS[0]));		
		}
		
		distances.push(null);
		times.push(null);
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
	public Stack<Photo[]> photos(){
		return photos;
	}
	
	public Stack<Boolean> Open(){
		return open;
	}
	
	public Stack<URL> URLs(){
		return urls;
	}
	
	public Stack<Boolean> PermanentlyClosed(){
		return permanentlyClosed;
	}
	}

