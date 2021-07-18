package com.example.acko.exceptions;

import java.util.Date;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonInclude(value = Include.NON_NULL)
public class ValidationError {
	
	private Long timestamp = new Date().getTime();
	
	private int status;
	
	private String message;
	
	private String url;
	
	private Map<String, String> validationError;

	public ValidationError(int status, String message, String url) {
		super();
		this.status = status;
		this.message = message;
		this.url = url;
	}
	
	

}
