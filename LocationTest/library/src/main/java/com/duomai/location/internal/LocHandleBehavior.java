package com.duomai.location.internal;


import com.duomai.location.LocResult;

/**
 * 定位时间处理类
 * @author york
 * @date 8/27/15
 * @since 1.0.0
 */
public interface LocHandleBehavior {

    /**
     * 获取到定位
     */
    void onGetLocation(LocResult locResult);

    /**
     * 给谷歌和安卓定位的时间到。
     */
    void onGoogleOrAndroidLocTimeEnd();

}
