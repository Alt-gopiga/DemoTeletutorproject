package com.Inteletutor.utils;

import java.io.File;
import java.io.FileInputStream;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.annotations.DataProvider;

import com.Inteletutor.wrappers.BaseClassWrappers;

public class DataInputProvider extends BaseClassWrappers{

	/*public String dataSheetName;

	@DataProvider(name="fetchData")
	public Object[][] getExcel(){
		return getSheet(dataSheetName);
	}*/

	public static Object[][] getSheet(String sheetName){

		Object[][] data=null;

		try {
			 FileInputStream fis=new FileInputStream(new File("./data/"+sheetName+".xlsx"));
			 XSSFWorkbook workbook=new XSSFWorkbook(fis);
			 XSSFSheet sheet=workbook.getSheetAt(0);

			 // get the number of rows
			 int rowCount=sheet.getLastRowNum();

			 // get the number of columns
			 int columnCount=sheet.getRow(0).getLastCellNum();

			 data=new String[rowCount][columnCount];

			      for(int i=1;i<rowCount+1;i++){
			    	  	XSSFRow row=sheet.getRow(i);

			    	  	for(int j=0;j<columnCount;j++){
			    	  		String cellValue=row.getCell(j).getStringCellValue();
			    	  		data[i-1][j]=cellValue;// add to data array[0][0]
			    	  	}

			      }
			 fis.close();
			 workbook.close();

		} catch (Exception e) {

		}

		return data;
	}
}
