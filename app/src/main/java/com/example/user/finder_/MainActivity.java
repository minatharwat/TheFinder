package com.example.user.finder_;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.user.finder_.Fragments.MainFragment;

public class MainActivity extends AppCompatActivity {

    MainFragment mainFragment;
    android.support.v4.app.FragmentManager fragmentManager;
    android.support.v4.app.FragmentTransaction fragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        if (savedInstanceState==null){

            mainFragment=new MainFragment();
            fragmentManager=getSupportFragmentManager();
            fragmentTransaction=fragmentManager.beginTransaction();
            fragmentTransaction.add(R.id.MainFragment,mainFragment,"MainFragment");
            fragmentTransaction.commit();

        }else {

            mainFragment=new MainFragment();
            fragmentManager=getSupportFragmentManager();
            fragmentTransaction=fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.MainFragment,mainFragment);
            fragmentTransaction.commit();









        }







    }
}
