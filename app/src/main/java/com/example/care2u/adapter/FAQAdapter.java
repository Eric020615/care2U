package com.example.care2u.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.care2u.R;
import com.example.care2u.entity.FAQ;

import java.util.List;

public class FAQAdapter extends BaseAdapter {

    private Context context;
    private List<FAQ> faqInfoList;

    public FAQAdapter(Context context, List<FAQ> faqInfoList) {
        this.context = context;
        this.faqInfoList = faqInfoList;
    }

    @Override
    public int getCount() {
        return faqInfoList.size();
    }

    @Override
    public Object getItem(int i) {
        return faqInfoList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        FAQAdapter.ViewHolder holder;
        if(view == null){
            holder = new FAQAdapter.ViewHolder();
            view = LayoutInflater.from(context).inflate(R.layout.item_faq,null);
            holder.question = view.findViewById(R.id.question_tv);
            holder.answer= view.findViewById(R.id.answer_tv);
            holder.show_ans= view.findViewById(R.id.show_ans_IV);
            view.setTag(holder);
        }else{
            holder = (FAQAdapter.ViewHolder) view.getTag();
        }
        FAQ faqInfo = faqInfoList.get(i);
        holder.question.setText(faqInfo.getQuestion());
        holder.answer.setText(faqInfo.getAnswer());
        holder.question.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (holder.answer.getVisibility()==View.VISIBLE)
                    holder.answer.setVisibility(View.GONE);
                else
                    holder.answer.setVisibility(View.VISIBLE);
            }
        });

        holder.show_ans.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (holder.answer.getVisibility()==View.VISIBLE)
                    holder.answer.setVisibility(View.GONE);
                else
                    holder.answer.setVisibility(View.VISIBLE);
            }
        });
        return view;
    }

    public final class ViewHolder{

        public TextView question;
        public TextView answer;
        public ImageView show_ans;
    }

}
