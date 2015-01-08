package com.example.tonghu.fragment;

import android.app.Activity;
import android.os.Bundle;

/**
 * Created by tonghu on 1/7/15.
 */
public class DetaillActivity extends Activity{
    public static final String EXTRA_LINK = "link";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        DetailFragment detailFragment = (DetailFragment) getFragmentManager().findFragmentById(R.id.f_detail);
        detailFragment.setText(getIntent().getStringExtra(EXTRA_LINK));
    }
}
