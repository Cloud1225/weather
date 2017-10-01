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
    double currentTemp, temp;
    public TemperatureCustomAnimation(TemperatureCustomView view, double temperature, double maxTemperature, double minTemperature)
    {
        temperatureCustomView = view;
        currentSweepAngle = 0;
        currentTemp = minTemperature;
        temp = temperature;
        temperatureCustomView.setTemp(temperature);
        temperatureCustomView.setMaxTemp(maxTemperature);
        temperatureCustomView.setMinTemp(minTemperature);
    }

    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t) {
        //super.applyTransformation(interpolatedTime, t);
        temperatureCustomView.setSweepAngle(currentSweepAngle);
        temperatureCustomView.setCurrentTemp(currentTemp);
        temperatureCustomView.invalidate();
        currentSweepAngle += 3;
        currentTemp += 1;
        if(currentTemp > temp)
            currentTemp = temp;
        if(currentSweepAngle > temperatureCustomView.getMAX_ANGLE()) {
            //currentAngle = customView.getAngle();
            currentSweepAngle = 0;
            this.cancel();
        }
        Log.e("Custom Animation", "Current: " + currentSweepAngle);
    }
}
