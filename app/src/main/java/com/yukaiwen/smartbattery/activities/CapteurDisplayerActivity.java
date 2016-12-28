package com.yukaiwen.smartbattery.activities;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
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

    // [START declare_database_ref]
    private DatabaseReference mDatabase;
    // [END declare_database_ref]

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


        Button Register_Capteur_Btn = (Button)findViewById(R.id.Register_Capteur_Btn);
        Register_Capteur_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // [START initialize_database_ref]
                mDatabase = FirebaseDatabase.getInstance().getReference();
                // [END initialize_database_ref]

                // [START write operation]
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                final String userId = user.getUid();

                for (int i = 0; i < arrayOfCapteurs.size(); i++) {
                    OneCapteur capteur = arrayOfCapteurs.get(i);
                    mDatabase.child("users").child(userId).child("capteurs").child(i+"").child("sensor_Name").setValue(capteur.getSensorName());

                    float[] values = capteur.getValues();
                    for(int j = 0; j < values.length; j++) {
                        mDatabase.child("users").child(userId).child("capteurs").child(i+"").child("sensor_Values").child(j+"").setValue(values[j]);
                    }
                }
                // [END write operation]
            }
        });
    }

    public void updateSensorValue(SensorEvent sensorEvent)
    {
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
