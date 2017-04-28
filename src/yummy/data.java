package yummy;

import java.util.Stack;

public class data {
	public Restaurant[] restaurants;
	
	public data(Stack<String> foods, String origin, String city_name) throws Exception{
		GeoCoder details = new GeoCoder(city_name , foods, origin);
		Restaurant[] info = new Restaurant[details.distance_getter().size()];
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
			
			info[i] = new Restaurant(name, address, time, distance, rating, food);
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
		}
		
	
}
