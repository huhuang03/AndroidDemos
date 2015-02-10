package cn.tonghu.fileencryption.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import cn.tonghu.fileencryption.activity.MainActivity;
import cn.tonghu.fileencryption.global.GlobalVariable;

/**
 * Created by tonghu on 2/4/15.
 */
public class OutPhoneReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (GlobalVariable.getEnterNum().equals(intent.getStringExtra(Intent.EXTRA_PHONE_NUMBER))) {
            setResultData(null);
            Intent i = new Intent(context, MainActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(i);
        }
    }
}
