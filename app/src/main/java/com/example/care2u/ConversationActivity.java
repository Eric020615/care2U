package com.example.care2u;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.care2u.databinding.ActivityConversationBinding;
import com.google.firebase.FirebaseApiNotAvailableException;
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
    String senderRoom,receiverRoom,name;
    ConversationAdapter conversationAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityConversationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        receiver_id=getIntent().getStringExtra("id");
        name=getIntent().getStringExtra("name");
        senderRoom= FirebaseAuth.getInstance().getUid()+receiver_id;
        receiverRoom= receiver_id+FirebaseAuth.getInstance().getUid();
        databaseReferenceSender= FirebaseDatabase.getInstance().getReference("chats").child(senderRoom);
        databaseReferenceReceiver= FirebaseDatabase.getInstance().getReference("chats").child(receiverRoom);
        conversationAdapter=new ConversationAdapter(this);
        binding.conversationRecycler.setLayoutManager(new LinearLayoutManager(this));
        binding.nameChat.setText(name);

        databaseReferenceSender.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                conversationAdapter.clear();
                for (DataSnapshot dataSnapshot:snapshot.getChildren()){
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
                String message=binding.messageEdit.getText().toString();
                binding.messageEdit.setText("");
                if (message.trim().length()>0){
                    sendMessage(message);
                }
            }
        });

        binding.sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(),ChatActivity.class);
                startActivity(intent);
            }
        });
    }

    private void sendMessage(String message){
        String messageID= UUID.randomUUID().toString();
        MessageModel messageModel=new MessageModel(messageID, FirebaseAuth.getInstance().getUid(),message);
        conversationAdapter.add(messageModel);
        databaseReferenceSender.child(messageID).setValue(messageModel);
        databaseReferenceReceiver.child(messageID).setValue(messageModel);
    }
}