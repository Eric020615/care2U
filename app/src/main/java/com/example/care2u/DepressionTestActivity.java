package com.example.care2u;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.care2u.adapter.DepTestAdapter;

import java.util.ArrayList;

public class DepressionTestActivity extends AppCompatActivity {

    ArrayList<String> dep_test_question_list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_depression_test);
        RecyclerView dep_test_rv = findViewById(R.id.dep_test_rv);
        Button submit = findViewById(R.id.submit_btn);


        ImageView close_iv = findViewById(R.id.close_iv);
        close_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        initPage();
        DepTestAdapter depTestAdapter = new DepTestAdapter(this,dep_test_question_list);
        dep_test_rv.setAdapter(depTestAdapter);
        dep_test_rv.setLayoutManager(new LinearLayoutManager(this));

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<Double> result = depTestAdapter.getTotalMark();
                double total = 0;
                for (double i: result) {
                    total = total + i;
                }
                View popup = getLayoutInflater().inflate(R.layout.pop_window_test_score,null);
                Button ok_btn = popup.findViewById(R.id.ok_btn);
                TextView score_tv = popup.findViewById(R.id.score_tv);
                TextView level_tv = popup.findViewById(R.id.level_tv);
                score_tv.setText(String.valueOf(total));
                level_tv.setText(String.valueOf(determineLevel(total)));

                PopupWindow popupWindow = new PopupWindow(getApplicationContext());
                popupWindow.setOutsideTouchable(false);
                popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                popupWindow.setContentView(popup);
                popupWindow.setWidth(1000);
                popupWindow.setHeight(1000);
                popupWindow.showAtLocation(view, Gravity.CENTER,0,0);
                ok_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        popupWindow.dismiss();
                    }
                });
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        DepTestAdapter.clearResult();
    }

    private void initPage() {
        dep_test_question_list.add("I have been feeling depressed most of the time.");
        dep_test_question_list.add("I have lost pleasure in things I used to enjoy.");
        dep_test_question_list.add("I have been feeling tired and have had little energy.");
        dep_test_question_list.add("I have been having trouble sleeping.");
        dep_test_question_list.add("I have been feeling anxious or worried");
        dep_test_question_list.add("I have been having trouble concentrating.");
        dep_test_question_list.add("I have been feeling that I am a failure or that I have let myself or my family down.");
        dep_test_question_list.add("I have been feeling that everything is an effort.");
        dep_test_question_list.add("I have been feeling that life is not worth living.");
        dep_test_question_list.add("I have been having thoughts of hurting myself.");

    }

    public String determineLevel(double score){
        String level ="";
        if (score >= 36){
            level = "Severe";
        }else if(score >=26){
            level = "Moderate";
        }else if(score >=15){
            level = "Mild";
        }else
            level = "Healthy";

        return level;

    }






}