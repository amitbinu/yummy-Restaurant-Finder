package com.example.android.yummy;


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

public class MainActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener{
    private static int REQUEST =112;
    private static GoogleApiClient mGoogleApiClient;
    public static String CITY, COMMUNITY, COUNTRY, STATE;
    public static double latitude, longitude;

    private static LocationRequest mLocationRequest;

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
        UserLocation userLocation = new UserLocation(latitude,longitude);
        nextActivity(userLocation.cityName, userLocation.communityName, userLocation.stateName, userLocation.countryName);}
        catch (Exception e){}
        Log.e("RESULT", location.getLatitude() + " " + location.getLongitude());
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.e("STATUS", "Locations services suspended");
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {Log.e("CONNECTION Failed", "ERROR");}

    private void nextActivity(String cityName, String communityName, String stateName, String CountryName){
        onStop();
        CITY = cityName;
        COMMUNITY = communityName;
        STATE = stateName;
        COUNTRY = CountryName;
        Log.e("Starting", "starting the next activity");
        Intent intent = new Intent(this, Main2Activity.class);
        double starTime = System.currentTimeMillis();
        while(System.currentTimeMillis()-starTime < 2500){}
        startActivity(intent);
    }
}
