package com.example.yi.tablayoutdemo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * Created by qiliantao on 3/17/16.
 */
public class MainBaseFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fragment_main_base, container, false);
        TextView tv = (TextView) inflate.findViewById(android.R.id.text1);
        tv.setText(this.getClass().getSimpleName());
        return inflate;
    }
}
