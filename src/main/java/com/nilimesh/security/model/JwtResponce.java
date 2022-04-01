package com.nilimesh.security.model;

import java.io.Serializable;

public class JwtResponce implements Serializable{
	
	private static final long serialVersionUID = -7523979091924046844L;
	private final String jwttoken;
	
	public JwtResponce(String jwttoken) {
		this.jwttoken = jwttoken;
	}

	public String getJwttoken() {
		return jwttoken;
	}

	
	
	

}
