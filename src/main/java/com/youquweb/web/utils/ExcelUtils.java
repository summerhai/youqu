package com.youquweb.web.utils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

//import org.json.JSONArray;
//import org.json.JSONObject;


/**
 * Created by Administrator on 2017/8/19 0019.
 */
public class ExcelUtils {
    public static void main(String[] args) throws IOException {
        String correctPath = "C:\\Users\\hanlaiming\\Desktop\\funitem.xlsx";
        String errorPath = "abc";
        String nullPtah = null;
        String emptyPath = "";
//        Assert.assertSame(null,ExcelUtils.excelToJSON(nullPtah));
//        Assert.assertSame(null,ExcelUtils.excelToJSON(errorPath));
//        Assert.assertSame(null,ExcelUtils.excelToJSON(emptyPath));
        JSONArray result = excelToJSON(correctPath);
        System.out.println(result);
    }


    public static JSONArray excelToJSON(String path) throws IOException {

        File file = new File(path);
        InputStream is = new FileInputStream(file);
        Workbook workbook = null;
        JSONArray jsonArray = new JSONArray();
        try {
            if (path.endsWith("xlsx")) {
                workbook = new XSSFWorkbook(is);
            } else if (path.endsWith("xls")) {
                workbook = new HSSFWorkbook(is);
            } else {
                throw new IllegalArgumentException("The file is not Excel file");
            }

            int numbOfSheets = workbook.getNumberOfSheets();

            for (int numSheet = 0; numSheet < numbOfSheets; numSheet++) {
                Sheet sheet= workbook.getSheetAt(numSheet);
                if (sheet == null) {
                    continue;
                }
                int rowStart = sheet.getFirstRowNum();
                int rowEnd = sheet.getLastRowNum();
                Row firstRow = sheet.getRow(rowStart);//第一行
                int cellStart = firstRow.getFirstCellNum();//第一列
                int cellEnd = firstRow.getLastCellNum();//最后一列
                for (int i = 0; i < rowEnd; i++) {//行 每一行是一个对象
                    JSONObject jsonObject = new JSONObject();
                    Row eachRow = sheet.getRow(i+1);
                    Cell key = null,value = null;
                    for (int j = cellStart; j < cellEnd; j++) {//列
                        key = firstRow.getCell(j);
                        value = eachRow.getCell(j);
//                        System.out.println("key:"+key+",value:"+value);
                        if (value == null) {
                            jsonObject.put(key.toString(),"无");
                        } else {
                            jsonObject.put(key.toString(),value.toString().replace("\n","<br>"));
                        }

                    }
//                    jsonArray.put(jsonObject);
                    jsonArray.add(jsonObject);
                }

            }
            workbook.close();
            is.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return jsonArray;

    }

}

