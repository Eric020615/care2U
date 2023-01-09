package com.example.care2u.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.care2u.R;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ConsultationHistoryAdapter extends RecyclerView.Adapter<ConsultationHistoryAdapter.MyViewHolder>{

    private Context context;
    private static List<String>historyList;



    public ConsultationHistoryAdapter(Context context,List<String> historyList){
        this.context=context;
        this.historyList = historyList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_consultation_history,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder,int position) {
        holder.doctor_name_tv.setText("Dr Elizabeth Smith");
        holder.appointment_date_tv.setText("2023-01-10");
        holder.time_slot_tv.setText("10am-12pm");
        holder.show_advice_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (holder.advice_tv.getVisibility()==View.VISIBLE)
                    holder.advice_tv.setVisibility(View.GONE);
                else
                    holder.advice_tv.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public int getItemCount() {
        return 1;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder{
        private CircleImageView imageProfile;
        private TextView doctor_name_tv;
        private TextView appointment_date_tv;
        private TextView time_slot_tv;
        private TextView advice_tv;
        private ImageView show_advice_iv;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imageProfile = itemView.findViewById(R.id.imageProfile);
            doctor_name_tv = itemView.findViewById(R.id.doctor_name_tv);
            appointment_date_tv = itemView.findViewById(R.id.appointment_date_tv);
            time_slot_tv = itemView.findViewById(R.id.time_slot_tv);
            advice_tv = itemView.findViewById(R.id.advice_tv);
            show_advice_iv = itemView.findViewById(R.id.show_advice_iv);

        }


    }
}
