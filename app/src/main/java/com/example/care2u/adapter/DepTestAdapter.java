package com.example.care2u.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.care2u.R;

import java.util.ArrayList;
import java.util.List;

public class DepTestAdapter extends RecyclerView.Adapter<DepTestAdapter.MyViewHolder>{

    private Context context;
    private static List<String> questionList;
    static ArrayList<Double> totalMark = new ArrayList<>();


    public DepTestAdapter(Context context,List<String> questionList){
        this.context=context;
        this.questionList = questionList;
        for (int i = 0; i < questionList.size(); i++) {
            totalMark.add(0.0);
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_depression_test,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder,int position) {
        String question = questionList.get(position);
        if(position%2 == 0 ){
            holder.relativeLayout.setBackgroundResource(R.color.test);
        }
        holder.question.setText(question);
        holder.radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i){
                    case R.id.mark1:
                        totalMark.set(position,1.0);
                        break;
                    case R.id.mark2:
                        totalMark.set(position,2.0);
                        break;
                    case R.id.mark3:
                        totalMark.set(position,3.0);
                        break;
                    case R.id.mark4:
                        totalMark.set(position,4.0);
                        break;
                    case R.id.mark5:
                        totalMark.set(position,5.0);
                        break;
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return questionList.size();
    }

    public ArrayList<Double> getTotalMark(){
        return totalMark;
    }

    public static void clearResult() {
        for (int i = 0; i < questionList.size(); i++) {
            totalMark.set(i,0.0);
        }
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView question;
        private RadioGroup radioGroup;
        private RelativeLayout relativeLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            question=itemView.findViewById(R.id.question_tv);
            radioGroup=itemView.findViewById(R.id.dep_test_rg);
            relativeLayout=itemView.findViewById(R.id.test_ly);

        }


    }
}
