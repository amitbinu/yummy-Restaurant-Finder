package yummy;

import java.util.ArrayList;

/**
 * 
 * @author amitb
 *This class sorts the restaurants based on time/distance/rating. There are three methods to do this. 
 *If the user wants to sort it based on time, first it will call the sort class and sorts the time(s). Then restSortTime in this class is called, which will
* sort the restaurants based on the time and it will return that. 
 */
public class RestaurantSort {
	/**
	 * This method sorts the restaurants based on the distance
	 * @param a is a sorted array of distances
	 * @param info is unsorted array with restaurant objects.
	 * @return is the sorted array that contains the restaurant objects. 
	 */
	public static Restaurant[] restSortDist(Comparable[] a, Restaurant[] info){
		Restaurant[] temp = new Restaurant[a.length];
		
		ArrayList<Double> newA = new ArrayList<Double>();
		
		/**
		 * Adds everything in <i> info </i> to the Arraylist <i> newA </i>, so methods like indexOf and contains can be used. 
		 */
		for (int i =0; i < info.length; i++){
			try{
			newA.add(info[i].distance);
			}
			catch(NullPointerException e){
				newA.add(null);
			}
		}
		
	
		for (int i =0; i< a.length; i++){
			
			int j = newA.indexOf(a[i]);
			temp[i] = info[j];
		}
		
		return temp;
	}
	
	public static Restaurant[] restSortTime(Comparable[] a, Restaurant[] info){
		Restaurant[] temp = new Restaurant[a.length];
		
		ArrayList<Double> newA = new ArrayList<Double>();
		
		/**
		 * Adds everything in <i> info </i> to the Arraylist <i> newA </i>, so methods like indexOf and contains can be used. 
		 */
		for (int i =0; i < info.length; i++){
			try{
			newA.add(info[i].time);
			}
			catch(NullPointerException e){
				newA.add(null);
			}
		}
		
	
		for (int i =0; i< a.length; i++){
			
			int j = newA.indexOf(a[i]);
			temp[i] = info[j];
		}
		
		return temp;
	}
	
	public static Restaurant[] restSortRating(Comparable[] a, Restaurant[] info){
		Restaurant[] temp = new Restaurant[a.length];
		
		ArrayList<Double> newA = new ArrayList<Double>();
		
		/**
		 * Adds everything in <i> info </i> to the Arraylist <i> newA </i>, so methods like indexOf and contains can be used. 
		 */
		for (int i =0; i < info.length; i++){
			try{
			newA.add(info[i].rating);
			}
			catch(NullPointerException e){
				newA.add(null);
			}
		}
		
	
		for (int i =0; i< a.length; i++){
			
			int j = newA.indexOf(a[i]);
			temp[i] = info[j];
		}
		
		return temp;
	}
}
