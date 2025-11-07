package com.th.android.replace_instrumentation

import android.app.Activity
import android.app.Application
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.compose.foundation.layout.Spacer
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.splashscreen.SplashScreen
import dalvik.system.DexFile
import java.lang.Exception
import java.util.logging.Logger

class App : Application() {

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
//        replaceIInstruction()
        disableSplashDelay()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            // 提前禁用 Splash 显示条件
            registerActivityLifecycleCallbacks(object : ActivityLifecycleCallbacks {
                override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
//                    activity.splashScreen.setKeepOnScreenCondition { false }
                }

                // 其他回调可以忽略
                override fun onActivityStarted(activity: Activity) {}
                override fun onActivityResumed(activity: Activity) {}
                override fun onActivityPaused(activity: Activity) {}
                override fun onActivityStopped(activity: Activity) {}
                override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {}
                override fun onActivityDestroyed(activity: Activity) {}
            })
        }
    }

    private fun disableSplash(clazz: Class<*>) {
        try  {
            val animationField = clazz.getDeclaredField("mIconAnimationDuration")
            animationField.isAccessible = true
            animationField.setLong(null, 0L)
        } catch (e: Exception) {
            Log.i("test1", e.stackTraceToString())
        }
    }

    fun disableSplashDelay() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            Log.i("test1", "aaaaaaaaaaa")
            android.window.SplashScreen::class.java.apply {
                declaredClasses.forEach {
                    Log.i("test1", "bbb: $it")
                    if (it.name.contains("SplashScreenImpl")) {
                        disableSplash(it)
                    }
                }
//                declaredClasses.apply {
//                    Log.i("test1", "bbb: $this")
//                }
            }
        }

        val dexFile = DexFile(packageCodePath)
        val entries = dexFile.entries()
        while (entries.hasMoreElements()) {
            val className = entries.nextElement()
            if (className.contains("SplashScreen")) {
                Log.i("class-debug", className)
            }
        }

        try {
//            val splashScreenClass = Class.forName("android.window.SplashScreen\$SplashScreenView")
            val splashScreenImplClass =
                Class.forName("android.window.SplashScreen.\$SplashScreenImpl")
            Log.i("test", "splashScreenImplClass: " + splashScreenImplClass)

            // 利用反射清除 animationController 或设置动画时长为 0
            val animationField = splashScreenImplClass.getDeclaredField("mIconAnimationDuration")
            animationField.isAccessible = true
            animationField.setLong(null, 0L)
            Log.i("test", "set long to 0")
        } catch (e: Throwable) {
            Log.e("test", "disableSplashDelay: $e")
        }
    }

}