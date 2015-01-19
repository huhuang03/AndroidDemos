package com.example.tonghu.apidemo.view;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Toast;

import com.example.tonghu.apidemo.R;

public class TakeShootCut extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.activity_take_shootcut);
		super.onCreate(savedInstanceState);
	}
	
	public void shutcut(View view) {
		view.getRootView().setDrawingCacheEnabled(true);
		view.getRootView().setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
		view.getRootView().buildDrawingCache();
		Bitmap bitmap = view.getRootView().getDrawingCache();
		File file = new File(Environment.getExternalStorageDirectory().getPath() + File.separator + System.currentTimeMillis() + ".jpg");
		try {
			bitmap.compress(Bitmap.CompressFormat.JPEG, 100, new FileOutputStream(file));
			Toast.makeText(getBaseContext(), "save to file done", Toast.LENGTH_LONG).show();
			Intent intent = new Intent(Intent.ACTION_VIEW);
			intent.setDataAndType(Uri.fromFile(file), "image/jpeg");
			startActivity(intent);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
	}
}
