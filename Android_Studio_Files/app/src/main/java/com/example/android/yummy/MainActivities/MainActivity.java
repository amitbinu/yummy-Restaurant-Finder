package com.example.android.yummy.MainActivities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.yummy.DataManager.Constants;
import com.example.android.yummy.apiCalls.PopularRestaurants;
import com.example.android.yummy.apiCalls.distanceTimeBackThread;
import com.example.android.yummy.apiCalls.photoRequest;
import com.example.android.yummy.R;
import com.example.android.yummy.apiCalls.UserLocation;
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

public class MainActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener{
    private static GoogleApiClient mGoogleApiClient;
    public static String CITY, COMMUNITY, COUNTRY, STATE;
    public static double latitude, longitude;
    public static PopularRestaurants popularRestaurants;
    private static LocationRequest mLocationRequest;
    public static MainActivity object;
    public static photoRequest pictures;
    private static TextView textView;
    private static ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.object = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressBar = (ProgressBar) findViewById(R.id.progressBar3);
        progressBar.setVisibility(View.VISIBLE);
        textView = (TextView) findViewById(R.id.Update);
        textView.setText("Getting your Location ...");
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        String[] PERMISSIONS = {android.Manifest.permission.ACCESS_COARSE_LOCATION,android.Manifest.permission.ACCESS_FINE_LOCATION};
        if (!hasPermissions(this, PERMISSIONS)) {
            ActivityCompat.requestPermissions( this, PERMISSIONS, 112 );
            double starTIime = System.currentTimeMillis();
            while(System.currentTimeMillis()-starTIime < 3000){
            }
            if(!hasPermissions(this, PERMISSIONS)){
                this.finish();
                System.exit(0);
            }
            else{
                if(isOnline() == true){
                    onStart();
                }
                else{
                    Toast.makeText(this,"Need Internet to work",Toast.LENGTH_LONG).show();
                    Intent settingsIntent = new Intent(Settings.ACTION_WIFI_SETTINGS);
                    startActivityForResult(settingsIntent, 9003);
                    onBackPressed();
                }
            }
        }
        else{
            if(isOnline() == true){
                onStart();
            }
            else{
                Toast.makeText(this,"Need Internet to work",Toast.LENGTH_LONG).show();
                Intent settingsIntent = new Intent(Settings.ACTION_WIFI_SETTINGS);
                startActivityForResult(settingsIntent, 9003);
                onBackPressed();
            }
        }
    }

    private Boolean isOnline(){
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
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
    }

    @Override
    public void onConnectionSuspended(int i) {
        onStop();
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {}

    private void nextActivity(String cityName, String communityName, String stateName, String CountryName){
        textView.setText("Gathering info about near by restaurants ...");
        onStop();
        CITY = cityName;
        COMMUNITY = communityName;
        STATE = stateName;
        COUNTRY = CountryName;
        GeoApiContext context = new GeoApiContext().setApiKey(Constants.ApiKey);
        popularRestaurants = new PopularRestaurants(COMMUNITY + " " + CITY + " " + COUNTRY,context, object);

    }

    private static String[] origin,destinations;

    public void startNextActivity(){
        Intent intent = new Intent(this, Main2Activity.class);
        startActivity(intent);
    }
    public static void datafetcher(){
        PlacesSearchResponse address = popularRestaurants.address;
        pictures = new photoRequest(popularRestaurants.address);
        origin = new String[1];
        origin[0] = latitude + " "  + longitude;
        destinations = new String[address.results.length];
        for (int i =0; i < destinations.length ; i++){
            destinations[i] = address.results[i].formattedAddress;
        }
        GeoApiContext context = new GeoApiContext().setApiKey(Constants.ApiKey);
        new distanceTimeBackThread(origin, destinations, context, Main2Activity.object);
    }
}
