package com.example.acko.user;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;

import lombok.Data;

@Data
public class JwtResponse {
	
	private String token;
	
	private String type = "Bearer";
	
	private String email;
	
	private Collection<? extends GrantedAuthority> authorities;

	public JwtResponse(String token, String email, Collection<? extends GrantedAuthority> authorities) {
		super();
		this.token = token;
		this.email = email;
		this.authorities = authorities;
	}
	
	

}
