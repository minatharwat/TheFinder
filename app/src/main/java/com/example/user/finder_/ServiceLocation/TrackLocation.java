package com.example.user.finder_.ServiceLocation;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;

/**
 * Created by User on 25/01/2017.
 */

public class TrackLocation implements LocationListener {

    public static Location location;
    public static boolean Running=false;
    //check the running to run this service
    public TrackLocation(){
        Running=true;
        //for some phones that doesn't have a gps app not work.
        location=new Location("undefined");
        location.setLatitude(0);
        location.setLongitude(0);

    }
    @Override
    public void onLocationChanged(Location location) {
        this.location=location;
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }
}
