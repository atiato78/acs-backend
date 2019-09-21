package com.ahmad.rest.webservices_cpe.restfulwebservices;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ahmad.rest.webservices_cpe.restfulwebservices.classes.ACS_Authentication;
import com.ahmad.rest.webservices_cpe.restfulwebservices.classes.CPEReboot;
import com.ahmad.rest.webservices_cpe.restfulwebservices.classes.DeviceOperation;
import com.ahmad.rest.webservices_cpe.restfulwebservices.classes.RequestDiagnostics;
import com.ahmad.rest.webservices_cpe.restfulwebservices.classes.RequestPing;
import com.ahmad.rest.webservices_cpe.restfulwebservices.classes.RequestServices;
import com.ahmad.rest.webservices_cpe.restfulwebservices.classes.RequestTrace;
import com.ahmad.rest.webservices_cpe.restfulwebservices.classes.RequestWifiSettings;
import com.ahmad.rest.webservices_cpe.restfulwebservices.classes.Trending;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class MainResource {
	
	@PostMapping("/requesttrendingsent")
	public List<?> TrendingSent(@RequestBody BeanInfo beanInfo) throws Exception {
		beanInfo.setAcs_token(new ACS_Authentication().getAcsToken());		
		return new Trending().GetTrendingSentBytes(beanInfo);
	}
	
	@PostMapping("/cpereboot")
	public List<?> CPEReboot(@RequestBody BeanInfo beanInfo) throws Exception {
		beanInfo.setAcs_token(new ACS_Authentication().getAcsToken());	
		JSONObject result = new CPEReboot().doReboot(beanInfo);
		String operationId = result.getJSONArray("deviceOperationUris").getString(0);
		beanInfo.setOperation_id(operationId);
		
		beanInfo.setAcs_token(new ACS_Authentication().getAcsToken());
		JSONObject result2 = new DeviceOperation().getOperationById(beanInfo);
		String deviceOperationState = result2.getString("deviceOperationState");
		System.out.println("deviceOperationState: " + deviceOperationState);
		
		HashMap<String,String> map = new HashMap<String,String>();
		map.put("operationId",operationId);
		map.put("deviceOperationState",deviceOperationState);
		List<HashMap<String,String>> list = new ArrayList<HashMap<String,String>>();
		list.add(map);
		
		return list;
	}
	
	
	@PostMapping("/deviceoperationstate")
	public List<HashMap<String, String>> DeviceOperation(@RequestBody BeanInfo beanInfo) throws Exception {
		List<HashMap<String,String>> list = new ArrayList<HashMap<String,String>>();
		beanInfo.setAcs_token(new ACS_Authentication().getAcsToken());
		JSONObject result = new DeviceOperation().getOperationById(beanInfo);
		String deviceOperationState = result.getString("deviceOperationState");
		System.out.println("****** " + deviceOperationState);
		
		HashMap<String,String> map = new HashMap<String,String>();
		map.put("deviceOperationState", deviceOperationState);
	


		for (Object object : result.getJSONArray("deviceOperationParameters")) {
				System.out.println("object: " + object.toString());
				HashMap<String,String> map_op = new HashMap<String,String>();
				if(((JSONObject)object).get("name").toString().contains("Average"))
				map_op.put("AvarageResponseTime", ((JSONObject)object).get("value").toString());
				if(((JSONObject)object).get("name").toString().contains("DiagnosticsState"))
				map_op.put("DiagnosticsState", ((JSONObject)object).get("value").toString());
				if(((JSONObject)object).get("name").toString().contains("FailureCount"))
				map_op.put("FailureCount", ((JSONObject)object).get("value").toString());
				if(((JSONObject)object).get("name").toString().contains("MaximumResponseTime"))
				map_op.put("MaximumResponseTime", ((JSONObject)object).get("value").toString());
				if(((JSONObject)object).get("name").toString().contains("MinimumResponseTime"))
				map_op.put("MinimumResponseTime", ((JSONObject)object).get("value").toString());
				if(((JSONObject)object).get("name").toString().contains("SuccessCount"))
				map_op.put("SuccessCount", ((JSONObject)object).get("value").toString());
				//fix for traceroute
				if(((JSONObject)object).get("name").toString().contains("TraceRouteDiagnostics.ResponseTime"))
				map_op.put("ResponseTime", ((JSONObject)object).get("value").toString());
				if(((JSONObject)object).get("name").toString().contains("TraceRouteDiagnostics.RouteHopsNumberOfEntries"))
				map_op.put("RouteHopsNumberOfEntries", ((JSONObject)object).get("value").toString());
                if(!map_op.isEmpty())
				list.add(map_op);
			}
			list.add(map);
		
		return list;
	}
	
	
	
	@PostMapping("/requestservices")
	public List<HashMap<String,String>> Request_Services(@RequestBody BeanInfo beanInfo) throws Exception {
		beanInfo.setAcs_token(new ACS_Authentication().getAcsToken());
		List<HashMap<String,String>> result = new RequestServices().getRequestServices(beanInfo);
		return result;
	}

	@PostMapping("/requestdiagnostics")
	public List<HashMap<String,String>> Request_Diagnostics(@RequestBody BeanInfo beanInfo) throws Exception {
		beanInfo.setAcs_token(new ACS_Authentication().getAcsToken());
		List<HashMap<String,String>> result = new RequestDiagnostics().getRequestDiagnostics(beanInfo);
		return result;
	}
	
	@PostMapping("/ping")
	public List<HashMap<String,String>> Request_Ping(@RequestBody BeanInfo beanInfo) throws Exception {
		beanInfo.setAcs_token(new ACS_Authentication().getAcsToken());
		List<HashMap<String,String>> result = new RequestPing().getPingDiag(beanInfo);
		JSONObject op_id =new RequestPing().setPostPingDiagnostics(beanInfo);
		String operationId = op_id.getJSONArray("deviceOperationUris").getString(0);
		beanInfo.setOperation_id(operationId);

		beanInfo.setAcs_token(new ACS_Authentication().getAcsToken());
		JSONObject result2 = new DeviceOperation().getOperationById(beanInfo);
		String deviceOperationState = result2.getString("deviceOperationState");
		System.out.println("deviceOperationState: " + deviceOperationState);
		
		HashMap<String,String> map = new HashMap<String,String>();
		map.put("operationId",operationId);
		map.put("deviceOperationState",deviceOperationState);
		List<HashMap<String,String>> list = new ArrayList<HashMap<String,String>>();
		list.add(map);
		
		return list;
	}
	
	@PostMapping("/traceroute")
	public List<HashMap<String,String>> Request_Trace(@RequestBody BeanInfo beanInfo) throws Exception {
		beanInfo.setAcs_token(new ACS_Authentication().getAcsToken());
		List<HashMap<String,String>> result = new RequestTrace().getTraceDiag(beanInfo);
		JSONObject op_id =new RequestTrace().setPostTraceDiagnostics(beanInfo);
		String operationId = op_id.getJSONArray("deviceOperationUris").getString(0);
		beanInfo.setOperation_id(operationId);

		beanInfo.setAcs_token(new ACS_Authentication().getAcsToken());
		JSONObject result2 = new DeviceOperation().getOperationById(beanInfo);
		String deviceOperationState = result2.getString("deviceOperationState");
		System.out.println("deviceOperationState: " + deviceOperationState);
		
		HashMap<String,String> map = new HashMap<String,String>();
		map.put("operationId",operationId);
		map.put("deviceOperationState",deviceOperationState);
		List<HashMap<String,String>> list = new ArrayList<HashMap<String,String>>();
		list.add(map);
		
		return list;
	}

	@PostMapping("/requestwifisettings")
	public String Request_WifiSettings(@RequestBody BeanInfo beanInfo) throws Exception {
		beanInfo.setAcs_token(new ACS_Authentication().getAcsToken());
		return new RequestWifiSettings().getWifiSettings(beanInfo);
	}
	
	
	@PostMapping("/postwifisettings")
	public String Post_WifiSettings(@RequestBody BeanInfo beanInfo) throws Exception {
		beanInfo.setAcs_token(new ACS_Authentication().getAcsToken());
		
		System.out.println("postwifisettings=> "+beanInfo.getSubmitted_wifi_settings());
		return new RequestWifiSettings().setWifiSettings(beanInfo);
	}

}
