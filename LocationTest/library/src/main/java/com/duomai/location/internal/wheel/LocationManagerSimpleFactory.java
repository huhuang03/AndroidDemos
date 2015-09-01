package com.duomai.location.internal.wheel;

import android.content.Context;

/**
 * @author york
 * @date 8/27/15
 * @since 1.0.0
 */
public class LocationManagerSimpleFactory {

    public static final int TYPE_G = 4;
    public static final int TYPE_AN = 5;
    public static final int TYPE_IP = 6;
    private static GoogleLocationManager googleLocationManager;
    private static AndroidLocationManager androidLocationManager;
    private static IPLocationManager ipLocationManager;

    public static BaseLocateManager getBaseLocationManagerByType(Context context, int type) {
        context = context.getApplicationContext();
        switch (type) {
            case TYPE_G:
                if (googleLocationManager == null) {
                    googleLocationManager = new GoogleLocationManager(context);
                }
                return googleLocationManager;
            case TYPE_AN:
                if (androidLocationManager == null) {
                    androidLocationManager = new AndroidLocationManager(context);
                }
                return androidLocationManager;
            case TYPE_IP:
                if (ipLocationManager == null) {
                    ipLocationManager = new IPLocationManager(context);
                }
                return ipLocationManager;
        }
        return null;
    }
}
