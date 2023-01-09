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

    }

    @Override
    public int getItemCount() {
        return historyList.size();
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
