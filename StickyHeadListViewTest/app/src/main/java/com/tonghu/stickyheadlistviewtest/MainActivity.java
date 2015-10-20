package com.tonghu.stickyheadlistviewtest;

import android.app.ListActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends ListActivity {
    private View stickView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        stickView = findViewById(R.id.stickView);

        List<String> itemStrings = new ArrayList<>();
        for (int i = 0; i < 40; i++) {
            itemStrings.add("item --- " + (i + 1));
        }
        getListView().addHeaderView(View.inflate(this, R.layout.v_head, null));
        getListView().addHeaderView(View.inflate(this, R.layout.v_stick_head, null));
        getListView().setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, itemStrings));
        getListView().setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                Log.i("tonghu", "MainActivity, onScroll(L39): " + firstVisibleItem);
                if (firstVisibleItem >= 1) {
                    stickView.setVisibility(View.VISIBLE);
                } else {
                    stickView.setVisibility(View.GONE);
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
