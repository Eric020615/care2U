package com.example.care2u;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class EditProfileActivity extends AppCompatActivity {

    EditText editName, editAge, editOccupation, editHeight, editWeight, editEmail;
    RadioGroup gender, bloodtype;
    RadioButton genderSelection, bloodtypeSelection;
    Button buttonSave;
    String nameUser, ageUser, occupationUser, heightUser, weightUser, emailUser;
    DatabaseReference reference;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    String uid = mAuth.getCurrentUser().getUid();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);


        ImageView back_button = findViewById(R.id.IV_back);
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        reference = FirebaseDatabase.getInstance().getReference("Users");

        reference.child(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                nameUser = snapshot.child("username").getValue(String.class);
                ageUser  = snapshot.child("age").getValue(String.class);
                occupationUser = snapshot.child("occupation").getValue(String.class);
                heightUser = snapshot.child("height").getValue(String.class);
                weightUser = snapshot.child("weight").getValue(String.class);
                emailUser = snapshot.child("email").getValue(String.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        editName = findViewById(R.id.name_ET);
        editAge= findViewById(R.id.age_ET);
        editOccupation = findViewById(R.id.occupation_ET);
        editHeight = findViewById(R.id.height_ET);
        editWeight = findViewById(R.id.weight_ET);
        buttonSave = findViewById(R.id.button_Save);
        gender = (RadioGroup) findViewById(R.id.gender_RG);
        bloodtype = (RadioGroup) findViewById(R.id.bloodtype_RG);

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!editName.getText().toString().isEmpty()&&!editAge.getText().toString().isEmpty()&&!editOccupation.getText().toString().isEmpty()
                    &&!editHeight.getText().toString().isEmpty()&&!editWeight.getText().toString().isEmpty()){

                    if(isNameChange()||isAgeChange()||isOccupationChange()||isHeightChange()||isWeightChange()){
                        genderSelection = (RadioButton) findViewById(gender.getCheckedRadioButtonId());
                        bloodtypeSelection = (RadioButton) findViewById(bloodtype.getCheckedRadioButtonId());

                        reference.child(uid).child("username").setValue(editName.getText().toString());
                        reference.child(uid).child("age").setValue(editAge.getText().toString());
                        reference.child(uid).child("occupation").setValue(editOccupation.getText().toString());
                        reference.child(uid).child("height").setValue(editHeight.getText().toString());
                        reference.child(uid).child("weight").setValue(editWeight.getText().toString());
                        reference.child(uid).child("Gender").setValue(genderSelection.getText());
                        reference.child(uid).child("Blood Type").setValue(bloodtypeSelection.getText());

                        double height = Double.parseDouble(String.valueOf(editHeight.getText().toString()));
                        double weight = Double.parseDouble(String.valueOf(editWeight.getText().toString()));
                        double bmi = weight / ((height/100) * (height/100));
                        reference.child(uid).child("BMI").setValue(String.format("%.1f",bmi));

                        Toast.makeText(EditProfileActivity.this, "Saved", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(EditProfileActivity.this, "No Changes Found", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(EditProfileActivity.this, "Please do not leave empty space!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public boolean isNameChange(){
        if(!nameUser.equals(editName.getText().toString())){
            nameUser = editName.getText().toString();
            return true;
        }
        else if(nameUser == null){
            nameUser = editName.getText().toString();
            return true;
        }
        else{
            return false;
        }
    }

    public boolean isAgeChange(){
        if (ageUser!=null){
            if(!ageUser.equals(editAge.getText().toString())){
                ageUser = editAge.getText().toString();
                return true;
            }
            else{
                return false;
            }
        }
        else if(ageUser == null){
            ageUser = editAge.getText().toString();
            return true;
        }
        else{
            return false;
        }
    }

    public boolean isOccupationChange(){
        if (occupationUser!=null){
            if(!occupationUser.equals(editOccupation.getText().toString())){
                occupationUser = editOccupation.getText().toString();
                return true;
            }
            else{
                return false;
            }
        }
        else if(occupationUser == null){
            occupationUser = editOccupation.getText().toString();
            return true;
        }
        else{
            return false;
        }
    }

    public boolean isHeightChange(){
        if (heightUser != null){
            if(!heightUser.equals(editHeight.getText().toString())){
                heightUser = editHeight.getText().toString();
                return true;
            }
            else{
                return false;
            }
        }
        else if(heightUser == null){
            heightUser = editHeight.getText().toString();
            return true;
        }
        else{
            return false;
        }
    }

    public boolean isWeightChange(){
        if (weightUser != null){
            if(!weightUser.equals(editWeight.getText().toString())&&(weightUser!=null)){
                weightUser = editWeight.getText().toString();
                return true;
            }
            return false;
        }
        else if(weightUser == null){
            weightUser = editWeight.getText().toString();
            return true;
        }
        else{
            return false;
        }
    }


}