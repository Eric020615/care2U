package com.example.care2u;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.proto.ProtoOutputStream;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.care2u.adapter.NotificationAdapter;
import com.example.care2u.entity.NotificationModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class NotificationFragment extends Fragment {

    private View root;
    DatabaseReference databaseReference;

    public NotificationFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_notification, container, false);
        databaseReference = FirebaseDatabase.getInstance().getReference("Notification");
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        String uid = mAuth.getCurrentUser().getUid();
        RecyclerView notification = root.findViewById(R.id.notificationList);
        NotificationAdapter notificationAdapter = new NotificationAdapter(getContext());
        notification.setLayoutManager(new LinearLayoutManager(getContext()));

        databaseReference.child(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                notificationAdapter.clear();
                for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                    NotificationModel notificationModel = dataSnapshot.getValue(NotificationModel.class);
                    notificationAdapter.add(notificationModel);
                }
                notificationAdapter.reverse();
                notification.setAdapter(notificationAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return root;
    }
}