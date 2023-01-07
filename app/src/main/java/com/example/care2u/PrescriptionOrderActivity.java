package com.example.care2u;

import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.care2u.adapter.PrescriptionOrderAdapter;

public class PrescriptionOrderActivity extends AppCompatActivity {

    private AlphaAnimation buttonClick = new AlphaAnimation(1F, 0.8F);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prescription_order);
        RecyclerView prescription_rv = findViewById(R.id.prescription_order_rv);
        ImageView close_iv = findViewById(R.id.IV_back);
        TextView no_order_message = findViewById(R.id.no_order_tv);
        close_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.startAnimation(buttonClick);
                finish();
            }
        });
        PrescriptionOrderAdapter prescriptionOrderAdapter = new PrescriptionOrderAdapter(this);
        prescription_rv.setAdapter(prescriptionOrderAdapter);
        prescription_rv.setLayoutManager(new LinearLayoutManager(this));
        prescriptionOrderAdapter.addOrder();
        if (!prescriptionOrderAdapter.isOrderEmpty()){
            no_order_message.setVisibility(View.GONE);
        }
    }
}