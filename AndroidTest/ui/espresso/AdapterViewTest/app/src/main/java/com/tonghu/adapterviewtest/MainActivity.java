package com.tonghu.adapterviewtest;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends ListActivity {
    private List<String> stringList = new ArrayList<>();
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        for (int i = 0; i < 100; i++) {
            stringList.add("Item: " + i);
        }
        listView = (ListView) findViewById(android.R.id.list);
        getListView().setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);
        getListView().setAdapter(new BaseAdapter() {
            @Override
            public int getCount() {
                return stringList.size();
            }

            @Override
            public Object getItem(int position) {
                return stringList.get(position);
            }

            @Override
            public long getItemId(int position) {
                return 0;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                if (convertView == null) {
                    View inflate = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_single_choice, parent, false);
                    convertView = inflate;
                }
                ((TextView) convertView).setText(stringList.get(position));
                return convertView;
            }
        });
    }

    @Override
    public ListView getListView() {
        return listView;
    }
}
