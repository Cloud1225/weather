package com.example.sabiha.weather;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by Sabiha on 9/13/2017.
 */

public class CustomView extends View {

    private int color;
    private int labelPosition;
    private Paint circlePaint;

    private  double xNew, yNew, xOld, yOld;
    public CustomView(Context context, AttributeSet attributeSet)
    {
        super(context, attributeSet);
        circlePaint = new Paint();
        TypedArray a = context.getTheme().obtainStyledAttributes(attributeSet, R.styleable.CustomView, 0, 0);
        try {
            color = a.getColor(R.styleable.CustomView_circleColor, getResources().getColor(R.color.colorPrimary));
            labelPosition = a.getInteger(R.styleable.CustomView_labelPosition, 0);
        } finally {
            a.recycle();
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        float width = this.getMeasuredWidth()/2;
        float height = this.getMeasuredHeight()/2;

        //getResources().getDisplayMetrics().widthPixels + " " + ge
        Log.e("CustomView", getResources().getDisplayMetrics().widthPixels + " " + getResources().getDisplayMetrics().heightPixels);
        Log.e("CustomView", width +"  "+height);

        float radius = width > height ? height - 20 - 15 : width - 20 - 15;
        Log.e("CustomView_Radius: ", String.valueOf(radius));
        circlePaint.setColor(color);
        circlePaint.setAntiAlias(true);
        circlePaint.setStyle(Paint.Style.STROKE);
        circlePaint.setStrokeWidth(3);
        circlePaint.setPathEffect(new DashPathEffect(new float[]{10, 5}, 0));
        RectF rectf = new RectF(width - radius, height - radius, width + radius, height + radius);
        canvas.drawArc(rectf, 180, 180, false, circlePaint);

        float secCircleX = width - radius;
        float secCircleY = height;
        circlePaint.setPathEffect(null);
        canvas.drawCircle(secCircleX, secCircleY, 15, circlePaint);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec);

    }
    public int getColor()
    {
        return color;
    }

    public int getLabelPosition()
    {
        return labelPosition;
    }

    public void setColor(int col)
    {
        color = col;
        invalidate();
        requestLayout();
    }

    public void setLabelPosition(int lPosition)
    {
        labelPosition = lPosition;
        invalidate();
        requestLayout();
    }

    public void animCalc(double startTime, double endTime, double presentTime, double centreTime, double radius)
    {
        double a, b, angle;
        double totalTime = endTime - startTime;
        if(presentTime == startTime)
        {
            angle = 0;
            a = 0;
            b = 0;
            xNew = xOld;
            yNew = yOld;
        }
        else if(presentTime == endTime)
        {
            angle = 180;
            a = 2*radius;
            b = 0;
            xNew = xOld + 2*radius;
            yNew = yOld;
        }
        else if(centreTime > presentTime)
        {
            a = ((centreTime - presentTime)*2*radius)/totalTime;
            b = Math.sqrt(Math.pow(radius, 2) - Math.pow(a, 2));
            angle = Math.acos(a/radius);
            xNew = (radius - a) + xOld;
            yNew = yOld - b;
        }
        else if(centreTime == presentTime)
        {
            angle = 90;
            a = radius;
            b = radius;
            xNew = xOld + radius;
            yNew = yOld - radius;
        }
        else
        {
            a = ((presentTime - centreTime)*2*radius)/totalTime;
            b = Math.sqrt(Math.pow(radius, 2) - Math.pow(a, 2));
            angle = Math.acos(a/radius);
            xNew = radius + a + xOld;
            yNew = yOld - b;
        }
    }
}
