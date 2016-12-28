package com.yukaiwen.smartbattery.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.yukaiwen.smartbattery.R;
import com.yukaiwen.smartbattery.model.OneCapteur;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by yukaiwen on 28/12/2016.
 */

public class CapteursAdapter extends ArrayAdapter<OneCapteur> {

    public CapteursAdapter(Context context, ArrayList<OneCapteur> arrayOfCapteurs) {
        super(context, 0, arrayOfCapteurs);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        OneCapteur oneCapteur = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_capteur, parent, false);
        }
        // Lookup view for data population
        TextView tv = (TextView) convertView.findViewById(R.id.item_capteur);

        float[] values = oneCapteur.getValues();
        // Populate the data into the template view using the data object
        String message = oneCapteur.getSensorName() + ":\n"
                + ((values == null)?"no data available": Arrays.toString(values));
        // Populate the data into the template view using the data object
        tv.setText(message);

        // Return the completed view to render on screen
        return convertView;
    }

}
