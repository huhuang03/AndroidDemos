package com.tonghu.locationtest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.duomai.location.ErrCode;
import com.duomai.location.LocClient;
import com.duomai.location.LocListener;
import com.duomai.location.LocOptions;
import com.duomai.location.LocResult;
import com.google.android.gms.common.GoogleApiAvailability;

import java.util.List;


public class MainActivity extends AppCompatActivity implements LocListener {
    private TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv = (TextView) findViewById(R.id.tv);
        LocOptions locOptions = new LocOptions();
        locOptions.setLocListener(this);
        locOptions.setTimeOut(15000);
        LocClient.getInstance(this).setLocOptions(locOptions);
    }

    public void start(View view) {
        tv.setText("定位中");
        LocClient.getInstance(this).requestOnce();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        int googlePlayServicesAvailable = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(this);
        Log.i("tonghu","MainActivity, onResume(L44): " + googlePlayServicesAvailable);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LocClient.getInstance(this).release();
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

    @Override
    public void onLocResult(ErrCode errCode, List<LocResult> results) {
        Log.i("tonghu", "MainActivity, onLocResult(L55): ");
        if (results.size() > 0) {
            tv.setText(results.get(0).toString());
        } else {
            tv.setText("定位失败!");
        }
        if (errCode == ErrCode.SERVICE_VERSION_UPDATE_REQUIRED) {
            LocClient.showUpdateGoogleServiceDialog(this);
        }
    }
}
