package com.yukaiwen.smartbattery.model;

/**
 * Created by yukaiwen on 23/12/2016.
 */

public class OneSpecificLocation {

    final String DRAWABLE = "drawable/";

    private float longitude;
    private float latitude;
    private String locationTitle;
    private String locationAddress;
    private String locationImrUrl;

    public OneSpecificLocation(float latitude, float longitude, String locationTitle,String locationAddress, String locationImrUrl) {
        this.longitude = longitude;
        this.latitude = latitude;
        this.locationTitle = locationTitle;
        this.locationAddress = locationAddress;
        this.locationImrUrl = locationImrUrl;
    }

    public String getImgUrl() {
        return DRAWABLE + locationImrUrl;
    }

    public float getLongitude() {
        return longitude;
    }

    public float getLatitude() {
        return latitude;
    }

    public String getLocationTitle() {
        return locationTitle;
    }

    public String getLocationAddress() {
        return locationAddress;
    }

    public String getLocationImrUrl() {
        return locationImrUrl;
    }
}
