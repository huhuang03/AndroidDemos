package com.duomai.location.internal.wheel;

import android.content.Context;

import com.duomai.location.ErrCode;
import com.duomai.location.LocResult;
import com.duomai.location.utils.IoUtils;

import org.json.JSONObject;

/**
 * //TODO WIFI情况
 * Created by tonghu on 7/16/15.
 */
public class IPLocationManager extends BaseLocateManager{

    public IPLocationManager(Context context) {
    }

    @Override
    public int getType() {
        return LocationManagerSimpleFactory.TYPE_IP;
    }

    @Override
    protected void stopInternal() {
        //do noting
    }

    @Override
    protected void startInternal() {
        final LocResult locationData = new LocResult();
        locationData.setFrom(LocResult.From.IP);
        try {
            String locationJson = IoUtils.getStringFromUrl("http://www.telize.com/geoip/");

            JSONObject jsonObject = new JSONObject(locationJson);
            locationData.setErrCode(ErrCode.OK);
            if (jsonObject.has("latitude")) {
                locationData.setLat(jsonObject.getDouble("latitude"));
            }
            if (jsonObject.has("longitude")) {
                locationData.setLon(jsonObject.getDouble("longitude"));
            }
            StringBuilder sb = new StringBuilder();
            if (jsonObject.has("country")) {
                sb.append(jsonObject.getString("country"));
            }
            if (jsonObject.has("city")) {
                sb.append("\t").append(jsonObject.getString("city"));
            }
            if (jsonObject.has("country")) {
                locationData.setCountry(jsonObject.getString("country"));
            }
            if (jsonObject.has("country_code")) {
                locationData.setCountryCode(jsonObject.getString("country_code"));
            }
            locationData.setDetailAddress(sb.toString());
        } catch (Exception e) {
            e.printStackTrace();
            locationData.setErrCode(ErrCode.ERR_OTHER);
        }

        handleGetLocation(locationData);
    }

}
