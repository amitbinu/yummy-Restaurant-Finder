package com.example.android.yummy.MainActivities;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;


import com.example.android.yummy.Backthread.PopularRestaurants;
import com.example.android.yummy.Backthread.distanceTimeBackThread;
import com.example.android.yummy.Backthread.photoRequest;
import com.example.android.yummy.Backthread.yelp;
import com.example.android.yummy.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStates;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.maps.GeoApiContext;
import com.google.maps.model.PlacesSearchResponse;
import com.google.maps.model.PlacesSearchResult;
import com.yelp.fusion.client.models.Business;

import java.util.ArrayList;

import static com.example.android.yummy.Backthread.yelp.response;
import static com.example.android.yummy.DataManager.GeoCoder.commonRestaurants_Google;
import static com.example.android.yummy.DataManager.GeoCoder.commonRestaurants_Yelp;


public class MainActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener{
    private static GoogleApiClient mGoogleApiClient;
    public static String CITY, COMMUNITY, COUNTRY, STATE;
    public static double latitude, longitude;
    public static PopularRestaurants popularRestaurants;
    private static LocationRequest mLocationRequest;
    private MainActivity object;
    public static photoRequest pictures;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.object = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        String[] PERMISSIONS = {android.Manifest.permission.ACCESS_COARSE_LOCATION,android.Manifest.permission.ACCESS_FINE_LOCATION};
        if (!hasPermissions(this, PERMISSIONS)) {
            ActivityCompat.requestPermissions( this, PERMISSIONS, 112 );
         //   LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
            double starTIime = System.currentTimeMillis();
            while(System.currentTimeMillis()-starTIime < 3000){
            }
            if(!hasPermissions(this, PERMISSIONS)){
                this.finish();
                System.exit(0);
            }
            else{
                onStart();
            }

        }
        else{
        onStart();}
    }

    private static boolean hasPermissions(Context context, String... permissions) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

    protected void onStart(){
        mGoogleApiClient.connect();
        super.onStart();
    }

    protected void onStop(){
        mGoogleApiClient.disconnect();
        super.onStop();
    }

    @Override
    public void onConnected(Bundle bundle) {
        Log.e("STATUS", mGoogleApiClient.isConnected()+"");

        mLocationRequest = LocationRequest.create();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(1);
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder().addLocationRequest(mLocationRequest);
        PendingResult<LocationSettingsResult> result = LocationServices.SettingsApi.checkLocationSettings(mGoogleApiClient ,builder.build());
        result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
            @Override
            public void onResult(LocationSettingsResult locationSettingsResult) {
                final Status status = locationSettingsResult.getStatus();
                final LocationSettingsStates states = locationSettingsResult.getLocationSettingsStates();
                Log.e("States", states.isGpsUsable()+"");

                switch (status.getStatusCode()) {
                    case LocationSettingsStatusCodes.SUCCESS:
                        Location location = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);

                        if(location!=null){
                            try{

                                latitude = location.getLatitude();
                                longitude = location.getLongitude();
                                onStop();
                                UserLocation userLocation = new UserLocation(latitude,longitude);
                                nextActivity(userLocation.cityName, userLocation.communityName, userLocation.stateName, userLocation.countryName);
                            }
                            catch (Exception e){}
                        }
                        else{LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient,mLocationRequest,MainActivity.this);}
                        break;
                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                        try {
                            status.startResolutionForResult(MainActivity.this, 0);
                        }
                        catch (IntentSender.SendIntentException e) {}

                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                        break;
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        switch (resultCode){
            case Activity.RESULT_OK:
                Log.e("User", "pressed OK");
                LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest,this);
                break;
            case Activity.RESULT_CANCELED:
                this.finish();
                System.exit(0);
                break;
            default:
                break;
        }
    }

    @Override
    public void onLocationChanged(Location location){
        try{
            latitude = location.getLatitude();
            longitude = location.getLongitude();
            onStop();
        UserLocation userLocation = new UserLocation(latitude,longitude);
        nextActivity(userLocation.cityName, userLocation.communityName, userLocation.stateName, userLocation.countryName);}
        catch (Exception e){}
        Log.e("RESULT", location.getLatitude() + " " + location.getLongitude());
    }

    @Override
    public void onConnectionSuspended(int i) {
        onStop(); Log.e("STATUS", "Locations services suspended");
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {Log.e("CONNECTION Failed", "ERROR");}

    private void nextActivity(String cityName, String communityName, String stateName, String CountryName){
        onStop();
        CITY = cityName;
        COMMUNITY = communityName;
        STATE = stateName;
        COUNTRY = CountryName;
        Log.d("Starting", "starting the next activity");
      //  yelpcaller = new yelp("Best Restaurants", COMMUNITY + " " + CITY + " " + STATE + " " + COUNTRY, object);
        GeoApiContext context = new GeoApiContext().setApiKey("AIzaSyB9RIqgSUPHG1eg182FbxOamicGXFgjBDA");
        popularRestaurants = new PopularRestaurants(COMMUNITY + " " + CITY + " " + COUNTRY,context, object);
        Intent intent = new Intent(this, Main2Activity.class);
        startActivity(intent);
    }

    private static String[] origin,destinations;
    public void datafetcher(){



        PlacesSearchResponse address = popularRestaurants.address;
    //
     //
        pictures = new photoRequest(popularRestaurants.address);
        origin = new String[1];
        origin[0] = latitude + " "  + longitude;
        destinations = new String[address.results.length];
        for (int i =0; i < destinations.length ; i++){
            destinations[i] = address.results[i].formattedAddress;
        }
        finalCall();
    }


    public static void finalCall(){
        GeoApiContext context = new GeoApiContext().setApiKey("AIzaSyB9RIqgSUPHG1eg182FbxOamicGXFgjBDA");
        new distanceTimeBackThread(origin, destinations, context, Main2Activity.object);
    }
}
