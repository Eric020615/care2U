package com.example.care2u;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.care2u.adapter.ConsultationHistoryAdapter;

import java.util.ArrayList;

public class ConsultationHistoryActivity extends AppCompatActivity {

    ArrayList<String> history_list = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultation_history);
        RecyclerView history_rv = findViewById(R.id.history_info_rv);
        ImageView close_iv = findViewById(R.id.close_iv);
        close_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        ConsultationHistoryAdapter consHistoryAdapter = new ConsultationHistoryAdapter(this,history_list);
        history_rv.setAdapter(consHistoryAdapter);
        history_rv.setLayoutManager(new LinearLayoutManager(this));
    }
}