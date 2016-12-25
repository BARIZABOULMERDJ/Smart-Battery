package com.yukaiwen.smartbattery.services;

import com.yukaiwen.smartbattery.model.OneSpecificLocation;

import java.util.ArrayList;

/**
 * Created by yukaiwen on 23/12/2016.
 */
public class DataService {
    private static DataService instance = new DataService();

    public static DataService getInstance() {
        return instance;
    }

    private DataService() {
    }

    public ArrayList<OneSpecificLocation> getBootcampLocationsWithin10MilesOfZip(int zipcode) {
        //pretending we are downloading data from the server

        ArrayList<OneSpecificLocation> list = new ArrayList<>();
        list.add(new OneSpecificLocation(35.279f, -120.663f, "Donwtown", "762 Higuera Street Luis Obispo, CA 93401", "slo"));
        list.add(new OneSpecificLocation(35.302f, -120.658f, "On The Campus", "1 Grand Ave, San Luis Obispo, CA 93401", "slo"));
        list.add(new OneSpecificLocation(35.267f, -120.652f, "East Side Tower", "2494 Victoria Ave, San Luis Obispo, CA 93401", "slo"));

        return list;
    }
}
