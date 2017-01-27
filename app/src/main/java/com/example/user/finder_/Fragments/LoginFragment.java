package com.example.user.finder_.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.user.finder_.Info;
import com.example.user.finder_.R;
import com.example.user.finder_.Trackers;


public class LoginFragment extends Fragment {

     EditText phone;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View l;
        l=inflater.inflate(R.layout.fragment_login,container,false);

        phone=(EditText)l.findViewById(R.id.phonedit);

        Button next=(Button)l.findViewById(R.id.next);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Info.phonenumber=Info.FormatPhoneNumber(phone.getText().toString());
                Info.updateInformation(Info.phonenumber);
                Info info=new Info(getActivity());
                info.save();

                getActivity().finish();
                Intent i= new Intent(getActivity(), Trackers.class);
                startActivity(i);
            }
        });



        return l;
    }

}
