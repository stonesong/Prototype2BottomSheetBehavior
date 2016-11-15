package com.example.pierrechanson.prototypebottomsheet;

import java.util.ArrayList;

/**
 * Created by guillaumemeral on 11/11/16.
 */

public class DummyDataObject {
    public String title;
    public String availableBikes;
    public String distanceTime;
    public ArrayList<String> freeBikes;

    public DummyDataObject(String title, String availableBikes, String distanceTime, ArrayList<String> freeBikes) {
        this.title = title;
        this.availableBikes = availableBikes;
        this.distanceTime = distanceTime;
        this.freeBikes = freeBikes;
    }
}
