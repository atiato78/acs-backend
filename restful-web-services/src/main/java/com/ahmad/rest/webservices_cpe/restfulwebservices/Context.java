package com.ahmad.rest.webservices_cpe.restfulwebservices;

public class Context {

	private final String acs_url = "http://35.204.108.12:8090";
	private final String acs_username = "administrator";
	private final String acs_password = "incognito2019";
	
	public Context() {
		
	}

	public String getAcs_url() {
		return acs_url;
	}

	public String getAcs_username() {
		return acs_username;
	}

	public String getAcs_password() {
		return acs_password;
	}
	
}