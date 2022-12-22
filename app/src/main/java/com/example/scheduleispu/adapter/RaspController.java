package com.example.scheduleispu.adapter;


import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class RaspController {
    Spinner sp_ft;
    ArrayAdapter<String> ad_ft;

    Spinner sp_gr;
    ArrayAdapter<String> ad_gr;

    int current_day;
    int current_group;
    int current_faculty;


    public RaspController() {
        this.current_day =0 ;
        this.current_group = 0;
        this.current_faculty = 0;

    }
}
