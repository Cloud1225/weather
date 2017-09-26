package com.example.sabiha.weather;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.nfc.Tag;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.Transformation;

/**
 * Created by Sabiha on 9/13/2017.
 */

public class CustomView extends View {

    private Context myContext;
    private String TAG = CustomView.class.getSimpleName();
    private int color;
    private int labelPosition;
    private Paint circlePaint;

    private double xOld, yOld, radius, width, height, currentPosX, currentPosY, angle, currentAngle;
    private double smallCirRadius = 15, padding = 20;

    public CustomView(Context context, AttributeSet attributeSet)
    {
        super(context, attributeSet);
        myContext = context;
        circlePaint = new Paint();
        TypedArray a = context.getTheme().obtainStyledAttributes(attributeSet, R.styleable.CustomView, 0, 0);
        try {
            color = a.getColor(R.styleable.CustomView_circleColor, getResources().getColor(R.color.colorPrimary));
            labelPosition = a.getInteger(R.styleable.CustomView_labelPosition, 0);
        } finally {
            a.recycle();
        }
    }

    public void initialize()
    {
        width = this.getMeasuredWidth()/2;
        height = this.getMeasuredHeight()/2;
        Log.e("CustomView_Radius: ", "Width: "+width + "  Height:  "+height);

        radius = width > height ? height - padding - smallCirRadius : width - padding - smallCirRadius;
        //Log.e("CustomView_Radius: ", String.valueOf(radius));

        circlePaint.setAntiAlias(true);

        currentPosX = xOld = width - radius;
        currentPosY = yOld = height;
        currentAngle = 0;
        Log.e(TAG, "Radius: " + radius + " OldX: " + xOld + " OldY: "+ yOld);
    }
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        circlePaint.setColor(color);
        circlePaint.setStyle(Paint.Style.STROKE);
        circlePaint.setStrokeWidth(3);
        circlePaint.setPathEffect(new DashPathEffect(new float[]{10, 5}, 0));
        RectF rectf = new RectF((float) (width - radius), (float) (height - radius), (float) (width + radius), (float) (height + radius));
        canvas.drawArc(rectf, 180, 180, false, circlePaint);

        circlePaint.setStyle(Paint.Style.FILL);
        circlePaint.setPathEffect(null);
        circlePaint.setColor(Color.LTGRAY);
        canvas.drawArc(rectf, 180, (float) currentAngle, true, circlePaint);

        circlePaint.setPathEffect(null);
        circlePaint.setStyle(Paint.Style.FILL);
        circlePaint.setColor(Color.YELLOW);
        canvas.drawCircle((float) currentPosX, (float) currentPosY, (float) smallCirRadius, circlePaint);
        Log.e(TAG, "In Draw X: "+currentPosX + " Y: "+ currentPosY);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec);
        Log.e(TAG, widthMeasureSpec+"    "+heightMeasureSpec);
        initialize();
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

    public double getAngle() {
        return angle;
    }

    public void setAngle(double angle) {
        this.angle = angle;
    }

    public void setCurrentPosX(double x)
    {
        this.currentPosX = x;
    }

    public double getCurrentPosX()
    {
        return currentPosX;
    }
    public void setCurrentPosY(double y)
    {
        this.currentPosY = y;
    }
    public double getCurrentPosY()
    {
        return currentPosY;
    }
    public void calcAngle(double startTime, double endTime, double presentTime, double centerTime)
    {
        double a;
        double totalTime = endTime - startTime;
        a = (Math.abs(centerTime - presentTime)*2*radius)/totalTime;
        angle = presentTime == startTime ? 0 : (presentTime == centerTime ? 90: (presentTime == endTime || presentTime > endTime ? 180 : Math.acos(a/radius) * (180/Math.PI)));
        if(angle != 180 && presentTime > centerTime)
            angle = 180 - angle;
        Log.e(TAG, "  Angle: " + angle);
    }

    public void calcPosition(double currentAngle)
    {
        this.currentAngle = currentAngle;
        if(currentAngle == 0)
        {
            setCurrentPosX(xOld);
            setCurrentPosY(yOld);
        }
        else if(currentAngle == 90)
        {
            setCurrentPosX(xOld + radius);
            setCurrentPosY(yOld - radius);
        }
        else if(currentAngle == 180)
        {
            setCurrentPosX(xOld + (2*radius));
            setCurrentPosY(yOld);
        }
        else if(currentAngle > 0 && currentAngle < 90)
        {
            setCurrentPosX(xOld + (radius * (1 - Math.cos(currentAngle * Math.PI / 180))));
            setCurrentPosY(yOld - (radius * Math.sin(currentAngle * Math.PI / 180)));
        }
        else if(currentAngle > 90 && currentAngle < 180)
        {
            setCurrentPosX(xOld + (radius * (1 + Math.cos((180 - currentAngle) * Math.PI / 180))));
            setCurrentPosY(yOld - (radius * Math.sin((180 - currentAngle) * Math.PI / 180)));
        }

        Log.e(TAG, "CurrentPosX: " + currentPosX + " CurrentPosY: " + currentPosY + "  CurrentAngle: " + currentAngle);
    }

    /*public void animCalc1(double startTime, double endTime, double presentTime, double centerTime)
    {
        double a, b, angle;
        double totalTime = endTime - startTime;
        if(presentTime == startTime)
        {
            angle = 0;
            a = 0;
            b = 0;
            xNew = xOld + a;
            yNew = yOld - b;
        }
        else if(presentTime == endTime)
        {
            angle = 180;
            a = 2*radius;
            b = 0;
            xNew = xOld + a;
            yNew = yOld - b;
        }
        else if(centerTime > presentTime)
        {
            a = ((centerTime - presentTime)*2*radius)/totalTime;
            b = Math.sqrt(Math.pow(radius, 2) - Math.pow(a, 2));
            angle = Math.acos(a/radius);
            xNew = xOld + (radius - a);
            yNew = yOld - b;
        }
        else if(centerTime == presentTime)
        {
            angle = 90;
            a = radius;
            b = radius;
            xNew = xOld + a;
            yNew = yOld - b;
        }
        else
        {
            a = ((presentTime - centerTime)*2*radius)/totalTime;
            b = Math.sqrt(Math.pow(radius, 2) - Math.pow(a, 2));
            angle = Math.acos(a/radius);
            xNew = xOld + radius + a;
            yNew = yOld - b;
        }

        Log.e(TAG, "xNew: " + xNew + " xOld: " + xOld + " yNew: " + yNew +" yOld: " + yOld + "  Angle: " + angle);
    }*/

    /*public void animateCircle()
    {
        while (xNew >= currentPosX)
        {
            currentPosX += 10;
            currentPosY -= 10;
            if(currentPosY < yNew)
                currentPosY = yNew;
            Log.e(TAG, "X: "+currentPosX + " Y: "+ currentPosY);
            this.invalidate();
            this.requestLayout();
        }
        //requestLayout();
    }*/
}
