package com.tonghu.getresolution;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = (TextView)findViewById(R.id.textView);
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        StringBuilder sb = new StringBuilder();
        sb.append("px(w * H) : ");
        sb.append(displayMetrics.widthPixels).append(" * ").append(displayMetrics.heightPixels).append("\n");
        sb.append("density: ").append(displayMetrics.density).append("\n");
        sb.append("densityDpi: ").append(displayMetrics.densityDpi).append("\n");

        sb.append("dp(w * H) : ");
        sb.append(px2dp(this,displayMetrics.widthPixels)).append(" * ").append(px2dp(this,displayMetrics.heightPixels)).append("\n");
        textView.setText(sb.toString());
    }

    public static final int dp2px(Context context, int dp) {
        float desity = context.getResources().getDisplayMetrics().density;
        return (int) (dp * desity);
    }


    public static final int px2dp(Context context, int px) {
        float desity = context.getResources().getDisplayMetrics().density;
        return (int) (px / desity);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
