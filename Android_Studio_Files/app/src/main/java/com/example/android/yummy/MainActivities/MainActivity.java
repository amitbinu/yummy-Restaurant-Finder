package com.example.android.yummy.MainActivities;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Location;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;


import com.example.android.yummy.Backthread.PopularRestaurants;
import com.example.android.yummy.Backthread.distanceTimeBackThread;
import com.example.android.yummy.DataManager.Restaurant;
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

public class MainActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener{
    private static int REQUEST =112;
    private static GoogleApiClient mGoogleApiClient;
    public static String CITY, COMMUNITY, COUNTRY, STATE;
    public static double latitude, longitude;
    public static PopularRestaurants popularRestaurants;
    private static LocationRequest mLocationRequest;
    public static double[] distances;
    public static double[] times;
    private static Restaurant[] PopularRestaurants;
    private static Bitmap[] bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        String[] PERMISSIONS = {android.Manifest.permission.ACCESS_COARSE_LOCATION,android.Manifest.permission.ACCESS_FINE_LOCATION};
        if (!hasPermissions(this, PERMISSIONS)) {
            ActivityCompat.requestPermissions( this, PERMISSIONS, REQUEST );
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);

        }
        onStart();
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
        Log.e("ONLOCATIONCHANGED", "in locationchanged method");
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
        onStop();
        CITY = cityName;
        COMMUNITY = communityName;
        STATE = stateName;
        COUNTRY = CountryName;
        Log.e("Starting", "starting the next activity");
        Intent intent = new Intent(this, Main2Activity.class);
        GeoApiContext context = new GeoApiContext().setApiKey("AIzaSyB9RIqgSUPHG1eg182FbxOamicGXFgjBDA");
        popularRestaurants = new PopularRestaurants(COMMUNITY,context);
        while(popularRestaurants.address == null){}
        String[] origin ={latitude + " " + longitude};
        String[] destinations = new String[popularRestaurants.address.results.length];
        for (int i =0; i < destinations.length; i++){
            destinations[i] = popularRestaurants.address.results[i].formattedAddress;
        }
        distanceTimeBackThread distancesANDtimes = new distanceTimeBackThread(origin, destinations, context);
        while(distancesANDtimes.result == null){}
        distances = new double[distancesANDtimes.result.rows[0].elements.length];
        times = new double[distancesANDtimes.result.rows[0].elements.length];

        for (int i =0; i < distances.length; i++){
            String distance = distancesANDtimes.result.rows[0].elements[i].distance.humanReadable;
            String[] dist = distance.split("km");
            distances[i] = Double.parseDouble(dist[0]);
            String time = distancesANDtimes.result.rows[0].elements[i].duration.humanReadable;
            String[] tim = time.split("min");
            times[i] = Double.parseDouble(tim[0]);
        }

        startActivity(intent);
    }
}
