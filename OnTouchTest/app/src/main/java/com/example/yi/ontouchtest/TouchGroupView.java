package com.example.yi.ontouchtest;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.RelativeLayout;

/**
 * Created by yi on 12/31/15.
 */
public class TouchGroupView extends RelativeLayout{
    public TouchGroupView(Context context) {
        this(context, null);
    }

    public TouchGroupView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TouchGroupView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        boolean rst = super.onTouchEvent(event);
        rst = true;
        Log.i("tonghu", "Group's onTouchEvent() result: " + rst + " for event: " + TouchChildView.getActionString(event.getAction()));
        return rst;
    }
}
