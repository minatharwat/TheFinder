package com.example.user.finder_.ServiceLocation;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.util.Log;

import com.example.user.finder_.Info;

/**
 * Created by User on 27/01/2017.
 */

public class BootCondition extends BroadcastReceiver{
    @Override
    public void onReceive(Context context, Intent intent) {

        if (intent.getAction().equalsIgnoreCase("android.intent.action.BOOT_COMPLETED")){

            Info info=new Info(context);
            info.loading_data();


            if (!TrackLocation.Running) {
                Log.e("sssssss","1");
                TrackLocation trackLocation = new TrackLocation();
                LocationManager lm = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);


                lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, trackLocation);


            }
            if (!MyService.Running) {

                Log.e("sssssss","boot");
                Log.d("start the service", "ok");
                Intent intent2 = new Intent(context, MyService.class);
                context.startService(intent);


            }



        }
    }
}
