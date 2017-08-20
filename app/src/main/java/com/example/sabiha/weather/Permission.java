package com.example.sabiha.weather;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.content.ContextCompat;

/**
 * Created by Sabiha on 8/20/2017.
 */

public class Permission {
    public static boolean check_FINE_LOCATION_Permission(Context activity) {
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1) {
            return true;
        }
        try {
            int result = ContextCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION);
            if (result == PackageManager.PERMISSION_GRANTED) {
                return true;
            } else {
                //  ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, requestCode_ACCESS_FINE_LOCATION);
                return false;
            }
        } catch (RuntimeException exceptionIgnored){
            return false;
        }
    }
}
