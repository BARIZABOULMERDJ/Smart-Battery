package com.yukaiwen.smartbattery.activities;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.yukaiwen.smartbattery.R;
import com.yukaiwen.smartbattery.adapters.CapteursAdapter;
import com.yukaiwen.smartbattery.model.OneCapteur;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CapteurDisplayerActivity extends AppCompatActivity {
    /** * The sensor manager */
    private SensorManager sensorManager;
    private List<Sensor> sensors;
    private List<float[]> latestSensorEvents = new ArrayList<float[]>();
    private ArrayList<OneCapteur> arrayOfCapteurs;
    private SensorEventListener sensorListener;
    private ListView sensorListView;
    private CapteursAdapter capteursAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_capteur_displayer);

        // Instanicer le SensorManager
        sensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);

        sensors = sensorManager.getSensorList(Sensor.TYPE_ALL);
        for (int i = 0; i < sensors.size(); i++) latestSensorEvents.add(null);
        // We create the listener for the sensor events
        this.sensorListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                updateSensorValue(event);
            }
            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {}
        };

        // Construct the data source
        arrayOfCapteurs = new ArrayList<OneCapteur>();
        for (int i = 0; i < sensors.size(); i++) {
            arrayOfCapteurs.add(new OneCapteur(sensors.get(i).getName(), latestSensorEvents.get(i)));
        }

        // Create the adapter to convert the array to views
        capteursAdapter = new CapteursAdapter(this, arrayOfCapteurs);
        // We create a list view displaying all the sensor data
        // Data for a sensor is put as a float array into an array list
        sensorListView = new ListView(this);
        // Attach the adapter to a ListView
        sensorListView = (ListView) findViewById(R.id.mobile_list);
        sensorListView.setAdapter(capteursAdapter);

    }

    public void updateSensorValue(SensorEvent sensorEvent)
    {
//        latestSensorEvents.set(sensors.indexOf(sensorEvent.sensor), Arrays.copyOf(sensorEvent.values, sensorEvent.values.length));
        arrayOfCapteurs.get(sensors.indexOf(sensorEvent.sensor)).setValues(Arrays.copyOf(sensorEvent.values, sensorEvent.values.length));
        capteursAdapter.notifyDataSetChanged();
    }

    @Override
    public void onResume()
    {
        super.onResume();
        // We register the listener for all the sensors
        for (Sensor s: sensors) sensorManager.registerListener(sensorListener, s, SensorManager.SENSOR_DELAY_UI);
    }

    @Override
    public void onPause()
    {
        super.onPause();
        // We unregister the listener
        for (Sensor s: sensors) sensorManager.unregisterListener(sensorListener, s);
    }

}
