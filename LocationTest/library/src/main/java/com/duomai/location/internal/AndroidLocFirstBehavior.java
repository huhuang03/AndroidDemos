package com.duomai.location.internal;


import com.duomai.location.ErrCode;
import com.duomai.location.LocClient;
import com.duomai.location.LocResult;

import java.util.ArrayList;
import java.util.List;

/**
 * 优先使用Android和Google的定义
 * 如果在规定时间{@link #onGoogleOrAndroidLocTimeEnd()}。即总超时时间的2/3时间内，谷歌或安卓定位还没有结果
 * 而ip定位有结果时，使用ip定位
 * @author york
 * @date 8/28/15
 * @since 1.0.0
 */
public class AndroidLocFirstBehavior implements LocHandleBehavior{
    private LocClient locClient;
    private LocResult androidLocResult;
    private LocResult googleLocResult;
    private LocResult netLocResult;
    private boolean isHandled = false;

    public AndroidLocFirstBehavior(LocClient locClient) {
        this.locClient = locClient;
    }

    @Override
    public void onGetLocation(LocResult locResult) {
        if (!isHandled && locResult.getErrCode() == ErrCode.ERR_TIME_OUT) {
            handleResult(locResult.getErrCode(), null);
            return;
        }

        LocResult.From from = locResult.getFrom();
        if (from == LocResult.From.G) {
            googleLocResult = locResult;
            if (!isHandled) {
                List<LocResult> locResults = new ArrayList<>();
                locResults.add(googleLocResult);
                handleResult(ErrCode.OK, locResults);
            }
        } else if (from == LocResult.From.AN) {
            androidLocResult = locResult;
            if (!isHandled) {
                List<LocResult> locResults = new ArrayList<>();
                locResults.add(androidLocResult);
                handleResult(ErrCode.OK, locResults);
            }
        } else if (from == LocResult.From.IP) {
            netLocResult = locResult;
        }
    }

    private void handleResult(final ErrCode errCode, final List<LocResult> locResults) {
        locClient.getMainHandler().post(new Runnable() {
            @Override
            public void run() {
                if (locClient.getLocOptions().getLocListener() != null) {
                    locClient.getLocOptions().getLocListener().onLocResult(errCode, locResults);
                    isHandled = true;
                }
            }
        });
    }

    @Override
    public void onGoogleOrAndroidLocTimeEnd() {
        if (!isHandled  && netLocResult != null) {
            List<LocResult> locResults = new ArrayList<>();
            locResults.add(netLocResult);
            handleResult(ErrCode.OK, locResults);
        }
    }


}
