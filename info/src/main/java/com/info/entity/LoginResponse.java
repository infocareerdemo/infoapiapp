package com.info.entity;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class LoginResponse {

	String responseText;
	String userToken;
	
	public String getResponseText() {
		return responseText;
	}
	public void setResponseText(String responseText) {
		this.responseText = responseText;
	}
	public String getUserToken() {
		return userToken;
	}
	public void setUserToken(String userToken) {
		this.userToken = userToken;
	}
}
