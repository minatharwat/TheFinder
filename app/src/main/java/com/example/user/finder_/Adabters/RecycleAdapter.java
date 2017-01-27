package com.example.user.finder_.Adabters;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.user.finder_.Info;
import com.example.user.finder_.Models.UserModel;
import com.example.user.finder_.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

import static com.example.user.finder_.Fragments.TrackersFragment.ResultNumber;
import static com.example.user.finder_.Info.phonenumber;

/**
 * Created by User on 19/01/2017.
 */

public class RecycleAdapter extends RecyclerView.Adapter<RecycleAdapter.Holder> {

    public int i;

   public Context context;
   List<UserModel> trackers ;

    public RecycleAdapter(List<UserModel> trackers,Context context){

        this.trackers=trackers;
        this.context=context;

    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.user_row,parent,false);
        Holder holder=new Holder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(Holder holder, final int position) {
        UserModel tracker=trackers.get(position);
        holder.username.setText(tracker.getUserName());
        holder.phoneNumber.setText(tracker.getPhoneNumber());
        //using and excuting of listener long click
        holder.setLongClickListener(new LongclickListener() {
            @Override
            public void LongClickListiner(int pos) {
                i=pos;
            }
        });



    }

    @Override
    public int getItemCount() {
      return  trackers.size();
    }

    public class Holder extends RecyclerView.ViewHolder implements View.OnLongClickListener,View.OnCreateContextMenuListener {
        TextView username;
        TextView phoneNumber;
        LongclickListener longClickListener;
        public Holder(View itemView) {
            super(itemView);
            username=(TextView) itemView.findViewById(R.id.username);
            phoneNumber=(TextView)itemView.findViewById(R.id.phone);

            itemView.setOnLongClickListener(this);
            itemView.setOnCreateContextMenuListener(this);

        }
        //long click listener

        public void setLongClickListener(LongclickListener longClickListener){
            this.longClickListener=longClickListener;

        }

        @Override
        public boolean onLongClick(View view) {
            this.longClickListener.LongClickListiner(getLayoutPosition());
            return false;
        }

        @Override
        public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {

            //context menu
            contextMenu.setHeaderTitle("Do you Want to Delete Cnotact");
            contextMenu.add(0,0,0,"Delete");
        }

    }

    //delete row
    public void delete_row(){
        //get i position
       //UserModel s=trackers.get(i);

        Info.mytrukers.remove(trackers.get(i).userName);
        trackers.remove(i);

        //Info.mytrukers.remove(trackers.get(i).PhoneNumber);
        DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference();
        databaseReference.child("users").child(phonenumber).child("finders").child(ResultNumber).removeValue();




    }


}
