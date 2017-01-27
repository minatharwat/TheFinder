package com.example.user.finder_;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    Context c;
    private GoogleMap mMap;
DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
       databaseReference= FirebaseDatabase.getInstance().getReference();
        Bundle b=getIntent().getExtras();
        Loadlocation(b.getString("phonenumber"));



    }


    void Loadlocation(final String phonenumber){


            databaseReference.child("users").child(phonenumber).child("Location").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    Map<String, Object> td = (HashMap<String, Object>) dataSnapshot.getValue();
                    try {
                        if (phonenumber.equals(null)) {
                            Log.d("555555555", "4445556663300000000");
                            Log.d("555555555", "4445556663300000000");
                            Log.d("555555555", "4445556663300000000");
                            Log.d("555555555", "4445556663300000000");
                            Log.d("555555555", "4445556663300000000");
                        }
                        Log.d("555555555", "00000000////" + phonenumber);
                        double latiude = Double.parseDouble(td.get("latiude").toString());
                        double longtiude = Double.parseDouble(td.get("longtiude").toString());
                        sydney = new LatLng(latiude, longtiude);
                        LastdateOnline = td.get("LastOnlinetime").toString();
                        loadMap();


                    } catch (NullPointerException e) {


                        Toast.makeText(getApplicationContext(), "The Person you want to track doesn't have this app", Toast.LENGTH_SHORT).show();
                    }
                }
                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });



    }
    void loadMap(){



        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


    }




    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    LatLng sydney;
    String LastdateOnline="";
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        // sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Last online"+LastdateOnline));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney,20));
    }
}
