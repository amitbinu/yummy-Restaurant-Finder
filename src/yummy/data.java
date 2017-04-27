package yummy;

import java.util.Stack;

public class data {
	public Restaurant[] restaurants;
	
	public data(Stack<String> foods, String origin, String city_name) throws Exception{
		GeoCoder data = new GeoCoder(city_name , foods, origin);
		Restaurant[] info = new Restaurant[data.distance_getter().size()];
	//	System.out.println(data.distance_getter().size() + " This is the size of info");
		String food = foods.pop();
		int count =0; //count variable to count the number of nulls in the restaurant_names / distnace_getter() etc. 
		for (int i = 0; i < info.length; i++){
			if (data.restaurant_names().get(i) == null){
				count++;
				if(i != info.length -1){
					System.out.println("This is the i value of null" + i);
					food = foods.pop(); 
					continue;
				}
				else{
					break;
				}
			}
			String name  = data.restaurant_names().get(i);
			String address = data.address().get(i);
			Double distance = data.distance_getter().get(i);
			Double time = data.time_getter().get(i);
			Double rating =data.ratings().get(i);
			System.out.println("");
			System.out.println(name + " "  + address + " " + distance + " " + time + " " + rating);
			info[i] = new Restaurant(name, address, time, distance, rating, food);
		}
		restaurants = new Restaurant[info.length - count];
		
		int index = 0;
		for (int i =0; i < info.length; i ++){
			if (info[i].restuarant_name != null){
				restaurants[index] = info[i];
				index++;
			}
		}
		
	}
}
