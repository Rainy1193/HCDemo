package com.homecaravan.android.ui;

/**
 * Created by khoanguyen on 6/10/2016.
 */

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.View;

import com.homecaravan.android.R;


public class Triangle extends View {

    private int measuredWidth, measuredHeight;
    private float density;
    private Paint mTrianglePaint, mStrokePaint;
    private PointF a, b, c;
    private Path mTrianglePath;
    private float mStrokeWidth;

    // Default values
    private int mTriangleColor = 0xAA4CAF50; //ARGB int
    private int mStrokeColor = Color.BLACK; //ARGB int
    private float defaultPadding = 0; //dp

    public Triangle(Context context) {
        super(context);
        init(context, null, 0);
    }

    public Triangle(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0);
    }

    public Triangle(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs, int style) {

        Resources res = context.getResources();
        density = res.getDisplayMetrics().density;
        defaultPadding *= density;


        // Get the values from XML
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.Triangle, style, 0);

        int tmp;

        mTriangleColor = typedArray.getColor(R.styleable.Triangle_triangleColor, mTriangleColor);
        mStrokeColor = typedArray.getColor(R.styleable.Triangle_triangleStrokeColor, mStrokeColor);

        tmp = typedArray.getDimensionPixelSize(R.styleable.Triangle_triangleStrokeWidth, -1);
        mStrokeWidth = tmp != -1 ? tmp : 2 * density; // Use 2dp as a default value

        typedArray.recycle();

        a = new PointF();
        b = new PointF();
        c = new PointF();

        mTrianglePath = new Path();

        mTrianglePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTrianglePaint.setStyle(Paint.Style.FILL);
        mTrianglePaint.setColor(mTriangleColor);

        mStrokePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mStrokePaint.setStyle(Paint.Style.STROKE);
        mStrokePaint.setStrokeWidth(mStrokeWidth);
        mStrokePaint.setColor(mStrokeColor);

    }

    public void setTriangleColorResId(int resId) {
        // Example: setTriangleColorResId(R.color.blue);
        setTriangleColor(getContext().getResources().getColor(resId));
    }

    public void setTriangleColor(String color) {
        setTriangleColor(Color.parseColor(color));
    }

    public void setTriangleColor(int color) {
        mTriangleColor = color;
        mTrianglePaint.setColor(mTriangleColor);
        invalidate();
    }

    public void setStrokeColorResId(int resId) {
        // Example: setTriangleColorResId(R.color.blue);
        setStrokeColor(getContext().getResources().getColor(resId));
    }

    public void setStrokeColor(String color) {
        setTriangleColor(Color.parseColor(color));
    }

    public void setStrokeColor(int color) {
        mStrokeColor = color;
        mStrokePaint.setColor(mStrokeColor);
        invalidate();
    }

    public void setStrokeWidth(float strokeWidth) {
        if (strokeWidth < 0)
            throw new IllegalArgumentException("Stroke width cannot be < 0");
        //NOTE: input parameter is meant to be in pixels, you need to convert dp, sp or anything else
        // when calling this method
        mStrokeWidth = strokeWidth;
        mStrokePaint.setStrokeWidth(mStrokeWidth);
        invalidate();
    }

    public int getStrokeColor() {
        return mStrokeColor;
    }

    public float getStrokeWidth() {
        return mStrokeWidth;
    }

    public int getTriangleColor() {
        return mTriangleColor;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        measuredHeight = getDefaultSize(getSuggestedMinimumHeight(), heightMeasureSpec);
        measuredWidth = getDefaultSize(getSuggestedMinimumWidth(), widthMeasureSpec);

        setMeasuredDimension(measuredWidth, measuredHeight);
        //Log.d(TAG, "Height: " + measuredHeight + " Width: " + measuredWidth);
    }

    private float getPaddingOrDefault(int padding, float defaultValue) {
        return padding != 0 ? padding : defaultValue;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (measuredHeight <= 0 || measuredWidth <= 0) {
            // Not much we can draw...  :/
            return;
        }

        // Define the points of the triangle... make this so that it suits your needs

        // Top point
        a.x = getPaddingOrDefault(getPaddingLeft(), defaultPadding);
        a.y = getPaddingOrDefault(getPaddingLeft(), defaultPadding)-2;
        //(measuredHeight - getPaddingOrDefault(getPaddingBottom(), defaultPadding)) / 2f;

        // Bottom left point
        b.x = measuredWidth - getPaddingOrDefault(getPaddingLeft(), defaultPadding)-3;
        b.y = (measuredHeight - getPaddingOrDefault(getPaddingBottom(), defaultPadding)) / 2f;

        // Bottom right point
        c.x = getPaddingOrDefault(getPaddingLeft(), defaultPadding);
        c.y = measuredHeight - getPaddingOrDefault(getPaddingBottom(),defaultPadding)+2;

        // Clear the path from previous onDraw
        mTrianglePath.reset();

        // Make a path of triangle
        mTrianglePath.moveTo(a.x, a.y);
        mTrianglePath.lineTo(b.x, b.y);
        mTrianglePath.lineTo(c.x, c.y);
       // mTrianglePath.lineTo(a.x, a.y);
        //mTrianglePath.close();

        // Draw the triangle and the stroke
        canvas.drawPath(mTrianglePath, mTrianglePaint);
        canvas.drawPath(mTrianglePath, mStrokePaint);

    }
}
