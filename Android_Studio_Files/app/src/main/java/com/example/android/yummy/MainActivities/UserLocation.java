package com.example.android.yummy.MainActivities;

import android.util.Log;

import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.model.GeocodingResult;
import com.google.maps.model.LatLng;

/**
 * Created by amitb on 2017-05-08.
 */

public class UserLocation {

    public static String communityName, cityName, stateName, countryName;

    private static GeoApiContext context;
    public UserLocation(double latitude,double longitude) throws Exception{

        LatLng location = new LatLng(latitude, longitude);
        context = new GeoApiContext().setApiKey("AIzaSyB9RIqgSUPHG1eg182FbxOamicGXFgjBDA");
        GeocodingApi geolocationApi = null;

        GeocodingResult[] geocodingApiRequest = geolocationApi.reverseGeocode(context,location).await();

        communityName = geocodingApiRequest[0].addressComponents[2].longName;
        cityName = geocodingApiRequest[0].addressComponents[3].longName;
        stateName = geocodingApiRequest[0].addressComponents[5].longName;
        countryName = geocodingApiRequest[0].addressComponents[6].longName;
        Log.e("ADDRESS", "WORKED" );


    }
}