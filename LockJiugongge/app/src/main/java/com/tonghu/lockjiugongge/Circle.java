package com.tonghu.lockjiugongge;

import android.annotation.TargetApi;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.os.Build;
import android.util.Log;

/**
 * @功能描述：
 * @作者：york
 * @创建时间：7/26/15
 */
public class Circle {
    private float centerX;
    private float centerY;
    private float radius;
    public static final int S_NORMAL = 1;
    public static final int S_SELECTED = 2;
//    public static final int S_ERROR
    private int curState = S_NORMAL;
    private Paint normalPaint = null;
    private Paint selectedPaint = null;
    private Paint linePaint;
    private int num;

    public Circle(int num, float centerX, float centerY, float radius) {
        this.num = num;
        this.centerX = centerX;
        this.centerY = centerY;
        this.radius = radius;

        normalPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        normalPaint.setColor(Color.GREEN);

        selectedPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        selectedPaint.setColor(Color.GRAY);

//        linePaint = new Paint((Paint.ANTI_ALIAS_FLAG);
        linePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        linePaint.setColor(Color.GRAY);
        linePaint.setStrokeWidth(5);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public void draw(Canvas canvas) {
        switch (curState) {
            case S_NORMAL:
                canvas.drawOval(centerX - radius, centerY - radius, centerX + radius, centerY + radius, normalPaint);
                break;
            case S_SELECTED:
                canvas.drawOval(centerX - radius, centerY - radius, centerX + radius, centerY + radius, selectedPaint);
                break;
        }
    }

    public void setCurState(int curState) {
        this.curState = curState;
    }

    public float getCenterX() {
        return centerX;
    }

    public float getCenterY() {
        return centerY;
    }

    public void lineTo(Canvas canvas, Circle circle) {
        lineTo(canvas, new PointF(circle.getCenterX(), circle.getCenterY()));
    }

    public void lineTo(Canvas canvas, PointF endPoint) {
        canvas.drawLine(centerX, centerY, endPoint.x, endPoint.y, linePaint);
    }

    public boolean isInside(float x, float y) {
        boolean rst =  Math.sqrt((centerX - x) * (centerX - x) +  (centerY - y) * (centerY - y)) < radius;
        return rst;
    }

    public int getNum() {
        return num;
    }
}
