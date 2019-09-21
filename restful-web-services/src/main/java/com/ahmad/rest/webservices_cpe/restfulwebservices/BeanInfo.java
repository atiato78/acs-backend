package com.ahmad.rest.webservices_cpe.restfulwebservices;

import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

public class BeanInfo {

	private String username;
	private String password;
	private String acs_token;
	private String device_serialnumber;
	private String device_id;
	private String model_id;
	private String operation_id;
	private String system_name;
	private JSONObject submitted_wifi_settings;
	private String ping_id;
	private String trace_id;
	private List<HashMap<String,String>> params;
	private List<HashMap<String,String>> trace;
	
	
	public BeanInfo() {
		//super();
	}
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getDevice_serialnumber() {
		return device_serialnumber;
	}
	public void setDevice_serialnumber(String device_serialnumber) {
		this.device_serialnumber = device_serialnumber;
	}
	public String getDevice_id() {
		return device_id;
	}
	public void setDevice_id(String device_id) {
		this.device_id = device_id;
	}

	public String getModel_id() {
		return model_id;
	}

	public void setModel_id(String model_id) {
		this.model_id = model_id;
	}

	public String getAcs_token() {
		return acs_token;
	}

	public void setAcs_token(String acs_token) {
		this.acs_token = acs_token;
	}

	public String getOperation_id() {
		return operation_id;
	}

	public void setOperation_id(String operation_id) {
		this.operation_id = operation_id;
	}

	public String getSystem_name() {
		return system_name;
	}

	public void setSystem_name(String system_name) {
		this.system_name = system_name;
	}

	public JSONObject getSubmitted_wifi_settings() {
		return submitted_wifi_settings;
	}

	public void setSubmitted_wifi_settings(JSONObject submitted_wifi_settings) {
		this.submitted_wifi_settings = submitted_wifi_settings;
	}

    public void setpingdiag_id(String ping_id) {
		this.ping_id = ping_id;
	}
	public String getpingdiag_id() {
		return ping_id;
	}

	public void settracediag_id(String trace_id) {
		this.trace_id = trace_id;
	}
	public String gettracediag_id() {
		return trace_id;
	}
	
	public void setping_param( List<HashMap<String,String>> params){

		this.params=params;

	}

	public List<HashMap<String,String>> getping_param(){

		return params;

	}

	public void settrace_param( List<HashMap<String,String>> trace){

		this.trace=trace;

	}

	public List<HashMap<String,String>> gettrace_param(){

		return trace;

	}
	
}
