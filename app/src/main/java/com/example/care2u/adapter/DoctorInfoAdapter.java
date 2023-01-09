package com.example.care2u.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.care2u.BookingAppointmentActivity;
import com.example.care2u.R;
import com.example.care2u.entity.Doctor;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class DoctorInfoAdapter extends RecyclerView.Adapter<DoctorInfoAdapter.MyViewHolder> {

    private Context context;
    ArrayList<Doctor> doctorList = new ArrayList<>();
    int [] doctor_image = {R.drawable.doctor1,R.drawable.doctor2,R.drawable.doctor3,R.drawable.doctor4,R.drawable.doctor5};
    private AlphaAnimation buttonClick = new AlphaAnimation(1F, 0.8F);

    public DoctorInfoAdapter(Context context, ArrayList<Doctor> doctorList) {
        this.context = context;
        this.doctorList = doctorList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_doctor_info, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
            Doctor doctor = doctorList.get(position);
            holder.name.setText(doctor.getName());
            holder.description.setText(doctor.getDescription());
            holder.original.setText(doctor.getOriginal());
            holder.rating.setText(String.format("%.1f",doctor.getRating()));
            holder.profile_view.setImageResource(doctor_image[position]);

            holder.book_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    view.startAnimation(buttonClick);
                    Bundle bundle = new Bundle();
                    bundle.putString("name",doctor.getName());
                    bundle.putInt("image",doctor_image[position]);
                    Intent intent = new Intent(context, BookingAppointmentActivity.class);
                    intent.putExtras(bundle);
                    context.startActivity(intent);
                }
            });

    }

    @Override
    public int getItemCount() {
        return doctorList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        CircleImageView profile_view;
        TextView name;
        TextView description;
        TextView original;
        Button book_btn;
        TextView rating;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            profile_view = itemView.findViewById(R.id.imageProfile);
            name = itemView.findViewById(R.id.name_tv);
            description = itemView.findViewById(R.id.description_tv);
            original = itemView.findViewById(R.id.original_tv);
            book_btn = itemView.findViewById(R.id.book_btn);
            rating = itemView.findViewById(R.id.rating_tv);
        }


    }
}
