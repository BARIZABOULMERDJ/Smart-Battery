package com.yukaiwen.smartbattery.model;

import java.util.List;

/**
 * Created by yukaiwen on 28/12/2016.
 */

public class OneCapteur {

    private String sensorName;
    private float[] values;

    public OneCapteur(String sensorName, float[] values) {
        this.sensorName = sensorName;
        this.values = values;
    }

    public String getSensorName() {
        return sensorName;
    }

    public void setSensorName(String sensorName) {
        this.sensorName = sensorName;
    }

    public float[] getValues() {
        return values;
    }

    public void setValues(float[] values) {
        this.values = values;
    }
}
