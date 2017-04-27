package yummy;

import java.util.Stack;

public class tests {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		Stack<String> foods = new Stack<String>();
		foods.push("Burger");
		foods.push("Pizza");
		double start = System.currentTimeMillis();
		data test = new data(foods, "2024 Aldermead Rd", "Mississauga");
		double end = System.currentTimeMillis();
		System.out.println((end- start)/1000); 
		
		
		System.out.println("  ");
	/*	for (Restaurant i : test.restaurants){
			System.out.println(i.restuarant_name);
		} */
		
		System.out.println(test.restaurants.toString());
	}

}
