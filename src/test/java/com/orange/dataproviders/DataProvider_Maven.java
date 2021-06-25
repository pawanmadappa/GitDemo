package com.orange.dataproviders;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class DataProvider_Maven {
	
	@org.testng.annotations.DataProvider(name ="dataFromExcel")
	public static Object[][] dataFromExcel() throws Exception {
		File f = new File("./data/testdata_orange.xlsx");
		FileInputStream fis = new FileInputStream(f);
		XSSFWorkbook wb = new XSSFWorkbook(fis);
		XSSFSheet ws = wb.getSheet("invalidData");
		int rows = ws.getLastRowNum();
		Object[][] obj = new String[rows+1][2];
		for(int i=0;i<=rows;i++) {
			String userName = ws.getRow(i).getCell(0).getStringCellValue();
			String password = ws.getRow(i).getCell(1).getStringCellValue();
	
			obj[i][0] = userName;
			obj[i][1] = password;
		}
		return obj;
	}

}
