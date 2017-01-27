package com.example.user.finder_.Adabters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.user.finder_.Info;
import com.example.user.finder_.MapsActivity;
import com.example.user.finder_.Models.UserModel;
import com.example.user.finder_.R;

import java.util.List;

/**
 * Created by User on 26/01/2017.
 */

public class RecycleAdapterM extends RecyclerView.Adapter<RecycleAdapterM.Holder1> {
    List<UserModel> trackers ;
    Context context;
    public RecycleAdapterM(List<UserModel> trackers,Context context){

        this.context=context;
        this.trackers=trackers;
    }
    @Override
    public Holder1 onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.user_row,parent,false);
        Holder1 holder=new Holder1(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(Holder1 holder, int position) {
        UserModel tracker=trackers.get(position);
        holder.username.setText(tracker.getUserName());
        holder.phoneNumber.setText(tracker.getPhoneNumber());

    }

    @Override
    public int getItemCount() {
        return trackers.size();
    }

    public class Holder1 extends RecyclerView.ViewHolder implements View.OnClickListener{



        TextView username;
        TextView phoneNumber;

        public Holder1(View itemView) {
            super(itemView);
            username=(TextView) itemView.findViewById(R.id.username);
            phoneNumber=(TextView)itemView.findViewById(R.id.phone);
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            int position=getLayoutPosition();
            final Intent intent1;
            UserModel user=trackers.get(position);
            Info.updateInformation(user.userName);
             intent1=new Intent(context.getApplicationContext(), MapsActivity.class);
            intent1.putExtra("phonenumber",user.userName);
            itemView.getContext().startActivity(intent1);
        }
    }
}
