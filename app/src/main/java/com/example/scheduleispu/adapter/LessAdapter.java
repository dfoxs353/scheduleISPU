package com.example.scheduleispu.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.scheduleispu.R;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class LessAdapter extends RecyclerView.Adapter<LessAdapter.LessViewHolder> {
    LayoutInflater inflater;
    ArrayList<String> list_time =  new ArrayList<String>();
    ArrayList<String> less =  new ArrayList<String>();

    public LessAdapter() {
        list_time.add("8:00");
        list_time.add("9:50");
        list_time.add("11:40");
        list_time.add("14:00");
        list_time.add("15:50");
        list_time.add("17:40");
        list_time.add("19:25");
    }

    public void setList_time(ArrayList<String> list_time) {
        this.list_time = list_time;
    }

    public void setLess(ArrayList<String> less) {
        this.less = less;
    }

    @NonNull
    @Override
    public LessViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.from(parent.getContext()).inflate(R.layout.lesson_layout,parent,false);
        return new LessViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull LessViewHolder holder, int position) {
            String lesson = less.get(position);
            String time = list_time.get(position);

            if(lesson.contains("лек.")){
                holder.time.setBackgroundResource(R.drawable.clips_orange);
            }else if(lesson.contains("сем.")){
                holder.time.setBackgroundResource(R.drawable.clips_blue);
            }else if(lesson == ""){
                holder.time.setBackgroundResource(R.drawable.clips_white);
            }
            else {
                holder.time.setBackgroundResource(R.drawable.clips_red);
            }

            holder.lesson.setText(lesson);
            holder.time.setText(time);
    }

    @Override
    public int getItemCount() {
        return less.size();
    }

    public class LessViewHolder extends RecyclerView.ViewHolder{
        TextView time,lesson;
        public LessViewHolder(@NonNull View itemView) {
            super(itemView);
            time = itemView.findViewById(R.id.t_time);
            lesson = itemView.findViewById(R.id.t_lesson);
        }
    }
}

