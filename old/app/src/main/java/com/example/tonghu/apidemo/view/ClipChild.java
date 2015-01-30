package com.example.tonghu.apidemo.view;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.example.tonghu.apidemo.R;

public class ClipChild extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.actiivty_clip_child);
		TextView tView = (TextView) findViewById(R.id.tv_clip);
		tView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
//				v.setScaleX(v.getScaleX() + 0.5f);
				ObjectAnimator animation = ObjectAnimator.ofFloat(v, "scaleX", v.getScaleX(), v.getScaleX() + 0.5f);
				animation.start();
			}
		});
	}
}
