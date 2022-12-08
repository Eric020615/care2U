package com.example.care2u;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class StartingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_starting);
        Button LoginBtn = findViewById(R.id.start_login_button);
        Button SignUpBtn = findViewById(R.id.start_signup_button);
        LoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(StartingActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        SignUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(StartingActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }
}