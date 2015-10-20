package com.tonghu.hidesoftboardtest;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;

/**
 * @author york
 * @date 9/24/15
 * @since 1.0.0
 */
public class InputActiivty extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input);
        findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                InputActiivty.this.finish();
                AlertDialog.Builder builder = new AlertDialog.Builder(InputActiivty.this);
                builder.setPositiveButton("退出", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        InputActiivty.this.finish();
                    }
                });
                builder.show();
            }
        });
    }
}
