package com.example.user.finder_;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.user.finder_.Fragments.LoginFragment;

public class Login extends AppCompatActivity {
LoginFragment loginFragment;
    android.support.v4.app.FragmentManager fragmentManager;
    android.support.v4.app.FragmentTransaction fragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);




        if(savedInstanceState==null){

            loginFragment=new LoginFragment();
            fragmentManager=getSupportFragmentManager();
            fragmentTransaction=fragmentManager.beginTransaction();
            fragmentTransaction.add(R.id.LogFragment,loginFragment,"LoginFragment");
            fragmentTransaction.commit();


        }else
        {

            loginFragment=new LoginFragment();
            fragmentManager=getSupportFragmentManager();
            fragmentTransaction=fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.LogFragment,loginFragment);
            fragmentTransaction.commit();




        }

    }


}
