package cn.tonghu.fileencryption.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import cn.tonghu.fileencryption.R;
import cn.tonghu.fileencryption.fragment.FileListFragment;
import cn.tonghu.fileencryption.fragment.UnEncryptionFileListFragment;

/**
 * Created by tonghu on 2/5/15.
 */
public class UnEncryptionActivity extends FragmentActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_encryption);
        getSupportFragmentManager().beginTransaction().replace(R.id.content, new UnEncryptionFileListFragment(), "unencrypt")
                .commit();
    }
}
