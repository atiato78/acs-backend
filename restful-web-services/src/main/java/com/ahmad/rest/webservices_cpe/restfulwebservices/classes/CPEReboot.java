package com.ahmad.rest.webservices_cpe.restfulwebservices.classes;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.JSONObject;

import com.ahmad.rest.webservices_cpe.restfulwebservices.BeanInfo;
import com.ahmad.rest.webservices_cpe.restfulwebservices.Context;

public class CPEReboot {
	
	Context context;
	
	public CPEReboot() {
		context = new Context();
	}
	

	private JSONObject Reboot(BeanInfo beanInfo) {
		String url = context.getAcs_url() + "/devices/" + beanInfo.getDevice_id() + "/reboot";
		HttpClient httpClient = HttpClientBuilder.create().build();
		HttpPost request = new HttpPost(url);
		JSONObject operationId = null;
		try {
			StringEntity params = new StringEntity(
					"{\"schedule\":{\"meta\":{\"type\":\"IMMEDIATE\",\"notifyDevice\":null},\"startDate\":null,\"endDate\":null,\"timePeriods\":[]}}");
			request.addHeader("content-type", "application/json");
			request.addHeader("Authorization", beanInfo.getAcs_token());
			request.setEntity(params);
			HttpResponse response = httpClient.execute(request);

			System.out.println("Response Code : " + response.getStatusLine().getStatusCode());

			BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
			StringBuffer result = new StringBuffer();
			String line = "";
			while ((line = rd.readLine()) != null) {
				result.append(line);
			}

			System.out.println("Response==> " + result.toString());
			operationId= new JSONObject(result.toString());
			
			

		} catch (Exception ex) {
			System.out.println(ex);
		} finally {
			// ahm
			request.releaseConnection();
		}
		return operationId;
	}
	
	
	public JSONObject doReboot(BeanInfo beanInfo) {
		return Reboot(beanInfo);
	}

}
