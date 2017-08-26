package com.example.sabiha.weather;

import android.app.Activity;
import android.content.Context;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

/**
 * Created by Sabiha on 8/24/2017.
 */

public class LocationDetailsPlayService  implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, LocationListener {

    public abstract interface LocationCallback {
        public void handleNewLocation(Location location);
    }

    private Context locContext;
    private GoogleApiClient locGoogleApiClient;
    //private Location location;
    private LocationRequest locRequest;
    private double longitude, latitude;
    private LocationCallback mLocationCallback;

    public LocationDetailsPlayService(Context context, LocationCallback callback)
    {
        locContext = context;
        mLocationCallback = callback;
        locGoogleApiClient = new GoogleApiClient.Builder(locContext)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();

        locRequest = new LocationRequest();
        locRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locRequest.setInterval(AppConstants.UPDATE_INTERVAL);
        locRequest.setFastestInterval(AppConstants.FASTEST_INTERVAL);
    }

    public double getLongitude()
    {
        return longitude;
    }

    public double getLatitude()
    {
        return latitude;
    }

    public void startConnection()
    {
        if (locGoogleApiClient != null) {
            locGoogleApiClient.connect();
        }
    }

    public void stopConnection()
    {
        if (locGoogleApiClient.isConnected()) {
            LocationServices.FusedLocationApi.removeLocationUpdates(locGoogleApiClient, this);
            locGoogleApiClient.disconnect();
        }
    }

    public void getLocationUpdate()
    {

        if(Permission.check_FINE_LOCATION_Permission((Activity) locContext, AppConstants.LOCATION_PERMISSION_REQUEST_CODE)) {
            Location location = LocationServices.FusedLocationApi.getLastLocation(locGoogleApiClient);
            //if (location == null) {
            LocationServices.FusedLocationApi.requestLocationUpdates(locGoogleApiClient, locRequest, this);
            //} else {
            mLocationCallback.handleNewLocation(location);
            //}
        }

    }

    /*public void handleNewLocation(Location location)
    {
        latitude = location.getLatitude();
        longitude = location.getLongitude();
    }*/
    @Override
    public void onConnected(@Nullable Bundle bundle) {
        getLocationUpdate();
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        /*
         * Google Play services can resolve some errors it detects.
         * If the error has a resolution, try sending an Intent to
         * start a Google Play services activity that can resolve
         * error.
         */
        if (connectionResult.hasResolution()) {
            try {
                // Start an Activity that tries to resolve the error
                connectionResult.startResolutionForResult((Activity) locContext, AppConstants.CONNECTION_FAILURE_RESOLUTION_REQUEST);
                /*
                 * Thrown if Google Play services canceled the original
                 * PendingIntent
                 */
            }
            catch (IntentSender.SendIntentException e) {
                // Log the error
                e.printStackTrace();
            }
        } else {
            /*
              * user with the error.
             */
            Log.i("ERROR", "Location services connection failed with code " + connectionResult.getErrorCode());
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        mLocationCallback.handleNewLocation(location);
    }
}
