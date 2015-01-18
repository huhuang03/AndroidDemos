package com.example.tonghu.guaguaka.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Xfermode;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.example.tonghu.guaguaka.R;

/**
 * Created by tonghu on 1/18/15.
 * 底图是一张图片
 */
public class SketchpadView extends View {

    private static final String TAG = SketchpadView.class.getSimpleName();
    private Bitmap bitmap;
    private Canvas canvas;
    private Paint drawPaint;
    private int drawWidth;
    private Path drawPath;
    private Bitmap backBitmap;
    private Xfermode dstOutXfermode = new PorterDuffXfermode(PorterDuff.Mode.DST_OUT);

    public SketchpadView(Context context) {
        super(context);
        initial();
    }

    public SketchpadView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initial();
    }

    public SketchpadView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initial();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        bitmap = Bitmap.createBitmap(getMeasuredWidth(), getMeasuredHeight(), Bitmap.Config.ARGB_8888);
        canvas = new Canvas(bitmap);

        canvas.drawColor(Color.parseColor("#c0c0c0"));
    }

    private void initial() {
        canvas = new Canvas();
        drawPath = new Path();

        drawPaint = new Paint();
        drawPaint.setAntiAlias(true);
        drawPaint.setDither(true);
        drawPaint.setColor(Color.RED);
        drawPaint.setStyle(Paint.Style.STROKE);
        drawPaint.setStrokeJoin(Paint.Join.ROUND);
        drawPaint.setStrokeCap(Paint.Cap.ROUND);
        drawWidth = 25;
        drawPaint.setStrokeWidth(drawWidth);

        backBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.t2);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Log.i(TAG, "onDraw");
        canvas.drawBitmap(backBitmap, 0, 0, null);
        canvas.drawBitmap(bitmap, 0, 0, null);
    }


    private float startX;
    private float startY;
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.i(TAG, "onTouchEvent");
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startX = event.getX();
                startY = event.getY();
                drawPath.moveTo(event.getX(), event.getY());
                break;
            case MotionEvent.ACTION_MOVE:
                float dx = Math.abs(event.getX() - startX);
                float dy = Math.abs(event.getY() - startY);
                if (dx > 3 || dy > 3) {
                    drawPath.lineTo(event.getX(), event.getY());
                }
                drawPaint.setXfermode(dstOutXfermode);
                canvas.drawPath(drawPath, drawPaint);
                invalidate();
                break;

        }
        return true;
    }
}
