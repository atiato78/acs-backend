package com.ahmad.rest.webservices_cpe.restfulwebservices.classes;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;

import com.ahmad.rest.webservices_cpe.restfulwebservices.Context;

public class ACS_Authentication {

	Context context;
	
	public ACS_Authentication() {
		context = new Context();
	}
	
	private String ACSAuthToken() throws Exception {
		String url = context.getAcs_url() + "/authentication";
		HttpClient httpClient = HttpClientBuilder.create().build();
		HttpPost request = new HttpPost(url);
		String parsedToken = "";
		try {
			StringEntity params = new StringEntity(
					"{\"username\":\"" + context.getAcs_username() + "\",\"password\":\"" + context.getAcs_password() + "\"} ");
			request.addHeader("content-type", "application/json");
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

			String token = result.toString().replaceAll("\\{\"IncognitoUms auth\":\"", "");
			token = token.replaceAll("\"\\}", "");
			parsedToken = "IncognitoUms auth=" + token;
			System.out.println("parsedToken==> " + token);
			

		} catch (Exception ex) {
			System.out.println(ex);
		} finally {
			// ahm
			request.releaseConnection();
		}
		return parsedToken;
	}
	
	public String getAcsToken() throws Exception {
		return ACSAuthToken();
	}

}
