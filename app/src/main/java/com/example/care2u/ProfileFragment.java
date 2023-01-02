package com.example.care2u;



import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.example.care2u.entity.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment implements View.OnClickListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private View root;

    public ProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        root = inflater.inflate(R.layout.fragment_profile, container, false);
        Button faq_button = root.findViewById(R.id.faqs_button);
        Button edit_profile_button = root.findViewById(R.id.edit_profile_button);
        Button log_out_button = root.findViewById(R.id.logout_button);
        TextView name = root.findViewById(R.id.name_TV);

        showUserData();
        passUserData();

        FirebaseAuth Auth=FirebaseAuth.getInstance();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Users");
        String uid=Auth.getCurrentUser().getUid();
        databaseReference.child(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user=snapshot.getValue(User.class);
                name.setText(user.getUsername());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        log_out_button.setOnClickListener(this);
        faq_button.setOnClickListener(this);
        edit_profile_button.setOnClickListener(this);
        return root;
    }

    @Override
    public void onClick(View view) {
        final Intent[] intent = new Intent[1];
        switch (view.getId()){
            case R.id.edit_profile_button:
                intent[0] = new Intent(getActivity(),EditProfileActivity.class);
                startActivity(intent[0]);
                break;
            case R.id.faqs_button:
                intent[0] = new Intent(getActivity(),FAQActivity.class);
                startActivity(intent[0]);
                break;
            case R.id.logout_button:
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Log Out");
                builder.setMessage("Are you sure to log out");
                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        FirebaseAuth mAuth = FirebaseAuth.getInstance();
                        mAuth.signOut();
                        intent[0] = new Intent(getActivity(),LoginActivity.class);
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

    public void showUserData(){

       Intent intent = getActivity().getIntent();

       String nameUser = intent.getStringExtra("name");
       String ageUser = intent.getStringExtra("age");
       String occupationUser = intent.getStringExtra("occupation");
       String heightUser = intent.getStringExtra("height");
       String weightUser = intent.getStringExtra("weight");

       TextView name = root.findViewById(R.id.name_TV);
       TextView age = root.findViewById(R.id.age_TV);
       TextView occupation = root.findViewById(R.id.role_TV);
       TextView height = root.findViewById(R.id.height_TV);
       TextView weight = root.findViewById(R.id.weight_TV);

       name.setText(nameUser);
       age.setText(ageUser);
       occupation.setText(occupationUser);
       height.setText(heightUser);
       weight.setText(weightUser);

        Log.d("TAG", "onCreate: " + nameUser + ageUser);
    }

    public void passUserData(){
        TextView name = root.findViewById(R.id.name_TV);

        String nameUser = name.getText().toString().trim();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");
        Query checkUserDatabase = reference.orderByChild("username").equalTo(nameUser);

        checkUserDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){

                    String nameFromDB = snapshot.child(nameUser).child("name").getValue(String.class);
                    String ageFromDB = snapshot.child(nameUser).child("age").getValue(String.class);
                    String occupationFromDB = snapshot.child(nameUser).child("occupation").getValue(String.class);
                    String heightFromDB = snapshot.child(nameUser).child("height").getValue(String.class);
                    String weightFromDB = snapshot.child(nameUser).child("weight").getValue(String.class);

                    Intent intent= new Intent(getActivity(),EditProfileActivity.class);

                    intent.putExtra("name", nameFromDB);
                    intent.putExtra("age", ageFromDB);
                    intent.putExtra("occupation", occupationFromDB);
                    intent.putExtra("height", heightFromDB);
                    intent.putExtra("weight", weightFromDB);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}