package com.example.android.yummy.DataManager;

import android.graphics.Bitmap;
import android.util.Log;

import com.example.android.yummy.MainActivities.Result;
import com.google.android.gms.location.places.Place;
import com.google.maps.model.PlaceDetails;
import com.google.maps.model.PlacesSearchResult;
import java.util.ArrayList;
import edu.princeton.cs.algs4.Quick;


public class sort {
    public static Boolean checker = true;
    private static data object;
    private static GeoCoder details;
    public static ArrayList<Restaurant> Sortedrestaurants;
    private static ArrayList<Double> tempDistance = new ArrayList<>();
    private static ArrayList<Double> tempTime = new ArrayList<>();
    private static ArrayList<Double> tempRating = new ArrayList<>();
    private static ArrayList<Bitmap> tempPictures = new ArrayList<>();
    private static ArrayList<PlacesSearchResult> tempRestaurants = new ArrayList<>();
    private static ArrayList<PlaceDetails> tempMoreInfo = new ArrayList<>();
	public sort(data object, String sortValue){
        details = object.details;
        tempDistance = new ArrayList<>();
        tempTime = new ArrayList<>();
        tempRating = new ArrayList<>();
        tempPictures = new ArrayList<>();
        tempRestaurants = new ArrayList<>();
        tempMoreInfo = new ArrayList<>();
        for(int i =0; i < details.distance_getter().size(); i++){
            tempDistance.add(details.distance_getter().get(i));
            tempTime.add(details.time_getter().get(i));
            tempRating.add(details.ratings().get(i));
            tempPictures.add(details.pictures.pictures1.get(i));
            tempRestaurants.add(details.commonRestaurants_Google.get(i));
            Log.e("detailedInfo",details.detailedInfo.get(i).name);
            tempMoreInfo.add(details.detailedInfo.get(i));
        }
        this.object = object;
        this.Sortedrestaurants = new ArrayList<>();
        Result.updating("Sorting the restaurants ...");
        switch (sortValue){
            case("Distance(low-high)"):
                distanceLow();
                break;
            case("Distance(high-low)"):
                distanceHigh();
                break;
            case("Rating(high-low)"):
                ratingHigh();
                break;
            case("Rating(low-high)"):
                ratingLow();
                break;
            case("Time(low-high)"):
                timeLow();
                break;
            case("Time(high-low)"):
                timeHigh();
                break;
        }
    }


  private static void distanceLow(){
        Double[] distance = new Double[details.distance_getter().size()];
        for(int i =0; i < distance.length; i++){
            distance[i] = details.distance_getter().get(i);
        }
        Quick.sort(distance);

        for (double dist : distance){
            int index = tempDistance.indexOf(dist);
            PlaceDetails moreInfo = tempMoreInfo.get(index);
            String name = tempRestaurants.get(index).name;
            String address = tempRestaurants.get(index).formattedAddress;
            Double time = tempTime.get(index);
            Double rating = tempRating.get(index);
            Bitmap picture = tempPictures.get(index);
            Boolean open;
            String price;
            try{
                price = "N/A";
            }
            catch (Exception e){
                checker = false;
                price = "N/A";
            }
            try {
                open = object.details.commonRestaurants_Google.get(index).openingHours.openNow;
            }
            catch (Exception e){
                open = null;
            }
            Boolean permanent = object.details.commonRestaurants_Google.get(index).permanentlyClosed;
            Log.e("placeDetails-Name",moreInfo.name+" " + index);
            Sortedrestaurants.add(new Restaurant(name,address,time,dist,rating,picture,open,permanent,price,moreInfo));
            tempDistance.remove(index);
            tempRestaurants.remove(index);
            tempTime.remove(index);
            tempRating.remove(index);
            tempPictures.remove(index);
            tempMoreInfo.remove(index);
        }
    }

    private static void distanceHigh(){
        Double[] distance = new Double[details.distance_getter().size()];
        for(int i =0; i < distance.length; i++){
            distance[i] = details.distance_getter().get(i);
        }
        Quick.sort(distance);
        for (int i = distance.length-1; i >=0; i--){
            double dist = distance[i];
            int index = tempDistance.indexOf(dist);
            PlaceDetails placeDetails = tempMoreInfo.get(index);
            String name = tempRestaurants.get(index).name;
            String address = tempRestaurants.get(index).formattedAddress;
            Double time = tempTime.get(index);
            Double rating = tempRating.get(index);
            Bitmap picture = tempPictures.get(index);
            Boolean open;
            String price;
            try{
                price = "N/A";
            }
            catch (Exception e){
                checker = false;
                price = "N/A";
            }
            try {
                open = object.details.commonRestaurants_Google.get(index).openingHours.openNow;
            }
            catch (Exception e){
                open = null;
            }
            Boolean permanent = object.details.commonRestaurants_Google.get(index).permanentlyClosed;
            Sortedrestaurants.add(new Restaurant(name,address,time,dist,rating,picture,open,permanent,price,placeDetails));
            tempDistance.remove(index);
            tempRestaurants.remove(index);
            tempTime.remove(index);
            tempRating.remove(index);
            tempPictures.remove(index);
        }

    }

    private static void timeLow(){
        Double[] time =new Double[tempTime.size()];

        for(int i =0 ; i<time.length; i++){
            time[i] = tempTime.get(i);
        }
        Quick.sort(time);
        for (double hours: time){
            int index = tempTime.indexOf(hours);
            PlaceDetails placeDetails = tempMoreInfo.get(index);
            String name = tempRestaurants.get(index).name;
            String address = tempRestaurants.get(index).formattedAddress;
            Double distance = tempDistance.get(index);
            Double rating = tempRating.get(index);
            Bitmap picture = tempPictures.get(index);
            Boolean open;
            String price;
            try{
                price = "N/A";
            }
            catch (Exception e){
                checker = false;
                price = "N/A";
            }
            try {
                open = object.details.commonRestaurants_Google.get(index).openingHours.openNow;
            }
            catch (Exception e){
                open = null;
            }
            Boolean permanent = object.details.commonRestaurants_Google.get(index).permanentlyClosed;
            Sortedrestaurants.add(new Restaurant(name,address,hours,distance,rating,picture,open,permanent,price,placeDetails));
            tempDistance.remove(index);
            tempRestaurants.remove(index);
            tempTime.remove(index);
            tempRating.remove(index);
            tempPictures.remove(index);
        }
    }

    private static void timeHigh(){
        Double[] time =new Double[tempTime.size()];
        for(int i =0 ; i<time.length; i++){
            time[i] = tempTime.get(i);
        }
        Quick.sort(time);
        for (int i = time.length-1; i >=0; i--){
            double hours = time[i];
            int index = tempTime.indexOf(hours);
            PlaceDetails placeDetails = tempMoreInfo.get(index);
            String name = tempRestaurants.get(index).name;
            String address = tempRestaurants.get(index).formattedAddress;
            Double distance = tempDistance.get(index);
            Double rating = tempRating.get(index);
            Bitmap picture = tempPictures.get(index);
            Boolean open;
            String price;
            try{
                price = "N/A";
            }
            catch (Exception e){
                checker = false;
                price = "N/A";
            }
            try {
                open = object.details.commonRestaurants_Google.get(index).openingHours.openNow;
            }
            catch (Exception e){
                open = null;
            }
            Boolean permanent = object.details.commonRestaurants_Google.get(index).permanentlyClosed;
            Sortedrestaurants.add(new Restaurant(name,address,hours,distance,rating,picture,open,permanent,price,placeDetails));
            tempDistance.remove(index);
            tempRestaurants.remove(index);
            tempTime.remove(index);
            tempRating.remove(index);
            tempPictures.remove(index);
        }
    }

    private static void ratingLow(){
        Double[] rating = new Double[tempRating.size()];
        for(int i =0; i<rating.length; i++){
            rating[i] = tempRating.get(i);
        }
        Quick.sort(rating);
        for (Double rate : rating){
            int index = tempRating.indexOf(rate);
            PlaceDetails placeDetails = tempMoreInfo.get(index);
            String name = tempRestaurants.get(index).name;
            String address = tempRestaurants.get(index).formattedAddress;
            Double distance = tempDistance.get(index);
            Double time = tempTime.get(index);
            Bitmap picture = tempPictures.get(index);
            Boolean open;
            String price;
            try{
                price = "N/A";
            }
            catch (Exception e){
                checker = false;
                price = "N/A";
            }
            try {
                open = object.details.commonRestaurants_Google.get(index).openingHours.openNow;
            }
            catch (Exception e){
                open = null;
            }
            Boolean permanent = object.details.commonRestaurants_Google.get(index).permanentlyClosed;
            Sortedrestaurants.add(new Restaurant(name,address,time,distance,rate,picture,open,permanent,price,placeDetails));
            tempDistance.remove(index);
            tempRestaurants.remove(index);
            tempTime.remove(index);
            tempRating.remove(index);
            tempPictures.remove(index);
        }
    }

    private static void ratingHigh(){
        Double[] rating = new Double[tempRating.size()];
        for(int i =0; i<rating.length; i++){
            rating[i] = tempRating.get(i);
        }
        Quick.sort(rating);
        for (int i = rating.length-1; i >= 0; i--){
            Double rate = rating[i];
            int index = tempRating.indexOf(rate);
            PlaceDetails placeDetails = tempMoreInfo.get(index);
            String name = tempRestaurants.get(index).name;
            String address = tempRestaurants.get(index).formattedAddress;
            Double distance = tempDistance.get(index);
            Double time = tempTime.get(index);
            Bitmap picture = tempPictures.get(index);
            Boolean open;
            String price;
            try{
                price = "N/A";
            }
            catch (Exception e){
                checker = false;
                price = "N/A";
            }
            try {
                open = object.details.commonRestaurants_Google.get(index).openingHours.openNow;
            }
            catch (Exception e){
                open = null;
            }
            Boolean permanent = object.details.commonRestaurants_Google.get(index).permanentlyClosed;
            Sortedrestaurants.add(new Restaurant(name,address,time,distance,rate,picture,open,permanent,price,placeDetails));
            tempDistance.remove(index);
            tempRestaurants.remove(index);
            tempTime.remove(index);
            tempRating.remove(index);
            tempPictures.remove(index);
        }
    }
}
