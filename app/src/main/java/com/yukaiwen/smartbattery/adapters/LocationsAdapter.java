package com.yukaiwen.smartbattery.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yukaiwen.smartbattery.R;
import com.yukaiwen.smartbattery.holders.LocationsViewHolder;
import com.yukaiwen.smartbattery.model.OneSpecificLocation;

import java.util.ArrayList;

/**
 * Created by yukaiwen on 23/12/2016.
 */

public class LocationsAdapter extends RecyclerView.Adapter<LocationsViewHolder>{

    private ArrayList<OneSpecificLocation> locations;

    public LocationsAdapter(ArrayList<OneSpecificLocation> locations) {
        this.locations = locations;
    }

    @Override
    public LocationsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View card = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_location, parent, false);
        return new LocationsViewHolder(card);
    }

    @Override
    public void onBindViewHolder(LocationsViewHolder holder, int position) {
        final OneSpecificLocation location = locations.get(position);
        holder.updateUI(location);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Load details page
            }
        });

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
