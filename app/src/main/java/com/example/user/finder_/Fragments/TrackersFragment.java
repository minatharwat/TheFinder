package com.example.user.finder_.Fragments;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.user.finder_.Adabters.RecycleAdapter;
import com.example.user.finder_.Info;
import com.example.user.finder_.Models.UserModel;
import com.example.user.finder_.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.example.user.finder_.R.id.finish;


public class TrackersFragment extends Fragment {
   Context c;
    public static String ResultNumber;
     public static String name ;
    RecyclerView recyclerView;
    List<UserModel> users=new ArrayList<UserModel>();
    RecycleAdapter adapter;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View t;
        t=inflater.inflate(R.layout.fragment_trackers,container,false);

      //  users.add(new UserModel("mina","0223565"));//intialize to constrctor a static data just for now
        recyclerView=(RecyclerView)t.findViewById(R.id.recycletrackers);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
         adapter=new RecycleAdapter(users,c);
        recyclerView.setAdapter(adapter);
        Refresh();

        return t;

    }
    //_____________________________________________________________________________________________________________________

    void Refresh(){
        users.clear();
        for (Map.Entry obj:Info.mytrukers.entrySet()){
                //key is the name and value is the phone number and that
            users.add(new UserModel(obj.getValue().toString(),obj.getKey().toString()));

        }
        //telling that the adapter that data changed
           adapter.notifyDataSetChanged();


    }

//________________________________________________

    //access to permissions
    //check the user choice
    void CheckUserPermsions(){
        if ( Build.VERSION.SDK_INT >= 23){
            if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_CONTACTS) !=
                    PackageManager.PERMISSION_GRANTED  ){
                requestPermissions(new String[]{
                                Manifest.permission.READ_CONTACTS},
                        REQUEST_CODE_ASK_PERMISSIONS);
                return ;
            }
        }

        PickContact();

    }
//get acces to loaction permission
final private int REQUEST_CODE_ASK_PERMISSIONS = 123;



    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_ASK_PERMISSIONS:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    PickContact();
                } else {
                    // Permission Denied
                    Toast.makeText(getActivity(),"your message" , Toast.LENGTH_SHORT)
                            .show();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }
//____________________________________________________________________________

    void PickContact(){
        Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
        startActivityForResult(intent, PICK_CONTACT);
    }

    // Declare
    static final int PICK_CONTACT=1;
    @Override
    public void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);

        switch (reqCode) {
            case (PICK_CONTACT) :
                if (resultCode == Activity.RESULT_OK) {

                    Uri contactData = data.getData();

                    Cursor c = getActivity().getContentResolver().query(contactData, null, null, null, null);
                    if (c.moveToFirst()) {


                        String id =c.getString(c.getColumnIndexOrThrow(ContactsContract.Contacts._ID));

                        String hasPhone =c.getString(c.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));
                         ResultNumber="No number";
                        if (hasPhone.equalsIgnoreCase("1")) {
                            Cursor phones =getActivity().getContentResolver().query(
                                    ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null,
                                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID +" = "+ id,
                                    null, null);
                            phones.moveToFirst();
                            ResultNumber = Info.FormatPhoneNumber (phones.getString(phones.getColumnIndex("data1")));
                           // System.out.println("number is:"+cNumber);
                        }
                          name = c.getString(c.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                        Info.mytrukers.put(ResultNumber,name);
                        DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference();
                        databaseReference.child("users").child(Info.phonenumber).child("finders").child(ResultNumber).setValue(true);
                        Info info=new Info(getActivity());
                        info.save();
                        Refresh();

                        //update firebase and
                        //update list
                        //update database
                    }
                }
                break;
        }
    }












//menus
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();
        if(id==R.id.add){
            CheckUserPermsions();
        return true;
        }
        else if (id== finish){

            Info info=new Info(getActivity());
            info.save();
            getActivity().finish();
         return true;

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        if(item.getTitle()=="Delete"){
           try {

                adapter.delete_row();

                }catch (Exception e){

               Toast.makeText(getContext(),"Add the Selected Contact again then deleted",Toast.LENGTH_LONG).show();

           }       //save the deleting
            Refresh();
            Info info=new Info(getActivity());
            info.save();
            adapter.notifyDataSetChanged();
            //Refresh();
            //Info info=new Info(getActivity());
            //info.save();




        }
        return super.onContextItemSelected(item);
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        inflater.inflate(R.menu.listofcontact,menu);

    }



}
