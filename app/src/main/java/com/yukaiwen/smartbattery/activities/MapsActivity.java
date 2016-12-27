package com.yukaiwen.smartbattery.activities;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.yukaiwen.smartbattery.R;
import com.yukaiwen.smartbattery.fragments.MainFragment;

public class MapsActivity extends FragmentActivity implements GoogleApiClient.OnConnectionFailedListener, GoogleApiClient.ConnectionCallbacks, LocationListener{

    final int PERMISSION_LOCATION = 111; //arbitrary number
    private GoogleApiClient mGoogleApiClient;
    private MainFragment mainFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        // create the mGoogleApiClient
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this/* FragmentActivity */, this/* OnConnectionFailedListener */)
                .addConnectionCallbacks(this)
                .addApi(LocationServices.API)
                .build();

        // load the MainFragment
        mainFragment = (MainFragment) getSupportFragmentManager().findFragmentById(R.id.container_main);
        // if the mainFragment doesn't exist, create an instance of mainFragment
        if(mainFragment == null) {
            mainFragment = MainFragment.newInstance();
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.container_main, mainFragment)
                    .commit();
        }
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        /**
         * In order to connect the service, we should first check the permission. Otherwise, we'll prompt to user to give permission.
         * checkSelfPermission : to check the permission so that we can use the service
         * ACCESS_FINE_LOCATION : very detail
         * PERMISSION_GRANTED : means the permission gived
         */
        if (ContextCompat.checkSelfPermission(this/*this activity*/, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this/*this context*/, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_LOCATION/*arbitrary number as id, just for later check which service being used*/);
            Log.v("DONKEY", "Requesting permissions");
        } else {
            // if the permission exists, start the connection.
            Log.v("DONKEY", "Starting Location Services from onConnected");
            startLocationServices();
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {
        Log.v("DONKEY", "Long: " + location.getLongitude() + "Lat: " + location.getLatitude());
        mainFragment.setUserMarker(new LatLng(location.getLongitude(), location.getLatitude())); //show the user's current location
    }

    /**
     * After created the mGoogleApiClient,
     * it'll call this function to connect the mGoogleApiClient
     */
    @Override
    protected void onStart() {
        mGoogleApiClient.connect();
        super.onStart();
    }

    /**
     * this function is to disconnect the mGoogleApiClient
     */
    @Override
    protected void onStop() {
        mGoogleApiClient.disconnect();
        super.onStop();
    }

    /**
     * In response to the first condition of onConnected(),
     * when the permission isn't given, a prompt will pop up to ask the permission.
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case PERMISSION_LOCATION: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    startLocationServices();
                    Log.v("DONKEY", "Permission Granted - starting services");
                } else {
                    //show a dialog showing something like, "I can't run your location dummy - you denied permission!"
                    Log.v("DONKEY", "Permission not granted");
                }
            }
        }
    }

    public void startLocationServices() {
        // Show information in Android Monitor to let me know if it starts the location service
        Log.v("DONKEY", "Starting Location Services Called");

        /**
         * LocationServices.FusedLocationApi.requestLocationUpdates
         * needs the user to give permission to app.
         * So we need try-catch to get the exception
         * if the user doesn't give the permission.
         */
        try {
            LocationRequest req = LocationRequest.create().setPriority(LocationRequest.PRIORITY_LOW_POWER);
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, req, this/* callback, the listener*/);
            Log.v("DONKEY", "Requesting location updates");
        } catch (SecurityException exception) {
            // Show dialog to user saying we can't get location unless they give app permission
            Log.v("DONKEY", exception.toString());
        }


    }
}
