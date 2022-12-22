package com.example.scheduleispu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.os.Debug;
import android.util.Log;
import android.view.View;
import android.view.ViewDebug;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.scheduleispu.adapter.LessAdapter;
import com.example.scheduleispu.adapter.RaspController;


import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;


import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    SharedPreferences settings;

    RecyclerView lessRecyclerView;
    LessAdapter lessadapter;

    AssetManager assetManager;
    InputStream is;


    boolean start_up = true;
    boolean first_week = true;


    View current_button;

    Button[] buttonslist = new Button[7];
    Button b_first_wk,b_second_wk;

    ArrayList<String> lessons;
    ArrayList<String> groups;
    ArrayList<String> faculty;

    xlsReader xlsReader;

    Spinner sp_ft;
    ArrayAdapter<String> ad_ft;

    Spinner sp_gr;
    ArrayAdapter<String> ad_gr;

    int current_day;
    int current_group;
    int current_faculty;

    final String SAVED_FACULTY = "currentFaculty";
    final String SAVED_GROUP = "currentGroup";





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);




        initView();
        initButtons();
        settings = getPreferences(MODE_PRIVATE);

        current_button = findViewById(R.id.b_mnday);
        current_button.setBackgroundColor(getResources().getColor(R.color.purple_200));
        b_first_wk.setBackgroundColor(getResources().getColor(R.color.purple_200));


        assetManager = getAssets();
        xlsReader = new xlsReader(assetManager);


        initFacultySpiner();
        initGroupSpiner();

        initValues();


        updateConditon();
    }

    protected void onDestroy(){
        super.onDestroy();
        saveValues();
        finish();
    }

    private void initValues(){
        this.current_day =0 ;
        getVales();

    }

    private void saveValues(){
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(SAVED_GROUP, Integer.toString(current_group));
        editor.putString(SAVED_FACULTY, Integer.toString(current_faculty));
        editor.commit();
        Log.d("Save","Save1");
    }

    private  void getVales(){


        try{
            String str1,str2;
            str1 = settings.getString(SAVED_GROUP, "");
            str2 = settings.getString(SAVED_FACULTY, "");
            Log.d("Save",str1);
            Log.d("Save",str2);


            sp_ft.setSelection(Integer.parseInt(str2));
            sp_gr.setSelection(Integer.parseInt(str1));


            Log.d("Save","Get2");

        }catch (Exception e){
            current_group = 0;
            current_faculty = 0;
        }
    }

    private void initFacultySpiner() {
        faculty = xlsReader.getFaculty();


        sp_ft = findViewById(R.id.spinner_faculty);
        ad_ft = new ArrayAdapter(this, R.layout.my_selected_item, faculty);

        sp_ft.setAdapter(ad_ft);

        AdapterView.OnItemSelectedListener itemSelectedListener = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                current_faculty = i;
                groups = xlsReader.getGroup(current_faculty);
                ad_gr.clear();
                ad_gr.addAll(groups);
                ad_gr.notifyDataSetChanged();
                if(start_up){
                    start_up = false;
                }
                else {
                    sp_gr.setSelection(0);
                }
                updateConditon();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        };

        ad_ft.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_ft.setOnItemSelectedListener(itemSelectedListener);
    }



    private void initGroupSpiner(){
        groups = xlsReader.getGroup(current_faculty);
        sp_gr = findViewById(R.id.spinner_group);
        ad_gr = new ArrayAdapter(this, R.layout.my_selected_item, groups);
        sp_gr.setAdapter(ad_gr);

        AdapterView.OnItemSelectedListener itemSelectedListener = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                current_group = i;
                updateConditon();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        };
        ad_gr.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_gr.setOnItemSelectedListener(itemSelectedListener);
    }


    private void updateConditon() {
        lessons = xlsReader.loadRasp(first_week,current_day,current_group,current_faculty);
        lessadapter.setLess(lessons);
        lessadapter.notifyDataSetChanged();
        saveValues();
    }



    private void initButtons() {
        b_first_wk = findViewById(R.id.b_fst_week);
        b_second_wk = findViewById(R.id.b_scn_week);


        buttonslist[0] = findViewById(R.id.b_mnday);
        buttonslist[1] = findViewById(R.id.b_tuday);
        buttonslist[2] = findViewById(R.id.b_wdday);
        buttonslist[3] = findViewById(R.id.b_thday);
        buttonslist[4] = findViewById(R.id.b_frday);
        buttonslist[5] = findViewById(R.id.b_stday);
        buttonslist[6] = findViewById(R.id.b_snday);

        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()){
                    case R.id.b_mnday:
                        current_day = 0;
                        current_button.setBackgroundColor(getResources().getColor(R.color.purple_500));
                        view.setBackgroundColor(getResources().getColor(R.color.purple_200));
                        current_button = view;
                        updateConditon();
                        break;
                    case R.id.b_tuday:
                        current_day = 1;
                        current_button.setBackgroundColor(getResources().getColor(R.color.purple_500));
                        view.setBackgroundColor(getResources().getColor(R.color.purple_200));
                        current_button = view;
                        updateConditon();
                        break;
                    case R.id.b_wdday:
                        current_day = 2;
                        current_button.setBackgroundColor(getResources().getColor(R.color.purple_500));
                        view.setBackgroundColor(getResources().getColor(R.color.purple_200));
                        current_button = view;
                        updateConditon();
                        break;
                    case R.id.b_thday:
                        current_day = 3;
                        current_button.setBackgroundColor(getResources().getColor(R.color.purple_500));
                        view.setBackgroundColor(getResources().getColor(R.color.purple_200));
                        current_button = view;
                        updateConditon();
                        break;
                    case R.id.b_frday:
                        current_day = 4;
                        current_button.setBackgroundColor(getResources().getColor(R.color.purple_500));
                        view.setBackgroundColor(getResources().getColor(R.color.purple_200));
                        current_button = view;
                        updateConditon();
                        break;
                    case R.id.b_stday:
                        current_day = 5;
                        current_button.setBackgroundColor(getResources().getColor(R.color.purple_500));
                        view.setBackgroundColor(getResources().getColor(R.color.purple_200));
                        current_button = view;
                        updateConditon();
                        break;
                    case R.id.b_snday:
                        current_day = 6;
                        current_button.setBackgroundColor(getResources().getColor(R.color.purple_500));
                        view.setBackgroundColor(getResources().getColor(R.color.purple_200));
                        current_button = view;
                        updateConditon();
                        break;
                    case R.id.b_fst_week:
                        first_week = true;
                        b_first_wk.setBackgroundColor(getResources().getColor(R.color.purple_200));
                        b_second_wk.setBackgroundColor(getResources().getColor(R.color.purple_500));
                        updateConditon();
                        break;
                    case R.id.b_scn_week:
                        first_week = false;
                        b_second_wk.setBackgroundColor(getResources().getColor(R.color.purple_200));
                        b_first_wk.setBackgroundColor(getResources().getColor(R.color.purple_500));
                        updateConditon();
                        break;
                };
            }
        };

        b_first_wk.setOnClickListener(onClickListener);
        b_second_wk.setOnClickListener(onClickListener);


        buttonslist[0].setOnClickListener(onClickListener);
        buttonslist[1].setOnClickListener(onClickListener);
        buttonslist[2].setOnClickListener(onClickListener);
        buttonslist[3].setOnClickListener(onClickListener);
        buttonslist[4].setOnClickListener(onClickListener);
        buttonslist[5].setOnClickListener(onClickListener);
        buttonslist[6].setOnClickListener(onClickListener);

    }

    private void initView() {
        lessRecyclerView = findViewById(R.id.less_recycler_view);
        lessRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        lessadapter = new LessAdapter();
        lessRecyclerView.setAdapter(lessadapter);
    }


}