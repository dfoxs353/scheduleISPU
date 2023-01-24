package com.example.scheduleispu;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "noteDB";
    public static final String TABLE_NOTES = "notes";

    public static final String KEY_ID = "_id";
    public static final String KEY_NOTE = "note";
    public static final String KEY_WEEK = "week";
    public static final String KEY_DAY = "day";
    public static final String KEY_TIME = "time";

    public DBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table " + TABLE_NOTES + "(" + KEY_ID
                + " integer primary key, " + KEY_NOTE + " text, " + KEY_WEEK + " integer, " + KEY_DAY + " integer, " + KEY_TIME + " integer" +")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("drop table if exists " + TABLE_NOTES);

        onCreate(sqLiteDatabase);
    }
}
