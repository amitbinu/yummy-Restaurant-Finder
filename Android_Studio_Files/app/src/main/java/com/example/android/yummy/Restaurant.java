package com.example.android.yummy;
import java.net.URL;

import com.google.maps.model.Photo;

public class Restaurant {
	public String restuarant_name;
	public String address;
	public Double time;
	public Double distance;
	public Double rating;
	public String food;
	public Photo[] photos;
	public Boolean open;
	public Boolean permanentlyClosed;
	public URL url;
	public Restaurant(String restaurant_name, String address, Double time, Double distance, Double rating, String food_name, Photo[] samplePhotos, Boolean open, Boolean permanentlyClosed, URL url){
		this.restuarant_name = restaurant_name;
		this.address = address;
		this.time =time;
		this.distance = distance;
		this.rating = rating;
		this.food = food_name;
		this.photos = samplePhotos;
		this.open = open;
		this.permanentlyClosed = permanentlyClosed;
		this.url = url;
	}
	
	public Restaurant(String food_name){
		this.food = food_name;
		this.restuarant_name = null;
		this.distance = null;
		this.time = null;
		this.rating = null;
		this.photos = null;
		this.open = null;
		this.permanentlyClosed = null;
		this.url = null;
	}
}
