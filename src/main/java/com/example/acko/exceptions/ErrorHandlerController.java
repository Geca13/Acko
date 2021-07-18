package com.example.acko.exceptions;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.error.ErrorAttributeOptions.Include;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

@RestController
public class ErrorHandlerController implements ErrorController {

	@Autowired
	private ErrorAttributes errorAttributes;
	
	@RequestMapping("/error")
	ValidationError handleError(WebRequest request) {
		Map<String, Object> attributes = errorAttributes.getErrorAttributes(request, ErrorAttributeOptions.of(Include.MESSAGE));
		
		String message = (String) attributes.get("message");
		String url = (String) attributes.get("path");
		int status = (int) attributes.get("status");
		
		return new ValidationError(status, message, url);
	}
	
	public String getErrorPath() {
		return "/error";
	}
	
}
