package com.example.care2u;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class EditProfileActivity extends AppCompatActivity {

    EditText editName, editAge, editOccupation, editHeight, editWeight, editEmail;
    Button buttonSave;
    String nameUser, ageUser, occupationUser, heightUser, weightUser, emailUser;
    DatabaseReference reference;
    TextView name = findViewById(R.id.name_TV);
    TextView age = findViewById(R.id.age_TV);
    TextView occupation = findViewById(R.id.role_TV);
    TextView height = findViewById(R.id.height_TV);
    TextView weight = findViewById(R.id.weight_TV);
    TextView email = findViewById(R.id.email_ET);

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

        reference = FirebaseDatabase.getInstance().getReference("users");

        editName = findViewById(R.id.name_ET);
        editAge= findViewById(R.id.age_ET);
        editOccupation = findViewById(R.id.occupation_ET);
        editHeight = findViewById(R.id.height_ET);
        editWeight = findViewById(R.id.weight_ET);
        editEmail = findViewById(R.id.email_ET);
        buttonSave = findViewById(R.id.button_Save);

        showData();

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isNameChange()||isAgeChange()||isOccupationChange()||isHeightChange()||isWeightChange()|| isEmailChange()){
                    Toast.makeText(EditProfileActivity.this, "Saved", Toast.LENGTH_SHORT).show();
                } else
                    Toast.makeText(EditProfileActivity.this, "No Changes Found", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public boolean isNameChange(){
        if(!nameUser.equals(editName.getText().toString())){
            reference.child(String.valueOf(name)).child("name").setValue(editName.getText().toString());
            nameUser = editName.getText().toString();
            return true;
        } else{
            return false;
        }
    }

    public boolean isAgeChange(){
        if(!ageUser.equals(editAge.getText().toString())){
            reference.child(String.valueOf(age)).child("age").setValue(editAge.getText().toString());
            ageUser = editAge.getText().toString();
            return true;
        } else{
            return false;
        }
    }

    public boolean isOccupationChange(){
        if(!occupationUser.equals(editOccupation.getText().toString())){
            reference.child(String.valueOf(occupation)).child("occupation").setValue(editOccupation.getText().toString());
            occupationUser = editOccupation.getText().toString();
            return true;
        } else{
            return false;
        }
    }

    public boolean isEmailChange(){
        if(!emailUser.equals(editEmail.getText().toString())){
            reference.child(String.valueOf(email)).child("email").setValue(editEmail.getText().toString());
            emailUser = editEmail.getText().toString();
            return true;
        } else{
            return false;
        }
    }

    public boolean isHeightChange(){
        if(!heightUser.equals(editHeight.getText().toString())){
            reference.child(String.valueOf(height)).child("height").setValue(editHeight.getText().toString());
            heightUser = editHeight.getText().toString();
            return true;
        } else{
            return false;
        }
    }

    public boolean isWeightChange(){
        if(!weightUser.equals(editWeight.getText().toString())){
            reference.child(String.valueOf(weight)).child("height").setValue(editWeight.getText().toString());
            weightUser = editWeight.getText().toString();
            return true;
        } else{
            return false;
        }
    }

    public void showData(){

        Intent intent= getIntent();

        nameUser = intent.getStringExtra("name");
        ageUser = intent.getStringExtra("age");
        occupationUser = intent.getStringExtra("occupation");
        heightUser = intent.getStringExtra("height");
        weightUser = intent.getStringExtra("weight");

        editName.setText(nameUser);
        editAge.setText(ageUser);
        editOccupation.setText(occupationUser);
        editHeight.setText(heightUser);
        editWeight.setText(weightUser);

    }
}