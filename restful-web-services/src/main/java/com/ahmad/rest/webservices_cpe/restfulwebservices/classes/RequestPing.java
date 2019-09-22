package com.ahmad.rest.webservices_cpe.restfulwebservices.classes;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.JSONArray;
import org.json.JSONObject;


import com.ahmad.rest.webservices_cpe.restfulwebservices.BeanInfo;
import com.ahmad.rest.webservices_cpe.restfulwebservices.Context;

public class RequestPing {

	Context context;

	public RequestPing() {
		context = new Context();
	}

	private List<HashMap<String,String>> GetPingDiagnostics(BeanInfo beanInfo) {
		// try {
		// 	beanInfo.setAcs_token(new ACS_Authentication().getAcsToken());
		// } catch (Exception e) {
		// 	// TODO Auto-generated catch block
		// 	e.printStackTrace();
		// }
		List<HashMap<String,String>> result_id = new RequestDiagnostics().getRequestDiagnostics(beanInfo);
		
		for (int i=0;i<result_id.size();i++)
	{
		if (result_id.get(i).get("name").equals("IP Ping Diagnostics"))
		beanInfo.setpingdiag_id (result_id.get(i).get("id"));
	}
		
		String url = context.getAcs_url() + "/devicemodels/"+ beanInfo.getModel_id() + "/diagnostics/"+beanInfo.getpingdiag_id();
		System.out.println("url: " + url);
		
		List<HashMap<String,String>> list = new ArrayList<HashMap<String,String>>();
		JSONObject response_object = null;
		
		HttpClient httpClient = HttpClientBuilder.create().build();
		HttpGet request = new HttpGet(url);
		try {
			request.addHeader("content-type", "application/json");
			request.addHeader("Authorization", beanInfo.getAcs_token());
			HttpResponse response = httpClient.execute(request);

			System.out.println("Response Code : " + response.getStatusLine().getStatusCode());

			BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
			StringBuffer result = new StringBuffer();
			String line = "";
			while ((line = rd.readLine()) != null) {
				result.append(line);
			}

			System.out.println("Ping Diagnostics ==> " + result.toString());	
			
		//	response_object = result.toString();
			
			response_object = new JSONObject(result.toString());
			for (Object object : response_object.getJSONArray("diagnosticParameters")) {
				System.out.println("object: " + object.toString());
				HashMap<String,String> map = new HashMap<String,String>();
                if (((JSONObject)object).get("name").toString().contains("Interface"))
				map.put("Interface", ((JSONObject)object).get("name").toString());
				if (((JSONObject)object).get("name").toString().contains("Host"))
				map.put("Host", ((JSONObject)object).get("name").toString());
				if (((JSONObject)object).get("name").toString().contains("DSCP"))
				map.put("DSCP", ((JSONObject)object).get("name").toString());
				if (((JSONObject)object).get("name").toString().contains("DataBlockSize"))
				map.put("DataBlockSize", ((JSONObject)object).get("name").toString());
				if (((JSONObject)object).get("name").toString().contains("Timeout"))
				map.put("Timeout", ((JSONObject)object).get("name").toString());
				if (((JSONObject)object).get("name").toString().contains("NumberOfRepetitions"))
				map.put("NumberOfRepetitions", ((JSONObject)object).get("name").toString());


				list.add(map);
				beanInfo.setping_param(response_object);
			}

		} catch (Exception ex) {
			System.out.println(ex);
		} finally {
			// ahm
			request.releaseConnection();
		}
		return list;
	}
	
	
	
	private JSONObject PostPingDiagnostics(BeanInfo beanInfo){
		String url = context.getAcs_url() + "/devices/"+ beanInfo.getDevice_id() + "/diagnostics/"+beanInfo.getpingdiag_id();
		System.out.println("url: " + url);
		
		List<HashMap<String,String>> list = new ArrayList<HashMap<String,String>>();
		String response_object = null;
		JSONObject operationId =null;
		
		HttpClient httpClient = HttpClientBuilder.create().build();
		HttpPost request = new HttpPost(url);
		try {
			
			String	interface_1 =null ;
			String	host=null;
			String	DSCP=null ;
			String	DataBlockSize =null;
			String	Timeout=null ;
			String	NumberOfRepetitions=null ;
			
//			JSONArray a = new JSONArray();
//			a.put(beanInfo.getSubmitted_wifi_settings().get(0));
			System.out.println("params : " + beanInfo.getping_param().toString());
			
			for (Object object : beanInfo.getping_param().getJSONArray("diagnosticParameters")) {
				System.out.println("object: " + object.toString());
				HashMap<String,String> map = new HashMap<String,String>();
                if (((JSONObject)object).get("name").toString().contains("Interface"))
				interface_1 =((JSONObject)object).get("name").toString();
				if (((JSONObject)object).get("name").toString().contains("Host"))
				host= ((JSONObject)object).get("name").toString();
				if (((JSONObject)object).get("name").toString().contains("DSCP"))
				DSCP=((JSONObject)object).get("name").toString();
				if (((JSONObject)object).get("name").toString().contains("DataBlockSize"))
				DataBlockSize= ((JSONObject)object).get("name").toString();
				if (((JSONObject)object).get("name").toString().contains("Timeout"))
				Timeout=((JSONObject)object).get("name").toString();
				if (((JSONObject)object).get("name").toString().contains("NumberOfRepetitions"))
				NumberOfRepetitions=((JSONObject)object).get("name").toString();

			}
				
			


			

			String params_submit ="{\"operationParameters\":[{ \"name\":\""+host+"\",\"value\":\"8.8.8.8\"},{\"name\":\""+NumberOfRepetitions+"\",\"value\":\"2\"},{\"name\":\""+DataBlockSize+"\",\"value\":\"1024\"  }],\"schedule\":{\"meta\":{\"type\":\"IMMEDIATE\", \"notifyDevice\":null},\"startDate\":null,\"endDate\":null,\"timePeriods\":[]}}";
			
			StringEntity params = new StringEntity(params_submit);
			params.setContentType("application/json");
			System.out.println("params : " + params);
					
			request.addHeader("Content-Type", "application/json");
			request.addHeader("Authorization", beanInfo.getAcs_token());
			request.setEntity( params);
			HttpResponse response = httpClient.execute(request);

			System.out.println("Response Code : " + response.getStatusLine().getStatusCode());

			BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
			StringBuffer result = new StringBuffer();
			String line = "";
			while ((line = rd.readLine()) != null) {
				result.append(line);
			}

			System.out.println("PostDiagnositcs ==> " + result.toString());	
			
			response_object = result.toString();
			 operationId= new JSONObject(result.toString());

//			for (Object object : response_object.getJSONArray("results")) {
//				System.out.println("object: " + object.toString());
//				HashMap<String,String> map = new HashMap<String,String>();
//				map.put("systemName", ((JSONObject)object).get("systemName").toString());
//				map.put("description", ((JSONObject)object).get("description").toString());
//				map.put("title", ((JSONObject)object).get("title").toString());
//				list.add(map);
//			}

		} catch (Exception ex) {
			System.out.println(ex);
		} finally {
			// ahm
			request.releaseConnection();
		}
		return operationId;
	} 
	
	
	
	public List<HashMap<String,String>> getPingDiag(BeanInfo beanInfo) {
		return GetPingDiagnostics(beanInfo);
	}
	
	public JSONObject setPostPingDiagnostics(BeanInfo beanInfo) {
		return PostPingDiagnostics(beanInfo);
	}
}
