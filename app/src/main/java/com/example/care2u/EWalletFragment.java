package com.example.care2u;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.care2u.adapter.TransactionAdapter;
import com.example.care2u.entity.TransactionModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class EWalletFragment extends Fragment {

    private View root;
    private AlphaAnimation buttonClick = new AlphaAnimation(1F, 0.8F);

    public EWalletFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        TopUpFragment fragment = new TopUpFragment();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Assets");
        DatabaseReference transactionRef = FirebaseDatabase.getInstance().getReference("Transaction_History");
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        TransactionAdapter transactionAdapter = new TransactionAdapter(getContext());
        root = inflater.inflate(R.layout.fragment_e_wallet, container, false);
        ImageButton TopUpButton = root.findViewById(R.id.TopUpBtn);
        TextView balance = root.findViewById(R.id.BalanceCount);
        RecyclerView transaction = root.findViewById(R.id.transaction_list);
        transaction.setLayoutManager(new LinearLayoutManager(getContext()));
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

        TopUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.startAnimation(buttonClick);
                fragment.show(getActivity().getSupportFragmentManager(), "Top Up");
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

        return root;
    }
}