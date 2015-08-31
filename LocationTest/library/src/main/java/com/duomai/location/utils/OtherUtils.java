package com.duomai.location.utils;

import android.text.TextUtils;
import android.util.Log;

import com.duomai.location.LocResult;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

/**
 * @author york
 * @date 8/31/15
 * @since 1.0.0
 */
public class OtherUtils {
    private static final String GOOGLE_LOCATION_URL = "http://maps.google.cn/maps/api/geocode/json?latlng=%1$s,%2$s&language=zh-CN&sensor=false";

    /** 这是一个同步的方法 */
    public static void getLocStringFromLatLon(LocResult locResult) {
        String url = String.format(GOOGLE_LOCATION_URL, locResult.getLat(), locResult.getLon());
        Log.i("tonghu","OtherUtils, getLocStringFromLatLon(L20): " + locResult + ", " + url);
        try {
            String stringFromUrl = IoUtils.getStringFromUrl(url);
            Log.i("tonghu", "OtherUtils, getLocStringFromLatLon(L20): " + stringFromUrl);
            JSONObject jsonObject = new JSONObject(stringFromUrl);
            JSONArray resultsJA = jsonObject.getJSONArray("results");
            for (int i = 0; i < resultsJA.length(); i++) {
                JSONObject resultJO = resultsJA.getJSONObject(i);
                boolean isRouteType = false;
                boolean isCountry = false;
                JSONArray typesJA = resultJO.getJSONArray("types");
                if (typesJA.length() == 1 &&
                        ("route".equals(typesJA.getString(0)) || "street_address".equals(typesJA.getString(0)))) {
                    isRouteType = true;
                }
                if (typesJA.length() == 2 && "country".equals(typesJA.getString(0)) && "political".equals(typesJA.getString(1))) {
                    isCountry = true;
                }

                if (isRouteType) {
                    locResult.setDetailAddress(resultJO.getString("formatted_address"));

                    if (TextUtils.isEmpty(locResult.getCountry())) {
                        JSONArray addressJA = resultJO.getJSONArray("address_components");
                        for (int j = 0; j < addressJA.length(); j++) {
                            JSONObject addressJO = addressJA.getJSONObject(j);
                            JSONArray addressTypeJA = addressJO.getJSONArray("types");
                            if (addressTypeJA.length() == 2 && "country".equals(addressTypeJA.getString(0)) && "political".equals(addressTypeJA.getString(1))) {
                                locResult.setCountry(addressJO.getString("long_name"));
                                locResult.setCountryCode(addressJO.getString("short_name"));
                            }
                        }
                    }
                }

                if (isCountry) {
                    JSONArray addressJA = resultJO.getJSONArray("address_components");
                    if (addressJA.length() >= 0) {
                        locResult.setCountry(addressJA.getJSONObject(0).getString("long_name"));
                        locResult.setCountryCode(addressJA.getJSONObject(0).getString("short_name"));
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public interface OnGetLocStringListener {
        void onGetLocString();
    }
}
