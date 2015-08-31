package com.tonghu.copyandpasterinlistview;

import android.content.ClipData;
import android.content.Context;
import android.text.Layout;
import android.text.Selection;
import android.util.AttributeSet;
import android.util.Log;
import android.view.ActionMode;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.widget.EditText;

/**
 * @author york
 * @date 8/17/15
 * @since 1.0.0
 */
public class TextPage extends EditText{
    public TextPage(Context context) {
        super(context);
    }

    public TextPage(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TextPage(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

//        getOffsetForPosition()
        setCustomSelectionActionModeCallback(new ActionMode.Callback() {
            @Override
            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                Log.i("tonghu","TextPage, onCreateActionMode(L37): ");
                menu.close();
                return true;
            }

            @Override
            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                Log.i("tonghu","TextPage, onPrepareActionMode(L44): ");
                menu.close();
                return true;
            }

            @Override
            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                Log.i("tonghu","TextPage, onActionItemClicked(L51): ");
                return false;
            }

            @Override
            public void onDestroyActionMode(ActionMode mode) {
                Log.i("tonghu","TextPage, onDestroyActionMode(L57): ");
            }
        });

    }

//    @Override
//    protected boolean getDefaultEditable() {
//        return true;
//    }
//
//    @Override
//    protected void onCreateContextMenu(ContextMenu menu) {
//        //不做任何处理，为了阻止长按的时候弹出上下文菜单
//    }


//    private int off; //字符串的偏移值
//
//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//        Log.i("tonghu", "TextPage, onTouchEvent(L44): ");
//        int action = event.getAction();
//        Layout layout = getLayout();
//        int line = 0;
//        switch(action) {
//            case MotionEvent.ACTION_DOWN:
//                line = layout.getLineForVertical(getScrollY()+ (int)event.getY());
//                off = layout.getOffsetForHorizontal(line, (int) event.getX());
//                Selection.setSelection(getEditableText(), off);
//                Log.i("tonghu", "TextPage, onTouchEvent(L59): " + getEditableText());
//                break;
//            case MotionEvent.ACTION_MOVE:
//            case MotionEvent.ACTION_UP:
//                line = layout.getLineForVertical(getScrollY()+(int)event.getY());
//                int curOff = layout.getOffsetForHorizontal(line, (int)event.getX());
//                Log.i("tonghu","TextPage, onTouchEvent(L59): " + getEditableText());
//                Selection.setSelection(getEditableText(), off, curOff);
//                break;
//        }
//        return true;
//    }

}
