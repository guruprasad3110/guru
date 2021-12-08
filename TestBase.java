package org.com.base;

import java.io.File;
import java.io.FileInputStream;
//import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
//import org.testng.annotations.BeforeTest;

//import com.relevantcodes.extentreports.ExtentTest;



public class TestBase    {

	private static Properties properties;
public	Object name;
	
public Utilities utils;



private static List<String> testcase=new ArrayList<String>();

public static List<String> testnames()  {
	try {
		
	properties=new Properties();

	FileInputStream config=new FileInputStream(new File(System.getProperty("user.dir")+"/src/main/java/org/com/config/config.properties"));
	properties.load(config);
	
		FileInputStream data=new FileInputStream(new File(System.getProperty("user.dir")+properties.getProperty("TestDataPath")));
		
		XSSFWorkbook excel=new XSSFWorkbook(data);
		XSSFSheet sheet	= excel.getSheet(properties.getProperty("Scenario"));
	int rowcount=sheet.getPhysicalNumberOfRows();
	if(testcase.size()>0) {
		testcase.clear();
	}
	for(int i=1;i<rowcount; i++){
	XSSFRow  rows=sheet.getRow(i);
	XSSFCell cells=rows.getCell(1);
	String value=cells.getStringCellValue();

	if(value.equals("Yes") ) {

		//XSSFCell cellmethod=rows.getCell(2);
	//String methodcell=cellmethod.getStringCellValue();
	
	XSSFCell cellmethods=rows.getCell(0);
	String methodcells=cellmethods.getStringCellValue();

testcase.add(methodcells);
	}
	}
	excel.close();
	}
	
	catch(IOException io) {
		
		
	}
	return testcase;
}




	
		

	
	


	
	


	public Object  GetActualResult() throws IOException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, ClassNotFoundException, InstantiationException {
		String names = Thread.currentThread().getStackTrace()[2].getMethodName();
		/* StackTraceElement[] ste = Thread.currentThread().getStackTrace();
		
String names=ste[ste.length-10].getMethodName();*/
	System.out.println(names);
		properties=new Properties();

		FileInputStream config=new FileInputStream(new File(System.getProperty("user.dir")+"/src/main/java/org/com/config/config.properties"));
		properties.load(config);
		
			FileInputStream data=new FileInputStream(new File(System.getProperty("user.dir")+properties.getProperty("TestDataPath")));
			
			XSSFWorkbook excel=new XSSFWorkbook(data);
			XSSFSheet sheet	= excel.getSheet(properties.getProperty("Scenario"));
		int rowcount=sheet.getPhysicalNumberOfRows();

	/*	for(int i=1;i<rowcount; i++){
		XSSFRow  rows=sheet.getRow(i);
		XSSFCell cells=rows.getCell(1);
		String value=cells.getStringCellValue();

		if(value.equals("Yes") ) {
	
			XSSFCell cellmethod=rows.getCell(2);
		String methodcell=cellmethod.getStringCellValue();
		
		XSSFCell cellmethods=rows.getCell(0);
		String methodcells=cellmethods.getStringCellValue();
	//testcase.add(methodcells);
		XSSFCell urlcell=rows.getCell(3);
		String url= urlcell.getStringCellValue();
		String ClassName = "org.com.restclient.RestAPICall";
        Class<?> Classes = Class.forName(ClassName); // convert string classname to class
      
	Object MethodInvoke = Classes.newInstance(); 
		Method getNameMethod =MethodInvoke.getClass().getMethod(methodcell,String.class);
		//String names=getNameMethod.getName();
		
		 name = (Object) getNameMethod.invoke(MethodInvoke,url);
	utils	=new Utilities();
	
	   
		
		
	
		}
	
		}*/
		//String tc=testcase();
	
		for(int i=1;i<rowcount; i++){               //userget
			//for(String testcases:testnames()) {
			XSSFRow  rows=sheet.getRow(i);
			XSSFCell cells=rows.getCell(0);
			String value=cells.getStringCellValue();
			
			if(names.equals(value)) {
				XSSFCell cellmethod=rows.getCell(2);
				String methodcell=cellmethod.getStringCellValue();
				
			
			
				XSSFCell urlcell=rows.getCell(3);
				String url= urlcell.getStringCellValue();
				String ClassName = "org.com.restclient.RestAPICall";
		        Class<?> Classes = Class.forName(ClassName); // convert string classname to class
		      
			Object MethodInvoke = Classes.newInstance(); 
				Method getNameMethod =MethodInvoke.getClass().getMethod(methodcell,String.class);
				//String names=getNameMethod.getName();
				
				 name = (Object) getNameMethod.invoke(MethodInvoke,url);
			utils	=new Utilities();
			//stat=true;
			//break;
			}
			
			/*}
			if(stat=true) {
				break;
			}*/
			
		}
		
	
		excel.close();
		
		
		

		
			
	
		return name;	
		
	}
	
	public Object  GetActualResult(String dataname) throws IOException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, ClassNotFoundException, InstantiationException {
		String names = Thread.currentThread().getStackTrace()[2].getMethodName();
		/* StackTraceElement[] ste = Thread.currentThread().getStackTrace();
		
String names=ste[ste.length-10].getMethodName();*/
	System.out.println(names);
		properties=new Properties();

		FileInputStream config=new FileInputStream(new File(System.getProperty("user.dir")+"/src/main/java/org/com/config/config.properties"));
		properties.load(config);
		
			FileInputStream data=new FileInputStream(new File(System.getProperty("user.dir")+properties.getProperty("TestDataPath")));
			
			XSSFWorkbook excel=new XSSFWorkbook(data);
			XSSFSheet sheet	= excel.getSheet(properties.getProperty("Scenario"));
		int rowcount=sheet.getPhysicalNumberOfRows();

	/*	for(int i=1;i<rowcount; i++){
		XSSFRow  rows=sheet.getRow(i);
		XSSFCell cells=rows.getCell(1);
		String value=cells.getStringCellValue();

		if(value.equals("Yes") ) {
	
			XSSFCell cellmethod=rows.getCell(2);
		String methodcell=cellmethod.getStringCellValue();
		
		XSSFCell cellmethods=rows.getCell(0);
		String methodcells=cellmethods.getStringCellValue();
	//testcase.add(methodcells);
		XSSFCell urlcell=rows.getCell(3);
		String url= urlcell.getStringCellValue();
		String ClassName = "org.com.restclient.RestAPICall";
        Class<?> Classes = Class.forName(ClassName); // convert string classname to class
      
	Object MethodInvoke = Classes.newInstance(); 
		Method getNameMethod =MethodInvoke.getClass().getMethod(methodcell,String.class);
		//String names=getNameMethod.getName();
		
		 name = (Object) getNameMethod.invoke(MethodInvoke,url);
	utils	=new Utilities();
	
	   
		
		
	
		}
	
		}*/
		//String tc=testcase();
	
		for(int i=1;i<rowcount; i++){               //userget
			//for(String testcases:testnames()) {
			XSSFRow  rows=sheet.getRow(i);
			XSSFCell cells=rows.getCell(0);
			String value=cells.getStringCellValue();
			
			if(names.equals(value)) {
				XSSFCell cellmethod=rows.getCell(2);
				String methodcell=cellmethod.getStringCellValue();
				
			
			
				XSSFCell urlcell=rows.getCell(3);
				String url= urlcell.getStringCellValue();
				
				XSSFRow rowdata=sheet.getRow(0);
				XSSFCell datacell=rowdata.getCell(5);
				DataFormatter df = new DataFormatter();
				 dataname = df.formatCellValue(datacell);
				String ClassName = "org.com.restclient.RestAPICall";
		        Class<?> Classes = Class.forName(ClassName); // convert string classname to class
		      
			Object MethodInvoke = Classes.newInstance(); 
				Method getNameMethod =MethodInvoke.getClass().getMethod(methodcell,String.class);
				//String names=getNameMethod.getName();
				
				
				 name = (Object) getNameMethod.invoke(MethodInvoke,url,dataname);
			utils	=new Utilities();
			//stat=true;
			//break;
			}
			
			/*}
			if(stat=true) {
				break;
			}*/
			
		}
		
	
		excel.close();
		
		
		

		
			
	
		return name;	
		
	}
	
	
}
