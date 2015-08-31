package com.duomai.location;

/**
 * @author york
 * @date 8/27/15
 * @since 1.0.0
 */
public class LocOptions {
    private LocListener locListener;
    private static final int TIME_OUT = 5000;   //ms
    private int timeOut = TIME_OUT;

    public LocOptions() {
        timeOut = TIME_OUT;
    }

    public void setLocListener(LocListener locListener) {
        this.locListener = locListener;
    }

    public LocListener getLocListener() {
        return locListener;
    }

    public void setTimeOut(int timeOut) {
        this.timeOut = timeOut;
    }

    public int getTimeOut() {
        return timeOut;
    }
}
