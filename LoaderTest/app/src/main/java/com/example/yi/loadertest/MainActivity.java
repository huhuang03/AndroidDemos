package com.example.yi.loadertest;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Integer>> {

    @Override
    public Loader<List<Integer>> onCreateLoader(int id, Bundle args) {
        Log.i("tonghu", "onCreateLoader() ");
        return new AsyncTaskLoader<List<Integer>>(this) {

            @Override
            public List<Integer> loadInBackground() {
                Log.i("tonghu", "loadInBackground() ");
                return createDate();
            }

        };

    }

    @Override
    public void onLoadFinished(android.support.v4.content.Loader<List<Integer>> loader, List<Integer> data) {
        MainActivity.this.data.addAll(data);
        Log.i("tonghu", "onLoadFinished() data: " + MainActivity.this.data);
    }

    @Override
    public void onLoaderReset(android.support.v4.content.Loader<List<Integer>> loader) {

    }

    private static final String TAG = MainActivity.class.getSimpleName();


    private int now = 0;

    private List<Integer> data = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });
        fab.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                // Refresh
                return false;
            }
        });
        getSupportLoaderManager().initLoader(0, null, this)
//                ;
                .forceLoad();
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

    private List<Integer> createDate() {
        List<Integer> rst = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            rst.add(now++);
        }
        return rst;
    }
}
