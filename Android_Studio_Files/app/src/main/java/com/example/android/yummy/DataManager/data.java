package com.example.android.yummy.DataManager;
import java.util.Stack;

import com.google.maps.model.Photo;

public class data {
	public static Restaurant[] restaurants;
	private static GeoCoder details;
	private static String[] temp;
    private static Stack<String> foods;
	public data(Stack<String> foods, String origin, String city_name) throws Exception{
        this.foods = foods;
		temp = new String[foods.size()];
		for (int i =0; i < foods.size(); i++){
			temp[i] = foods.get(i);
		}
		
		details = new GeoCoder(city_name , foods, origin, this);}
	public static void results(){
		Restaurant[] info = new Restaurant[details.restaurant_names().size()];
		for(String s : temp){
			foods.push(s);
		}
		
		String food = foods.pop();
		int count =0; //count variable to count the number of nulls in the restaurant_names / distnace_getter() etc. 
		for (int i = 0; i < info.length; i++){
			if (details.restaurant_names().get(i) == null){
				count++;
				if(i != info.length -1){
					food = foods.pop(); 
					continue;
				}
				else{
					break;
				}
			}
			String name  = details.restaurant_names().get(i);
			String address = details.restaurant_addresses().get(i);
			Double distance = details.distance_getter().get(i);
             Double time = details.time_getter().get(i);
			Double rating =details.ratings().get(i);
			Photo[] pictures = details.photos().get(i);
			Boolean open = details.Open().get(i);
			Boolean permanentlyClosed = details.PermanentlyClosed().get(i);
			java.net.URL url = details.URLs().get(i);
			info[i] = new Restaurant(name, address, time, distance, rating, food, pictures, open,permanentlyClosed,url );
		}
		restaurants = new Restaurant[info.length - count];
		
		int index = 0;
		for (int i =0; i < info.length; i ++){
			try{
			if (info[i].restuarant_name != null){
				
				restaurants[index] = info[i];
				index++;
				}
			}
				catch(NullPointerException e){
				continue;
				}
			}
			Result.getter();
		}
		
	
}
