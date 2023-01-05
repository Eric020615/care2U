package com.example.care2u.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.care2u.R;
import com.example.care2u.entity.NotificationModel;
import com.example.care2u.entity.PrescriptionOrder;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class PrescriptionOrderAdapter extends RecyclerView.Adapter<PrescriptionOrderAdapter.MyViewHolder> {

    private Context context;
    static ArrayList<PrescriptionOrder> prescriptionOrders = new ArrayList<>();
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://care2u-99f78-default-rtdb.firebaseio.com/");


    public PrescriptionOrderAdapter(Context context) {
        this.context = context;
    }

    public boolean isOrderEmpty() {
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
        if (order.isPaid() == false) {
            holder.order_ID.setText("Order ID: " + order.getOrderID());
            holder.start_order_date_tv.setText("Order Start Date: ");
            holder.estimated_delivery_date_tv.setText("Estimated Delivery Date: ");
            holder.pay_btn.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle("Payment");
                    builder.setMessage("Are you sure to pay?");
                    builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                            String address =holder.address_et.getText().toString();
                            System.out.println(address);
                            if (address.isEmpty()) {
                                Toast.makeText(context, "Address cannot be empty.", Toast.LENGTH_LONG).show();
                            } else {
                                databaseReference.child("Assets").child(FirebaseAuth.getInstance().getUid()).child("Money").addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        String money = snapshot.getValue(String.class);
                                        if (money==null||Double.parseDouble(money)<85){
                                            Toast.makeText(view.getContext(), "Insufficient balance",Toast.LENGTH_SHORT).show();
                                        }
                                        else{
                                            double temp = Double.parseDouble(money)-85.0;
                                            databaseReference.child("Assets").child(FirebaseAuth.getInstance().getUid()).child("Money").setValue(String.format("%.2f", temp));
                                            databaseReference.child("Notification").child(FirebaseAuth.getInstance().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                    NotificationModel notificationModel = new NotificationModel("Deducted RM85 from your balance.","");
                                                    String notification_ID = String.valueOf(snapshot.getChildrenCount()+1);
                                                    FirebaseDatabase.getInstance().getReference("Notification").child(FirebaseAuth.getInstance().getUid()).child(notification_ID).setValue(notificationModel);

                                                }

                                                @Override
                                                public void onCancelled(@NonNull DatabaseError error) {

                                                }
                                            });
                                            holder.start_order_date_tv.setText("Order Start Date: " + order.getStart_order_date());
                                            holder.estimated_delivery_date_tv.setText("Estimated Delivery Date: " + order.getEstimated_delivery_date());
                                            holder.payment_status_tv.setText("Paid");
                                            holder.payment_status_tv.setTextColor(Color.BLUE);
                                            holder.pay_btn.setVisibility(View.INVISIBLE);
                                            holder.amount_tv.setTextColor(Color.BLUE);
                                            holder.address_tv.setText("Address: " + address);
                                            order.setAddress(address);
                                            holder.address_tv.setVisibility(View.VISIBLE);
                                            holder.address_ly.setVisibility(View.GONE);
                                            holder.done_icon.setVisibility(View.VISIBLE);
                                            order.setPaid(true);
                                            Toast.makeText(view.getContext(), "Payment successful",Toast.LENGTH_SHORT).show();


                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });


                            }
                        }
                    });

                    builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.cancel();
                        }
                    });

                    AlertDialog dialog = builder.create();
                    dialog.show();

                }
            });
        } else {
            holder.start_order_date_tv.setText("Order Start Date: " + order.getStart_order_date());
            holder.estimated_delivery_date_tv.setText("Estimated Delivery Date: " + order.getEstimated_delivery_date());
            holder.payment_status_tv.setText("Paid");
            holder.payment_status_tv.setTextColor(Color.BLUE);
            holder.pay_btn.setVisibility(View.GONE);
            holder.amount_tv.setTextColor(Color.BLUE);
            holder.address_tv.setText("Address: " + order.getAddress());
            holder.address_tv.setVisibility(View.VISIBLE);
            holder.address_ly.setVisibility(View.GONE);
            holder.done_icon.setVisibility(View.VISIBLE);

        }
    }

    @Override
    public int getItemCount() {
        return prescriptionOrders.size();
    }


    public void addOrder() {
        if (prescriptionOrders.size() == 0) {
            prescriptionOrders.add(new PrescriptionOrder());
        }
        return;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView order_ID;
        TextView start_order_date_tv;
        TextView estimated_delivery_date_tv;
        TextView payment_status_tv;
        TextView amount_tv;
        EditText address_et;
        Button pay_btn;
        TextView address_tv;
        LinearLayout address_ly;
        ImageView done_icon;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            order_ID = itemView.findViewById(R.id.order_id_tv);
            start_order_date_tv = itemView.findViewById(R.id.start_order_date_tv);
            estimated_delivery_date_tv = itemView.findViewById(R.id.estimated_delivery_date_tv);
            payment_status_tv = itemView.findViewById(R.id.payment_status_tv);
            address_et = itemView.findViewById(R.id.address_et);
            address_tv = itemView.findViewById(R.id.address_tv);
            pay_btn = itemView.findViewById(R.id.pay_btn);
            amount_tv = itemView.findViewById(R.id.amount_tv);
            address_ly = itemView.findViewById(R.id.address_ly);
            done_icon = itemView.findViewById(R.id.done_payment_iv);

        }


    }
}
