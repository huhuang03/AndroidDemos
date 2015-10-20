package com.tonghu.androiddesignlibrarytest;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

/**
 * @author york
 * @date 9/30/15
 * @since 1.0.0
 */
public class HomeActivity extends ListActivity{
    private static final String[] TITLES = new String[] {"Floating Action Button", "AppBarLayout"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getListView().setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, TITLES));
        getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent();
                switch (position) {
                    case 0:
                        i = new Intent(HomeActivity.this, MainActivity.class);
                        break;
                }
                startActivity(i);
            }
        });
    }
}
