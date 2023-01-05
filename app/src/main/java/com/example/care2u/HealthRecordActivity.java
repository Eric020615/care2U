package com.example.care2u;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class HealthRecordActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health_record);

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Users");
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        String uid=mAuth.getCurrentUser().getUid();
        ImageView back_button = findViewById(R.id.IV_back);
        Button calculate_BMI_button = findViewById(R.id.calculate_BMI_button);
        Button depression_test_button = findViewById(R.id.depression_test_button);
        Button consultation_history_button = findViewById(R.id.consultation_history_button);
        Button reference_button = findViewById(R.id.reference_button);
        TextView result_BMI = findViewById(R.id.ResultBMI);
        TextView result_DEP = findViewById(R.id.ResultDEP);
        databaseReference.child(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String BMI=snapshot.child("BMI").getValue(String.class);
                String Depression=snapshot.child("Depression").getValue(String.class);
                if (!snapshot.hasChild("Depression")){
                    result_DEP.setText("-");
                }
                else{
                    result_DEP.setText(Depression);
                }

                if(!snapshot.hasChild("BMI")){
                    result_BMI.setText("-");
                }
                else{
                    result_BMI.setText(BMI);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
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
                intent = new Intent(HealthRecordActivity.this,DepressionTestActivity.class);
                startActivity(intent);
                break;
            case R.id.consultation_history_button:
                break;
            case R.id.reference_button:
                View popup = getLayoutInflater().inflate(R.layout.popup_window_reference_bmi_depression,null);
                Button ok = popup.findViewById(R.id.ok_btn);

                PopupWindow popupWindow = new PopupWindow(getApplicationContext());
                popupWindow.setOutsideTouchable(false);
                popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                popupWindow.setContentView(popup);
                popupWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
                popupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
                popupWindow.showAtLocation(view, Gravity.CENTER,0,0);
                ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        popupWindow.dismiss();
                    }
                });
                break;

        }
    }
}