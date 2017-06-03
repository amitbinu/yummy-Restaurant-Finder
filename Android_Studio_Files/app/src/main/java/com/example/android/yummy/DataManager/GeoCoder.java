package com.example.android.yummy.DataManager;

import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import java.util.ArrayList;
import java.util.Stack;
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
	 * Yelp object that is used to call the yelp Api with appropriate parameters.
	 */
	private static yelp yelpCaller;
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
	 * The PlaceSearchResults of all the restaurants that are returned by Google Maps API but not by Yelp API.
	 */
	private static ArrayList<PlacesSearchResult> Google2;
	/**
	 * The <i> yelp.models.Business </i> of the common restaurants that are returned by both Google maps API and Yelp API.
	 */
    public static ArrayList<Business> commonRestaurants_Yelp;
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
	 * A boolean variable that will be changed to true once the yelp's response is gotten.
	 */
	public static Boolean checker_for_yelp = false;
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
	 * @throws Exception Throws exceptions mainly for invalid requests made to google and / or yelp.
	 */
	public GeoCoder (String city_name, String foods_from_user, String origin, data object) throws Exception{
		this.origin = origin;
		this.city = city_name;
		this.food = foods_from_user;
		this.object = this;
		this.Dataobject = object;
		context = new GeoApiContext().setApiKey(Constants.ApiKey);
		distances = new Stack<Double>();
		times = new Stack<Double>();
		ratings = new Stack<Double>();
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
		if (!FOOD.isEmpty()) {
			yelpCaller = new yelp(FOOD + " Restaurants ",city, object);
			test1 = new restaurant_getter(origin,city,context,FOOD,object);
		}
	}
	/**
	 * This method is called after both yelp's and google maps' response is got.
	 * This method finds as many common restaurants in google maps' and yelp's response.
	 * @throws Exception
	 */
	public static void afterWAIT() throws Exception{
		PlacesSearchResponse address = test1.address;
        commonRestaurants_Google = new ArrayList<>();
        commonRestaurants_Yelp = new ArrayList<>();
		Google1 = new ArrayList<>();
		Google2 = new ArrayList<>();
        CommonFinder(address);
        while(address.nextPageToken != null){
            if(Google1.size()>40){break;}
            second_address secondAddress = new second_address(address.nextPageToken,new GeoApiContext().setApiKey(Constants.ApiKey));
            while(secondAddress.address ==null){}
            address = secondAddress.address;
            CommonFinder(address);
        }
        int j =0;
		if (Google1.size() < 10) {
			for (int i = 0; i < Google1.size() + Google2.size(); i++) {
				if (i < Google1.size()) {
					commonRestaurants_Google.add(Google1.get(i));
				}
				if(i >= Google1.size() && !commonRestaurants_Google.contains(Google2.get(j))) {
					commonRestaurants_Google.add(Google2.get(j));
					j++;
				}
			}
		}
		else{
			commonRestaurants_Google = Google1;
		}
            com.example.android.yummy.MainActivities.Result.updating("Gathering restaurants' pictures");
            pictures = new photoRequest(commonRestaurants_Google);
            DataOrganizer();
		}
    /**
     * This method finds the common Restaurants in Google's and Yelp's response.
     * @param address The PlaseSearchResponse returned by the Google Maps API after a request was made.
     */
    private static void CommonFinder(PlacesSearchResponse address) {
        for(int j =0; j < address.results.length; j++){
            for (int i =0; i < yelpCaller.response.body().getBusinesses().size(); i++){
                if (yelpCaller.response.body().getBusinesses().get(i).getName().equals(address.results[j].name) && (!(Google1.contains(address.results[j])))){
                   try{
					    Log.e("name", yelpCaller.response.body().getBusinesses().get(i).getName());
                        String price = yelpCaller.response.body().getBusinesses().get(i).getPrice();
                        if(price != null){
                        Google1.add(address.results[j]);
                        commonRestaurants_Yelp.add(yelpCaller.response.body().getBusinesses().get(i));
                        }
                        else{
                            String isItNull = null;
                            for (int findPrice=i+1; findPrice< yelpCaller.response.body().getBusinesses().size();findPrice++){
                                if (yelpCaller.response.body().getBusinesses().get(findPrice).getName().equals(address.results[j].name)){
                                    try{
                                        isItNull = yelpCaller.response.body().getBusinesses().get(findPrice).getPrice();
                                        Boolean checking = isItNull.equals("");
                                        Google1.add(address.results[j]);
                                        commonRestaurants_Yelp.add(yelpCaller.response.body().getBusinesses().get(i));
                                        break;
                                    }
                                    catch (NullPointerException f){
                                        continue;
                                    }
                                }
                            }
                            if(isItNull == null){
                                Google1.add(address.results[j]);
                                commonRestaurants_Yelp.add(yelpCaller.response.body().getBusinesses().get(i));
                            }
                        }
                    }
                    catch (ExceptionInInitializerError e){
                        Log.e("GeoCoder-commonFinder",e.getMessage());
                    }
                    break;
                }
            }
            if(!Google1.contains(address.results[j])){
				Log.e("name", address.results[j].name);
				Google2.add(address.results[j]);
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
		ratings.push(null);
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
                String[] removeMINS = dist.rows[0].elements[i].duration.toString().split("mins");

                distances.push(Double.parseDouble(removeUnit[0]));
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
	 * @return a stack that contains the google ratings of all the restaurants that have the food user wants.
	 */
	public Stack<Double> ratings(){
		return ratings;
	}

	}

