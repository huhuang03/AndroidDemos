package cn.tonghu.fileencryption.global;

import android.content.Context;
import android.os.Environment;

import java.io.File;

/**
 * Created by tonghu on 2/4/15.
 */
public class GlobalVariable {

    public static String getEnterNum() {
        return "*0414#";
    }

    public static File getSecriteRootDictionary() {
        File file  = new File(Environment.getExternalStorageDirectory().getAbsolutePath(),
                "." + "cn.tonghu.fileencryption");
        if (!file.exists()) {
            file.mkdir();
        }
        return file;
    }

}
