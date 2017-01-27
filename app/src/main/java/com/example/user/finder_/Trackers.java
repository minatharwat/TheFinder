package com.example.user.finder_;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.user.finder_.Fragments.TrackersFragment;

public class Trackers extends AppCompatActivity {
     TrackersFragment trackersFragment;
    android.support.v4.app.FragmentManager fragmentManager;
    android.support.v4.app.FragmentTransaction fragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trackers);

        if(savedInstanceState==null){

            trackersFragment=new TrackersFragment();
            fragmentManager=getSupportFragmentManager();
            fragmentTransaction=fragmentManager.beginTransaction();
            fragmentTransaction.add(R.id.TrackersFragment,trackersFragment,"TrackersFragment");
            fragmentTransaction.commit();


        }else
        {

            trackersFragment=new TrackersFragment();
            fragmentManager=getSupportFragmentManager();
            fragmentTransaction=fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.TrackersFragment,trackersFragment);
            fragmentTransaction.commit();




        }






    }
}
