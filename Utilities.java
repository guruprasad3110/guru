package org.com.base;

import java.io.File;
import java.io.FileInputStream;

import java.io.IOException;

import java.util.Properties;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


public  class Utilities  {
public 	String ActualValue;

	public String GetExpectedValue (String ColumnName) throws IOException {
		int colno=0; 
		int rowno=0;
		String names = Thread.currentThread().getStackTrace()[2].getMethodName();
	
	Properties	properties=new Properties();
		FileInputStream config=new FileInputStream(new File(System.getProperty("user.dir")+"/src/main/java/org/com/config/config.properties"));
		properties.load(config);
	
			FileInputStream data=new FileInputStream(new File(System.getProperty("user.dir")+properties.getProperty("TestDataPath")));
		
			
			XSSFWorkbook excel=new XSSFWorkbook(data);
			XSSFSheet sheet	= excel.getSheet(properties.getProperty("Scenario"));
		int rowcount=sheet.getPhysicalNumberOfRows();
		
		for(int i=1;i<rowcount; i++){
			XSSFRow  rows=sheet.getRow(i);
			XSSFCell cells=rows.getCell(0);
			String value=cells.getStringCellValue();
			
			if(value.equals(names)) {
			 rowno=i;
			}}
		
		XSSFRow rowscols=sheet.getRow(0);
		for(int x=0;x<=rowscols.getLastCellNum();x++) {
			
			String vals=rowscols.getCell(x).getStringCellValue();
			if(vals.equals(ColumnName)) {
				
				 colno=x;
				 break;
				 
			}
		}
		
		//System.out.println(rowno +" " + colno);
		XSSFRow row=sheet.getRow(rowno);
		XSSFCell cells=row.getCell(colno);
		DataFormatter df = new DataFormatter();
		ActualValue = df.formatCellValue(cells);
	//	 ActualValue=cells.getStringCellValue();

	
			
		
		
		
		

	
		excel.close();
	
		
		return ActualValue;
		
	}
}