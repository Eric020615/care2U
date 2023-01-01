package com.example.care2u.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.care2u.ConversationActivity;
import com.example.care2u.R;
import com.example.care2u.entity.TransactionModel;
import com.example.care2u.entity.User;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.MyViewHolder> {

    private Context context;
    private List<TransactionModel>TransactionList;

    public TransactionAdapter(Context context){
        this.context=context;
        TransactionList=new ArrayList<>();
    }

    public void add(TransactionModel transactionModel){
        TransactionList.add(transactionModel);
        notifyDataSetChanged();
    }

    public void clear(){
        TransactionList.clear();
        notifyDataSetChanged();
    }

    public void reverse(){
        Collections.reverse(TransactionList);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.transaction_history,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        TransactionModel transactionModel = TransactionList.get(position);
        holder.amount.setText("+RM"+transactionModel.getTransaction_amount());
    }

    @Override
    public int getItemCount() {
        return TransactionList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView amount;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            amount=itemView.findViewById(R.id.transaction_amount);
        }
    }
}
