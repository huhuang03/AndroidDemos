package com.example.tonghu.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

/**
 * Created by tonghu on 1/7/15.
 */
public class DetailFragment extends Fragment{
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_rssitem_detail, container, false);
        return view;
    }

    public void setText(String item) {
        TextView textView = (TextView) getView().findViewById(R.id.tv_detail);
        textView.setText("detai: " + item);
    }
}
