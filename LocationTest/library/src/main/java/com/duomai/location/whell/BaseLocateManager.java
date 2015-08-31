package com.duomai.location.whell;


import com.duomai.location.LocResult;

/**
 * Created by tonghu on 7/15/15.
 */
public abstract class BaseLocateManager {
    protected OnGetLocationListener onGetLocationListener;
    /**
     * 暂时不用
     */
    protected boolean isStopped = false;

    protected boolean isRunning = false;


    public abstract int getType();

    public void start() {
        if (isRunning) {
            return;
        }
        startInternal();
    }

    public void stop() {
        isStopped = true;
        stopInternal();
    }

    protected void handleGetLocation(LocResult locResult) {
        if (!isStopped && onGetLocationListener != null) {
            onGetLocationListener.onGetLocation(locResult);
        }
        isRunning = false;
    }

    protected  abstract void stopInternal();

    protected abstract void startInternal();

    public void setOnGetLocationListener(OnGetLocationListener onGetLocationListener) {
        this.onGetLocationListener = onGetLocationListener;
    }

    public interface OnGetLocationListener {
        void onGetLocation(LocResult locResult);
    }

}
