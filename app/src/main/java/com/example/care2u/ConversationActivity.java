package com.example.care2u;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.care2u.adapter.ConversationAdapter;
import com.example.care2u.databinding.ActivityConversationBinding;
import com.example.care2u.entity.MessageModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.UUID;

public class ConversationActivity extends AppCompatActivity {

    ActivityConversationBinding binding;
    String receiver_id;
    DatabaseReference databaseReferenceSender;
    DatabaseReference databaseReferenceReceiver;
    String senderRoom, receiverRoom, name;
    ConversationAdapter conversationAdapter;
    String key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityConversationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        receiver_id = getIntent().getStringExtra("id");
        name = getIntent().getStringExtra("name");
        senderRoom = FirebaseAuth.getInstance().getUid() + receiver_id;
        receiverRoom = receiver_id + FirebaseAuth.getInstance().getUid();
        databaseReferenceSender = FirebaseDatabase.getInstance().getReference("chats").child(senderRoom);
        databaseReferenceReceiver = FirebaseDatabase.getInstance().getReference("chats").child(receiverRoom);
        conversationAdapter = new ConversationAdapter(this);
        binding.conversationRecycler.setLayoutManager(new LinearLayoutManager(this));
        binding.nameChat.setText(name);

        databaseReferenceSender.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                conversationAdapter.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    MessageModel messageModel = dataSnapshot.getValue(MessageModel.class);
                    conversationAdapter.add(messageModel);
                }
                binding.conversationRecycler.setAdapter(conversationAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        binding.sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String message = binding.messageEdit.getText().toString();
                binding.messageEdit.setText("");
                if (message.trim().length() > 0) {
                    sendMessage(message, receiverRoom, senderRoom);
                }
            }
        });

        binding.conversationBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ChatActivity.class);
                startActivity(intent);
            }
        });
    }

    private void sendMessage(String message, String receiverRoom, String senderRoom) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://care2u-99f78-default-rtdb.firebaseio.com/");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                key = "1";
                MessageModel messageModel;
                if (snapshot.hasChild("chats")) {
                    key = String.valueOf(snapshot.child("chats").child(receiverRoom).getChildrenCount()) + 1;
                    messageModel = new MessageModel(key, FirebaseAuth.getInstance().getUid(), message);
                    conversationAdapter.add(messageModel);
                    databaseReferenceSender.child(key).setValue(messageModel);
                    databaseReferenceReceiver.child(key).setValue(messageModel);

                } else {
                    messageModel = new MessageModel(key, FirebaseAuth.getInstance().getUid(), message);
                    conversationAdapter.add(messageModel);
                    databaseReferenceSender.child(key).setValue(messageModel);
                    databaseReferenceReceiver.child(key).setValue(messageModel);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}