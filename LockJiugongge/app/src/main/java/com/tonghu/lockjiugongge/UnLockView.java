package com.tonghu.lockjiugongge;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * @功能描述：
 * @作者：york
 * @创建时间：7/26/15
 */
public class UnLockView extends View {
    public boolean isInitCalculated = false;
    private Circle[][] circles = new Circle[3][3];
    private List<Circle> selectedCircles = new ArrayList<>();

    public UnLockView(Context context) {
        this(context, null);
    }

    public UnLockView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public UnLockView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (!isInitCalculated) {
            initCalculated();
            isInitCalculated = true;
        }
        for (int i = 0; i < circles.length; i++) {
            for (int j = 0; j < circles[i].length; j++) {
                circles[i][j].draw(canvas);
            }
        }

        if (isInSelectedMode) {
            Circle curCircle = null;
            for (int i = 0; i < selectedCircles.size(); i++) {
                if (curCircle != null) {
                    curCircle.lineTo(canvas, selectedCircles.get(i));
                }
                curCircle = selectedCircles.get(i);
            }
            if (curCircle != null) {
                curCircle.lineTo(canvas, new PointF(curTouchX, curTouchY));
            }
        }
    }

    private boolean isInSelectedMode = false;
    private float curTouchX = 0;
    private float curTouchY = 0;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        curTouchX = event.getX();
        curTouchY = event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                for (int i = 0; i < circles.length; i++) {
                    for (int j = 0; j < circles[i].length; j++) {
                        if (circles[i][j].isInside(event.getX(), event.getY())) {
                            isInSelectedMode = true;
                            circles[i][j].setCurState(Circle.S_SELECTED);
                            selectedCircles.add(circles[i][j]);
                        }
                        invalidate();
                        return true;
                    }
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if (isInSelectedMode) {
                    for (int i = 0; i < circles.length; i++) {
                        for (int j = 0; j < circles[i].length; j++) {
                            if (circles[i][j].isInside(event.getX(), event.getY())) {
                                if (!selectedCircles.contains(circles[i][j])) {
                                    Log.i("tonghu","UnLockView, onTouchEvent(L89) add to selected: " + circles[i][j].getNum());
                                    circles[i][j].setCurState(Circle.S_SELECTED);
                                    selectedCircles.add(circles[i][j]);
                                    return true;
                                }
                            }
                        }
                    }
                }
            break;
            case MotionEvent.ACTION_UP:
                for (int i = 0; i < circles.length; i++) {
                    for (int j = 0; j < circles[i].length; j++) {
                        circles[i][j].setCurState(Circle.S_NORMAL);
                    }
                }
                selectedCircles.clear();
                isInSelectedMode = false;
                break;

        }
        postInvalidate();
        return super.onTouchEvent(event);
    }

    private void initCalculated() {
        int width = getWidth() > getHeight()? getHeight() : getWidth();
        float radius = (float) (getWidth() / 4.0 / 2 * 2 / 3);
        int offsetX = (getWidth() - width) / 2;
        int offsetY = (getHeight() - width) / 2;

        for (int i = 0; i < circles.length; i++) {
            for (int j = 0; j < circles[i].length; j++) {
                circles[i][j] = new Circle(i * circles.length + j, offsetX + width / 4* (i+1), offsetY + width / 4* (j+1), radius);
            }
        }
    }

}
