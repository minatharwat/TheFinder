package com.example.user.finder_;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public class Info {
    public static String phonenumber="";
    //map for updating the same user if have the same number phone

    public static Map<String,String> mytrukers=new HashMap<>();

    public static void updateInformation(String phone){
        //update the date of user
        DateFormat dateFormat=new SimpleDateFormat("yyyy/MM/dd HH:MM:ss");
        Date data=new Date();
        //new node in database firebase
        DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference();
        databaseReference.child("users").child(phone).child("Updates").setValue(dateFormat.format(data));




        }

    //format phone number
    public static String FormatPhoneNumber(String Oldnmber){
        try{
            String numberOnly= Oldnmber.replaceAll("[^0-9]", "");
            if(Oldnmber.charAt(0)=='+') numberOnly="+" +numberOnly ;
            if (numberOnly.length()>=10)
                numberOnly=numberOnly.substring(numberOnly.length()-10,numberOnly.length());
            return(numberOnly);
        }
        catch (Exception ex){
            return(" ");
        }
    }

Context c;
    SharedPreferences sp;
    public Info(Context c){
        this.c=c;
        sp=c.getSharedPreferences("myrefrence",c.MODE_PRIVATE);

    }


  public void save(){
      String mytrackerslist="";
      for (Map.Entry       o:Info.mytrukers.entrySet()){
          if (mytrackerslist.length()==0){

              mytrackerslist=o.getKey()+"%"+o.getValue();

          }else{

              mytrackerslist=mytrackerslist+"%"+o.getKey()+"%"+o.getValue();
          }
      }
     if(mytrackerslist.length()==0){
         mytrackerslist = "empty";}

         SharedPreferences.Editor editor = sp.edit();
         editor.putString("mytrackerlist", mytrackerslist);
         editor.putString("phonenumber", phonenumber);
         editor.commit();



  }



    public void loading_data(){
        mytrukers.clear();
         phonenumber=sp.getString("phonenumber","empty");
        String mytrackerslist=sp.getString("mytrackerlist","empty");
        if((!mytrackerslist.equals("empty"))){

            String[] user=mytrackerslist.split("%");
            //i+2 name and phonenumber so 2 places in once
            for (int i=0;i<user.length;i=i+2){
                Info.mytrukers.put(user[i],user[i+1]);

            }
        }
        if(phonenumber.equals("empty")){

            Intent intent=new Intent(c,Login.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            c.startActivity(intent);
        }
    }

}
