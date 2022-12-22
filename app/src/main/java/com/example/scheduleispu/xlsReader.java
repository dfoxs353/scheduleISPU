package com.example.scheduleispu;

import android.content.res.AssetManager;
import android.util.Log;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

import java.io.InputStream;
import java.util.ArrayList;

public class xlsReader {
    AssetManager assetManager;
    InputStream is;
    HSSFWorkbook workbook;
    HSSFSheet sheet;

    public xlsReader(AssetManager assetManager) {
        this.assetManager = assetManager;
        init();
    }

    private void init(){
        try {
            is = assetManager.open("Rasp.xls");
            POIFSFileSystem myFileSystem = new POIFSFileSystem(is);

            workbook = new HSSFWorkbook(myFileSystem);
        }catch (Exception e){
            return;
        }
    }

    private String getxlsValue(int faculty, int i,int j){
        try {

            sheet = workbook.getSheetAt(faculty);

            HSSFRow row = sheet.getRow(i);
            HSSFCell cell = row.getCell(j);
            return cell.toString();
        }catch (Exception e){
            return e.toString();
        }
    }

    public ArrayList<String> getFaculty(){
        try {
           ArrayList<String> faculty = new ArrayList<String>();
            for(int i=0;i<workbook.getNumberOfSheets();i++){
                faculty.add(workbook.getSheetAt(i).getSheetName());
            }
            return faculty;
        }catch (Exception e){
            return  null;
        }
    }

    public ArrayList<String> getGroup(int faculty){
        try {
            ArrayList<String> groups = new ArrayList<String>();

            sheet = workbook.getSheetAt(faculty);

            for(int i=0;i<sheet.getRow(0).getPhysicalNumberOfCells();i++){
                groups.add(getxlsValue(faculty,0,i));
            }

            return groups;
        }catch (Exception e){
            return null;
        }
    }

    public ArrayList<String> loadRasp(boolean week,int day, int group, int faculty){
        try{
            ArrayList<String> lessons = new ArrayList<String>();


            for(int it=0 ;it<7;it++){
                if(week){
                    lessons.add(getxlsValue(faculty,it+day*7+1, group));
                }else {
                    lessons.add(getxlsValue(faculty,it+day*7+51, group));
                }
            }
            return lessons;
        }catch (Exception e){
            return null;
        }
    }
}
