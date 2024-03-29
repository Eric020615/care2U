package com.example.care2u;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;

public class ProfileFragment extends Fragment implements View.OnClickListener {

    private View root;
    private AlphaAnimation buttonClick = new AlphaAnimation(1F, 0.8F);

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        root = inflater.inflate(R.layout.fragment_profile, container, false);
        Button faq_button = root.findViewById(R.id.faqs_button);
        Button edit_profile_button = root.findViewById(R.id.edit_profile_button);
        Button prescription_order_button = root.findViewById(R.id.prescription_order_button);
        Button log_out_button = root.findViewById(R.id.logout_button);
        TextView name = root.findViewById(R.id.name_TV);
        TextView age = root.findViewById(R.id.age_TV);
        TextView occupation = root.findViewById(R.id.role_TV);
        TextView height = root.findViewById(R.id.height_TV);
        TextView weight = root.findViewById(R.id.weight_TV);
        TextView bloodtype = root.findViewById(R.id.bloodtype_TV);
        TextView gender = root.findViewById(R.id.gender_TV);
        ImageView profile_pic = root.findViewById(R.id.profile_picture_IV);
        ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Fetching Image");
        progressDialog.setCancelable(false);
        progressDialog.show();

        FirebaseAuth Auth = FirebaseAuth.getInstance();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Users");
        String uid = Auth.getCurrentUser().getUid();
        StorageReference storageReference = FirebaseStorage.getInstance().getReference("images/" + uid);
        try {
            File localfile = File.createTempFile("Temp File", ".jpg");
            storageReference.getFile(localfile)
                    .addOnCompleteListener(new OnCompleteListener<FileDownloadTask.TaskSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<FileDownloadTask.TaskSnapshot> task) {
                            if (progressDialog.isShowing())
                                progressDialog.dismiss();

                            Bitmap bitmap = BitmapFactory.decodeFile(localfile.getAbsolutePath());
                            profile_pic.setImageBitmap(bitmap);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            if (progressDialog.isShowing())
                                progressDialog.dismiss();

                        }
                    });
        } catch (IOException exception) {
            exception.printStackTrace();
        }

        databaseReference.child(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                name.setText(snapshot.child("username").getValue(String.class));
                age.setText(snapshot.child("age").getValue(String.class) + " years old");
                occupation.setText(snapshot.child("occupation").getValue(String.class));
                height.setText(snapshot.child("height").getValue(String.class) + " cm");
                weight.setText(snapshot.child("weight").getValue(String.class) + " kg");
                bloodtype.setText(snapshot.child("Blood Type").getValue(String.class));
                gender.setText(snapshot.child("Gender").getValue(String.class));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        log_out_button.setOnClickListener(this);
        faq_button.setOnClickListener(this);
        edit_profile_button.setOnClickListener(this);
        prescription_order_button.setOnClickListener(this);

        return root;
    }

    @Override
    public void onClick(View view) {
        final Intent[] intent = new Intent[1];
        switch (view.getId()) {
            case R.id.edit_profile_button:
                view.startAnimation(buttonClick);
                intent[0] = new Intent(getActivity(), EditProfileActivity.class);
                startActivity(intent[0]);
                break;
            case R.id.faqs_button:
                view.startAnimation(buttonClick);
                intent[0] = new Intent(getActivity(), FAQActivity.class);
                startActivity(intent[0]);
                break;
            case R.id.prescription_order_button:
                view.startAnimation(buttonClick);
                intent[0] = new Intent(getActivity(), PrescriptionOrderActivity.class);
                startActivity(intent[0]);
                break;
            case R.id.logout_button:
                view.startAnimation(buttonClick);
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Log Out");
                builder.setMessage("Are you sure to log out");
                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        FirebaseAuth mAuth = FirebaseAuth.getInstance();
                        mAuth.signOut();
                        intent[0] = new Intent(getActivity(), LoginActivity.class);
                        startActivity(intent[0]);
                    }
                });

                builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();

        }
    }

}