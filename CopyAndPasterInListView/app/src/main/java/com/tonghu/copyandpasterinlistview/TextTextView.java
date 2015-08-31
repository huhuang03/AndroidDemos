package com.tonghu.copyandpasterinlistview;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.text.Selection;
import android.text.Spannable;
import android.text.method.ArrowKeyMovementMethod;
import android.util.AttributeSet;
import android.util.Log;
import android.view.ActionMode;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

/**
 * @author york
 * @date 8/17/15
 * @since 1.0.0
 */
public class TextTextView extends TextView{
    private boolean isInSelection = false;
    public TextTextView(Context context) {
        super(context);
        setTextIsSelectable(true);
//        setMovementMethod(ArrowKeyMovementMethod.getInstance());
//        setText("fdafjidosjfidsakfldjsaklfdjsaklfdjskalfjieojivxkfldsjfoidsjfiodsjaifodsjaifodsji");
//        Selection.setSelection((Spannable) getText(), 2);
//        startActionMode()
        Log.i("tonghu", "TextTextView, TextTextView(L23): ");
//        setTextIsSelectable(true);
//        setFocusableInTouchMode(true);
//        setFocusable(true);
//        setClickable(true);
//        setLongClickable(true);
//        setMovementMethod(ArrowKeyMovementMethod.getInstance());
//        setText(getText(), BufferType.SPANNABLE);
//        setCustomSelectionActionModeCallback(new ActionMode.Callback() {
//            @Override
//            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
//                Log.i("tonghu", "TextTextView, onCreateActionMode(L30): ");
//                isInSelection = true;
//                return true;
//            }
//
//            @Override
//            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
//                menu.clear();
//                Activity activity = (Activity) getContext();
//                if (activity.getActionBar() != null) {
//                    Log.i("tonghu","TextTextView, onPrepareActionMode(L52): ");
//                    activity.getActionBar().hide();
//                } else {
//                    Log.i("tonghu","TextTextView, onPrepareActionMode(L55):  actionBar is null");
//                }
//                return false;
//            }
//
//            @Override
//            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
//                Log.i("tonghu","TextTextView, onActionItemClicked(L45): ");
//                return false;
//            }
//
//            @Override
//            public void onDestroyActionMode(ActionMode mode) {
//                Log.i("tonghu","TextTextView, onDestroyActionMode(L51): ");
//                isInSelection = false;
//            }
//        });
    }

//    @Override
//    protected void onFocusChanged(boolean focused, int direction, Rect previouslyFocusedRect) {
//        Log.i("tonghu","TextTextView, onFocusChanged(L60): ");
//        if (hasFocus()) {
//            super.onFocusChanged(focused, direction, previouslyFocusedRect);
//        } else {
//            if (isInSelection) {
//                //do nothing
//            } else {
//                super.onFocusChanged(focused, direction, previouslyFocusedRect);
//            }
//        }
//    }
//
//    @Override
//    public void onWindowFocusChanged(boolean hasWindowFocus) {
//        Log.i("tonghu","TextTextView, onWindowFocusChanged(L74): ");
//        super.onWindowFocusChanged(hasWindowFocus);
//    }


    @Override
    protected void onFocusChanged(boolean focused, int direction, Rect previouslyFocusedRect) {
        Log.i("tonghu","TextTextView, onFocusChanged(L97): " + focused);
        super.onFocusChanged(true, direction, previouslyFocusedRect);
    }

    public TextTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TextTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onCreateContextMenu(ContextMenu menu) {
        super.onCreateContextMenu(menu);
    }
}
