package com.duomai.location.internal.wheel;

import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.duomai.location.ErrCode;
import com.duomai.location.LocResult;
import com.duomai.location.utils.OtherUtils;

/**
 * Created by tonghu on 7/16/15.
 */
public class AndroidLocationManager extends BaseLocateManager implements android.location.LocationListener{
    LocationManager locationManager;
    private long locationRefreshMinTime = 5000;
    private long locationRefreshMinDistance = 0;

    public AndroidLocationManager(Context context) {
        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
    }

    @Override
    public int getType() {
        return LocationManagerSimpleFactory.TYPE_AN;
    }

    @Override
    protected void stopInternal() {
        //do nothing
    }

    @Override
    protected void startInternal() {
        Log.i("tonghu", "AndroidLocationManager, startInternal(L38): an11111111111111");
        try {
            try {
                Location gpsLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                Location netWorkLocation = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                if (gpsLocation != null || netWorkLocation != null) {
                    onLocationChanged(gpsLocation != null ? gpsLocation : netWorkLocation);
                    return;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            Handler handler = new Handler(Looper.getMainLooper());
            handler.post(new Runnable() {
                @Override
                public void run() {
                    //must call in main thread.
                    if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                        locationManager.requestSingleUpdate(LocationManager.GPS_PROVIDER, AndroidLocationManager.this, Looper.getMainLooper());
//                        locationManager.requestSingleUpdate(LocationManager.GPS_PROVIDER, locationRefreshMinTime, locationRefreshMinDistance, AndroidLocationManager.this);
                    } else {
                        locationManager.requestSingleUpdate(LocationManager.NETWORK_PROVIDER, AndroidLocationManager.this, Looper.getMainLooper());
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            Log.i("tonghu", "AndroidLocationManager, startInternal(L38): an22222222222");
            LocResult locResult = new LocResult();
            locResult.setFrom(LocResult.From.ANDROID);
            handleGetLocation(locResult);
        }
    }

    @Override
    public void onLocationChanged(final Location location) {
        Log.i("tonghu","AndroidLocationManager, onLocationChanged(L78): " + location);
        new Thread(new Runnable() {
            @Override
            public void run() {
                LocResult locationData = new LocResult();
                locationData.setFrom(LocResult.From.ANDROID);

                if (location != null) {
                    locationData.setErrCode(ErrCode.OK);
                    locationData.setLat(location.getLatitude());
                    locationData.setLon(location.getLongitude());
                    OtherUtils.getLocStringFromLatLon(locationData);
                } else {
                    locationData.setErrCode(ErrCode.ERR_OTHER);
                }
                handleGetLocation(locationData);
            }
        }).start();
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
