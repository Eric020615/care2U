package com.example.care2u;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.MyViewHolder> {

    private Context context;
    private List<User>userList;
    private List<String>userID;

    public ChatAdapter(Context context){
        this.context=context;
        userList=new ArrayList<>();
    }

    public void add(User user){
        userList.add(user);
        notifyDataSetChanged();
    }

    public void clear(){
        userList.clear();
        notifyDataSetChanged();
    }

    public void addID(String id){
        userID.add(id);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_chat_adapter,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        User user = userList.get(position);
        holder.name.setText(user.getUsername());
        holder.email.setText(user.getEmail());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context,ConversationActivity.class);
                intent.putExtra("id", user.getUserid());
                intent.putExtra("name",user.getUsername());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView name,email;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.chat_name);
            email=itemView.findViewById(R.id.email);
        }
    }
}
