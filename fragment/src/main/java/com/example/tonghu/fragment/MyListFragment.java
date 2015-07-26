package com.example.tonghu.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by tonghu on 1/7/15.
 */
public class MyListFragment extends Fragment{
    private OnItemSelectedListener listener;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_rsslist_overview, container, false);
        view.findViewById(R.id.bt).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateDetail();
            }
        });
        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof OnItemSelectedListener) {
            listener = (OnItemSelectedListener) activity;
        } else {
            throw new ClassCastException(activity.toString()
                    + " must implemenet MyListFragment.OnItemSelectedListener");
        }
    }

    public interface OnItemSelectedListener{
        public void onRSSItemSelected(String link);
    }

    private void updateDetail() {
        String newTime = String.valueOf(System.currentTimeMillis());
        listener.onRSSItemSelected(newTime);
    }
}
