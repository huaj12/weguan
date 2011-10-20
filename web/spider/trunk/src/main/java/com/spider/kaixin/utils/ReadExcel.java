package com.spider.kaixin.utils;

import org.apache.poi.hssf.usermodel.HSSFCell;

public class ReadExcel {

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
			return String.valueOf(new Double(cell.getNumericCellValue())
					.intValue());
		}
		return "";
	}
}
