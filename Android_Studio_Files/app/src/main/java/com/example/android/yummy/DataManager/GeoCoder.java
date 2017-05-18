package com.example.android.yummy.DataManager;


import android.util.Log;

import java.net.URL;
import java.util.Stack;


import com.example.android.yummy.Backthread.distanceTimeBackThread;
import com.example.android.yummy.Backthread.restaurant_getter;
import com.example.android.yummy.Backthread.second_address;
import com.google.maps.*;
import com.google.maps.model.DistanceMatrix;
import com.google.maps.model.Photo;
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

	private static GeoCoder object;
	private static Stack<Boolean> open;
	
	private static Stack<Boolean> permanentlyClosed;
	private static data Dataobject;
	private static Stack<URL> urls;
	/**
	 * The constructor assigns the parameters to appropriate global variables and then calls the restaurant_getter() method. 
	 * @param city_name This is the name of the city the user is currently in. 
	 * @param foods_from_user Stack that contains the food(s) user wants
	 * @param origin This is the exact location of user. 
	 * @throws Exception Throws exceprions mainly for invalid requests made to google. 
	 */
	public GeoCoder(String city_name, Stack<String> foods_from_user, String origin, data object) throws Exception{

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
		urls = new Stack<URL>();
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
			//PlacesSearchResponse address = PlacesApi.textSearchQuery(context, f + " restaurants in " + city).await();
			test1 = new restaurant_getter(origin,city, FOOD,context,f,object);

		}
	}



	public static void afterWAIT(String origin, Stack<String> FOOD) throws Exception{
		PlacesSearchResponse address = test1.address; //Could be a null. Check restaurant_getter();
		Stack<String> tempAdd = new Stack<String>();
		for (int i =0; i < address.results.length; i++){

          //  yelp yelpAPi = new yelp(address.results[i].name, address.results[i].formattedAddress);
            Log.e("END", "Ending of yelpAPI");
			try{
				open.push(address.results[i].openingHours.openNow);}
			catch(NullPointerException e){
				open.push(null);
			}

			permanentlyClosed.push(address.results[i].permanentlyClosed);
			restaurants.push(address.results[i].name);
			urls.push(address.results[i].icon);
			addresses.push(address.results[i].formattedAddress);
			tempAdd.push(address.results[i].formattedAddress); //This is done so when there are more than one food in the Stack, distanceANdTime() method will use the addresses for only one food.
			photos.push(address.results[i].photos);
			double rating = (double) address.results[i].rating*1000000;
			rating = Math.round(rating);
			ratings.push(rating/1000000); //This is done so that the rating will have only one decimal value.
		}

		while(address.nextPageToken != null){
			test2 = new second_address(address.nextPageToken, context,object);

			while(true){
				if(get_address() != null){
					address = get_address();
					break;
				}

			}
			for (int i =0; i < address.results.length; i++){
              //  yelp yelpAPi = new yelp(address.results[i].name, address.results[i].formattedAddress);
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
        Log.e("tempADD's size", tempAdd.size() + "");
		distanceAndTime(origin, tempAdd);
		//Restaurant_getter(origin, city,FOOD);
		;

        Dataobject.results();
		}

	public static second_address test2;
	public static PlacesSearchResponse get_address(){
		PlacesSearchResponse addressLAST = test2.address;
		return addressLAST;
	}
	private static distanceTimeBackThread fetcher;
	/**
	 *
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
            Log.e("Destinations", destinations.get(i));
		}

		fetcher = new distanceTimeBackThread(temp, realAdress, context, object);
        while (fetcher.result == null){}
        Log.e("Size of destinations", fetcher.destinations.length + "");
        Log.e("Size of result", fetcher.result.rows[0].elements.length + "");
        DistanceMatrix dist = fetcher.result;
        for (int i =0; i < dist.destinationAddresses.length; i++){
            Log.e("RESULTS !!!!", dist.rows[0].elements[0].distance.toString());
        }


        for(int i =0; i < dist.rows[0].elements.length ; i++){
            Log.e("Index value", i +"");
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

