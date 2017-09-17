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
}
