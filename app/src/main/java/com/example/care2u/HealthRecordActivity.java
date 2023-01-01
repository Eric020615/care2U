package com.example.care2u;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class HealthRecordActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health_record);
        ImageView back_button = findViewById(R.id.IV_back);
        Button calculate_BMI_button = findViewById(R.id.calculate_BMI_button);
        Button depression_test_button = findViewById(R.id.depression_test_button);
        Button consultation_history_button = findViewById(R.id.consultation_history_button);
        Button reference_button = findViewById(R.id.reference_button);
        back_button.setOnClickListener(this);
        calculate_BMI_button.setOnClickListener(this);
        depression_test_button.setOnClickListener(this);
        consultation_history_button.setOnClickListener(this);
        reference_button.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()){
            case R.id.IV_back:
                finish();
                break;
            case R.id.calculate_BMI_button:
                intent = new Intent(HealthRecordActivity.this,CalculateBMIActivity.class);
                startActivity(intent);
                break;
            case R.id.depression_test_button:
                break;
            case R.id.consultation_history_button:
                break;
            case R.id.reference_button:
                break;

        }
    }
}