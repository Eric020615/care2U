package com.example.care2u.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.care2u.R;
import com.example.care2u.entity.PrescriptionOrder;

import java.util.ArrayList;

public class PrescriptionOrderAdapter extends RecyclerView.Adapter<PrescriptionOrderAdapter.MyViewHolder> {

    private Context context;
    ArrayList<PrescriptionOrder> prescriptionOrders = new ArrayList<>();


    public PrescriptionOrderAdapter(Context context) {
        this.context = context;
    }

    public boolean isOrderEmpty(){
        return prescriptionOrders.isEmpty();
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_prescription_order, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        PrescriptionOrder order = prescriptionOrders.get(position);
        holder.order_ID.setText("Order ID: " + order.getOrderID());
        holder.start_order_date_tv.setText("Order Start Date: ");
        holder.estimated_delivery_date_tv.setText("Estimated Delivery Date: ");
//        holder.payment_status_tv
        holder.pay_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (holder.address_et.getText().toString().isEmpty()) {
                    Toast.makeText(context, "Address cannot be empty.", Toast.LENGTH_LONG).show();
                } else {
                    holder.start_order_date_tv.setText("Order Start Date: " + order.getStart_order_date());
                    holder.estimated_delivery_date_tv.setText("Estimated Delivery Date: " + order.getEstimated_delivery_date());
                    holder.payment_status_tv.setText("Paid");
                    holder.payment_status_tv.setTextColor(Color.BLUE);
                    holder.pay_btn.setVisibility(View.INVISIBLE);
                    holder.amount_tv.setTextColor(Color.BLUE);

                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return prescriptionOrders.size();
    }


    public void addOrder() {
        prescriptionOrders.add(new PrescriptionOrder());
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView order_ID;
        TextView start_order_date_tv;
        TextView estimated_delivery_date_tv;
        TextView payment_status_tv;
        TextView amount_tv;
        EditText address_et;
        Button pay_btn;
        TextView no_order_message_tv;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            order_ID = itemView.findViewById(R.id.order_id_tv);
            start_order_date_tv= itemView.findViewById(R.id.start_order_date_tv);
            estimated_delivery_date_tv= itemView.findViewById(R.id.estimated_delivery_date_tv);
            payment_status_tv= itemView.findViewById(R.id.payment_status_tv);
            address_et= itemView.findViewById(R.id.address_et);
            pay_btn= itemView.findViewById(R.id.pay_btn);
            amount_tv= itemView.findViewById(R.id.amount_tv);

        }


    }
}
