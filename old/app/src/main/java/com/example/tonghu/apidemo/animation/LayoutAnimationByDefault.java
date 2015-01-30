package com.example.tonghu.apidemo.animation;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.GridView;

import com.example.tonghu.apidemo.R;

public class LayoutAnimationByDefault extends Activity {
	private int num;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_layout_animation_default);
		final GridLayout gridView = (GridLayout) findViewById(R.id.gv);
		findViewById(R.id.bt).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Button button = new Button(LayoutAnimationByDefault.this);
				button.setText(String.valueOf(num++));
				button.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						gridView.removeView(v);
					}
				});
				gridView.addView(button, gridView.getChildCount() == 0? 0 : 1);
			}
		});
	}
}
