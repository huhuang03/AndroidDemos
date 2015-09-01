package com.duomai.location.internal;

import android.util.Log;

import com.duomai.location.ErrCode;
import com.duomai.location.LocClient;
import com.duomai.location.LocResult;

import java.util.ArrayList;
import java.util.List;

/**
 * @author york
 * @date 8/27/15
 * @since 1.0.0
 */
public class OnlyGoogleBehavior implements LocHandleBehavior{
    private LocClient locClient;
    private boolean isSuccess = false;

    public OnlyGoogleBehavior(LocClient locClient) {
        this.locClient = locClient;
    }

    @Override
    public void onGetLocation(final LocResult locResult) {
        Log.i("tonghu","OnlyGoogleBehavior, onGetLocation(L26): " + locResult);
        if (!isSuccess && locResult.getErrCode() == ErrCode.ERR_TIME_OUT) {
            List<LocResult> rsts = new ArrayList<>();
            rsts.add(locResult);
            locClient.getLocOptions().getLocListener().onLocResult(locResult.getErrCode(), rsts);
            return;
        }

        if (locResult.getFrom() == LocResult.From.GOOGLE) {
            Log.i("tonghu","OnlyGoogleBehavior, onGetLocation(L28): ");
            if (locResult.getErrCode() == ErrCode.OK) {
                isSuccess = true;
                List<LocResult> rsts = new ArrayList<>();
                rsts.add(locResult);
                handleResult(ErrCode.OK, rsts);
            }
        }
    }

    private void handleResult(final ErrCode errCode, final List<LocResult> locResults) {
        if (locClient.getLocOptions().getLocListener() != null) {
            locClient.getMainHandler().post(new Runnable() {
                @Override
                public void run() {
                    locClient.getLocOptions().getLocListener().onLocResult(errCode, locResults);
                }
            });
        }
    }

    @Override
    public void onGoogleOrAndroidLocTimeEnd() {
        //do nothing
    }

    @Override
    public void onRequestOnce() {
        isSuccess = false;
    }

}
