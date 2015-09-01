package com.duomai.location;


import com.duomai.location.internal.FormatStringBean;

/**
 * 定位数据
 * @author york
 * @date 8/27/15
 * @since 1.0.0
 */
public class LocResult extends FormatStringBean {
    private ErrCode errCode;

    private String detailAddress;
    private double lat;
    private double lon;
    private From from;
    private String country;
    private String countryCode;

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public ErrCode getErrCode() {
        return errCode;
    }

    public void setErrCode(ErrCode errCode) {
        this.errCode = errCode;
    }

    public String getDetailAddress() {
        return detailAddress;
    }

    public void setDetailAddress(String detailAddress) {
        this.detailAddress = detailAddress;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public From getFrom() {
        return from;
    }

    public void setFrom(From from) {
        this.from = from;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public enum From {
        GOOGLE, ANDROID, IP
    }

}
