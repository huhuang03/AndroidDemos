package com.tonghu.databindingtest.controller;

import android.util.Log;
import android.view.View;

/**
 * @author york
 * @date 10/1/15
 * @since 1.0.0
 */
public class Handlers {

    public void onClickFriend(View view) {
        Log.i("tonghu", "Handlers, onClickFriend(L13): " + " click Friends!");
    }

    public void onClickEnemy(View view) {
        Log.i("tonghu","Handlers, onClickEnemy(L17): " + " click enemy!");
    }
}
