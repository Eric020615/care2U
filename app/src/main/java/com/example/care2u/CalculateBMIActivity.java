package com.example.care2u;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CalculateBMIActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText height_et;
    private EditText weight_et;
    private TextView result_tv;
    private TextView status_tv;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Users");
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculate_bmiactivity);

        ImageView close = findViewById(R.id.close_iv);
        height_et = findViewById(R.id.height_ET);
        weight_et = findViewById(R.id.weight_ET);
        Button calculate_btn = findViewById(R.id.calculate_BMI_button);
        result_tv = findViewById(R.id.result_tv);
        status_tv = findViewById(R.id.status_tv);

        calculate_btn.setOnClickListener(this);

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    public void onClick(View view) {
        Double height = Double.parseDouble(height_et.getText().toString());
        Double weight = Double.parseDouble(weight_et.getText().toString());

        double bmi = weight / ((height/100) * (height/100));
        String status ="";
        if (bmi>=30.0){
            status = "Obesity";
        }else if(bmi >=25.0 && bmi <=29.9){
            status = "Overweight";
        }else if(bmi >=18.5 && bmi <=24.9){
            status = "Healthy Weight";
        }else if(bmi <=18.5){
            status = "Underweight";
        }
        result_tv.setText(String.format("%.1f",bmi));
        status_tv.setText(status);

        height_et.setText("");
        weight_et.setText("");

    }
}

//    BMI	Weight Status
//        Below 18.5	Underweight
//        18.5—24.9	Healthy Weight
//        25.0—29.9	Overweight
//        30.0 and Above	Obesity