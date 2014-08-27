package com.example.tonghu.apidemo;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.ListActivity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class MainActivity extends ListActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		setContentView(R.layout.activity_main);
//		getActionBar().setDisplayHomeAsUpEnabled(true);
		String pathPrefix = getIntent().getStringExtra(Constants.EXTRA_PATH_PREFIX);
		if (pathPrefix == null) {
			pathPrefix = "";
		}
		
		getListView().setAdapter(new SimpleAdapter(this, genDatas(pathPrefix), android.R.layout.simple_list_item_1,
				new String[]{"title"}, new int[]{android.R.id.text1}));
		
	}
	
	private List<Map<String, Object>> genDatas(String prefix) {
		List<Map<String, Object>> rst = new ArrayList<Map<String,Object>>();
		Intent mainIntent = new Intent(Intent.ACTION_MAIN);
		mainIntent.addCategory(Constants.CATEGORY);
		
		PackageManager pm = getPackageManager();
		List<ResolveInfo> resolveInfos = pm.queryIntentActivities(mainIntent, 0);
		
		if (resolveInfos == null || resolveInfos.size() == 0) {
			return rst;
		}
		
		String[] prefixPath;
		String prefixWithSlash = prefix;
		if (prefix.equals("")) {
			prefixPath = null;
		} else {
			prefixPath = prefix.split("/");
			prefixWithSlash = prefix + "/";
		}
		
		int len = resolveInfos.size();
		List<String> entries = new ArrayList<String>();
		for (int i = 0; i < len; i++) {
			ResolveInfo info = resolveInfos.get(i);
			CharSequence labelSeq = info.loadLabel(pm);
			String label = labelSeq == null? info.activityInfo.name : labelSeq.toString();
			
			if (prefixWithSlash.length() == 0 || label.startsWith(prefixWithSlash)) {
                
                String[] labelPath = label.split("/");

                String nextLabel = prefixPath == null ? labelPath[0] : labelPath[prefixPath.length];

                if ((prefixPath != null ? prefixPath.length : 0) == labelPath.length - 1) {
                    addItem(rst, nextLabel, activityIntent(
                            info.activityInfo.applicationInfo.packageName,
                            info.activityInfo.name));
                } else {
                    if (!entries.contains(nextLabel)) {
                        addItem(rst, nextLabel, browseIntent(prefix.equals("") ? nextLabel : prefix + "/" + nextLabel));
                        entries.add(nextLabel);
                    }
                }
            }
		}
		
		return rst;
	}
	
	/* 添加title 和intent */
    protected void addItem(List<Map<String, Object>> data, String name, Intent intent) {
        Map<String, Object> temp = new HashMap<String, Object>();
        temp.put("title", name);
        temp.put("intent", intent);
        data.add(temp);
    }
    
    protected void onListItemClick(ListView l, View v, int position, long id) {
        @SuppressWarnings("unchecked")
		Map<String, Object> map = (Map<String, Object>)l.getItemAtPosition(position);
        Intent intent = (Intent) map.get("intent");
        startActivity(intent);
    }
	
	private Intent activityIntent(String pkgName, String clsName) {
		Intent intent = new Intent();
		intent.setClassName(pkgName, clsName);
		return intent;
	}
	
	private Intent browseIntent(String path) {
		Intent intent = new Intent();
		intent.putExtra(Constants.EXTRA_PATH_PREFIX, path);
		intent.setClass(this, MainActivity.class);
		return intent;
	}
	
	
	private Comparator<Map<String, Object>> mComparator = new Comparator<Map<String,Object>>() {
		/* 工厂构造方法，能返回子类而不让上层知道子类的存在 */
		private Collator collator = Collator.getInstance();
		@Override
		public int compare(Map<String, Object> lhs, Map<String, Object> rhs) {
			return collator.compare(lhs.get("title"), rhs.get("title"));
		}
	};

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
//		getMenuInflater().inflate(R.menu.main, menu);
//		MenuItem menuItem = menu.findItem(R.id.search);
//		menuItem.setOnActionExpandListener(new OnActionExpandListener() {
//			
//			@Override
//			public boolean onMenuItemActionExpand(MenuItem item) {
//				Log.i("tonghu",
//						"MainActivity.onCreateOptionsMenu(...).new OnActionExpandListener() {...}－onMenuItemActionExpand :");
//				return true;
//			}
//			
//			@Override
//			public boolean onMenuItemActionCollapse(MenuItem item) {
//				Log.i("tonghu",
//						"MainActivity.onCreateOptionsMenu(...).new OnActionExpandListener() {...}－onMenuItemActionCollapse :");
//				return true;
//			}
//		});
//		ShareActionProvider shareActionProvider = (ShareActionProvider) menu.findItem(R.id.share).getActionProvider();
//		shareActionProvider.setShareIntent(getDefaultIntent());
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		return true;
	}

	private Intent getDefaultIntent() {
	    Intent intent = new Intent(Intent.ACTION_SEND);
	    intent.setType("image/*");
	    return intent;
	}
}
