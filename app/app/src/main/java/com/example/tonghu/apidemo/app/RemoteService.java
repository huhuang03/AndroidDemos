package com.example.tonghu.apidemo.app;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

/**
 * Created by tonghu on 1/30/15.
 */
public class RemoteService extends Service{

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
