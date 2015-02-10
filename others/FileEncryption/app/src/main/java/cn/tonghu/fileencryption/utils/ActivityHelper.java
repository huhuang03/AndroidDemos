package cn.tonghu.fileencryption.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class ActivityHelper {
	private Context context;
	
	public ActivityHelper(Activity context) {
		this.context = context;
	}
	
	public void showToast(String toast) {
		Toast.makeText(context, toast, Toast.LENGTH_SHORT).show();
	}
	
	public void showToast(int toastId) {
		showToast(context.getString(toastId));
	}

	public void startActivity(Class<? extends Activity> clazz) {
		Intent i = new Intent(context, clazz);
		context.startActivity(i);
	}
}
