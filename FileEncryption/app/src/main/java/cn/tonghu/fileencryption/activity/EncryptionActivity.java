package cn.tonghu.fileencryption.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;

import cn.tonghu.fileencryption.R;
import cn.tonghu.fileencryption.fragment.EncryptionFileListFragment;

/**
 * Created by tonghu on 2/5/15.
 */
public class EncryptionActivity extends FragmentActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_encryption);
        getSupportFragmentManager().beginTransaction().replace(R.id.content, new EncryptionFileListFragment(), "encrypt")
                .commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
}
