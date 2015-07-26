package com.example.tonghu.meterialdemo;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.widget.TextView;


public class MainActivity extends Activity {
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        String data[] = new String[]{"aa", "bb", "cc", "dd", "ee", "ff", "hh", "ii", "jj", "kk", "ll", "mm", "nn", "oo",
//                "aa", "bb", "cc", "dd", "ee", "ff", "hh", "ii", "jj", "kk", "ll", "mm", "nn", "oo",
//                "aa", "bb", "cc", "dd", "ee", "ff", "hh", "ii", "jj", "kk", "ll", "mm", "nn", "oo",
//                "aa", "bb", "cc", "dd", "ee", "ff", "hh", "ii", "jj", "kk", "ll", "mm", "nn", "oo"};
//        recyclerView = (RecyclerView) findViewById(R.id.rv);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        recyclerView.setAdapter(new MyAdatper(data));

        final View cardView = findViewById(R.id.cv);
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (cardView.getVisibility() == View.VISIBLE) {
                    hide(cardView);
                } else {
                    showView(cardView);
                }
            }
        });
    }

    public static class MyAdatper extends  RecyclerView.Adapter<MyAdatper.ViewHolder> {
        private String[] data = null;

        public MyAdatper(String[] data) {
            this.data = data;
        }

        public static class ViewHolder extends  RecyclerView.ViewHolder {
            public TextView textView;


            public ViewHolder(TextView textView) {
                super(textView);
                this.textView = textView;
            }
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            TextView textView = new TextView(viewGroup.getContext());
            return new ViewHolder(textView);
        }

        @Override
        public void onBindViewHolder(ViewHolder viewHolder, int i) {
            viewHolder.textView.setText(data[i]);
        }

        @Override
        public int getItemCount() {
            return data.length;
        }

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

    private void showView(View view) {
        int cx = (view.getLeft() + view.getRight()) / 2;
        int cy = (view.getTop() + view.getBottom()) / 2;

        int finalRadius = Math.max(view.getWidth(), view.getHeight());

        Animator anim = ViewAnimationUtils.createCircularReveal(view, cx, cy, 0, finalRadius);

        view.setVisibility(View.VISIBLE);
        anim.start();
    }

    private void hide(final View view) {
        int cx = (view.getLeft() + view.getRight()) / 2;
        int cy = (view.getTop() + view.getBottom()) / 2;

        int initialRadius = view.getWidth();

        Animator anim = ViewAnimationUtils.createCircularReveal(view, cx, cy, initialRadius, 0);
        anim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                view.setVisibility(View.INVISIBLE);
            }
        });

        anim.start();
    }

}
