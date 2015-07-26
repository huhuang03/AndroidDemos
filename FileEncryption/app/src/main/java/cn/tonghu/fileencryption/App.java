package cn.tonghu.fileencryption;

import android.app.Application;

import cn.tonghu.fileencryption.utils.LogUtils;

/**
 * Created by tonghu on 2/4/15.
 */
public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        LogUtils.customTagPrefix = "file_encryption";
    }
}
