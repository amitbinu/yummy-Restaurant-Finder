package com.example.android.yummy.DataManager;

import android.graphics.Bitmap;
import android.util.Log;

import com.example.android.yummy.Backthread.yelp;
import com.google.maps.model.PlacesSearchResult;
import com.yelp.fusion.client.models.Business;

import java.util.ArrayList;

import edu.princeton.cs.algs4.Quick;

import static android.R.attr.name;


public class sort {
    private static sort SortObject;
    public static Boolean checker = true;
    private static data object;
    public static ArrayList<Restaurant> restaurants;
    private static ArrayList<Integer> price0 = new ArrayList<>();
    private static ArrayList<Integer> price1 = new ArrayList<>();
    private static ArrayList<Integer> price2 = new ArrayList<>();
    private static ArrayList<Integer> price3 = new ArrayList<>();
    private static ArrayList<Integer> price4 = new ArrayList<>();
    private static ArrayList<String> strangeRestaurants = new ArrayList<>();
    private static ArrayList<String> strangeResponse = new ArrayList<>();
    private static Integer integer, nextIndex;
    private static yelp yelpCall;
	public sort(data object, String sortValue){
        price0 = new ArrayList<>();
        price1 = new ArrayList<>();
        price2 = new ArrayList<>();
        price3 = new ArrayList<>();
        price4 = new ArrayList<>();
        strangeRestaurants = new ArrayList<>();
        strangeResponse = new ArrayList<>();
        SortObject = this;
        this.object = object;
        this.restaurants = new ArrayList<>();
        Result.updating("Sorting the restaurants ...");
        Log.e("SORT_VALUE", sortValue);
        switch (sortValue){
            case ("Price(low-high)"):
                priceLow();
            case("Price(high-low"):
                priceHigh();
            case("Distance(low-high"):
                distanceLow();
            case("Distance(high-low"):
                distanceHigh();
            case("Rating(high-low"):
                ratingHigh();
            case("Rating(low-high)"):
                ratingLow();
            case("Time(low-high)"):
                timeLow();
            case("Time(high-low)"):
                timeHigh();
        }
    }

    private static void setRestaurants(int i, String price){
        String name = object.details.commonRestaurants_Google.get(i).name;
        String address = object.details.commonRestaurants_Google.get(i).formattedAddress;
        Double time = object.details.time_getter().get(i);
        Double distance = object.details.distance_getter().get(i);
        Double rating = object.details.ratings().get(i);
        Bitmap picture = object.details.pictures.pictures1.get(i);
        Boolean open;
        try{
        open = object.details.commonRestaurants_Google.get(i).openingHours.openNow;}
        catch (NullPointerException e){
            open = null;
        }
        Boolean permanent = object.details.commonRestaurants_Google.get(i).permanentlyClosed;
        restaurants.add(new Restaurant(name, address,time, distance, rating, null, picture, open, permanent, price));
    }

    private static void priceLow(){
        for (int i =0; i < object.details.commonRestaurants_Yelp.size(); i++){
            Log.e("Google & Yelp", object.details.commonRestaurants_Google.get(i).name + " " + object.details.commonRestaurants_Yelp.get(i).getName() + " " + object.details.commonRestaurants_Yelp.get(i).getPrice() + 2);
        }


        if(object.details.commonRestaurants_Yelp.size() != 0){

            for (int i =0; i < object.details.commonRestaurants_Yelp.size(); i++){
                try{
                switch (object.details.commonRestaurants_Yelp.get(i).getPrice()){
                    case("$"):
                        price1.add(i);
                    case("$$"):
                        Log.e("Second Price", object.details.commonRestaurants_Yelp.get(i).getName() + " " +object.details.commonRestaurants_Yelp.get(i).getPrice());
                        price2.add(i);
                    case("$$$"):
                        price3.add(i);
                    case("$$$$"):
                        price4.add(i);
                }}
                catch (NullPointerException e){
                    Log.e("Name in sort", object.details.commonRestaurants_Yelp.get(i).getName());
                    Boolean pizzaPizza = object.details.commonRestaurants_Yelp.get(i).getName().equals("Pizza Pizza");
                    Boolean BurgerKing = object.details.commonRestaurants_Yelp.get(i).getName().equals("Burger King");
                    Boolean Mcdonalds = object.details.commonRestaurants_Yelp.get(i).getName().equals("McDonald's");
                    Boolean KFC = object.details.commonRestaurants_Yelp.get(i).getName().equals("Kfc");
                    Boolean Hero = object.details.commonRestaurants_Yelp.get(i).getName().equals("Hero Certified Burgers");
                    Boolean SwissChalet = object.details.commonRestaurants_Yelp.get(i).getName().equals("Swiss Chalet");
                    Log.e("Pizza Pizza", pizzaPizza+"");
                    if(pizzaPizza || BurgerKing || Mcdonalds || KFC || SwissChalet){
                        price1.add(i);
                    }
                    if(Hero){
                        price2.add(i);
                    }
                    if(!(pizzaPizza || BurgerKing || Mcdonalds || KFC || Hero || SwissChalet)){
                    price0.add(i);}
                }
            }
                for(Integer integer: price0){
                    setRestaurants(integer,"N/A");
                }
                for(Integer integer: price1){
                    setRestaurants(integer,"$");
                }
                for(Integer integer: price2){
                    Log.e("integer value", integer+"");
                    setRestaurants(integer,"$$");
                }
                for(Integer integer: price3){
                    setRestaurants(integer,"$$$");
                }
                for(Integer integer: price4){
                    setRestaurants(integer,"$$$$");
                }
            }

        else{
            checker = false;
            for(int i =0; i < object.details.test1.address.results.length; i++){
                setRestaurants(i, "N/A");
            }
        }
    }

    private static void priceHigh(){

        if(object.details.commonRestaurants_Yelp.size() != 0){
            ArrayList<Integer> price0 = new ArrayList<>();
            ArrayList<Integer> price1 = new ArrayList<>();
            ArrayList<Integer> price2 = new ArrayList<>();
            ArrayList<Integer> price3 = new ArrayList<>();
            ArrayList<Integer> price4 = new ArrayList<>();

            for (int i =0; i < object.details.commonRestaurants_Yelp.size(); i++){
                try {
                    switch (object.details.commonRestaurants_Yelp.get(i).getPrice()) {
                        case ("$$$$"):
                            price1.add(i);
                        case ("$$$"):
                            price2.add(i);
                        case ("$$"):
                            price3.add(i);
                        case ("$"):
                            price4.add(i);
                    }
                }
                catch (NullPointerException e){
                    price0.add(i);
                }
            }

                for(Integer integer: price0){
                    setRestaurants(integer,"N/A");
                }

                for(Integer integer: price1){
                    setRestaurants(integer,"$");
                }
                for(Integer integer: price2){
                    setRestaurants(integer,"$$");
                }
                for(Integer integer: price3){
                    setRestaurants(integer,"$$$");
                }
                for(Integer integer: price4){
                    setRestaurants(integer,"$$$$");
                }

        }
        else{
            checker = false;
        }
    }

    private static void distanceLow(){

    }

    private static void distanceHigh(){

    }

    private static void timeLow(){

    }

    private static void timeHigh(){

    }

    private static void ratingLow(){

    }

    private static void ratingHigh(){

    }

	public static Restaurant[] distance(Restaurant[] info){
		Comparable[] distances = new Comparable[info.length];

		for (int i =0; i < distances.length; i++){

			try{
			distances[i] = info[i].distance;
			}
			catch (NullPointerException e){
				distances[i] = null;
				continue;
			}
		}
		Quick quick_sort = null;

		distances = RemoveNull.correct(distances);
		quick_sort.sort(distances);

		/**
		 * The <b> RestaurantSort </b> will return the restaurants in the increasing order of <b> distances </b> <i> which was sorted using quicksort in this method</i>.
		 */
		return  RestaurantSort.restSortDist(distances, info);
	}

	public static Restaurant[] time(Restaurant[] info){
		Comparable[] times = new Comparable[info.length];

		for (int i =0; i < times.length; i++){
			try{
			times[i] = info[i].time;
			}
			catch (NullPointerException e){
				times[i] = null;
				continue;
			}
		}
		Quick quick_sort = null;

		times = RemoveNull.correct(times);
		quick_sort.sort(times);

		/**
		 * The <b> RestaurantSort </b> will return the restaurants in the increasing order of <b> times </b> <i> which was sorted using quicksort in this method</i>.
		 */
		return  RestaurantSort.restSortTime(times, info);
	}

	public static Restaurant[] rating(Restaurant[] info){
		Comparable[] ratings = new Comparable[info.length];

		for (int i =0; i < ratings.length; i++){
			try{
			ratings[i] = info[i].rating;
			}
			catch (NullPointerException e){
				ratings[i] = null;
				continue;
			}
		}
		Quick quick_sort = null;

		ratings = RemoveNull.correct(ratings);
		quick_sort.sort(ratings);

		/**
		 * The <b> RestaurantSort </b> will return the restaurants in the increasing order of <b> ratings </b> <i> which was sorted using quicksort in this method</i>.
		 */
		return  RestaurantSort.restSortRating(ratings, info);
	}
}
