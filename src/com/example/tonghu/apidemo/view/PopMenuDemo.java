package com.example.tonghu.apidemo.view;

import com.example.tonghu.apidemo.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.Toast;
import android.widget.PopupMenu.OnMenuItemClickListener;

public class PopMenuDemo extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
	}
	
	public void popup(View view) {
		PopupMenu popupMenu = new PopupMenu(this, view);
		popupMenu.inflate(R.menu.popup);
		popupMenu.setOnMenuItemClickListener(new OnMenuItemClickListener() {
			
			@Override
			public boolean onMenuItemClick(MenuItem item) {
				Toast.makeText(getBaseContext(), item.getTitle(), Toast.LENGTH_SHORT).show();
				return true;
			}
		});
		popupMenu.show();
	}
}
