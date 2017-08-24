package com.example.sabiha.weather;

import android.Manifest;
import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import static android.content.ContentValues.TAG;
import static android.content.Context.LOCATION_SERVICE;

/**
 * Created by Sabiha on 8/20/2017.
 */

public class LocationDetails extends Service {

    private static final int MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    final Context locContext;
    Activity activity;
    Location location;
    LocationManager locManager;
    double longitude;
    double latitude;
    int geocoderMaxResults = 1;
    String provider;
    public LocationDetails(Context context, Activity activity)
    {
        this.locContext = context;
        this.activity = activity;
        provider = LocationManager.NETWORK_PROVIDER;
    }

    public LocationListener mLocationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location loc) {

            Log.d("MSG", location.toString());
            if(loc != null) location = loc;
            updateCoordinate();
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    };

    public void getLocation() {
        try {
            //Permission.check_FINE_LOCATION_Permission(this);
            locManager = (LocationManager) locContext.getSystemService(LOCATION_SERVICE);
            boolean isProviderEnabled = locManager.isProviderEnabled(provider);

            if (isProviderEnabled && Permission.check_FINE_LOCATION_Permission(activity, 1)) {
            /*Criteria locationCritera = new Criteria();
            String providerName = locManager.getBestProvider(locationCritera, true);
            if(providerName!=null)
            {
                location = locManager.getLastKnownLocation(provider);
                updateCoordinate();
            }*/

                locManager.requestLocationUpdates(provider, 0, 0, mLocationListener);
                if (!locManager.equals(null)) {
                    location = (Location) locManager.getLastKnownLocation(provider);
                    updateCoordinate();
                }
            }
        }
        catch(Exception e)
        {
            Log.d("LOC", "Location Error");
        }
    }

    private void updateCoordinate() {
        if(location != null) {
            if(location.getLongitude()==0.0 && location.getLatitude()==0.0)
            {
                getLocation();
                return;
            }
            longitude = location.getLongitude();
            latitude = location.getLatitude();
        }
    }

    public double getLongitude(){
        if(location != null){
            longitude = location.getLongitude();
        }

        // return longitude
        return longitude;
    }

    public double getLatitude(){
        if(location != null){
            latitude = location.getLatitude();
        }

        // return latitude
        return latitude;
    }

    public List<Address> getGeocoderAddress(Context context) {
        if (location != null) {

            Geocoder geocoder = new Geocoder(context, Locale.ENGLISH);

            try {
                /**
                 * Geocoder.getFromLocation - Returns an array of Addresses
                 * that are known to describe the area immediately surrounding the given latitude and longitude.
                 */
                List<Address> addresses = geocoder.getFromLocation(latitude, longitude, this.geocoderMaxResults);

                return addresses;
            } catch (IOException e) {
                //e.printStackTrace();
                Log.e(TAG, "Impossible to connect to Geocoder", e);
            }
        }

        return null;
    }

    public String getCountryName(Context context) {
        List<Address> addresses = getGeocoderAddress(context);
        if (addresses != null && addresses.size() > 0) {
            Address address = addresses.get(0);
            String countryName = address.getCountryName();

            return countryName;
        } else {
            return null;
        }
    }

    public void unRegisterLocation()
    {
        if(Permission.check_FINE_LOCATION_Permission((Activity) locContext, 1)) {
            locManager.removeUpdates(mLocationListener);
        }
    }

    public void registerLocation()
    {
        /*if(Permission.check_FINE_LOCATION_Permission(locContext)) {
            locManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 10, mLocationListener);
        }*/
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
