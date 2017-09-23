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
    CustomView view;
    double stepX, stepY;
    public CustomAnimation(CustomView view)
    {
        this.view = view;
        stepX = Math.abs(view.xNew - view.xOld)/10;
        stepY = Math.abs(view.yNew - view.yOld)/10;
    }

    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t) {
        //super.applyTransformation(interpolatedTime, t);
        Log.e("Custom Animation", interpolatedTime +"  "+ t.toString());
        this.
        view.setCurrentPosX(stepX);
        view.setCurrentPosY(stepY);
        if(view.currentPosX >view.xNew || view.currentPosY > view.yNew)
        {
            view.currentPosX = view.xNew;
            view.currentPosY = view.yNew;
            this.cancel();
        }

        view.invalidate();
    }

}
