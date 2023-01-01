package com.example.care2u;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.care2u.entity.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {

    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://care2u-99f78-default-rtdb.firebaseio.com/");

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        TextView login_text = findViewById(R.id.login_text);
        mAuth = FirebaseAuth.getInstance();
        final Button registerBtn = findViewById(R.id.sign_up_button);
        final EditText name = findViewById(R.id.register_name);
        final EditText phone = findViewById(R.id.register_phone);
        final EditText email = findViewById(R.id.register_email);
        final EditText password = findViewById(R.id.register_password);
        final EditText confirm_password = findViewById(R.id.register_confirmpassword);

        login_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String name_text = name.getText().toString();
                final String phone_text = phone.getText().toString();
                final String email_text = email.getText().toString();
                final String password_text = password.getText().toString();
                final String confirm_password_text = confirm_password.getText().toString();
                if ((name_text.isEmpty()) || (phone_text.isEmpty()) || (email_text.isEmpty()) || (password_text.isEmpty()) || (confirm_password_text.isEmpty())) {
                    Toast.makeText(RegisterActivity.this, "Please do not leave the empty space", Toast.LENGTH_SHORT).show();
                } else if (!password_text.equals(confirm_password_text)) {
                    Toast.makeText(RegisterActivity.this, "The password you type not match", Toast.LENGTH_SHORT).show();
                } else {
                    mAuth.createUserWithEmailAndPassword(email_text, password_text).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                User user = new User(name_text, email_text, phone_text, FirebaseAuth.getInstance().getUid());
                                FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getUid()).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            FirebaseDatabase.getInstance().getReference("Assets").child(FirebaseAuth.getInstance().getUid()).child("Money").setValue("0").addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {

                                                }
                                            });
                                            Toast.makeText(RegisterActivity.this, "Account had been registered successfully.", Toast.LENGTH_SHORT).show();
                                            finish();
                                        }
                                    }
                                });

                            } else {
                                FirebaseAuthException e = (FirebaseAuthException) task.getException();
                                Toast.makeText(RegisterActivity.this, "Failed to register! " + e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }
}