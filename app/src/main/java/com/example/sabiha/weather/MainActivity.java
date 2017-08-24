package com.example.sabiha.weather;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import java.util.ArrayList;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;

public class MainActivity extends AppCompatActivity {

    //TextView textview;
    //final int locationPermission = 1;
    //LocationDetails ld;
    Location mLocation;
    TextView latLng;
    GoogleApiClient mGoogleApiClient;
    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;

    private LocationRequest mLocationRequest;
    private long UPDATE_INTERVAL = 15000;  /* 15 secs */
    private long FASTEST_INTERVAL = 5000; /* 5 secs */

    private ArrayList permissionsToRequest;
    private ArrayList permissionsRejected = new ArrayList();
    private ArrayList permissions = new ArrayList();
    private final static int ALL_PERMISSIONS_RESULT = 101;

    LocationDetailsPlayService LDPS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*ld = new LocationDetails(this, MainActivity.this);
        locationView();*/
        latLng = (TextView) findViewById(R.id.tb);

        LDPS = new LocationDetailsPlayService(this);

        /*permissions.add(ACCESS_FINE_LOCATION);
        permissions.add(ACCESS_COARSE_LOCATION);

        permissionsToRequest = findUnAskedPermissions(permissions);*/
        //get the permissions we have asked for before but are not granted..
        //we will store this in a global list to access later.

        Permission.check_FINE_LOCATION_Permission(this, AppConstants.LOCATION_PERMISSION_REQUEST_CODE);
        Permission.checkPlayServices(this);
        /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {


            if (permissionsToRequest.size() > 0)
                requestPermissions((String[])permissionsToRequest.toArray(new String[permissionsToRequest.size()]), ALL_PERMISSIONS_RESULT);
        }*/

        /*mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();*/
    }

    /*private ArrayList findUnAskedPermissions(ArrayList wanted) {
        ArrayList result = new ArrayList();

        for (Object perm : wanted) {
            if (!hasPermission(String.valueOf(perm))) {
                result.add(perm);
            }
        }

        return result;
    }*/

    @Override
    protected void onStart() {
        super.onStart();
        LDPS.startConnection();
        /*if (mGoogleApiClient != null) {
            mGoogleApiClient.connect();
        }*/
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (!checkPlayServices()) {
            latLng.setText("Please install Google Play services.");
        }

        /*if(ld != null)
        {
            ld.getLocation();
        }*/
    }

    @Override
    protected void onPause() {
        super.onPause();
        /*if(ld != null)
            ld.unRegisterLocation();*/
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LDPS.stopConnection();
        //stopLocationUpdates();
    }


    /*public void stopLocationUpdates()
    {
        if (mGoogleApiClient.isConnected()) {
            LocationServices.FusedLocationApi
                    .removeLocationUpdates(mGoogleApiClient, this);
            mGoogleApiClient.disconnect();
        }
    }*/

    /*@Override
    public void onConnected(@Nullable Bundle bundle) {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);


        if(mLocation!=null)
        {
            latLng.setText("Latitude : "+mLocation.getLatitude()+" , Longitude : "+mLocation.getLongitude());
        }

        startLocationUpdates();
    }

    @Override
    public void onConnectionSuspended(int i) {

    }
*/
    /*@Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {
        if(location!=null)
            latLng.setText("Latitude : "+location.getLatitude()+" , Longitude : "+location.getLongitude());
    }*/

    /*private boolean checkPlayServices() {
        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        int resultCode = apiAvailability.isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (apiAvailability.isUserResolvableError(resultCode)) {
                apiAvailability.getErrorDialog(this, resultCode, PLAY_SERVICES_RESOLUTION_REQUEST)
                        .show();
            } else
                finish();

            return false;
        }
        return true;
    }*/

    /*protected void startLocationUpdates() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(UPDATE_INTERVAL);
        mLocationRequest.setFastestInterval(FASTEST_INTERVAL);
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(getApplicationContext(), "Enable Permissions", Toast.LENGTH_LONG).show();
        }

        LocationServices.FusedLocationApi.requestLocationUpdates(
                mGoogleApiClient, mLocationRequest, this);


    }*/

    /*private boolean hasPermission(String permission) {
        if (canMakeSmores()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                return (checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED);
            }
        }
        return true;
    }

    private boolean canMakeSmores() {
        return (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1);
    }*/

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

        switch (requestCode) {

            case AppConstants.LOCATION_PERMISSION_REQUEST_CODE:
                /*for (Object perms : permissionsToRequest) {
                    if (!hasPermission(String.valueOf(perms))) {
                        permissionsRejected.add(perms);
                    }
                }

                if (permissionsRejected.size() > 0) {


                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (shouldShowRequestPermissionRationale(String.valueOf(permissionsRejected.get(0)))) {
                            showMessageOKCancel("These permissions are mandatory for the application. Please allow access.",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                requestPermissions((String[])permissionsRejected.toArray(new String[permissionsRejected.size()]), ALL_PERMISSIONS_RESULT);
                                            }
                                        }
                                    });
                            return;
                        }
                    }

                }
*/
                break;
        }

    }

    /*private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(MainActivity.this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }*/


    /*private void locationView()
    {
        if(Permission.check_FINE_LOCATION_Permission(MainActivity.this, locationPermission)) {

            String stringLongitude = String.valueOf(ld.getLongitude());
            textview = (TextView) findViewById(R.id.tb);
            textview.setText(stringLongitude);

            String stringLatitude = String.valueOf(ld.getLatitude());
            textview = (TextView) findViewById(R.id.textView2);
            textview.setText(stringLatitude);
        }
    }*/

    /*@Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch(requestCode)
        {
            case locationPermission:
                if(permissions != null && permissions.length > 0 && permissions[0].equals(Manifest.permission.ACCESS_FINE_LOCATION)
                        && permissions[1].equals(Manifest.permission.ACCESS_COARSE_LOCATION)
                        && grantResults!=null && grantResults.length >0 && grantResults[0] == PackageManager.PERMISSION_GRANTED
                        && grantResults[1] == PackageManager.PERMISSION_GRANTED)
                {
                    ld.getLocation();
                    locationView();
                }
                else
                    Toast.makeText(this, "Location Permission Denied", Toast.LENGTH_LONG).show();
                break;
            default:
                break;
        }
    }*/

}
