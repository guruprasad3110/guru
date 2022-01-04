package org.com.base;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/** Class to get expected values from excel sheet
 *  Sends expected value to Test case for comparing with Actual value
 * @author GBMoorthy
 *
 */
public  class Utilities  {
	
	//Declaring Global Variables for storing expected value
	
public 	boolean overtime=true;	
public 	String ExpectedValue;


/**Method to Get the Expected Value based on ColumnName mentioned in Test case
 * 
 * @param ColumnName
 * @return
 * @throws IOException
 */

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
		
		//Traversing through Excel Sheet to get Expected Column Value
		
		for(int i=1;i<rowcount; i++){
			XSSFRow  rows=sheet.getRow(i);
			XSSFCell cells=rows.getCell(0);
			String value=cells.getStringCellValue();
			
			if(value.equals(names)) {
			 rowno=i;
			}}
		
		XSSFRow rowscols=sheet.getRow(0);
		
	//Identifying the column name given in Test case
		
		for(int x=0;x<=rowscols.getLastCellNum();x++) {
			
			String vals=rowscols.getCell(x).getStringCellValue();
			if(vals.equals(ColumnName)) {
				
				 colno=x;
				 break;
				 
			}
		}
		

		XSSFRow row=sheet.getRow(rowno);
		XSSFCell cells=row.getCell(colno);
		DataFormatter df = new DataFormatter();
		ExpectedValue = df.formatCellValue(cells);

// Excel workbook is closed after getting expected values
	
		excel.close();
	
		
		return ExpectedValue;
		
	}
	
	/** Method to write value of Response Time in Excel
	 * 
	 * @param time
	 * @throws IOException
	 */
	public   void WriteResponseTime(long time) throws IOException {
		
		Properties	properties=new Properties();
		FileInputStream config=new FileInputStream(new File(System.getProperty("user.dir")+"/src/main/java/org/com/config/config.properties"));
		properties.load(config);
		
		File file = new File ((System.getProperty("user.dir")+properties.getProperty("ResponseTimeSheet")));
	
		FileInputStream	data=new FileInputStream(file);
			
			
			
			XSSFWorkbook excel=new XSSFWorkbook(data);
			XSSFSheet sheet	= excel.getSheet(properties.getProperty("Scenario"));
		int rowcount=sheet.getPhysicalNumberOfRows();
		
		String names = Thread.currentThread().getStackTrace()[2].getMethodName();
	
		XSSFRow row=sheet.createRow(rowcount);
		
		XSSFCell cellTime = row.createCell(1);
		XSSFCell cellTest=row.createCell(0);
		XSSFCell cellDate=row.createCell(2);
		
		//Verify the response time is greater than requirement
		
		if(time>600) {
			
			overtime=false;
		}
	//String responseTime=Long.toString(time);
	
	SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss aa");
	Date date = new Date(System.currentTimeMillis());
	
	
	
	cellTime.setCellValue(time);
	cellTest.setCellValue(names);
	cellDate.setCellValue(formatter.format(date));
	
	  CellStyle style = excel.createCellStyle();  
      // Setting Background color  
      style.setFillBackgroundColor(IndexedColors.RED.getIndex()); 
      style.setFillPattern(FillPatternType.LEAST_DOTS);
     
	
	//Highlighting the cell of response time in red if it is greater than time mentioned in requirement
      
	if(overtime==false) {
		
		cellTime.setCellStyle(style);
	}
	 
	
	
		FileOutputStream fout = new FileOutputStream(file);
		excel.write(fout);
		
		fout.close();
		excel.close();
		
		
		
	}
	
}