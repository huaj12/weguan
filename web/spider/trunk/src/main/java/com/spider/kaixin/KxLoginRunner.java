package com.spider.kaixin;

import java.io.File;
import java.io.FileInputStream;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

import com.spider.kaixin.service.KaiXinLogin;
import com.spider.kaixin.utils.ReadExcel;

public class KxLoginRunner {
	public static void main(String[] args) throws Exception {
		String filename = System.getProperty("user.dir")
				+ "/src/main/resources/kaixin/user.xls";
		File file = new File(filename);
		FileInputStream fint = new FileInputStream(file);
		POIFSFileSystem poiFileSystem = new POIFSFileSystem(fint);
		HSSFWorkbook workbook = new HSSFWorkbook(poiFileSystem);
		HSSFSheet sheet = workbook.getSheetAt(0);
		int size = sheet.getLastRowNum();
		for (int i = 0; i <= size; i++) {
			HSSFRow rowUser = sheet.getRow(i);
			String userName = ReadExcel.getCell(rowUser.getCell((short) 0));
			String password = ReadExcel.getCell(rowUser.getCell((short) 1));
			KaiXinLogin.start(userName, password);
		}
	}
}
