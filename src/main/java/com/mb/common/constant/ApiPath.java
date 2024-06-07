package com.mb.common.constant;

/**
 * A constant class for storing all rest api urls, base url and it's version
 * 
 * @author Mindbowser | rohit.kavthekar@mindbowser.com
 *
 */
public class ApiPath {

	// BASE URL
	public static final String V1_BASE_URL = "api/v1/";

	// App Controller
	public static final String HEALTH = "health";

	// User Controller
	public static final String USERS_BASE_URL = V1_BASE_URL + "users";
	public static final String USER_BY_UUID = "/{userUuid}";
	
	public static final String PROJECT_BASE_URL=V1_BASE_URL+"project";

	// Auth Controller
	public static final String AUTH_BASE_URL = V1_BASE_URL + "auth";
	public static final String SIGNUP = "signup";
	public static final String LOGIN = "login";
	public static final String REFRESH_TOKEN = "refresh-token";

	private ApiPath() {
	}
}
