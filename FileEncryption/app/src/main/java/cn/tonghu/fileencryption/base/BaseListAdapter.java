package cn.tonghu.fileencryption.base;

import java.util.List;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

/**
 * 以list为数据源的adapter
 * @author Administrator
 *
 * @param <B>
 */
public class BaseListAdapter<B> extends BaseAdapter {
	protected List<B> datas; 
	
	public BaseListAdapter(List<B> datas) {
		super();
		this.datas = datas;
	}

	@Override
	public int getCount() {
		return datas == null ? 0 : datas.size();
	}

	@Override
	public Object getItem(int position) {
		return datas.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		return null;
	}
	
	public void notifyWithData(List<B> datas) {
		this.datas = datas;
		notifyDataSetChanged();
	}

}
