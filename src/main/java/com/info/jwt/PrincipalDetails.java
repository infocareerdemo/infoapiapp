package com.info.jwt;

import lombok.Data;

@Data
public class PrincipalDetails {

	String email;
	int id;
	String role;
	String errorMessage;
	String name;

}
