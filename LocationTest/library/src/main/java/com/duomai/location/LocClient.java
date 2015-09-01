package com.duomai.location;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import com.duomai.location.internal.AndroidLocFirstBehavior;
import com.duomai.location.internal.LocHandleBehavior;
import com.duomai.location.internal.wheel.BaseLocateManager;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import static com.duomai.location.internal.wheel.LocationManagerSimpleFactory.TYPE_AN;
import static com.duomai.location.internal.wheel.LocationManagerSimpleFactory.TYPE_G;
import static com.duomai.location.internal.wheel.LocationManagerSimpleFactory.TYPE_IP;
import static com.duomai.location.internal.wheel.LocationManagerSimpleFactory.getBaseLocationManagerByType;


/**
 *
 * 暂时不考虑start()即重复定位，只支持{@link #requestOnce()}
 *
 * {@linkplain #requestOnce()}
 *
 * 暂时不考虑这种情况：在某一个定位还没有完成的时候，开启新一轮定位。这个时候其实结果还是上一次定位的，
 * 但是无所谓，能拿到数据就行
 *
 * 使用方法：
 * <code>
 *  LocClient locClient = LocClient.getInstance(context);
 *  LocOptions locOptions = new LocOptions();
 *  locOptions.setOnGetLocationListener(handleAtHere);
 *  locClient.setOptions(locOptions);
 *  locClient.requestOnce();
 * </code>
 *
 * 三种定位方式：
 *      1. Android自己的定位
 *      2. Google定位
 *      3. Wifi定位
 *
 * 目前策略：
 *      三种定位同时开启
 *      优先使用Google定位和gps定位
 *      如果在超时时间的2／3时间内Google定位和gps定位没有得到结果，则使用wifi定位结果
 *
 * 接下来考虑回收的问题
 * 如果不使用了LocClient，应主动调用{@link #release()}，因为是静态的，没法做到自动回收
 * @author york
 * @date 8/27/15
 * @since 1.0.0
 */
public class LocClient implements BaseLocateManager.OnGetLocationListener {
    private static LocClient instance;
    private Context context;
    private BaseLocateManager googleLocationManager;
    private BaseLocateManager ipLocationManager;
    private BaseLocateManager androidLocationManager;
    private LocHandleBehavior locHandleBehavior;
    private LocOptions locOptions;
    private ThreadPoolExecutor threadPoolExecutor;

    private Handler mainHandler = new Handler(Looper.getMainLooper());

    public Handler getMainHandler() {
        return mainHandler;
    }

    private LocClient(Context context) {
        this.context = context;
        locHandleBehavior = new AndroidLocFirstBehavior(this);
        if (!isInitialized()) {
            init();
        }
    }

    public static LocClient getInstance(Context context) {
        if (instance == null) {
            synchronized(LocClient.class) {
                if (instance == null) {
                    instance = new LocClient(context.getApplicationContext());
                }
            }
        }
        return instance;
    }

    public LocClient setLocOptions(LocOptions locOptions) {
        this.locOptions = locOptions;
        return this;
    }

    public LocOptions getLocOptions() {
        return locOptions;
    }


    /**
     * 如果正在定位，因为我们同时处理两个请求，所以直接返回false
     * @return 如果正在定位中，则返回false
     */
    public boolean requestOnce() {
        threadPoolExecutor.execute(new Runnable() {
            @Override
            public void run() {
                googleLocationManager.start();
            }
        });
        threadPoolExecutor.execute(new Runnable() {
            @Override
            public void run() {
                ipLocationManager.start();
            }
        });
        threadPoolExecutor.execute(new Runnable() {
            @Override
            public void run() {
                androidLocationManager.start();
            }
        });

        mainHandler.removeCallbacks(timeOutRunnable);
        mainHandler.removeCallbacks(googleAndAndroidTimeOut);
        mainHandler.postDelayed(timeOutRunnable, locOptions.getTimeOut());
        mainHandler.postDelayed(googleAndAndroidTimeOut, (long) (locOptions.getTimeOut() * (2 / 3.0)));
        locHandleBehavior.onRequestOnce();
        return false;
    }

    private Runnable timeOutRunnable = new Runnable() {
        @Override
        public void run() {
            LocResult locResult = new LocResult();
            locResult.setErrCode(ErrCode.ERR_TIME_OUT);
            locHandleBehavior.onGetLocation(locResult);
        }
    };

    private Runnable googleAndAndroidTimeOut = new Runnable() {
        @Override
        public void run() {
            locHandleBehavior.onGoogleOrAndroidLocTimeEnd();
        }
    };

    public void release() {
        stop();
        googleLocationManager = null;
        ipLocationManager = null;
        androidLocationManager = null;
        instance = null;
        threadPoolExecutor = null;
    }

    /**
     * @hide
     */
    private void stop() {
        mainHandler.removeCallbacks(timeOutRunnable);
        mainHandler.removeCallbacks(googleAndAndroidTimeOut);
        googleLocationManager.stop();
        ipLocationManager.stop();
        androidLocationManager.stop();
        if (!threadPoolExecutor.isShutdown()) {
            threadPoolExecutor.shutdownNow();
        }
    }


    private void init() {
        googleLocationManager = getBaseLocationManagerByType(context, TYPE_G);
        ipLocationManager = getBaseLocationManagerByType(context, TYPE_IP);
        androidLocationManager = getBaseLocationManagerByType(context, TYPE_AN);

        googleLocationManager.setOnGetLocationListener(this);
        ipLocationManager.setOnGetLocationListener(this);
        androidLocationManager.setOnGetLocationListener(this);
        threadPoolExecutor = new ThreadPoolExecutor(3, 3, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingDeque<Runnable>(),
                new ThreadFactory() {
                    @Override
                    public Thread newThread(Runnable r) {
                        return new Thread(r);
                    }
                });
    }

    private boolean isInitialized() {
        return googleLocationManager != null;
    }

    @Override
    public void onGetLocation(LocResult locResult) {
        locHandleBehavior.onGetLocation(locResult);
    }

    public static void showUpdateGoogleServiceDialog(Activity activity) {
        GooglePlayServicesUtil.getErrorDialog(ConnectionResult.SERVICE_VERSION_UPDATE_REQUIRED, activity, 0).show();
    }

}
