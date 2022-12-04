package com.example.care2u;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.Activity;
import android.os.Bundle;

import com.example.care2u.databinding.ActivityMainBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        replaceFragment(new HomeFragment());

        binding.bottomNavView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()){
                case R.id.home_button:
                    replaceFragment(new HomeFragment());
                    break;
                case R.id.schedule_button:
                    replaceFragment(new ScheduleFragment());
                    break;
                case R.id.chat_button:
                    replaceFragment(new ChatFragment());
                    break;
                case R.id.notification_button:
                    replaceFragment(new NotificationFragment());
                    break;
                case R.id.profile_button:
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
}
