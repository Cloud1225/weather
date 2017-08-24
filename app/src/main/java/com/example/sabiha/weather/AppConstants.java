package com.example.sabiha.weather;

/**
 * Created by Sabiha on 8/23/2017.
 */

public final class AppConstants {
    public static final int LOCATION_PERMISSION_REQUEST_CODE = 1; //Request code for location permission
    public static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    public static  long UPDATE_INTERVAL = 15000;  // For LocationRequest
    public static long FASTEST_INTERVAL = 5000;   // For LocationRequest
    /*
     * Define a request code to send to Google Play services
     * This code is returned in Activity.onActivityResult
     */
    public static int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;
}
