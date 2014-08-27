package com.example.myapidemo;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MenuItem.OnActionExpandListener;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		MenuItem menuItem = menu.findItem(R.id.search);
		menuItem.setOnActionExpandListener(new OnActionExpandListener() {
			
			@Override
			public boolean onMenuItemActionExpand(MenuItem item) {
				Log.i("tonghu",
						"MainActivity.onCreateOptionsMenu(...).new OnActionExpandListener() {...}－onMenuItemActionExpand :");
				return true;
			}
			
			@Override
			public boolean onMenuItemActionCollapse(MenuItem item) {
				Log.i("tonghu",
						"MainActivity.onCreateOptionsMenu(...).new OnActionExpandListener() {...}－onMenuItemActionCollapse :");
				return true;
			}
		});
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
//		return super.onOptionsItemSelected(item);
		return true;
	}

}
