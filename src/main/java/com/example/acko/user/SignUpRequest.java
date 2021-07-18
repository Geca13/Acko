package com.example.acko.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SignUpRequest {
	
	private String firstName;
	
	private String lastName;
	
	private String email;
	
	private String password;

}
