package com.example.care2u.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.care2u.ConversationActivity;
import com.example.care2u.R;
import com.example.care2u.entity.NotificationModel;
import com.example.care2u.entity.User;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.MyViewHolder> {

    private Context context;
    private List<NotificationModel>NotificationList;

    public NotificationAdapter(Context context){
        this.context=context;
        NotificationList=new ArrayList<>();
    }

    public void add(NotificationModel notificationModel){
        NotificationList.add(notificationModel);
        notifyDataSetChanged();
    }

    public void clear(){
        NotificationList.clear();
        notifyDataSetChanged();
    }

    public void reverse(){
        Collections.reverse(NotificationList);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.notification_row,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        NotificationModel notificationModel = NotificationList.get(position);
        holder.content.setText(notificationModel.getContent());
    }

    @Override
    public int getItemCount() {
        return NotificationList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView content;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            content = itemView.findViewById(R.id.notification_content);
        }
    }
}
