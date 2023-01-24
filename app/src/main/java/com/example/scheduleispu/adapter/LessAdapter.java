package com.example.scheduleispu.adapter;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.database.Cursor;
import android.content.Context;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.scheduleispu.DBHelper;
import com.example.scheduleispu.R;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class LessAdapter extends RecyclerView.Adapter<LessAdapter.LessViewHolder> {
    LayoutInflater inflater;

    private int current_day;
    private int current_week;

    Context context;

    AlertDialog.Builder builder;

    ArrayList<String> list_time =  new ArrayList<String>();
    ArrayList<String> list_day =  new ArrayList<String>();
    ArrayList<String> list_week =  new ArrayList<String>();
    ArrayList<String> less =  new ArrayList<String>();

    public DBHelper dbHelper;


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
        list_day.add("ПН");
        list_day.add("ВТ");
        list_day.add("СР");
        list_day.add("ЧТ");
        list_day.add("ПТ");
        list_day.add("СБ");
        list_day.add("ВС");
        list_week.add("Первая неделя");
        list_week.add("Вторая неделя");
        dbHelper = new DBHelper(context);
    }

    public int getCurrent_day() {
        return current_day;
    }

    public void setCurrent_day(int current_day) {
        this.current_day = current_day;
    }

    public int getCurrent_week() {
        return current_week;
    }

    public void setCurrent_week(int current_week) {
        this.current_week = current_week;
    }

    public void setList_time(ArrayList<String> list_time) {
        this.list_time = list_time;
    }

    public void setLess(ArrayList<String> less) {
        this.less = less;
    }

    private void changeNote(int position, View view){
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        View cl = LayoutInflater.from(view.getRootView().getContext()).inflate(R.layout.note_layout, null);

        TextView t_day = cl.findViewById(R.id.t_day);
        TextView t_less = cl.findViewById(R.id.t_less);
        TextView t_week = cl.findViewById(R.id.t_week);
        EditText et_note = cl.findViewById(R.id.editNote);
        t_day.setText(list_day.get(current_day));
        t_week.setText(list_week.get(current_week));
        t_less.setText(list_time.get(position));
        builder.setView(cl);


        builder.setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.setPositiveButton("Добавить", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                database.delete(DBHelper.TABLE_NOTES, DBHelper.KEY_WEEK + " = " + current_week + " and " + DBHelper.KEY_DAY + " = " + current_day + " and " + DBHelper.KEY_TIME + " = " + position,null);
                contentValues.put(DBHelper.KEY_NOTE, et_note.getText().toString());
                contentValues.put(DBHelper.KEY_WEEK, current_week);
                contentValues.put(DBHelper.KEY_DAY, current_day);
                contentValues.put(DBHelper.KEY_TIME, position);
                database.insert(DBHelper.TABLE_NOTES, null, contentValues);
                notifyDataSetChanged();
                dbHelper.close();

            }
        });
        builder.setNeutralButton("Удалить", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                //Cursor cursor = database.query(DBHelper.TABLE_NOTES, null, null, null, null, null, null);
                database.delete(DBHelper.TABLE_NOTES, DBHelper.KEY_WEEK + " = " + current_week + " and " + DBHelper.KEY_DAY + " = " + current_day + " and " + DBHelper.KEY_TIME + " = " + position,null);
                notifyDataSetChanged();
                dbHelper.close();

            }
        });


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

            SQLiteDatabase database = dbHelper.getWritableDatabase();
            Cursor cursor = database.rawQuery("select * from " + DBHelper.TABLE_NOTES + " where " + DBHelper.KEY_WEEK + "=" + current_week + " and " + DBHelper.KEY_DAY + "=" + current_day + " and " + DBHelper.KEY_TIME + "=" + position,null);
            if (cursor.moveToFirst()) {
                int idIndex = cursor.getColumnIndex(DBHelper.KEY_ID);
                int noteIndex = cursor.getColumnIndex(DBHelper.KEY_NOTE);
                int weekIndex = cursor.getColumnIndex(DBHelper.KEY_WEEK);
                int dayIndex = cursor.getColumnIndex(DBHelper.KEY_DAY);
                int timeIndex = cursor.getColumnIndex(DBHelper.KEY_TIME);
                do {
                    holder.note.setText(cursor.getString(noteIndex));

                } while (cursor.moveToNext());
            } else{
                holder.note.setText("");

            }


            cursor.close();
            dbHelper.close();
            database.close();

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
        TextView time,lesson,note;
        ConstraintLayout constraintLayout;
        public LessViewHolder(@NonNull View itemView) {
            super(itemView);
            constraintLayout = itemView.findViewById(R.id.constraintLayout);
            time = itemView.findViewById(R.id.t_time);
            lesson = itemView.findViewById(R.id.t_lesson);
            note = itemView.findViewById(R.id.t_note);
        }
    }
}

