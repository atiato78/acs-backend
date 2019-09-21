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

import com.ahmad.rest.webservices_cpe.restfulwebservices.BeanInfo;
import com.ahmad.rest.webservices_cpe.restfulwebservices.Context;

public class RequestWifiSettings {

	Context context;
	public RequestWifiSettings() {
		context = new Context();
	}
	
	private String GetWifiSettings(BeanInfo beanInfo){
		String url = context.getAcs_url() + "/devices/"+ beanInfo.getDevice_id() + "/services/"+beanInfo.getSystem_name()+"?reloadParameters=true";
		System.out.println("url: " + url);
		
		List<HashMap<String,String>> list = new ArrayList<HashMap<String,String>>();
		String response_object = null;
		
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

			System.out.println("GetWifiSettings ==> " + result.toString());	
			
			response_object = result.toString();
			
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
		return response_object;
	}
	
	
	
	private String PostWifiSettings(BeanInfo beanInfo){
		String url = context.getAcs_url() + "/devices/"+ beanInfo.getDevice_id() + "/services/"+beanInfo.getSystem_name()+"?reloadParameters=true";
		System.out.println("url: " + url);
		
		List<HashMap<String,String>> list = new ArrayList<HashMap<String,String>>();
		String response_object = null;
		
		HttpClient httpClient = HttpClientBuilder.create().build();
		HttpPost request = new HttpPost(url);
		try {
			
			
			
//			JSONArray a = new JSONArray();
//			a.put(beanInfo.getSubmitted_wifi_settings().get(0));
			System.out.println("submitted changes : " + beanInfo.getSubmitted_wifi_settings().toString());
			
			StringEntity params = new StringEntity(beanInfo.getSubmitted_wifi_settings().toString());
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

			System.out.println("PostWifiSettings ==> " + result.toString());	
			
			response_object = result.toString();
			
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
		return response_object;
	} 
	
	
	
	public String getWifiSettings(BeanInfo beanInfo) {
		return GetWifiSettings(beanInfo);
	}
	
	public String setWifiSettings(BeanInfo beanInfo) {
		return PostWifiSettings(beanInfo);
	}
}
