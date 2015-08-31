package com.tonghu.copyandpasterinlistview;

import android.app.ListActivity;
import android.os.Bundle;
import android.text.Selection;
import android.text.Spannable;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.tonghu.copyandpasterinlistview.lib.SelectableTextView;

/**
 * @author york
 * @date 8/17/15
 * @since 1.0.0
 */
public class SelectableListListActivity extends ListActivity{
    private final static int DEFAULT_SELECTION_LEN = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getListView().setAdapter(new BaseAdapter() {
            @Override
            public int getCount() {
                return 100;
            }

            @Override
            public Object getItem(int position) {
                return null;
            }

            @Override
            public long getItemId(int position) {
                return 0;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                if (convertView == null) {
                    convertView = getLayoutInflater().inflate(R.layout.item_list_selectable, parent, false);
                }
                SelectableTextView textView = (SelectableTextView) convertView;
//                    textView.setText(position + "fdafjidsajfodsafkldaskfldsakfdfjdajfkdlajkfldajkfldirjeiowmfklajfkldsajfkldsajfkdsajfdsjklfdsfdksj");
//                    Log.i("tonghu", "MainActivity, getView(L45): " + textView);
                Log.i("tonghu", "EditTextViewInListActivity, getView(L47): " + position);
//                if (TextUtils.isEmpty(textView.getText())) {
                    Log.i("tonghu", "EditTextViewInListActivity, getView(L51):  set text");
                    textView.setText("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" + position);
//                    Selection.setSelection((Spannable) textView.getText(), 5);
                initTextView(textView);
//                }

                return convertView;
            }
        });
        getListView().setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                return false;
            }
        });
    }

    private int mTouchX;
    private int mTouchY;

    private void initTextView(final SelectableTextView mTextView) {
        // make sure the TextView's BufferType is Spannable, see the main.xml
        mTextView.setDefaultSelectionColor(0x40FF00FF);


        mTextView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                showSelectionCursors(mTextView, mTouchX, mTouchY);
                return true;
            }
        });
        mTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTextView.hideCursor();
            }
        });
        mTextView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                mTouchX = (int) ((int) event.getX() + v.getX());
                mTouchY = (int) ((int) event.getY() + v.getY());
                return false;
            }
        });
    }

    private void showSelectionCursors(SelectableTextView mTextView, int x, int y) {
        int start = mTextView.getPreciseOffset(x, y);

        if (start > -1) {
            int end = start + DEFAULT_SELECTION_LEN;
            if (end >= mTextView.getText().length()) {
                end = mTextView.getText().length() - 1;
            }
            mTextView.showSelectionControls(start, end);
        }
    }
}
