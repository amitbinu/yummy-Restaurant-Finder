package com.example.android.yummy.DataManager;
import android.graphics.Bitmap;

import com.google.maps.model.PlaceDetails;

public class Restaurant {
	public String restuarant_name, address, price;
	public PlaceDetails moreInfo;
	public Double time, distance, rating;
	public Bitmap photos;
	public Boolean open , permanentlyClosed;
	public Restaurant(String restaurant_name, String address, Double time, Double distance, Double rating, Bitmap samplePhotos, Boolean open, Boolean permanentlyClosed, String price, PlaceDetails placeDetails){
		this.restuarant_name = restaurant_name;
		this.address = address;
		this.time =time;
		this.distance = distance;
		this.rating = rating;
		this.photos = samplePhotos;
		this.open = open;
		this.permanentlyClosed = permanentlyClosed;
		this.price = price;
		this.moreInfo = placeDetails;
	}
}
