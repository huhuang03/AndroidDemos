package com.example.tonghu.apidemo.app;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import com.example.tonghu.apidemo.R;

public class ActionBarTabs extends Activity{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
		setContentView(R.layout.activity_actionbar_tabs);
	}
	
	public void onAddTab(View v) {
		ActionBar bar = getActionBar();
		String text = "Tab: " + bar.getTabCount();
		bar.addTab(bar.newTab().setText(text)
				.setTabListener(new MyTabListener(new TabContentFragment(text))));
	}
	
	public static class TabContentFragment extends Fragment {
		private String text;

		public TabContentFragment() {
			super();
		}
		
		public TabContentFragment(String text) {
			this();
			this.text = text;
		}
		
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			TextView tView = new TextView(container.getContext());
			tView.setText(text);
			return tView;
		}
		
		public String getText() {
			return text;
		}
		
	};
	
	public void onToggleTabs(View view) {
		ActionBar actionBar = getActionBar();
		
		if (actionBar.getNavigationMode() == ActionBar.NAVIGATION_MODE_STANDARD) {
			actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
			actionBar.setDisplayOptions(0, ActionBar.DISPLAY_SHOW_TITLE);
		} else {
			actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
			actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_TITLE, ActionBar.DISPLAY_SHOW_TITLE);
		}
	}
	
	public void onRemoveTab(View view) {
		if (getActionBar().getTabCount() > 0) {
			getActionBar().removeTabAt(getActionBar().getTabCount() - 1);
		}
	}
	
	public void onRemoveAllTabs(View view) {
		getActionBar().removeAllTabs();
	}
	
	private class MyTabListener implements TabListener {
		private TabContentFragment fragment;
		
		
		public MyTabListener(TabContentFragment fragment) {
			super();
			this.fragment = fragment;
		}

		@Override
		public void onTabSelected(Tab tab, FragmentTransaction ft) {
			ft.add(R.id.fragment_content, fragment, fragment.getText());
		}

		@Override
		public void onTabUnselected(Tab tab, FragmentTransaction ft) {
			ft.remove(fragment);
		}

		@Override
		public void onTabReselected(Tab tab, FragmentTransaction ft) {
			// TODO Auto-generated method stub
			
		}
		
	}

}
