package com.spider.kaixin.utils;

import java.io.File;
import java.io.FileInputStream;
import java.util.Iterator;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

import com.spider.kaixin.KaiXinLogin;

public class ReadExcel {
	
	public static void main(String[] args) throws Exception {
		String filename = System.getProperty("user.dir") + "/src/main/resources/kaixin/user.xls";
	    File file = new File(filename);
	    FileInputStream fint = new FileInputStream(file);
	    POIFSFileSystem poiFileSystem = new POIFSFileSystem(fint);
	    HSSFWorkbook workbook = new HSSFWorkbook(poiFileSystem);
	    HSSFSheet sheet = workbook.getSheetAt(0);
	    int size=sheet.getLastRowNum();
	    for(int i=0;i<=size;i++){
	    	 HSSFRow rowUser = sheet.getRow(i);
	    	 String userName=getCell(rowUser.getCell((short) 0));
	    	 String password=getCell(rowUser.getCell((short) 1));
	    	 KaiXinLogin.start(userName,password);
	    }
	  }
	
	public static String getCell(HSSFCell cell) {
        if (cell == null)
            return "";
        switch (cell.getCellType()) {
          case HSSFCell.CELL_TYPE_STRING:
              return cell.getStringCellValue();
          case HSSFCell.CELL_TYPE_FORMULA:
              return cell.getCellFormula();
          case HSSFCell.CELL_TYPE_BLANK:
              return "";
          case HSSFCell.CELL_TYPE_BOOLEAN:
              return cell.getBooleanCellValue() + "";
          case HSSFCell.CELL_TYPE_ERROR:
             return cell.getErrorCellValue() + "";
          case HSSFCell.CELL_TYPE_NUMERIC:
              return String.valueOf(new Double(cell.getNumericCellValue()).intValue()) ;
         }
         return "";
      }
}
