package com.example.tonghu.flowlayout.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by tonghu on 1/20/15.
 * 没有考虑width为match_parent, height为wrap_content的情况
 */
public class FlowLayout extends ViewGroup {

    public FlowLayout(Context context) {
        super(context);
        initial();
    }

    public FlowLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initial();
    }

    public FlowLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initial();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int sizeWidht = MeasureSpec.getSize(widthMeasureSpec);
        int modeWidht = MeasureSpec.getMode(widthMeasureSpec);

        int sizeHeight = MeasureSpec.getSize(heightMeasureSpec);
        int modeHeight = MeasureSpec.getMode(heightMeasureSpec);

        Log.i("tonghu", sizeWidht + ", " + sizeHeight);

        setMeasuredDimension(sizeWidht, sizeHeight);

        for (int i = 0; i < getChildCount(); i++) {
            measureChild(getChildAt(i), widthMeasureSpec, heightMeasureSpec);
        }
    }

    private void initial() {

    }

    @Override
    protected void onLayout(boolean b, int i, int i2, int i3, int i4) {
        int width = getWidth();
//        int height = getHeight();

        int curHeight = 0;
        int lineWidth = 0;
        int lineHeight = 0;
        for (int j = 0; j < getChildCount(); j++) {
            View child = getChildAt(j);
            MarginLayoutParams layoutParams = (MarginLayoutParams) child.getLayoutParams();
            if ((lineWidth + child.getMeasuredWidth() + layoutParams.leftMargin + layoutParams.rightMargin) > width) {
                //换行
                curHeight += lineHeight;

                lineHeight = 0;
                lineWidth = 0;
            }

            lineHeight = Math.max(lineHeight, child.getMeasuredHeight() +  layoutParams.topMargin + layoutParams.bottomMargin);

            child.layout(lineWidth,
                    curHeight,
                    lineWidth + child.getMeasuredWidth() + layoutParams.leftMargin + layoutParams.rightMargin,
                    curHeight + child.getMeasuredHeight() + layoutParams.topMargin + layoutParams.bottomMargin);
            lineWidth = lineWidth + child.getMeasuredWidth() + layoutParams.rightMargin + layoutParams.leftMargin;
        }
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(), attrs);
    }
}
