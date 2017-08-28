package com.example.sabiha.weather;

import android.app.IntentService;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.text.TextUtils;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by Sabiha on 8/28/2017.
 */

public class FetchLocationAddressIntent extends IntentService {
    String Tag = this.getClass().getSimpleName();
    protected ResultReceiver resReceiver;
    public FetchLocationAddressIntent()
    {
        super("FetchLocationAddressIntent");
    }
    @Override
    protected void onHandleIntent(Intent intent) {
        String errorMessage = "";
        resReceiver = intent.getParcelableExtra(AppConstants.RECEIVER);

        // Check if receiver was properly registered.
        if (resReceiver == null) {
            Log.wtf(Tag, "No receiver received. There is nowhere to send the results.");
            return;
        }
        Geocoder geoCoder = new Geocoder(this, Locale.getDefault());
        Location location = intent.getParcelableExtra(AppConstants.LOCATION_DATA_EXTRA);

        if (location == null) {
            errorMessage = "No Location Data";
            Log.wtf(Tag, errorMessage);
            deliverResultToReceiver(AppConstants.FAILURE_RESULT, errorMessage);
            return;
        }
        List<Address> addresses = null;

        try{
            addresses = geoCoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
        }
        catch(IOException ioException)
        {
            Log.e(Tag, ioException.getMessage());
        }
        catch(IllegalArgumentException illArgException)
        {
            Log.e(Tag, illArgException.getMessage());
        }

        if(addresses == null || addresses.size() == 0)
        {
            errorMessage = "No Address Found";
            deliverResultToReceiver(AppConstants.FAILURE_RESULT, errorMessage);
        }
        else
        {
            Log.i(Tag, addresses.toString());

            Address address = addresses.get(0);
            ArrayList<String> addressFragments = new ArrayList<String>();

            // Fetch the address lines using getAddressLine,
            // join them, and send them to the thread.
            for(int i = 0; i <= address.getMaxAddressLineIndex(); i++) {
                addressFragments.add(address.getAddressLine(i));
            }

            Log.i(Tag, TextUtils.join(System.getProperty("line.separator"),
                    addressFragments));

            deliverResultToReceiver(AppConstants.SUCCESS_RESULT,
                    TextUtils.join(System.getProperty("line.separator"),
                            addressFragments));

        }
    }

    private void deliverResultToReceiver(int resultCode, String message)
    {
        Bundle bundle = new Bundle();
        bundle.putString(AppConstants.RESULT_DATA_KEY, message);
        resReceiver.send(resultCode, bundle);
    }
}
