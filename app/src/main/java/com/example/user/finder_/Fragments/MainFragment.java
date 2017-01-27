package com.example.user.finder_.Fragments;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.user.finder_.Adabters.RecycleAdapterM;
import com.example.user.finder_.Info;
import com.example.user.finder_.Models.UserModel;
import com.example.user.finder_.R;
import com.example.user.finder_.ServiceLocation.MyService;
import com.example.user.finder_.ServiceLocation.TrackLocation;
import com.example.user.finder_.Trackers;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;
import static com.example.user.finder_.Fragments.TrackersFragment.ResultNumber;


public class MainFragment extends Fragment {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    RecyclerView recyclerViewM;
    List<UserModel> users = new ArrayList<UserModel>();
   RecycleAdapterM adapter;
   DatabaseReference databaseReference;
       //Context context;
    @Nullable
    @Override


    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View m;
        m = inflater.inflate(R.layout.fragment_main, container, false);
        Log.d("refresh","-1");
        Info info = new Info(getActivity());
        info.loading_data();
        CheckUserPermsions();

        databaseReference= FirebaseDatabase.getInstance().getReference();

        //  users.add(new UserModel("mina","0223565"));//intialize to constrctor a static data just for now

        //check  phonenumber if exist ..if not go to login and set the phone number

        Log.e("refresh","0");

        Log.e("refresh","0");
        Log.e("refresh","1");
         recyclerViewM=(RecyclerView)m.findViewById(R.id.recycletracking);
         recyclerViewM.setLayoutManager(new LinearLayoutManager(getActivity()));
        Log.e("refresh","2");
         adapter=new RecycleAdapterM(users,getContext());
         recyclerViewM.setAdapter(adapter);
         Log.e("refresh","3");
       // SRefresh();

        return m;
    }

    @Override
    public void onResume(){
        super.onResume();
        SRefresh();
    }


    void SRefresh(){
        users.clear();
        Log.e("refresh","11");
        databaseReference.child("users").child(Info.phonenumber).child("finders").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Map<String, Object> td = (HashMap<String, Object>) dataSnapshot.getValue();

                Log.e("refresh","12");
                users.clear();
                if (td == null)  //no one allow you to find him
                {
                    users.add(new UserModel("NoTrackers", "No data avaliable"));
                    adapter.notifyDataSetChanged();
                    return;
                }
                // List<Object> values = td.values();


                Log.e("refresh","13");
                // get all contact to list from phone
                List<UserModel> list_contact = new ArrayList<UserModel>();
                try {


                    Cursor cursor = getActivity().getContentResolver().query(ContactsContract.CommonDataKinds.Phone
                            .CONTENT_URI, null, null, null, null);
                    while (cursor.moveToNext()) {
                        String name = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));

                        String phoneNumber = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                        list_contact.add(new UserModel(Info.FormatPhoneNumber(phoneNumber), name
                        ));

                    }
                }catch (NullPointerException e){

                    e.printStackTrace();
                    Toast.makeText(getActivity(),"Check your Network Connection and Re open the app",Toast.LENGTH_LONG).show();

                }
                Log.e("refrsh","14");
                // if the name is save chane his text
                // case who find me
                //comparing the name and phone and added to my list
                String tinfo;
                for (  String Numbers : td.keySet()) {
                    for (UserModel cs : list_contact) {
                        Log.e("refrsh","15");
                        //IsFound = SettingSaved.WhoIFindIN.get(cs.Detals);  // for case who i could find list
                        if (cs.PhoneNumber.length() >= 0)
                            Log.e("refrsh","15.5");
                            if (Numbers.contains(cs.PhoneNumber)) {
                                Log.e("refrsh","16");
                                users.add(new UserModel(cs.userName, cs.PhoneNumber));
                                break;
                          }

                    }

                }
                adapter.notifyDataSetChanged();
            }


            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                // Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
        Log.d("refresh","1");
       // adapter.notifyDataSetChanged();
    }


    //permision
    void CheckUserPermsions() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) !=
                    PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{
                                Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                        REQUEST_CODE_ASK_PERMISSIONS);
                return;
            }
        }

        start_the_services();

    }

    //get acces to loaction permission
    final private int REQUEST_CODE_ASK_PERMISSIONS = 123;

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_ASK_PERMISSIONS:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    start_the_services();
                } else {
                    // Permission Denied
                    Toast.makeText(getActivity(), "your message", Toast.LENGTH_SHORT)
                            .show();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    void start_the_services() {
        //start location track
        Log.e("qqqq","0");
        if (!TrackLocation.Running) {
            Log.e("sssssss","1");
            TrackLocation trackLocation = new TrackLocation();
            LocationManager lm = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);


            lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, trackLocation);


        }
        if (!MyService.Running) {

            Log.e("sssssss","2");
            Log.d("start the service", "ok");
            Intent intent = new Intent(getActivity(), MyService.class);
            getActivity().startService(intent);


        }


    }

//menu

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        inflater.inflate(R.menu.fragment_main, menu);

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();
        if(id==R.id.help){
            CheckUserPermsions();
            return true;
        }
        else if (id==R.id.adtrak){

            Intent intent=new Intent(getActivity(), Trackers.class);
            startActivity(intent);

            return true;

        }
        return super.onOptionsItemSelected(item);
    }
}
