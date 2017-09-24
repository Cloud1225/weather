package com.example.sabiha.weather;

import android.util.Log;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.Transformation;

/**
 * Created by Sabiha on 9/23/2017.
 */

public class CustomAnimation extends Animation{
    CustomView customView;
    double step, currentAngle;
    public CustomAnimation(CustomView view)
    {
        customView= view;
        step = customView.getAngle() / 10;
        currentAngle = 0.0;
    }

    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t) {
        //super.applyTransformation(interpolatedTime, t);
        customView.calcPosition(currentAngle);
        customView.invalidate();
        currentAngle += step;
        if(currentAngle > customView.getAngle()) {
            //currentAngle = customView.getAngle();
            currentAngle = 0;
            this.cancel();
        }
        Log.e("Custom Animation", "Current: " + currentAngle +" Main Angle "+ customView.getAngle());
    }

}
