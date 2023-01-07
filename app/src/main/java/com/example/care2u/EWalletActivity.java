package com.example.care2u;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.care2u.adapter.TransactionAdapter;
import com.example.care2u.entity.TransactionModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class EWalletActivity extends AppCompatActivity {

    private AlphaAnimation buttonClick = new AlphaAnimation(1F, 0.8F);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_e_wallet);
        TopUpFragment fragment = new TopUpFragment();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Assets");
        DatabaseReference transactionRef = FirebaseDatabase.getInstance().getReference("Transaction_History");
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        TransactionAdapter transactionAdapter = new TransactionAdapter(getApplicationContext());
        ImageView backBtn = findViewById(R.id.ewallet_back);
        ImageButton TopUpButton = findViewById(R.id.TopUpBtn);
        TextView balance = findViewById(R.id.BalanceCount);
        RecyclerView transaction = findViewById(R.id.transaction_list);
        transaction.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        String uid = mAuth.getCurrentUser().getUid();
        databaseReference.child(uid).child("Money").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String money = snapshot.getValue(String.class);
                if (money==null){
                    balance.setText("RM 0");
                }
                else {
                    balance.setText("RM " + money);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.startAnimation(buttonClick);
                finish();
            }
        });

        TopUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.startAnimation(buttonClick);
                fragment.show(getSupportFragmentManager(), "Top Up");
            }
        });

        transactionRef.child(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                transactionAdapter.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    TransactionModel transactionModel = dataSnapshot.getValue(TransactionModel.class);
                    transactionAdapter.add(transactionModel);
                }
                transactionAdapter.reverse();
                transaction.setAdapter(transactionAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}