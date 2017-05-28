package com.example.android.yummy.DataManager;

import android.graphics.Bitmap;
import android.util.Log;
import java.util.ArrayList;
import edu.princeton.cs.algs4.Quick;
import static com.example.android.yummy.DataManager.data.details;


public class sort {
    public static Boolean checker = true;
    private static data object;
    public static ArrayList<Restaurant> Sortedrestaurants;
    private static ArrayList<Integer> price0 = new ArrayList<>();
    private static ArrayList<Integer> price1 = new ArrayList<>();
    private static ArrayList<Integer> price2 = new ArrayList<>();
    private static ArrayList<Integer> price3 = new ArrayList<>();
    private static ArrayList<Integer> price4 = new ArrayList<>();
	public sort(data object, String sortValue){
        price0 = new ArrayList<>();
        price1 = new ArrayList<>();
        price2 = new ArrayList<>();
        price3 = new ArrayList<>();
        price4 = new ArrayList<>();
        this.object = object;
        this.Sortedrestaurants = new ArrayList<>();
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

        String name = details.commonRestaurants_Google.get(i).name;
        Log.e("checking", i + " " +name );
        String address = details.commonRestaurants_Google.get(i).formattedAddress;
        Double time = details.time_getter().get(i);
        Double distance = details.distance_getter().get(i);
        Double rating = details.ratings().get(i);
        Bitmap picture = details.pictures.pictures1.get(i);
        Boolean open;
        try{
            open = object.details.commonRestaurants_Google.get(i).openingHours.openNow;
        }
        catch (NullPointerException e){
            open = null;
        }
        Boolean permanent = object.details.commonRestaurants_Google.get(i).permanentlyClosed;
        Sortedrestaurants.add(new Restaurant(name, address,time, distance, rating, null, picture, open, permanent, price));
    }

    private static void priceLow(){
        if(details.commonRestaurants_Yelp.size() != 0){
            for (int i =0; i < details.pictures.pictures1.size(); i++){
                try{
                    String price = details.commonRestaurants_Yelp.get(i).getPrice();
                    if(price.equals("$")){
                        price1.add(i);
                    }
                    if(price.equals("$$")){
                        price2.add(i);
                    }
                    if(price.equals("$$$")){
                        price3.add(i);
                    }
                    if(price.equals("$$$$")){
                        price4.add(i);
                    }
                }
                catch (Exception e){
                    Boolean pizzaPizza = details.commonRestaurants_Google.get(i).name.equals("Pizza Pizza");
                    Boolean BurgerKing = details.commonRestaurants_Google.get(i).name.equals("Burger King");
                    Boolean Mcdonalds = details.commonRestaurants_Google.get(i).name.equals("McDonald's");
                    Boolean KFC = details.commonRestaurants_Google.get(i).name.equals("KFC");
                    Boolean Hero = details.commonRestaurants_Google.get(i).name.equals("Hero Certified Burgers");
                    Boolean SwissChalet = details.commonRestaurants_Google.get(i).name.equals("Swiss Chalet");
                    Boolean TimHortons = details.commonRestaurants_Google.get(i).name.equals("Tim Hortons");
                    Boolean Starbucks = details.commonRestaurants_Google.get(i).name.equals("Starbucks");
                    Boolean Pizzaville = details.commonRestaurants_Google.get(i).name.equals("Pizzaville");
                    Boolean Aw = details.commonRestaurants_Google.get(i).name.equals("A&W");
                    if(pizzaPizza || BurgerKing || Mcdonalds || KFC || SwissChalet || TimHortons){
                        price1.add(i);
                    }
                    if(Hero || Starbucks || Pizzaville ||Aw){
                        price2.add(i);
                    }
                    if(!(pizzaPizza || BurgerKing || Mcdonalds || KFC || Hero || SwissChalet || TimHortons || Starbucks || Pizzaville ||Aw)){
                    price0.add(i);}
                }
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
            for(Integer integer: price0){
                setRestaurants(integer,"N/A");
            }
            if(price0.size()>0){
                checker = false;
            }
            }

        else{
            checker = false;
            for(int i =0; i < details.commonRestaurants_Google.size(); i++){
                setRestaurants(i, "N/A");
            }
        }
    }

    private static void priceHigh(){
        if(details.commonRestaurants_Yelp.size() != 0){
            for (int i =0; i < details.pictures.pictures1.size(); i++){
                try{
                    String price = details.commonRestaurants_Yelp.get(i).getPrice();
                    if(price.equals("$")){
                        price1.add(i);
                    }
                    if(price.equals("$$")){
                        price2.add(i);
                    }
                    if(price.equals("$$$")){
                        price3.add(i);
                    }
                    if(price.equals("$$$$")){
                        price4.add(i);
                    }
                }
                catch (Exception e){
                    Boolean pizzaPizza = details.commonRestaurants_Google.get(i).name.equals("Pizza Pizza");
                    Boolean BurgerKing = details.commonRestaurants_Google.get(i).name.equals("Burger King");
                    Boolean Mcdonalds = details.commonRestaurants_Google.get(i).name.equals("McDonald's");
                    Boolean KFC = details.commonRestaurants_Google.get(i).name.equals("KFC");
                    Boolean Hero = details.commonRestaurants_Google.get(i).name.equals("Hero Certified Burgers");
                    Boolean SwissChalet = details.commonRestaurants_Google.get(i).name.equals("Swiss Chalet");
                    Boolean TimHortons = details.commonRestaurants_Google.get(i).name.equals("Tim Hortons");
                    Boolean Starbucks = details.commonRestaurants_Google.get(i).name.equals("Starbucks");
                    Boolean Pizzaville = details.commonRestaurants_Google.get(i).name.equals("Pizzaville");
                    Boolean Aw = details.commonRestaurants_Google.get(i).name.equals("A&W");
                    if(pizzaPizza || BurgerKing || Mcdonalds || KFC || SwissChalet || TimHortons){
                        price1.add(i);
                    }
                    if(Hero || Starbucks || Pizzaville ||Aw){
                        price2.add(i);
                    }
                    if(!(pizzaPizza || BurgerKing || Mcdonalds || KFC || Hero || SwissChalet || TimHortons || Starbucks || Pizzaville ||Aw)){
                        price0.add(i);}
                }
            }
            for(Integer integer: price4){
                setRestaurants(integer,"$$$$");
            }
            for(Integer integer: price3){
                setRestaurants(integer,"$$$");
            }
            for(Integer integer: price2){
                setRestaurants(integer,"$$");
            }
            for(Integer integer: price1){
                setRestaurants(integer,"$");
            }
            for(Integer integer: price0){
                setRestaurants(integer,"N/A");
            }
            if(price0.size()>0){
                checker = false;
            }
        }
    }

    private static void distanceLow(){
        Double[] distance =(Double[]) details.distance_getter().toArray();
        Quick.sort(distance);
        for (double dist : distance){
            int index = details.distance_getter().indexOf(dist);
            String name = details.commonRestaurants_Google.get(index).name;
            String address = details.commonRestaurants_Google.get(index).formattedAddress;
            Double time = details.time_getter().get(index);
            Double rating = details.ratings().get(index);
            Bitmap picture = details.pictures.pictures1.get(index);
            Boolean open;
            String price;
            try{
                price = object.details.commonRestaurants_Yelp.get(index).getPrice();
                open = object.details.commonRestaurants_Google.get(index).openingHours.openNow;
            }
            catch (NullPointerException e){
                checker = false;
                price = "N/A";
                open = null;
            }
            Boolean permanent = object.details.commonRestaurants_Google.get(index).permanentlyClosed;
            Sortedrestaurants.add(new Restaurant(name,address,time,dist,rating,null,picture,open,permanent,price));
        }
    }

    private static void distanceHigh(){
        Double[] distance =(Double[]) details.distance_getter().toArray();
        Quick.sort(distance);
        for (int i = distance.length-1; i >=0; i--){
            double dist = distance[i];
            int index = details.distance_getter().indexOf(dist);
            String name = details.commonRestaurants_Google.get(index).name;
            String address = details.commonRestaurants_Google.get(index).formattedAddress;
            Double time = details.time_getter().get(index);
            Double rating = details.ratings().get(index);
            Bitmap picture = details.pictures.pictures1.get(index);
            Boolean open;
            String price;
            try{
                price = object.details.commonRestaurants_Yelp.get(index).getPrice();
                open = object.details.commonRestaurants_Google.get(index).openingHours.openNow;
            }
            catch (NullPointerException e){
                checker = false;
                price = "N/A";
                open = null;
            }
            Boolean permanent = object.details.commonRestaurants_Google.get(index).permanentlyClosed;
            Sortedrestaurants.add(new Restaurant(name,address,time,dist,rating,null,picture,open,permanent,price));
        }
    }

    private static void timeLow(){
        Double[] time =(Double[]) details.time_getter().toArray();
        Quick.sort(time);
        for (double hours: time){
            int index = details.time_getter().indexOf(hours);
            String name = details.commonRestaurants_Google.get(index).name;
            String address = details.commonRestaurants_Google.get(index).formattedAddress;
            Double distance = details.distance_getter().get(index);
            Double rating = details.ratings().get(index);
            Bitmap picture = details.pictures.pictures1.get(index);
            Boolean open;
            String price;
            try{
                price = object.details.commonRestaurants_Yelp.get(index).getPrice();
                open = object.details.commonRestaurants_Google.get(index).openingHours.openNow;
            }
            catch (NullPointerException e){
                checker = false;
                price = "N/A";
                open = null;
            }
            Boolean permanent = object.details.commonRestaurants_Google.get(index).permanentlyClosed;
            Sortedrestaurants.add(new Restaurant(name,address,hours,distance,rating,null,picture,open,permanent,price));
        }
    }

    private static void timeHigh(){
        Double[] time =(Double[]) details.time_getter().toArray();
        Quick.sort(time);
        for (int i = time.length; i >=0; i--){
            double hours = time[i];
            int index = details.time_getter().indexOf(hours);
            String name = details.commonRestaurants_Google.get(index).name;
            String address = details.commonRestaurants_Google.get(index).formattedAddress;
            Double distance = details.distance_getter().get(index);
            Double rating = details.ratings().get(index);
            Bitmap picture = details.pictures.pictures1.get(index);
            Boolean open;
            String price;
            try{
                price = object.details.commonRestaurants_Yelp.get(index).getPrice();
                open = object.details.commonRestaurants_Google.get(index).openingHours.openNow;
            }
            catch (NullPointerException e){
                checker = false;
                price = "N/A";
                open = null;
            }
            Boolean permanent = object.details.commonRestaurants_Google.get(index).permanentlyClosed;
            Sortedrestaurants.add(new Restaurant(name,address,hours,distance,rating,null,picture,open,permanent,price));
        }
    }

    private static void ratingLow(){
        Double[] rating =(Double[]) details.ratings().toArray();
        Quick.sort(rating);
        for (Double rate : rating){
            int index = details.ratings().indexOf(rate);
            String name = details.commonRestaurants_Google.get(index).name;
            String address = details.commonRestaurants_Google.get(index).formattedAddress;
            Double distance = details.distance_getter().get(index);
            Double time = details.time_getter().get(index);
            Bitmap picture = details.pictures.pictures1.get(index);
            Boolean open;
            String price;
            try{
                price = object.details.commonRestaurants_Yelp.get(index).getPrice();
                open = object.details.commonRestaurants_Google.get(index).openingHours.openNow;
            }
            catch (NullPointerException e){
                checker = false;
                price = "N/A";
                open = null;
            }
            Boolean permanent = object.details.commonRestaurants_Google.get(index).permanentlyClosed;
            Sortedrestaurants.add(new Restaurant(name,address,time,distance,rate,null,picture,open,permanent,price));
        }
    }

    private static void ratingHigh(){
        Double[] rating =(Double[]) details.ratings().toArray();
        Quick.sort(rating);
        for (int i = rating.length-1; i >= 0; i--){
            Double rate = rating[i];
            int index = details.ratings().indexOf(rate);
            String name = details.commonRestaurants_Google.get(index).name;
            String address = details.commonRestaurants_Google.get(index).formattedAddress;
            Double distance = details.distance_getter().get(index);
            Double time = details.time_getter().get(index);
            Bitmap picture = details.pictures.pictures1.get(index);
            Boolean open;
            String price;
            try{
                price = object.details.commonRestaurants_Yelp.get(index).getPrice();
                open = object.details.commonRestaurants_Google.get(index).openingHours.openNow;
            }
            catch (NullPointerException e){
                checker = false;
                price = "N/A";
                open = null;
            }
            Boolean permanent = object.details.commonRestaurants_Google.get(index).permanentlyClosed;
            Sortedrestaurants.add(new Restaurant(name,address,time,distance,rate,null,picture,open,permanent,price));
        }
    }
}
