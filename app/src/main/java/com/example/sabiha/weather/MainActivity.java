package com.example.sabiha.weather;

import android.content.Intent;
import android.location.Location;
import android.os.Build;
import android.os.Handler;
import android.os.ResultReceiver;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity  implements LocationDetailsPlayService.LocationCallback{


    TextView latLng, addressTV;

    LocationDetailsPlayService LDPS;
    private AddressResultReceiver addResReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        latLng = (TextView) findViewById(R.id.tb);
        addressTV = (TextView) findViewById(R.id.textView2);
        LDPS = new LocationDetailsPlayService(this, this);
        addResReceiver = new AddressResultReceiver(new Handler());
    }

    private void loadLocation()
    {
        if(Permission.check_FINE_LOCATION_Permission(this, AppConstants.LOCATION_PERMISSION_REQUEST_CODE)) {

            String stringLongitude = String.valueOf(LDPS.getLongitude());
            String stringLatitude = String.valueOf(LDPS.getLatitude());
            latLng.setText(stringLongitude + "  " + stringLatitude);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        LDPS.startConnection();
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (!Permission.checkPlayServices(this)) {
            latLng.setText("Please install Google Play services.");
        }
        //loadLocation();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LDPS.stopConnection();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

        switch (requestCode) {

            case AppConstants.LOCATION_PERMISSION_REQUEST_CODE:
                if(permissions != null && permissions.length > 0
                        && grantResults!=null && grantResults.length >0 )
                {
                    LDPS.getLocationUpdate();
                    //loadLocation();
                }
                else
                    Toast.makeText(this, "Location Permission Denied", Toast.LENGTH_LONG).show();
                break;
        }

    }

    @Override
    public void handleNewLocation(Location location) {
        if(location != null) {
            double currentLatitude = location.getLatitude();
            double currentLongitude = location.getLongitude();

            latLng.setText(currentLongitude + "  " + currentLatitude);

            startIntentService(location);
        }
    }

    protected void startIntentService(Location location)
    {
        Intent intent = new Intent(this, FetchLocationAddressIntent.class);
        intent.putExtra(AppConstants.RECEIVER, addResReceiver);
        intent.putExtra(AppConstants.LOCATION_DATA_EXTRA, location);
        startService(intent);
    }

    private void displayAddressOutput(String address)
    {
        addressTV.setText(address);
        LDPS.stopConnection();
    }

    class AddressResultReceiver extends ResultReceiver {

        /**
         * Create a new ResultReceive to receive results.  Your
         * {@link #onReceiveResult} method will be called from the thread running
         * <var>handler</var> if given, or from an arbitrary thread if null.
         *
         * @param handler
         */
        public AddressResultReceiver(Handler handler) {
            super(handler);
        }

        @Override
        protected void onReceiveResult(int resultCode, Bundle resultData) {
            super.onReceiveResult(resultCode, resultData);
            String address = resultData.getString(AppConstants.RESULT_DATA_KEY);

            displayAddressOutput(address);

            if (resultCode == AppConstants.SUCCESS_RESULT) {
                Toast.makeText(MainActivity.this, "Address Found", Toast.LENGTH_LONG);
            }

        }

    }
}
