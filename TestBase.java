package org.com.base;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


/** Base Class that invokes the API Method mentioned in Test Data Sheet
 * Contains Methods for Get and Post
 * Returns the API Response or Values to Test Case for Assertion
 * @author GBMoorthy
 *
 */

public class TestBase    {
	
// Declaring Global Variables for Get and Post
	
private static Properties properties;
public	Object name;
public String testnames;
public Utilities utils;
 

/** Returns test cases marked as Yes in Excel Workbook to Annotation Transformer
 */

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

/** Method to verify the Response code for Get call
 * 
 * @return
 * @throws IOException
 * @throws IllegalAccessException
 * @throws IllegalArgumentException
 * @throws InvocationTargetException
 * @throws NoSuchMethodException
 * @throws SecurityException
 * @throws ClassNotFoundException
 * @throws InstantiationException
 */

public Object  GetActualResult() throws IOException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, ClassNotFoundException, InstantiationException {
		String names = Thread.currentThread().getStackTrace()[2].getMethodName();
		
		
	
		properties=new Properties();

		FileInputStream config=new FileInputStream(new File(System.getProperty("user.dir")+"/src/main/java/org/com/config/config.properties"));
		properties.load(config);
		
			FileInputStream data=new FileInputStream(new File(System.getProperty("user.dir")+properties.getProperty("TestDataPath")));
			
			XSSFWorkbook excel=new XSSFWorkbook(data);
			XSSFSheet sheet	= excel.getSheet(properties.getProperty("Scenario"));
		int rowcount=sheet.getPhysicalNumberOfRows();

	//Getting the URL field from Excel
		
		int urlcellnum = 0;
		XSSFRow urlcells=sheet.getRow(0);
		for(int r=0;r<urlcells.getLastCellNum();r++) {
			if(urlcells.getCell(r).getStringCellValue().equals("URL")) {
				urlcellnum=r;
				break;
			
			}
			
		}
	//Traversing through Excel Sheet to get Test cases  that needs to be Executed
		
		for(int i=1;i<rowcount; i++){              
			XSSFRow  rows=sheet.getRow(i);
			XSSFCell cells=rows.getCell(0);
			String value=cells.getStringCellValue();
			this.testnames=value;
			if(names.equals(value)) {
				XSSFRow rowmethod=sheet.getRow(0);
				int keyowrdforMethod=rowmethod.getLastCellNum();
				
				//Traversing through columns for Keywords of a Test Case in Excel
				
				for(int a=0;a<keyowrdforMethod;a++) {
				XSSFCell cellmethod=rowmethod.getCell(a);
				String methodcell=cellmethod.getStringCellValue();
				if(methodcell.contains("Keyword")) {
			XSSFCell methodsforrun=rows.getCell(a);
			String keywordstorun=methodsforrun.getStringCellValue();
			if(keywordstorun .equals(null)) {
				break;
			}
				XSSFCell urlcell=rows.getCell(urlcellnum);
				String url= urlcell.getStringCellValue();
				
				//Invokes the method mentioned as Keywords in Excel 
				
				String ClassName = "org.com.restclient.RestAPICall";
		        Class<?> Classes = Class.forName(ClassName); 
		      
			Object MethodInvoke = Classes.newInstance(); 
			
			
				Method getNameMethod =MethodInvoke.getClass().getMethod(keywordstorun,String.class);
				
				
					
				//Returns the Response/ Values from API method and compares it with the Expected Result in Utilities Class	
				 name = (Object) getNameMethod.invoke(MethodInvoke,url);
			utils	=new Utilities();
			return name;
				}
				else {
				
				}
				
				}
			
			
				}
			else {
				
			}
			
			
			
		}
		
	
		excel.close();
		
		
		

			
	return name;
		
		
	}
	
	//Method to get the current running Test cases for Get
	public String tcs() {
	
		return Thread.currentThread().getStackTrace()[9].getMethodName();
	}
	
	//Method to get the current running Test cases for Post
	public String tcspost() {
		
		return Thread.currentThread().getStackTrace()[8].getMethodName();
	}
	
	
	/** Method to verify the Response Value (Body) for Get call
	 * 
	 * @param dataname
	 * @return
	 * @throws IOException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 * @throws ClassNotFoundException
	 * @throws InstantiationException
	 */
	
	public Object  GetActualResult(Object dataname) throws IOException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, ClassNotFoundException, InstantiationException {
		String names = Thread.currentThread().getStackTrace()[2].getMethodName();
		
	
		properties=new Properties();

		FileInputStream config=new FileInputStream(new File(System.getProperty("user.dir")+"/src/main/java/org/com/config/config.properties"));
		properties.load(config);
		
			FileInputStream data=new FileInputStream(new File(System.getProperty("user.dir")+properties.getProperty("TestDataPath")));
			
			XSSFWorkbook excel=new XSSFWorkbook(data);
			XSSFSheet sheet	= excel.getSheet(properties.getProperty("Scenario"));
		int rowcount=sheet.getPhysicalNumberOfRows();
 
		//Getting the URL field from Excel
		
		int urlcellnum = 0;
		XSSFRow urlcells=sheet.getRow(0);
		for(int r=0;r<urlcells.getLastCellNum();r++) {
			if(urlcells.getCell(r).getStringCellValue().equals("URL")) {
				urlcellnum=r;
				break;
			
			}
			
		}
		
		//Traversing through Excel Sheet to get Test cases  that needs to be Executed
	
		for(int i=1;i<rowcount; i++){               
			
			XSSFRow  rows=sheet.getRow(i);
			XSSFCell cells=rows.getCell(0);
			String value=cells.getStringCellValue();
			
			if(names.equals(value)) {
				XSSFRow rowmethod=sheet.getRow(0);
				int keyowrdforMethod=rowmethod.getLastCellNum();
				
				//Traversing through columns for Keywords of a Test Case in Excel
				
				for(int a=0;a<keyowrdforMethod;a++) {
				XSSFCell cellmethod=rowmethod.getCell(a);
				String methodcell=cellmethod.getStringCellValue();
				if(methodcell.contains("Keyword")) {
			XSSFCell methodsforrun=rows.getCell(a);
			String keywordstorun=methodsforrun.getStringCellValue();
			if(keywordstorun .equals(null)) {
				break;
			}
				XSSFCell urlcell=rows.getCell(urlcellnum);
				String url= urlcell.getStringCellValue();
				
				//Invokes the method mentioned as Keywords in Excel 
				
				String ClassName = "org.com.restclient.RestAPICall";
				Class<?> Classes = Class.forName(ClassName);
			Object MethodInvoke = Classes.newInstance(); 
				Method getNameMethod =MethodInvoke.getClass().getMethod(keywordstorun,String.class,Object.class);
				
		//Returns the Response/ Values from API method and compares it with the Expected Result in Utilities Class	
				
				 name = (Object) getNameMethod.invoke(MethodInvoke,url,dataname);
			utils	=new Utilities();
			return name;
				}
				else {
				
				}
				
				}
			
			
				}
			else {
				
			}
			
			
			
		}
		
	
		excel.close();
		
		
		

		
			
	return name;
		
		
	}
	
	/** Method for Post call
	 * 
	 * @return
	 * @throws IOException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 * @throws ClassNotFoundException
	 * @throws InstantiationException
	 */
	
	public Object  postValues() throws IOException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, ClassNotFoundException, InstantiationException {
		String names = Thread.currentThread().getStackTrace()[2].getMethodName();
		
		
	
		properties=new Properties();

		FileInputStream config=new FileInputStream(new File(System.getProperty("user.dir")+"/src/main/java/org/com/config/config.properties"));
		properties.load(config);
		
			FileInputStream data=new FileInputStream(new File(System.getProperty("user.dir")+properties.getProperty("TestDataPath")));
			
			XSSFWorkbook excel=new XSSFWorkbook(data);
			XSSFSheet sheet	= excel.getSheet(properties.getProperty("Scenario"));
		int rowcount=sheet.getPhysicalNumberOfRows();

		//Getting the URL field from Excel
		
		int urlcellnum = 0;
		XSSFRow urlcells=sheet.getRow(0);
		for(int r=0;r<urlcells.getLastCellNum();r++) {
			if(urlcells.getCell(r).getStringCellValue().equals("URL")) {
				urlcellnum=r;
				break;
			
			}
			
		}
		
		//Traversing through Excel Sheet to get Test cases  that needs to be Executed
	
		for(int i=1;i<rowcount; i++){               
			XSSFRow  rows=sheet.getRow(i);
			XSSFCell cells=rows.getCell(0);
			String value=cells.getStringCellValue();
			
			if(names.equals(value)) {
				XSSFRow rowmethod=sheet.getRow(0);
				int keyowrdforMethod=rowmethod.getLastCellNum();
				
				//Traversing through columns for Keywords of a Test Case in Excel
				
				for(int a=0;a<keyowrdforMethod;a++) {
				XSSFCell cellmethod=rowmethod.getCell(a);
				String methodcell=cellmethod.getStringCellValue();
				if(methodcell.contains("Keyword")) {
			XSSFCell methodsforrun=rows.getCell(a);
			String keywordstorun=methodsforrun.getStringCellValue();
			if(keywordstorun .equals(null)) {
				break;
			}
				XSSFCell urlcell=rows.getCell(urlcellnum);
				String url= urlcell.getStringCellValue();
				String ClassName = "org.com.restclient.RestAPICall";
		        Class<?> Classes = Class.forName(ClassName); // convert string classname to class
		      
			Object MethodInvoke = Classes.newInstance(); 
			
			//Invokes the method mentioned as Keywords in Excel 
				Method getNameMethod =MethodInvoke.getClass().getMethod(keywordstorun,String.class);
				
				
				//Returns the Response/ Values from API method and compares it with the Expected Result in Utilities Class	
				
				 name = (Object) getNameMethod.invoke(MethodInvoke,url);
			//utils	=new Utilities();
			return name;
				}
				else {
				
				}
				
				}
			
			
				}
			else {
				
			}
			
			
		}
		
	
		excel.close();
		
		

		
			
	return name;
		
		
	}
	
	
	
}
