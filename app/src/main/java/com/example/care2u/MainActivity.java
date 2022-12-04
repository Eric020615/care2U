package com.example.care2u;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;

import com.example.care2u.databinding.ActivityMainBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(findViewById(R.id.tool_app));
        getSupportActionBar().setTitle("Home");
        replaceFragment(new HomeFragment());

        binding.toolApp.setOnMenuItemClickListener(item -> {
            switch(item.getItemId()){
                case R.id.logout_button:
                    Intent intent = new Intent(MainActivity.this,StartingActivity.class);
                    startActivity(intent);
                    break;
            }
            return true;
        });

        binding.bottomNavView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()){
                case R.id.home_button:
                    getSupportActionBar().setTitle("Home");
                    replaceFragment(new HomeFragment());
                    break;
                case R.id.schedule_button:
                    getSupportActionBar().setTitle("Schedule");
                    replaceFragment(new ScheduleFragment());
                    break;
                case R.id.chat_button:
                    getSupportActionBar().setTitle("Chat");
                    replaceFragment(new ChatFragment());
                    break;
                case R.id.notification_button:
                    getSupportActionBar().setTitle("Notification");
                    replaceFragment(new NotificationFragment());
                    break;
                case R.id.profile_button:
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
        getMenuInflater().inflate(R.menu.menu_top,menu);
        return true;
    }
}
