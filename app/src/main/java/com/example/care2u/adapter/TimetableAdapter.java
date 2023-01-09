package com.example.care2u.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.care2u.ConversationActivity;
import com.example.care2u.R;
import com.example.care2u.entity.Timetable;
import com.example.care2u.entity.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class TimetableAdapter extends RecyclerView.Adapter<TimetableAdapter.MyViewHolder> {

    private Context context;
    private List<Timetable>timetable_list;
    private AlphaAnimation buttonClick = new AlphaAnimation(1F, 0.8F);

    public TimetableAdapter(Context context){
        this.context=context;
        timetable_list=new ArrayList<>();
    }

    public void add(Timetable timetable){
        timetable_list.add(timetable);
        notifyDataSetChanged();
    }

    public void clear(){
        timetable_list.clear();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.appointment_row,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Timetable timetable = timetable_list.get(position);
        switch (timetable.getDoctor_name()){
            case "Dr Elizabeth Smith":
                holder.doctor_pic.setImageDrawable(context.getDrawable(R.drawable.doctor1));
                break;
            case "Dr John Smith":
                holder.doctor_pic.setImageDrawable(context.getDrawable(R.drawable.doctor2));
                break;
            case "Dr Natalia Ivanova":
                holder.doctor_pic.setImageDrawable(context.getDrawable(R.drawable.doctor3));
                break;
            case "Dr Maria Garcia":
                holder.doctor_pic.setImageDrawable(context.getDrawable(R.drawable.doctor4));
                break;
            case "Dr David Satcher":
                holder.doctor_pic.setImageDrawable(context.getDrawable(R.drawable.doctor5));
                break;

        }
        holder.doctor_name.setText(timetable.getDoctor_name());
        holder.date.setText(timetable.getDate());
        holder.time.setText(timetable.getTimeslot());
    }

    @Override
    public int getItemCount() {
        return timetable_list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView doctor_name,date,time;
        private CircleImageView doctor_pic;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            doctor_pic = (CircleImageView)itemView.findViewById(R.id.imageProfile);
            doctor_name = itemView.findViewById(R.id.doctor_name_tv);
            date = itemView.findViewById(R.id.appointment_date_tv);
            time = itemView.findViewById(R.id.time_slot_tv);
        }
    }
}
