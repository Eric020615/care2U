package com.example.care2u;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Html;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.care2u.databinding.ActivityMainBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity implements View.OnTouchListener {
    private AlphaAnimation buttonClick = new AlphaAnimation(1F, 0.8F);
    ActivityMainBinding binding;
    FirebaseAuth mAuth;
    FirebaseUser user;
    float dX;
    float dY;
    int lastAction;
    int happy;
    DatabaseReference happinessvalue = FirebaseDatabase.getInstance().getReference("Happiness Value").child(FirebaseAuth.getInstance().getUid());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.fab.setOnTouchListener(this);
        setSupportActionBar(findViewById(R.id.tool_app));
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#F9F9F9")));
        getSupportActionBar().setTitle(Html.fromHtml("<font color='000000'>Welcome Back!</font>"));
        replaceFragment(new HomeFragment());

        happinessvalue.child("Happy").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.getValue(Integer.class)==100){
                    View popup = getLayoutInflater().inflate(R.layout.quote,null);
                    PopupWindow popupWindow = new PopupWindow(getApplicationContext());
                    popupWindow.setOutsideTouchable(false);
                    popupWindow.setBackgroundDrawable(new ColorDrawable(Color.WHITE));
                    popupWindow.setContentView(popup);
                    popupWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
                    popupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        binding.toolApp.setOnMenuItemClickListener(item -> {
            switch (item.getItemId()) {
                case R.id.logout_button:
                    Intent intent = new Intent(MainActivity.this, StartingActivity.class);
                    startActivity(intent);
                    break;
            }
            return true;
        });

        binding.bottomNavView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.home_button:
                    getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#F9F9F9")));
                    getSupportActionBar().setTitle(Html.fromHtml("<font color='000000'>Welcome Back!</font>"));
                    replaceFragment(new HomeFragment());
                    break;
                case R.id.schedule_button:
                    getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#01639A")));
                    getSupportActionBar().setTitle("Schedule");
                    replaceFragment(new ScheduleFragment());
                    break;
                case R.id.chat_button:
                    Intent intent = new Intent(getApplicationContext(), ChatActivity.class);
                    startActivity(intent);
                    break;
                case R.id.notification_button:
                    getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#01639A")));
                    getSupportActionBar().setTitle("Notification");
                    replaceFragment(new NotificationFragment());
                    break;
                case R.id.profile_button:
                    getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#01639A")));
                    getSupportActionBar().setTitle("Profile");
                    replaceFragment(new ProfileFragment());
                    break;
            }
            return true;
        });
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_top, menu);
        return true;
    }

    @Override
    public boolean onTouch(View view, MotionEvent event) {
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                dX = view.getX() - event.getRawX();
                dY = view.getY() - event.getRawY();
                lastAction = MotionEvent.ACTION_DOWN;
                break;

            case MotionEvent.ACTION_MOVE:
                view.setY(event.getRawY() + dY);
                view.setX(event.getRawX() + dX);
                lastAction = MotionEvent.ACTION_MOVE;
                break;

            case MotionEvent.ACTION_UP:
                if (lastAction == MotionEvent.ACTION_DOWN)
                    happinessvalue.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            happy = snapshot.child("Happy").getValue(Integer.class);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                if (happy==90){
                    View popup = getLayoutInflater().inflate(R.layout.quote,null);
                    PopupWindow popupWindow = new PopupWindow(getApplicationContext());
                    popupWindow.setOutsideTouchable(false);
                    popupWindow.setBackgroundDrawable(new ColorDrawable(Color.WHITE));
                    popupWindow.setContentView(popup);
                    popupWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
                    popupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
                    popupWindow.showAtLocation(view, Gravity.CENTER,0,0);
                    happinessvalue.child("Happy").setValue(0);
                    ImageView closeBtn = popup.findViewById(R.id.quote_close);
                    closeBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            view.startAnimation(buttonClick);
                            popupWindow.dismiss();
                        }
                    });
                }
                else{
                    happy += 10;
                    happinessvalue.child("Happy").setValue(happy);
                    Toast.makeText(getApplicationContext(), "+10 Happiness Value", Toast.LENGTH_SHORT).show();
                }
                break;

            default:
                return false;
        }
        return true;
    }


    long mExitTime;
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK){
            if((System.currentTimeMillis()-mExitTime) >2000){
                Object mHelperUtils;
                Toast.makeText(this, "Click one more time to exit the app", Toast.LENGTH_SHORT).show();
                mExitTime = System.currentTimeMillis();
            }else{
                finish();
            }
            return  true;
        }
        return super.onKeyDown(keyCode, event);
    }


}
