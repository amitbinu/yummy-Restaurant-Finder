package com.example.android.yummy.DataManager;
import android.graphics.Bitmap;

import java.net.URL;

import com.google.maps.model.Photo;

public class Restaurant {
	public String restuarant_name;
	public String address;
	public Double time;
	public Double distance;
	public Double rating;
	public String food;
	public Bitmap photos;
	public Boolean open;
	public Boolean permanentlyClosed;
	public Restaurant(String restaurant_name, String address, Double time, Double distance, Double rating, String food_name, Bitmap samplePhotos, Boolean open, Boolean permanentlyClosed){
		this.restuarant_name = restaurant_name;
		this.address = address;
		this.time =time;
		this.distance = distance;
		this.rating = rating;
		this.food = food_name;
		this.photos = samplePhotos;
		this.open = open;
		this.permanentlyClosed = permanentlyClosed;
	}

}
