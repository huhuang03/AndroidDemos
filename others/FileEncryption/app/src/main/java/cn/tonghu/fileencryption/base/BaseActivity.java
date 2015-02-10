package cn.tonghu.fileencryption.base;

import android.app.Activity;
import android.os.Bundle;

import cn.tonghu.fileencryption.utils.ActivityHelper;

public class BaseActivity extends Activity{
	protected ActivityHelper helper;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		helper = new ActivityHelper(this);
	}
	
	protected void initPrecedure() {
		init();
		initView();
		initValue();
	}
	
	/**
	 * STEP 1 init something
	 */
	protected void init() {}

	/**
	 * STEP 2 findView and setListener and so on.
	 */
	protected void initView() {}

	/**
	 * STEP 3 init view' value. Get data from server and so on
	 */
	protected void initValue() {}
	
}