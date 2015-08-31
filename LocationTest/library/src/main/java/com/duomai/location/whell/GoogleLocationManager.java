package com.duomai.location.whell;

import android.content.Context;
import android.location.Location;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import com.duomai.location.ErrCode;
import com.duomai.location.LocResult;
import com.duomai.location.utils.OtherUtils;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

/**
 * Created by tonghu on 7/16/15.
 */
public class GoogleLocationManager extends BaseLocateManager implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    public static final String TAG = GoogleLocationManager.class.getSimpleName();

    private GoogleApiClient mGoogleApiClient;

    private boolean isRequestLocation;
    private boolean isBuildApiClientFinished;
    private boolean isBuildApiClientSuccessed;
    private Location mLastLocation;

    public GoogleLocationManager(Context context) {
        buildGoogleApiClient(context);
    }

    protected synchronized void buildGoogleApiClient(Context context) {
        mGoogleApiClient = new GoogleApiClient.Builder(context)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();
        Log.i(TAG,"GoogleLocationManager, buildGoogleApiClient(L41): ");
    }

    @Override
    public int getType() {
        return LocationManagerSimpleFactory.TYPE_G;
    }

    @Override
    protected void stopInternal() {
        mGoogleApiClient.disconnect();
    }

    @Override
    protected void startInternal() {
        Log.i(TAG,"GoogleLocationManager, startInternal(L56): " + isRunning);
        if (isRunning) {
            return;
        }
        isRequestLocation = true;
        if (isBuildApiClientFinished) {
            if (isBuildApiClientSuccessed) {
                getLastLocation();
            } else {
                handleError(ErrCode.ERR_OTHER);
            }
        }
    }

    @Override
    public void onConnected(Bundle bundle) {
        Log.i(TAG,"GoogleLocationManager, onConnected(L72): ");
        isBuildApiClientFinished = true;
        isBuildApiClientSuccessed = true;

        if (isRequestLocation) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    getLastLocation();
                }
            }).start();
        }
    }

    private void getLastLocation() {
        Log.i(TAG,"GoogleLocationManager, getLastLocation(L84): ");
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                mGoogleApiClient);
        if (mLastLocation != null) {
            LocResult locationData = new LocResult();
            locationData.setFrom(LocResult.From.G);
            locationData.setErrCode(ErrCode.OK);
            locationData.setLat(mLastLocation.getLatitude());
            locationData.setLon(mLastLocation.getLongitude());
            if (TextUtils.isEmpty(locationData.getDetailAddress())) {
                OtherUtils.getLocStringFromLatLon(locationData);
            }
            handleGetLocation(locationData);
        } else {
            handleError(ErrCode.ERR_OTHER);
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.i(TAG, "onConnectionFailed, errCode: " + connectionResult.getErrorCode());
        isBuildApiClientFinished = true;
        isBuildApiClientSuccessed = false;
        if (connectionResult.getErrorCode() == ConnectionResult.SERVICE_MISSING
                || connectionResult.getErrorCode() == ConnectionResult.SERVICE_VERSION_UPDATE_REQUIRED
                || connectionResult.getErrorCode() == ConnectionResult.SERVICE_DISABLED) {
        }
        if (connectionResult.getErrorCode() == ConnectionResult.SERVICE_VERSION_UPDATE_REQUIRED) {
            handleError(ErrCode.SERVICE_VERSION_UPDATE_REQUIRED);
        } else {
            handleError(ErrCode.ERR_OTHER);
        }
    }

    private void handleError(ErrCode errCode) {
        LocResult locationData = new LocResult();
        locationData.setFrom(LocResult.From.G);
        locationData.setErrCode(errCode);
        handleGetLocation(locationData);
    }

}
