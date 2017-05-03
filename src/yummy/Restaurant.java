package yummy;

import com.google.maps.model.Photo;

public class Restaurant {
	public String restuarant_name;
	public String address;
	public Double time;
	public Double distance;
	public Double rating;
	public String food;
	public Photo[] photos;
	public Restaurant(String restaurant_name, String address, Double time, Double distance, Double rating, String food_name, Photo[] samplePhotos){
		this.restuarant_name = restaurant_name;
		this.address = address;
		this.time =time;
		this.distance = distance;
		this.rating = rating;
		this.food = food_name;
		this.photos = samplePhotos;
	}
	
	public Restaurant(String food_name){
		this.food = food_name;
		this.restuarant_name = null;
		this.distance = null;
		this.time = null;
		this.rating = null;
		this.photos = null;
	}
}
