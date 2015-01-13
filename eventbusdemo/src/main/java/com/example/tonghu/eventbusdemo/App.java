package com.example.tonghu.eventbusdemo;

import android.app.Application;

import com.squareup.otto.Bus;
import com.squareup.otto.ThreadEnforcer;

/**
 * Created by tonghu on 1/8/15.
 */
public class App extends Application{
    public static Bus bus = new Bus(ThreadEnforcer.MAIN);
}
