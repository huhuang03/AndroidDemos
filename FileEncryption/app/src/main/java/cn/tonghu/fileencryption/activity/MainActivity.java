package cn.tonghu.fileencryption.activity;

import android.app.Activity;
import android.app.TabActivity;
import android.content.Intent;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TabHost;

import cn.tonghu.fileencryption.R;
import cn.tonghu.fileencryption.fragment.EncryptionFileListFragment;
import cn.tonghu.fileencryption.fragment.FileListFragment;
import cn.tonghu.fileencryption.fragment.UnEncryptionFileListFragment;
import cn.tonghu.fileencryption.utils.LogUtils;


public class MainActivity extends TabActivity {
    private TabHost mTabHost;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        TabActivity;
//        Fragment fragment = new FileListFragment();
//        Bundle argument = new Bundle();
//        argument.putSerializable(FileListFragment.EXTRA_ROOT_FILE, Environment.getExternalStorageDirectory());
//        fragment.setArguments(argument);
//        getSupportFragmentManager().beginTransaction().replace(R.id.container,
//                FileListFragment.createByRootFile(Environment.getExternalStorageDirectory(), FileListFragment.TYPE_ENCRYPTION)).commit();

        setContentView(R.layout.activity_main);
//        mTabHost = (FragmentTabHost)findViewById(android.R.id.tabhost);
//        mTabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);
        mTabHost = getTabHost();

//        mTabHost.addTab(mTabHost.newTabSpec("simple").setIndicator("未加密"),
//                EncryptionFileListFragment.class, null);
//        mTabHost.addTab(mTabHost.newTabSpec("simple1").setIndicator("已加密"),
//                UnEncryptionFileListFragment.class, null);

        mTabHost.addTab(mTabHost.newTabSpec("tag_unencrypt").setContent(new Intent(this, UnEncryptionActivity.class))
                .setIndicator("已加密"));
        mTabHost.addTab(mTabHost.newTabSpec("tag_encrypt").setContent(new Intent(this, EncryptionActivity.class))
                .setIndicator("未加密"));

        mTabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                LogUtils.i(tabId);
                mTabHost.setCurrentTabByTag(tabId);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
