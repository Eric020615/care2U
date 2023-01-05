package com.example.care2u;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.care2u.adapter.TransactionAdapter;
import com.example.care2u.entity.NotificationModel;
import com.example.care2u.entity.TransactionModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.UUID;

import okhttp3.internal.cache.DiskLruCache;

public class TopUpFragment extends DialogFragment {

    private View root;
    private String balance;
    private Double TopUpAmount = 0.00;
    private String order;
    private String key;
    private String notification_ID;

    public TopUpFragment() {
        // Required empty public constructor
    }

    public Dialog onCreateDialog() {
        return new AlertDialog.Builder(requireContext())
                .setMessage("Top Up")
                .setPositiveButton("ok", (dialog, which) -> {
                })
                .create();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_top_up, container, false);
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Assets");
        DatabaseReference transactionReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://care2u-99f78-default-rtdb.firebaseio.com/");
        DatabaseReference notificationReference = FirebaseDatabase.getInstance().getReference("Notification");
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        String uid = mAuth.getCurrentUser().getUid();
        Button TopUpButton = root.findViewById(R.id.TopUpButton);
        EditText Amount = root.findViewById(R.id.editTopUpAmount);
        databaseReference.child(uid).child("Money").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                balance = snapshot.getValue(String.class);
                if (balance==null){
                    balance="0";
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        TopUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ((Amount.getText().toString().isEmpty())||Double.parseDouble(Amount.getText().toString())<10) {
                    Toast.makeText(getActivity(), "Please type valid amount", Toast.LENGTH_LONG).show();
                } else {
                    Double TopUpAmount = Double.parseDouble(balance) + Double.parseDouble(Amount.getText().toString());
                    String updateAmount = String.format("%.2f", TopUpAmount);
                    databaseReference.child(uid).child("Money").setValue(updateAmount).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            TransactionModel transactionModel = new TransactionModel(Amount.getText().toString(), "");
                            transactionReference.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    key = "1";
                                    if (snapshot.child("Transaction_History").hasChild(uid)) {
                                        key = String.valueOf(snapshot.child("Transaction_History").child(uid).getChildrenCount()) + 1;
                                        transactionReference.child("Transaction_History").child(uid).child(key).setValue(transactionModel);
                                    } else {
                                        transactionReference.child("Transaction_History").child(uid).child(key).setValue(transactionModel);
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {
                                }
                            });
                            notificationReference.child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    NotificationModel notificationModel = new NotificationModel("You had reloaded RM"+Amount.getText().toString(),"");
                                    notification_ID = String.valueOf(snapshot.getChildrenCount()+1);
                                    FirebaseDatabase.getInstance().getReference("Notification").child(FirebaseAuth.getInstance().getUid()).child(notification_ID).setValue(notificationModel);

                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });

                            Toast.makeText(getActivity(), "Reload Sucessfully", Toast.LENGTH_LONG).show();
                            dismiss();
                        }
                    });
                }
            }
        });

        return root;
    }
}