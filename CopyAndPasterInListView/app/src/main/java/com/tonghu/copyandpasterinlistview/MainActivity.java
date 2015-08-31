package com.tonghu.copyandpasterinlistview;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends Activity {

    public static final String[] titles = new String[] {"Changed EditText", "EditText in ListView", "SelectableList"};

    public static final Class<? extends Activity>[] activities = new Class[]{ChangedEditTextActivity.class, EditTextViewInListActivity.class
    , SelectableListListActivity.class};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ListView listView = (ListView) findViewById(R.id.listView);
        if (listView != null) {
            listView.setAdapter(new BaseAdapter() {
                @Override
                public int getCount() {
                    return titles.length;
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
                public View getView(final int position, View convertView, ViewGroup parent) {
                    if (convertView == null) {
                        convertView = getLayoutInflater().inflate(R.layout.item_list, parent, false);
                    }
                    TextView textView = (TextView) convertView;
//                    textView.setText(position + "fdafjidsajfodsafkldaskfldsakfdfjdajfkdlajkfldajkfldirjeiowmfklajfkldsajfkldsajfkdsajfdsjklfdsfdksj");
//                    Log.i("tonghu", "MainActivity, getView(L45): " + textView);
                    textView.setText(titles[position]);
                    textView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent i = new Intent(MainActivity.this, activities[position]);
                            MainActivity.this.startActivity(i);
                        }
                    });
                    return convertView;
                }
            });
        }
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.i("tonghu", "MainActivity, onItemClick(L58): " + position);
            }
        });
    }

}
