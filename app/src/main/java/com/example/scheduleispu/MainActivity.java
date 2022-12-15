package com.example.scheduleispu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.res.AssetManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.scheduleispu.adapter.LessAdapter;
import com.example.scheduleispu.buttons.ButtonDay;


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

    RecyclerView lessRecyclerView;
    LessAdapter lessadapter;

    AssetManager assetManager;
    InputStream is;
    HSSFWorkbook workbook;
    HSSFSheet sheet;

    boolean first_week = true;
    ButtonDay[] buttonslist = new ButtonDay[7];
    ArrayList<String> lessons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        initView();
        initButtons();
        updateConditon();
    }

    private void updateConditon() {
        TextView textView = findViewById(R.id.text_condition_value);
        for (int i=0;i < buttonslist.length; i++) {
            if(buttonslist[i].isActive()){
                String str = buttonslist[i].getButton().getText().toString();
                textView.setText(getxlsValue(0,0));
                loadRasp(0,i);
                break;
            }
        }
    }

    private void loadRasp(int i, int j){
        try{
            lessons = new ArrayList<String>();


            for(int it=0 ;it<7;it++){
                if(first_week){
                    lessons.add(getxlsValue(it+j*7+1, i));
                    Log.d("xls", getxlsValue(i,it+j*7));
                }else {
                    lessons.add(getxlsValue(it+j*7+51, i));
                }
            }
            lessadapter.setLess(lessons);
        }catch (Exception e){

        }
    }

    private String getxlsValue(int i, int j){
        try {
            assetManager = getAssets();
            is = assetManager.open("Rasp.xls");
            POIFSFileSystem myFileSystem = new POIFSFileSystem(is);

            workbook = new HSSFWorkbook(myFileSystem);

            sheet = workbook.getSheetAt(0);

            HSSFRow row = sheet.getRow(i);
            HSSFCell cell = row.getCell(j);
            return cell.toString();
        }catch (Exception e){
            return e.toString();
        }
    }

    private void initButtons() {
        buttonslist[0] = new ButtonDay(findViewById(R.id.b_mnday),true);
        buttonslist[1] = new ButtonDay(findViewById(R.id.b_thday),false);
        buttonslist[2] = new ButtonDay(findViewById(R.id.b_wdday),false);
        buttonslist[3] = new ButtonDay(findViewById(R.id.b_thday),false);
        buttonslist[4] = new ButtonDay(findViewById(R.id.b_frday),false);
        buttonslist[5] = new ButtonDay(findViewById(R.id.b_stday),false);
        buttonslist[6] = new ButtonDay(findViewById(R.id.b_snday),false);

    }

    private void initView() {
        lessRecyclerView = findViewById(R.id.less_recycler_view);
        lessRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        lessadapter = new LessAdapter();
        lessRecyclerView.setAdapter(lessadapter);
    }


}