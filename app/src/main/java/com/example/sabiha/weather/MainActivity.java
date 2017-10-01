package com.example.sabiha.weather;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Handler;
import android.os.ResultReceiver;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import org.json.JSONException;

import java.util.Calendar;
import java.util.Date;


public class MainActivity extends AppCompatActivity  implements LocationDetailsPlayService.LocationCallback{


    TextView tempTV, descTV, windTV;
    ImageView iconView, windGIF;

    LocationDetailsPlayService LDPS;
    private AddressResultReceiver addResReceiver;
    String Tag = MainActivity.class.getSimpleName();
    public WeatherDetails weather;
    CustomView customView;
    TemperatureCustomView temperatureCustomView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //latLng = (TextView) findViewById(R.id.tb);
        //addressTV = (TextView) findViewById(R.id.textView2);
        tempTV = (TextView) findViewById(R.id.temp_textView);
        descTV = (TextView) findViewById(R.id.desc_textView);
        windTV = (TextView) findViewById(R.id.wind_textView);

        iconView = (ImageView) findViewById(R.id.icon_iView);
        windGIF = (ImageView) findViewById(R.id.windGIF);
        temperatureCustomView = (TemperatureCustomView) findViewById(R.id.temperature_custom_layout);
        customView = (CustomView) findViewById(R.id.custom_layout);
        //GlideDrawableImageViewTarget imageViewTarget = new GlideDrawableImageViewTarget(windGIF);
        Glide.with(this).load(R.raw.windmill).into(windGIF);

        LDPS = new LocationDetailsPlayService(this, this);
        addResReceiver = new AddressResultReceiver(new Handler());

        // Find the toolbar view inside the activity layout
        Toolbar toolbar = (Toolbar) findViewById(R.id.main_toolbar);
        // Sets the Toolbar to act as the ActionBar for this Activity window.
        // Make sure the toolbar exists in the activity and is not null
        setSupportActionBar(toolbar);
    }

    private void loadLocation()
    {
        if(Permission.check_FINE_LOCATION_Permission(this, AppConstants.LOCATION_PERMISSION_REQUEST_CODE)) {

            String stringLongitude = String.valueOf(LDPS.getLongitude());
            String stringLatitude = String.valueOf(LDPS.getLatitude());
            //latLng.setText(stringLongitude + "  " + stringLatitude);
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
            //latLng.setText("Please install Google Play services.");
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

            //latLng.setText(currentLongitude + "  " + currentLatitude);

            startIntentService(location);

            JasonAsyncTask jasonAsyncTask = new JasonAsyncTask();
            jasonAsyncTask.execute(new Location[]{location});
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
        //addressTV.setText(address);
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

    public class JasonAsyncTask extends AsyncTask<Location, Void, WeatherDetails> {
        @Override
        protected WeatherDetails doInBackground(Location... params) {
            String data = JasonParser.getWeatherData(params[0]);
            Log.i(Tag, data);
            try {
                weather = JasonParser.getWeatherDetails(data);
                weather.iconData = JasonParser.getWeatherImage(weather.currentWeather.getIcon());
                return weather;
            }
            catch (JSONException e)
            {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(WeatherDetails weatherDetails) {
            super.onPostExecute(weatherDetails);
            Log.e(Tag, String.valueOf(weather.system.getSunriseHours()));
            Log.e(Tag, String.valueOf(weather.system.getSunsetHours()));
            Log.e(Tag, weather.getDateTime().toString());

            double centerTime = (weather.system.getSunriseHours() + weather.system.getSunsetHours())/2;
            Log.e(Tag, String.valueOf(centerTime));
            double preTime = presentTimeCalc();
            Log.e(Tag, String.valueOf(preTime));
            customView.calcAngle(weather.system.getSunriseHours(), weather.system.getSunsetHours(), preTime, centerTime);
            /*customView.setOnClickListener(new View.OnClickListener()
            {

                @Override
                public void onClick(View v) {
                    CustomAnimation ca = new CustomAnimation(customView);
                    ca.setInterpolator(new AccelerateInterpolator());
                    //ca.setFillAfter(true);
                    ca.setDuration(4000L);
                    customView.invalidate();
                    customView.startAnimation(ca);
                }
            });*/
            TemperatureCustomAnimation tca = new TemperatureCustomAnimation(temperatureCustomView, weather.main.getTemperature(),
                    weather.main.getTempMax(), weather.main.getTempMin());
            tca.setInterpolator(new AccelerateInterpolator());
            tca.setDuration(9000L);
            temperatureCustomView.startAnimation(tca);

            CustomAnimation ca = new CustomAnimation(customView);
            ca.setInterpolator(new AccelerateInterpolator());
            ca.setDuration(9000L);
            customView.startAnimation(ca);

            tempTV.setText(String.valueOf(weatherDetails.main.getTemperature()));
            descTV.setText(weatherDetails.currentWeather.getDescription());

            Bitmap img = BitmapFactory.decodeByteArray(weatherDetails.iconData, 0, weatherDetails.iconData.length);
            iconView.setImageBitmap(img);
            windTV.setText("Dir: "+weatherDetails.wind.getWindDirection()+"  Speed: "+weatherDetails.wind.getWindSpeed());
        }

    }

    private double presentTimeCalc()
    {
        Date date = Calendar.getInstance().getTime();
        double hours = date.getHours() + (date.getMinutes()/60.0) + (date.getSeconds()/3600.0);
        Log.e(Tag + "Current Time", Calendar.getInstance().getTime().toString());
        return hours;
    }

    /*public void animateCircle()
    {
        while (customView.xNew >= customView.currentPosX)
        {
            customView.currentPosX += 1;
            customView.currentPosY -= 1;
            if(customView.currentPosY < customView.yNew)
                customView.currentPosY = customView.yNew;
            Log.e(Tag, "X: "+customView.currentPosX + " Y: "+ customView.currentPosY);
            customView.invalidate();
            //customView.requestLayout();
        }
        //requestLayout();
    }*/
}
