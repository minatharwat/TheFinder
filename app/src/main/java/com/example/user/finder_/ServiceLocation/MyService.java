package com.example.user.finder_.ServiceLocation;

import android.app.IntentService;
import android.content.Intent;
import android.location.Location;
import android.util.Log;

import com.example.user.finder_.Info;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by User on 25/01/2017.
 */

public class MyService extends IntentService {

    public static boolean Running=false;
    DatabaseReference databaseReference;
    public MyService(){
       super("MyService");
     Running=true;
        databaseReference= FirebaseDatabase.getInstance().getReference();

    }

    @Override
    protected void onHandleIntent(Intent intent) {

        databaseReference.child("users").child(Info.phonenumber).child("finders").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Location location=TrackLocation.location;
                databaseReference.child("users").child(Info.phonenumber).child("Location").child("latiude")
                        .setValue(TrackLocation.location.getLatitude());
                Log.d("1","ok");

                databaseReference.child("users").child(Info.phonenumber).child("Location").child("longtiude")
                        .setValue(TrackLocation.location.getLongitude());

                Log.d("2","ok");

                DateFormat dateFormat=new SimpleDateFormat("yyyy/MM/dd HH:MM:ss");
                Date data=new Date();
                databaseReference.child("users").child(Info.phonenumber).child("Location").child("LastOnlinetime")
                        .setValue(dateFormat.format(data).toString());

                Log.d("3","ok");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
