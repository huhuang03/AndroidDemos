package com.example.tonghu.apidemo.view;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.tonghu.apidemo.Cheeses;
import com.example.tonghu.apidemo.R;

import java.util.List;

/**
 * Created by tonghu on 1/19/15.
 */
public class SwipeRefreshLayoutDemo extends Activity {
    private ArrayAdapter<String> adapter;
    private ListView listView;
    private SwipeRefreshLayout swipeRefreshLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swipe_refresh_layout);
        listView = (ListView) findViewById(R.id.lv);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,
                android.R.id.text1, Cheeses.randomList(20));
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.srl);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new DummyBackgroundTask().execute();
            }
        });
        swipeRefreshLayout.setColorScheme(R.color.swipe_color_1,
        R.color.swipe_color_2, R.color.swipe_color_3, R.color.swipe_color_4);
        listView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add("refresh");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (!swipeRefreshLayout.isRefreshing()) {
            swipeRefreshLayout.setRefreshing(true);
            new DummyBackgroundTask().execute();
        }
        return true;
    }

    /**
     * Dummy {@link AsyncTask} which simulates a long running task to fetch new cheeses.
     */
    private class DummyBackgroundTask extends AsyncTask<Void, Void, List<String>> {

        static final int TASK_DURATION = 3 * 1000; // 3 seconds

        @Override
        protected List<String> doInBackground(Void... params) {
            // Sleep for a small amount of time to simulate a background-task
            try {
                Thread.sleep(TASK_DURATION);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // Return a new random list of cheeses
            return Cheeses.randomList(20);
        }

        @Override
        protected void onPostExecute(List<String> result) {
            super.onPostExecute(result);

            // Tell the Fragment that the refresh has completed
            onRefreshComplete(result);
        }

    }

    private void onRefreshComplete(List<String> result) {
        adapter.clear();
        adapter.addAll(result);
        swipeRefreshLayout.setRefreshing(false);
    }


}
