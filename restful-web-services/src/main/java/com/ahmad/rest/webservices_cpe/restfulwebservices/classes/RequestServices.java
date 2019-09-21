package com.ahmad.rest.webservices_cpe.restfulwebservices.classes;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.JSONObject;

import com.ahmad.rest.webservices_cpe.restfulwebservices.BeanInfo;
import com.ahmad.rest.webservices_cpe.restfulwebservices.Context;

public class RequestServices {

	Context context;
	public RequestServices() {
		context = new Context();
	}
	
	private List<HashMap<String,String>> GetRequestServices(BeanInfo beanInfo){
		String url = context.getAcs_url() + "/devices/"+ beanInfo.getDevice_id() + "/services";
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

			System.out.println("Device Operation Result ==> " + result.toString());	
			
			response_object = new JSONObject(result.toString());
			
			for (Object object : response_object.getJSONArray("results")) {
				System.out.println("object: " + object.toString());
				HashMap<String,String> map = new HashMap<String,String>();
				map.put("systemName", ((JSONObject)object).get("systemName").toString());
				map.put("description", ((JSONObject)object).get("description").toString());
				map.put("title", ((JSONObject)object).get("title").toString());
				list.add(map);
			}

		} catch (Exception ex) {
			System.out.println(ex);
		} finally {
			// ahm
			request.releaseConnection();
		}
		return list;
	}
	
	
	public List<HashMap<String,String>> getRequestServices(BeanInfo beanInfo) {
		return GetRequestServices(beanInfo);
	}
}
