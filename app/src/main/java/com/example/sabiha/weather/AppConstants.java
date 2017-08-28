package com.example.sabiha.weather;

/**
 * Created by Sabiha on 8/23/2017.
 */

public final class AppConstants {
    public static final int LOCATION_PERMISSION_REQUEST_CODE = 1; //Request code for location permission
    public static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    public static final long UPDATE_INTERVAL = 15000;  // For LocationRequest
    public static final long FASTEST_INTERVAL = 5000;   // For LocationRequest
    /*
     * Define a request code to send to Google Play services
     * This code is returned in Activity.onActivityResult
     */
    public static final int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;

    /*
    * Define Succes or Failure of result from IntentService used for reverse Geocoding.
    */
    public static final int SUCCESS_RESULT = 0;
    public static final int FAILURE_RESULT = 1;
    public static final String PACKAGE_NAME = "com.example.sabiha.weather";
    public static final String RECEIVER = PACKAGE_NAME + ".RECEIVER";
    public static final String RESULT_DATA_KEY = PACKAGE_NAME + ".RESULT_DATA_KEY";
    public static final String LOCATION_DATA_EXTRA = PACKAGE_NAME + ".LOCATION_DATA_EXTRA";

}
