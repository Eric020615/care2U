package com.example.care2u;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.example.care2u.databinding.ActivityMainBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity implements View.OnTouchListener {
    ActivityMainBinding binding;
    FirebaseAuth mAuth;
    FirebaseUser user;
    float dX;
    float dY;
    int lastAction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.fab.setOnTouchListener(this);

        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"Clicked",Toast.LENGTH_SHORT).show();
            }
        });

        setSupportActionBar(findViewById(R.id.tool_app));
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#F9F9F9")));
        getSupportActionBar().setTitle(Html.fromHtml("<font color='000000'>Welcome Back!</font>"));
        replaceFragment(new HomeFragment());

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
                    Intent intent=new Intent(getApplicationContext(),ChatActivity.class);
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
                    Toast.makeText(MainActivity.this, "Clicked!", Toast.LENGTH_SHORT).show();
                break;

            default:
                return false;
        }
        return true;
    }
}

