package com.example.care2u;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.ImageButton;

import androidx.fragment.app.Fragment;

public class HomeFragment extends Fragment{

    private View root;
    private AlphaAnimation buttonClick = new AlphaAnimation(1F, 0.8F);

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_home, container, false);
        ImageButton health_record_btn = root.findViewById(R.id.health_record_button);
        ImageButton chat_btn = root.findViewById(R.id.chat_button);
        ImageButton meditation_btn = root.findViewById(R.id.meditation_button);
        ImageButton e_wallet_btn = root.findViewById(R.id.my_wallet_button);
        ImageButton marketplace_btn = root.findViewById(R.id.marketplace_button);
        ImageButton timetable_btn = root.findViewById(R.id.timetable_button);
        timetable_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.startAnimation(buttonClick);
                Intent intent = new Intent(getActivity(), TimetableActivity.class);
                startActivity(intent);
            }
        });
        health_record_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.startAnimation(buttonClick);
                Intent intent = new Intent(getActivity(), HealthRecordActivity.class);
                startActivity(intent);
            }
        });
        e_wallet_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.startAnimation(buttonClick);
                Intent intent = new Intent(getActivity(),EWalletActivity.class);
                startActivity(intent);
            }
        });
        meditation_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.startAnimation(buttonClick);
                Intent intent = new Intent(getActivity(),MeditationActivity.class);
                startActivity(intent);
            }
        });
        marketplace_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.startAnimation(buttonClick);
                Intent intent = new Intent(getActivity(),MarketplaceDetailsActivity.class);
                startActivity(intent);
            }
        });

        return root;
    }
}
