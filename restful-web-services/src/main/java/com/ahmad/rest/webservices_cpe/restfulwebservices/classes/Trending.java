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
import org.json.JSONArray;
import org.json.JSONObject;

import com.ahmad.rest.webservices_cpe.restfulwebservices.BeanInfo;
import com.ahmad.rest.webservices_cpe.restfulwebservices.Context;

public class Trending {

	Context context;
	
	public Trending() {
		context = new Context();
	}
	
	
	private List<?> TrendingSentBytes(BeanInfo beanInfo){
		String trendingSentId = "";
		JSONArray trendingResult = new JSONArray();
		List<HashMap<String, String>> Sent_Received_Id_list = Get_TrendingId(beanInfo);

		for (int i = 0; i < Sent_Received_Id_list.size(); i++) {
			System.out.println("i=> "+i);
			if (Sent_Received_Id_list.get(i).get("name").toLowerCase().contains("sent")) {
				trendingSentId = Sent_Received_Id_list.get(i).get("id");				
			}
		}
		if (trendingSentId != null && !trendingSentId.equals("")) {
			trendingResult = Trending_TotalSent_ById(beanInfo, trendingSentId);
		}
//		System.out.println("TrendingSent result: "+trendingResult);
		return trendingResult.toList();
	}
	
	
	private List<HashMap<String, String>> Get_TrendingId(BeanInfo beanInfo) {
		String url = context.getAcs_url() + "/devicemodels/" + beanInfo.getModel_id() + "/trendingvalues/";

		List<HashMap<String, String>> Sent_Received_Id_list = new ArrayList<HashMap<String, String>>();
		System.out.println("url: "+url);
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
//			System.out.println("TRENDING Response==> " + result.toString());
			JSONObject json = new JSONObject(result.toString());
			for (int i = 0; i < json.getJSONArray("results").length(); i++) {
//				System.out.println("results[" + i + "] ==> " + json.getJSONArray("results").getJSONObject(i));
				String name = json.getJSONArray("results").getJSONObject(i).getString("name");
				String id = json.getJSONArray("results").getJSONObject(i).getString("id");

				if (name.toLowerCase().contains("sent") || name.toLowerCase().contains("receiv")) {
					HashMap<String, String> map = new HashMap<String, String>();
					map.put("name", name);
					map.put("id", id);
					Sent_Received_Id_list.add(map);
				}
			}

		} catch (Exception ex) {
			System.out.println(ex);
		} finally {
			// ahm
			request.releaseConnection();
		}
		return Sent_Received_Id_list;
	}

	
	private JSONArray Trending_TotalSent_ById(BeanInfo beanInfo, String trendingSentId) {
		String url = context.getAcs_url() + "/devices/" + beanInfo.getDevice_id() + "/trendingvalues/" + trendingSentId;

		JSONArray jsonArray = new JSONArray();
		System.out.println("url: "+url);
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
//			System.out.println("TRENDING Sent Total Response==> " + result.toString());
			JSONObject json = new JSONObject(result.toString());
			jsonArray = json.getJSONArray("results");
//			System.out.println(jsonArray);
		} catch (Exception ex) {
			System.out.println(ex);
		} finally {
			// ahm
			request.releaseConnection();
		}
		return jsonArray;
	}
	
	public List<?> GetTrendingSentBytes(BeanInfo beanInfo){
		return TrendingSentBytes(beanInfo);
	}

}
