package com.example.android.yummy.DataManager;

import com.example.android.yummy.DataManager.RemoveNull;
import com.example.android.yummy.DataManager.Restaurant;
import com.example.android.yummy.DataManager.RestaurantSort;

import edu.princeton.cs.algs4.Quick;


public class sort {
	
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
