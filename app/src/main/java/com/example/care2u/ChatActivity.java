package com.example.care2u;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;

import com.example.care2u.databinding.ActivityChatBinding;
import com.example.care2u.databinding.ActivityMainBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ChatActivity extends AppCompatActivity {
    ActivityChatBinding binding;
    DatabaseReference databaseReference;
    FirebaseAuth mAuth;
    ChatAdapter chatAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityChatBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        mAuth=FirebaseAuth.getInstance();

        databaseReference= FirebaseDatabase.getInstance().getReference("Users");
        chatAdapter=new ChatAdapter(this);
        binding.messageRecycleView.setLayoutManager(new LinearLayoutManager(this));

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                chatAdapter.clear();
                for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                    String uid=dataSnapshot.getKey();
                    if (!uid.equals(FirebaseAuth.getInstance().getUid())){
                        User user=dataSnapshot.getValue(User.class);
                        chatAdapter.add(user);
                    }
                }
                binding.messageRecycleView.setAdapter(chatAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}