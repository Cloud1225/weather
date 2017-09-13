package com.example.sabiha.weather;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Sabiha on 9/13/2017.
 */

public class CustomView extends View {

    private Color color;
    private int labelPosition;
    private Paint circlePaint;
    public CustomView(Context context, AttributeSet attributeSet)
    {
        super(context, attributeSet);
        circlePaint = new Paint();
        TypedArray a = context.getTheme().obtainStyledAttributes(attributeSet, R.styleable.CustomView, 0, 0);
        try {
            color = a.getColor(R.styleable.CustomView_circleColor, getResources().getColor(R.id.white));
            labelPosition = a.getInteger(R.styleable.CustomView_labelPosition, 0);
        } finally {
            a.recycle();
        }
    }
}
