package com.homecaravan.android.consumer.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.View;

import com.homecaravan.android.R;


/**
 * The type View arrow.
 */
public class ViewArrow extends View {
    private Paint paintShape;
    private int shapeColor;

    /**
     * Instantiates a new View arrow.
     *
     * @param context the context
     * @param attrs   the attrs
     */
    public ViewArrow(Context context, AttributeSet attrs) {
        super(context, attrs);
        setupAttributes(attrs);
        setupPaint();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawPath(getTrianglePath(), paintShape);
    }

    /**
     * Set up paint
     */

    private void setupPaint() {
        paintShape = new Paint(Paint.ANTI_ALIAS_FLAG);
        paintShape.setStyle(Paint.Style.FILL);
        paintShape.setColor(shapeColor);
    }

    /**
     * Set up attributes view
     */

    private void setupAttributes(AttributeSet attrs) {
        TypedArray a = getContext().getTheme().obtainStyledAttributes(attrs, R.styleable.ViewArrow, 0, 0);
        try {
            shapeColor = a.getColor(R.styleable.ViewArrow_color_view_arrow, Color.BLACK);
        } finally {
            a.recycle();
        }
    }

    public void setColor(int shapeColor) {
        this.shapeColor = shapeColor;
        invalidate();
    }

    /**
     * Draw view triangle
     *
     * @return the triangle path
     */
    protected Path getTrianglePath() {
        Point p1 = new Point(0, 0), p2 = null, p3 = null;
        p2 = new Point(getWidth() / 2, getHeight());
        p3 = new Point(getWidth(), 0);
        Path path = new Path();
        path.moveTo(p1.x, p1.y);
        path.lineTo(p2.x, p2.y);
        path.lineTo(p3.x, p3.y);
        return path;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int contentWidth = getWidth();
        int minw = contentWidth + getPaddingLeft() + getPaddingRight();
        int w = resolveSizeAndState(minw, widthMeasureSpec, 0);
        int minh = getHeight() + getPaddingBottom() + getPaddingTop();
        int h = resolveSizeAndState(minh, heightMeasureSpec, 0);
        setMeasuredDimension(w, h);
    }
}
