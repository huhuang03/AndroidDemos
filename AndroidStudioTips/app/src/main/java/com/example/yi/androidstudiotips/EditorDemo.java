package com.example.yi.androidstudiotips;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.util.Log;

import java.util.List;

/**
 * Created by yi on 1/15/16.
 */
public class EditorDemo {
    private static final String TAG = "EditorDemo";

    public boolean completion(String first, String second) {
        // Use tab to replace
        return  first.contentEquals(second);
    }

    public void completion2(Context context) {
        // User shift + ctrl + space to smart completion
        Bitmap bitmap = null;
        Drawable drawable = null;
    }

    public String selection(String first, String second, String third, String fourth) {
        return first.substring(0, first.length() - 1) + second + third + fourth.replace('a', 'b');
    }

    public static class InitializeFields {
        private final int first;
        private final boolean second;
        private final boolean third;
        private boolean forth;

        InitializeFields(int first, boolean second, boolean third, boolean forth) {

            this.first = first;
            this.second = second;
            this.third = third;
            this.forth = forth;
        }
    }

    public void instanceCheck(Object parameter) {
        if (parameter instanceof Context) {
            Context context = (Context) parameter;

        }
    }

    public int suppressStatements() {
        //noinspection UnnecessaryLocalVariable
        int result = 0;
        return result;
    }

    public void liveTemplate1(List<String> list) {
        //fori
        for (int j = 0; j < list.size(); j++) {

        }

        //list.fori
        for (int i = 0; i < list.size(); i++) {

        }

    }

    public int liveTempalte2(String p1, String p2) {
        //Logging: logt, logm, logr, logi
        Log.i(TAG, "liveTempalte2: ");
        Log.d(TAG, "liveTempalte2() called with: " + "p1 = [" + p1 + "], p2 = [" + p2 + "]");

        int result = 0;
        result++;

        Log.d(TAG, "liveTempalte2() returned: " + result);
        return result;
    }

    public void var() {
        //foo.var
        Class<? extends EditorDemo> aClass = getClass();
    }

}
