package com.example.yi.ontouchtest;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by yi on 12/31/15.
 */
public class TouchChildView extends View {

    public TouchChildView(Context context) {
        this(context, null);
    }

    public TouchChildView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TouchChildView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        boolean rst = super.onTouchEvent(event);
        rst = true;
        Log.i("tonghu", "Child's onTouchEvent() result: " + rst + " for event: " + getActionString(event.getAction()));
        return rst;
    }

    public static String getActionString(int action) {
        if (action == MotionEvent.ACTION_DOWN) {
            return "action_down";
        } else if (action == MotionEvent.ACTION_MOVE) {
            return "action_move";
        } else if (action == MotionEvent.ACTION_UP) {
            return "action_up";
        }
        return "action_other";
    }
}
