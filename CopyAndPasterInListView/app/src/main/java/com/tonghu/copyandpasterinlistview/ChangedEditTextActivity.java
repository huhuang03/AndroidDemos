package com.tonghu.copyandpasterinlistview;

import android.app.Activity;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

/**
 * @author york
 * @date 8/17/15
 * @since 1.0.0
 */
public class ChangedEditTextActivity extends Activity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TextView editText = new TextTextView(this);
        editText.setTextIsSelectable(true);
        editText.setBackgroundColor(getResources().getColor(android.R.color.holo_green_dark));
        editText.setPadding(20, 20, 20, 20);
        editText.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        editText.setHint("你好");
        editText.setText("fdapfjiodasjfiodajfiovajfiodjsiofejiqofjdisoafjdioasfjkld;sajfioewqjfiodsajfiopdsjfiodsajfkd");
        setContentView(editText);
    }
}
