package com.example.scheduleispu.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.scheduleispu.R;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class LessAdapter extends RecyclerView.Adapter<LessAdapter.LessViewHolder> {
    LayoutInflater inflater;

    private String current_day;
    private String current_week;

    Context context;

    AlertDialog.Builder builder;

    ArrayList<String> list_time =  new ArrayList<String>();
    ArrayList<String> less =  new ArrayList<String>();



    public LessAdapter(Context context) {
        this.context = context;
        builder = new AlertDialog.Builder(context);
        list_time.add("8:00");
        list_time.add("9:50");
        list_time.add("11:40");
        list_time.add("14:00");
        list_time.add("15:50");
        list_time.add("17:40");
        list_time.add("19:25");
    }

    public String getCurrent_day() {
        return current_day;
    }

    public void setCurrent_day(String current_day) {
        this.current_day = current_day;
    }

    public String getCurrent_week() {
        return current_week;
    }

    public void setCurrent_week(String current_week) {
        this.current_week = current_week;
    }

    public void setList_time(ArrayList<String> list_time) {
        this.list_time = list_time;
    }

    public void setLess(ArrayList<String> less) {
        this.less = less;
    }

    private void changeNote(int position, View view){
        View cl = LayoutInflater.from(view.getRootView().getContext()).inflate(R.layout.note_layout, null);
        builder.setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.setPositiveButton("Добавить", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.setNeutralButton("Удалить", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        TextView t_day = cl.findViewById(R.id.t_day);
        TextView t_less = cl.findViewById(R.id.t_less);
        TextView t_week = cl.findViewById(R.id.t_week);
        t_day.setText(current_day);
        t_week.setText(current_week);
        t_less.setText(list_time.get(position));
        builder.setView(cl);
        builder.show();
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

            holder.constraintLayout.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    changeNote(holder.getAdapterPosition(), view);
                    return true;
                }
            });

            holder.lesson.setText(lesson);
            holder.time.setText(time);
    }

    @Override
    public int getItemCount() {
        return less.size();
    }

    public class LessViewHolder extends RecyclerView.ViewHolder{
        TextView time,lesson;
        ConstraintLayout constraintLayout;
        public LessViewHolder(@NonNull View itemView) {
            super(itemView);
            constraintLayout = itemView.findViewById(R.id.constraintLayout);
            time = itemView.findViewById(R.id.t_time);
            lesson = itemView.findViewById(R.id.t_lesson);
        }
    }
}

