package cn.tonghu.fileencryption.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import java.io.File;
import java.util.List;

import cn.tonghu.fileencryption.base.BaseListAdapter;

/**
 * Created by tonghu on 2/5/15.
 */
public class FileListAdapter extends BaseListAdapter<File> {


    public FileListAdapter(List<File> datas) {
        super(datas);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_checked, parent, false);
        }
        TextView tv = (TextView) convertView;
        tv.setText(datas.get(position).getName());
        return convertView;
    }

}
