package com.example.care2u;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {

    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://care2u-99f78-default-rtdb.firebaseio.com/");
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button loginBtn = findViewById(R.id.login_button);
        final EditText email = findViewById(R.id.login_email);
        final EditText password = findViewById(R.id.login_password);
        mAuth = FirebaseAuth.getInstance();

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String email_text = email.getText().toString();
                final String password_text = password.getText().toString();
                if ((email_text.isEmpty())||(password_text.isEmpty())){
                    Toast.makeText(LoginActivity.this, "Please enter your email and password", Toast.LENGTH_SHORT).show();
                }
                else{
                    mAuth.signInWithEmailAndPassword(email_text,password_text)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                Toast.makeText(LoginActivity.this,"Account is correct.",Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                startActivity(intent);
                                finish();
                            }else{
                                Toast.makeText(LoginActivity.this,"Account not correct.",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });


//                    databaseReference.child("email").addListenerForSingleValueEvent(new ValueEventListener() {
//                        @Override
//                        public void onDataChange(@NonNull DataSnapshot snapshot) {
//                            if (snapshot.hasChild(email_text)){
//                                final String getPassword = snapshot.child(email_text).child("password").getValue(String.class);
//                                if (getPassword.equals(password_text)){
//                                    Toast.makeText(LoginActivity.this,"Successfully logged in",Toast.LENGTH_SHORT).show();
//                                    Intent intent = new Intent(LoginActivity.this,MainActivity.class);
//                                    startActivity(intent);
//                                }
//                                else{
//                                    Toast.makeText(LoginActivity.this,"Wrong Password",Toast.LENGTH_SHORT).show();
//                                }
//                            }
//                            else{
//                                Toast.makeText(LoginActivity.this,"Wrong Password",Toast.LENGTH_SHORT).show();
//                            }
//                        }
//                        @Override
//                        public void onCancelled(@NonNull DatabaseError error) {
//
//                        }
//                    });
                }

            }
        });
    }
}