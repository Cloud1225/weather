package com.example.sabiha.weather;

import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.Transformation;

/**
 * Created by Sabiha on 9/27/2017.
 */

public class TemperatureCustomAnimation  extends Animation {
    TemperatureCustomView temperatureCustomView;
    float currentSweepAngle;
    public TemperatureCustomAnimation(TemperatureCustomView view)
    {
        temperatureCustomView = view;
        currentSweepAngle = 0;
    }

    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t) {
        //super.applyTransformation(interpolatedTime, t);
        temperatureCustomView.setSweepAngle(currentSweepAngle);
        temperatureCustomView.invalidate();
        currentSweepAngle += 3;
        if(currentSweepAngle > temperatureCustomView.getMAX_ANGLE()) {
            //currentAngle = customView.getAngle();
            currentSweepAngle = 0;
            this.cancel();
        }
        Log.e("Custom Animation", "Current: " + currentSweepAngle);
    }
}
