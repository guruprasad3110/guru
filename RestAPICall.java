package org.com.restclient;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import org.apache.http.Header;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.com.base.TestBase;
import org.com.base.Utilities;
import org.json.JSONArray;
import org.json.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;

/** Contains the Methods to get the url request and Process it based on Get/Post call
 * 
 * @author GBMoorthy
 *
 */

public class RestAPICall {
	
	//Global Declaration of Variables used in Get/Post Methods
	
public int colno=0;
public int rowno=0;
private static Properties properties;
public CloseableHttpResponse responseget ;
public HttpGet http;
public CloseableHttpClient closablehttp;
public static String nameval,jobval;
public Utilities utils = new Utilities();

/**provides the response based on the url Request
 * 
 * @param url
 * @return
 * @throws ClientProtocolException
 * @throws IOException
 */
	public CloseableHttpResponse CreateRequest(String url) throws ClientProtocolException, IOException {
	
		TestBase tb =new TestBase();
		String names = tb.tcs();
		
		properties=new Properties();

		FileInputStream config=new FileInputStream(new File(System.getProperty("user.dir")+"/src/main/java/org/com/config/config.properties"));
		properties.load(config);
		
			FileInputStream data=new FileInputStream(new File(System.getProperty("user.dir")+properties.getProperty("TestDataPath")));
			
			XSSFWorkbook excel=new XSSFWorkbook(data);
			XSSFSheet sheet	= excel.getSheet(properties.getProperty("Scenario"));
		int rowcount=sheet.getPhysicalNumberOfRows();
		
		
		//Traversing in Excel to verify if the call needs Authentication
		
		for(int i=1;i<rowcount; i++){               
			
			XSSFRow  rows=sheet.getRow(i);
			XSSFCell cells=rows.getCell(0);
			String value=cells.getStringCellValue();
			
			if(names.equals(value)) {
				
	
				XSSFRow urlcells=sheet.getRow(0);
				for(int r=0;r<urlcells.getLastCellNum();r++) {
					if(urlcells.getCell(r).getStringCellValue().equals("Authentication Required")) {
				
						colno=r;
						rowno=i;
					break;
					}
				}
			break;
			}
		}
		
		XSSFRow rowauth=sheet.getRow(rowno);
		XSSFCell colauth=rowauth.getCell(colno);
		
	String authcol=colauth.getStringCellValue();
	if(authcol.equals("Yes")) {
		
		XSSFCell coluname=rowauth.getCell(colno+1);
		XSSFCell colpwd=rowauth.getCell(colno+2);
		
		String username=coluname.getStringCellValue();
		String pwd=colpwd.getStringCellValue();
		HashMap<String, String> headerMap = new HashMap<String, String>();
		headerMap.put("Username", username);
		headerMap.put("Password",pwd);
		
		closablehttp =HttpClients.createDefault();
		http= new HttpGet(url);
		for(Entry<String, String> entry : headerMap.entrySet()){
			http.addHeader(entry.getKey(), entry.getValue());
		}
		 responseget =  closablehttp.execute(http);
	}
		
						
		
	//Returns Repsonse for API call without Authnentication
	
					 else {
						 
						 closablehttp =HttpClients.createDefault();
							http= new HttpGet(url);
						responseget=closablehttp.execute(http);
							 
							 
							
							
							
					 }
					 		
			
	return  responseget;		
}
	
	/** Method to process Post call based on URL
	 * 
	 * @param url
	 * @param entityString
	 * @param headerMap
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	
	public CloseableHttpResponse post(String url, String entityString, HashMap<String, String> headerMap) throws ClientProtocolException, IOException{
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpPost httppost = new HttpPost(url); //http post request
		httppost.setEntity(new StringEntity(entityString)); //for payload
		
		//for headers:
		
		for(Map.Entry<String,String> entry : headerMap.entrySet()){
			httppost.addHeader(entry.getKey(), entry.getValue());
		}
		
		 responseget = httpClient.execute(httppost);
		 return  responseget;
		
		
	}
	/** Method to Get Status Code from Response
	 * 
	 * @param url
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	
	public String GetStatusCode(String url  ) throws ClientProtocolException, IOException {
				
		long timerequest= System.currentTimeMillis();
		
		 //Get Status Code
		
	int statusCodes= CreateRequest(url).getStatusLine().getStatusCode();
	
	//Response Time Calculation
	
	long timeexecution=System.currentTimeMillis();
	long timeresponse=timeexecution-timerequest;

	 
	utils.WriteResponseTime(timeresponse);	
	
	String statusCode=String.valueOf(statusCodes);
	  
	
	return statusCode;	
	}
	
	/** Method to get Status Message from Response
	 * 
	 * @param url
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	
	public String GetStatusMessage(String url  ) throws ClientProtocolException, IOException {
		
		long timerequest= System.currentTimeMillis();
		
		//Get Status Message
		
		String reason=CreateRequest(url).getStatusLine().getReasonPhrase();
		String statusCode= reason;
		
		//Response Time Calculation
		
	long timeexecution=System.currentTimeMillis();
	long timeresponse=timeexecution-timerequest;
     utils.WriteResponseTime(timeresponse);
		

	
	
	return statusCode;	
	}
	
	/** Method to get Complete JSON Response from API Response
	 * 
	 * @param url
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	
	public JSONObject GetJSONResponse(String url) throws ClientProtocolException, IOException {
		long timerequest= System.currentTimeMillis();
		
		//Get JSON Response
		
		String responses= EntityUtils.toString(CreateRequest(url).getEntity(), "UTF-8");
		
		//Response Time Calculation
		
		long timeexecution=System.currentTimeMillis();
		long timeresponse=timeexecution-timerequest;
		utils.WriteResponseTime(timeresponse);
		  JSONObject JSONResponse=new JSONObject(responses);
		
		
		return JSONResponse;
		
	}
	

	/** Method to get value from the JSON Response as a String
	 * 
	 * @param url
	 * @param jpath
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	
	public String GetValue(String url,Object jpath) throws ClientProtocolException, IOException {
	
		//Response Time Calculation
		
	long timerequest= System.currentTimeMillis();
		String responses= EntityUtils.toString(CreateRequest(url).getEntity(), "UTF-8");
		long timeexecution=System.currentTimeMillis();
		long timeresponse=timeexecution-timerequest;
		 
		
		 utils.WriteResponseTime(timeresponse);
		
    // Get value of a Response Data as String
		  JSONObject JSONResponse=new JSONObject(responses);
		
		  Object obj = JSONResponse;
			for(String s : ((String) jpath).split("/")) 
				if(!s.isEmpty()) 
					if(!(s.contains("[") || s.contains("]")))
						obj = ((JSONObject) obj).get(s);
					else if(s.contains("[") || s.contains("]"))
						obj = ((JSONArray) ((JSONObject) obj).get(s.split("\\[")[0])).get(Integer.parseInt(s.split("\\[")[1].replace("]", "")));
			return obj.toString();
	
		
	}
	
	/** Method to get all Headers from the response
	 * 
	 * @param url
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	
	public HashMap<String,String> GetHeaders(String url) throws ClientProtocolException, IOException {
		
		//Response Time Calculation
		
		long timerequest= System.currentTimeMillis();
		Header[] headerarray=CreateRequest(url).getAllHeaders();
		long timeexecution=System.currentTimeMillis();
		long timeresponse=timeexecution-timerequest;
		 utils.WriteResponseTime(timeresponse);
		
		
		HashMap<String,String> headers=new HashMap<String,String>();
		
		//Get all Headers
		
		for(Header header : headerarray) {
			headers.put(header.getName(), header.getValue());
			
		}

		return headers;
		
	}
	
	/** Method to Post values 
	 * 
	 * @param url
	 * @return
	 * @throws IOException
	 */
	
	public String CreateUser(String url) throws IOException {
		
		//Get name of Test case for getting Post Data from Excel
		
		
		TestBase tb =new TestBase();
		String names=tb.tcspost();
		
		
		//Add Default Header for Post
		
		HashMap<String, String> headerMap = new HashMap<String, String>();
		headerMap.put("Content-Type", "application/json");
	
		properties=new Properties();

		FileInputStream config=new FileInputStream(new File(System.getProperty("user.dir")+"/src/main/java/org/com/config/config.properties"));
		properties.load(config);
		
			FileInputStream data=new FileInputStream(new File(System.getProperty("user.dir")+properties.getProperty("TestDataPath")));
			
			XSSFWorkbook excel=new XSSFWorkbook(data);
			XSSFSheet sheet	= excel.getSheet(properties.getProperty("Scenario"));
		int rowcount=sheet.getPhysicalNumberOfRows();
		
		//Traverse through Excel for getting Post Data
		
		for(int i=1;i<rowcount; i++){               
			
			XSSFRow  rows=sheet.getRow(i);
			XSSFCell cells=rows.getCell(0);
			String value=cells.getStringCellValue();
			
			if(names.equals(value)) {
				
	
				XSSFRow urlcells=sheet.getRow(0);
				for(int r=0;r<urlcells.getLastCellNum();r++) {
					if(urlcells.getCell(r).getStringCellValue().equals("CreateUser")) {
				
						colno=r;
						rowno=i;
					break;
					}
				}
			break;
			}
		}
		
		XSSFRow rowauth=sheet.getRow(rowno);
		XSSFCell colauth=rowauth.getCell(colno);
	HashMap<String,String> valueget=new HashMap<String,String>();
	
	String authcol=colauth.getStringCellValue();
	
	String[] splitvals=authcol.split(",");
	for(int a=0; a<splitvals.length ;a++) {
		String[] label=splitvals[a].split(":");
		String keys=label[0]; 
		String valss=label[1];
	valueget.put(keys, valss);
		
	}
	
	//Get the Post values Seperately 
	
		for(Map.Entry<String, String> entry : valueget.entrySet()) {
			String Keyname=entry.getKey();
			if(Keyname.equals("name")) {
				 nameval= entry.getValue();
			}
			if(Keyname.equals(" job")) {
				
				jobval=entry.getValue(); 
			}
			
			
		}
		
		
		closablehttp = HttpClients.createDefault();
		HttpPost httppost = new HttpPost(url); //http post request for payload
		
		//for headers:
		
		for(Map.Entry<String,String> entry : headerMap.entrySet()){
			httppost.addHeader(entry.getKey(), entry.getValue());
		}
		
		
				ObjectMapper mapper = new ObjectMapper();
			Users users = new Users(nameval, jobval);
			
			//expected users object
			
			excel.close();
			
			File file= new File((System.getProperty("user.dir"))+"//src//main//resources//src//main//resources//postData//users.json");
try {			mapper.writeValue(file, users);
}
catch(Exception e) {
	
}
			String usersJsonString = mapper.writeValueAsString(users);
			
		//Executing Post Call
			
			httppost.setEntity(new StringEntity(usersJsonString));
			long timerequest= System.currentTimeMillis();
			post(url,usersJsonString,headerMap);
			
			long timeexecution=System.currentTimeMillis();
			long timeresponse=timeexecution-timerequest;
			 utils.WriteResponseTime(timeresponse);
			return "201";
	}
}
