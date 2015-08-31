package com.tonghu.draganddroptest;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * 未完成，头疼。
 * @author york
 * @date 8/31/15
 * @since 1.0.0
 */
public class DraggableDot extends View {
    private Paint mainPainter;

    public DraggableDot(Context context, AttributeSet attrs) {
        super(context, attrs);
        mainPainter = new Paint();
        mainPainter.setColor(Color.RED);
        setOnLongClickListener(new OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Log.i("tonghu", "DraggableDot, onLongClick(L27): ");
                v.startDrag(null, new DragShadowBuilder(v), null, 0);
                return false;
            }
        });
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int radius = getWidth() > getHeight() ? getWidth() / 2: getHeight() / 2;
        canvas.drawCircle(getWidth() / 2, getHeight() / 2, radius, mainPainter);
    }
}
