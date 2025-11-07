package com.th.android.replace_instrumentation

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Instrumentation
import android.content.Intent
import android.os.Bundle
import android.util.Log


@SuppressLint("PrivateApi", "DiscouragedPrivateApi")
fun replaceIInstruction() {
    try {
        val activityThreadClass = Class.forName("android.app.ActivityThread")
        val activityThread =
            activityThreadClass.getDeclaredField("sCurrentActivityThread").apply {
                isAccessible = true
            }.get(null)
        val mInstrumentationField = activityThreadClass.getDeclaredField("mInstrumentation")
        val srcInstrumentation = mInstrumentationField.apply {
            isAccessible = true
        }.get(activityThread) as Instrumentation
        if (srcInstrumentation is HackedInstrumentation) {
            return
        }
        val hackedInstrumentation =  HackedInstrumentation(srcInstrumentation)
        mInstrumentationField.apply {
            isAccessible = true
            set(activityThread, hackedInstrumentation)
        }
        Log.i("test", "activityThread: $activityThread, srcInstrumentation: $srcInstrumentation")
    } catch (e: Throwable) {
        Log.i("test", Log.getStackTraceString(e))
    }
}

class HackedInstrumentation(val src: Instrumentation): Instrumentation() {
    override fun newActivity(cl: ClassLoader?, className: String?, intent: Intent?): Activity {
        Log.i("test", "newActivity called with: ${className}, intent: $intent")
        return src.newActivity(cl, "com.th.android.replace_instrumentation.MainActivity2", intent)
    }

    override fun callActivityOnCreate(activity: Activity?, icicle: Bundle?) {
        Log.i("test", "callActivityOnCreate called for: $activity")
        super.callActivityOnCreate(activity, icicle)
    }

}

