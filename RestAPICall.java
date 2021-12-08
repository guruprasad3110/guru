package org.com.restclient;

import java.io.IOException;
import java.util.HashMap;

import org.apache.http.Header;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

public class RestAPICall {
public long timer;
	public CloseableHttpResponse Response(String url) throws ClientProtocolException, IOException {
	//StopWatch st=new StopWatch();
	// timer=System.currentTimeMillis();
		
		
		/*CredentialsProvider provider = new BasicCredentialsProvider();
		UsernamePasswordCredentials credentials
		 = new UsernamePasswordCredentials("user1", "user1Pass");
		provider.setCredentials(AuthScope.ANY, credentials);
		 
		HttpClient client = HttpClientBuilder.create()
		  .setDefaultCredentialsProvider(provider)
		  .build();*/
		
			CloseableHttpClient http= HttpClients.createDefault();
	HttpGet geturl= new HttpGet(url);
	
	CloseableHttpResponse responseget = http.execute(geturl);
	System.out.println(responseget);
	return responseget;
}
	public String GetStatusCode(String url  ) throws ClientProtocolException, IOException {
		/*long t1=System.currentTimeMillis()-timer;
		System.out.println("Response Time is "+t1);*/
	
	int statusCodes= Response(url).getStatusLine().getStatusCode();
	String reason=Response(url).getStatusLine().getReasonPhrase();
	String statusCode=String.valueOf(statusCodes)+ " "+ reason;
	  
	
	return statusCode;
	}
	public JSONObject GetJSONResponse(String url) throws ClientProtocolException, IOException {
	
		String responses= EntityUtils.toString(Response(url).getEntity(), "UTF-8");
		  JSONObject JSONResponse=new JSONObject(responses);
		//  System.out.println(JSONResponse);
		
		return JSONResponse;
		
	}
	

	
	public String GetJSONValue(String url,String jpath) throws ClientProtocolException, IOException {
	
		

		String responses= EntityUtils.toString(Response(url).getEntity(), "UTF-8");
		  JSONObject JSONResponse=new JSONObject(responses);
		
		  
		  Object obj = JSONResponse;
			for(String s : jpath.split("/")) 
				if(!s.isEmpty()) 
					if(!(s.contains("[") || s.contains("]")))
						obj = ((JSONObject) obj).get(s);
					else if(s.contains("[") || s.contains("]"))
						obj = ((JSONArray) ((JSONObject) obj).get(s.split("\\[")[0])).get(Integer.parseInt(s.split("\\[")[1].replace("]", "")));
			return obj.toString();
			  
		  
		//  System.out.println(JSONResponse);
	
		
	}
	public HashMap<String,String> GetHeaders(String url) throws ClientProtocolException, IOException {
		
		Header[] headerarray=Response(url).getAllHeaders();
		HashMap<String,String> headers=new HashMap<String,String>();
		for(Header header : headerarray) {
			headers.put(header.getName(), header.getValue());
			
		}
	//	System.out.println(headers);
		return headers;
		
	}
}
