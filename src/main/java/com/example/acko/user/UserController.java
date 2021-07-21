package com.example.acko.user;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;


@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/auth")
public class UserController {
	
	
	@Autowired
	UserService userService;
	
	@Autowired
	UserRepository userRepository;
	
	@PostMapping("/signUpAdmin")
	public ResponseEntity signUpAdministrator(@Valid @RequestBody SignUpRequest request) {
		
		if(userService.validate(request.getPassword())== false) {
			return ResponseEntity.badRequest().body("Your chosen password doesnt fit our creteria , it must contain at least 1 number, UpperCase and LowerCase letters and 1 special character");
		}
		
		if(userRepository.existsByEmail(request.getEmail())) {
			return ResponseEntity.badRequest().body("User with email " + request.getEmail() + " already exist in our database, please Sign In");
		}
		
		userService.signUpAdmin(request);
		
		return ResponseEntity.ok("Admin was created");
	}
	
	@PostMapping("/signUpUser")
	public ResponseEntity signUpUser(@Valid @RequestBody SignUpRequest request) {
		
		if(userService.validate(request.getPassword())== false) {
			return ResponseEntity.badRequest().body("Your chosen password doesnt fit our creteria , it must contain at least 1 number, UpperCase and LowerCase letters and 1 special character");
		}
		
		if(userRepository.existsByEmail(request.getEmail())) {
			return ResponseEntity.badRequest().body("User with email " + request.getEmail() + " already exist in our database, please Sign In");
		}
		
		userService.signUpUser(request);
		
		return ResponseEntity.ok(request.getFirstName() + " " + request.getLastName() + " thank you for signing up!");
	}
	
	@PostMapping("/signInUser")
	public ResponseEntity userSignIn(@RequestBody LoginRequest request, String token) {
		
		if(!userRepository.existsByEmail(request.getEmail())) {
			return ResponseEntity.badRequest().body("User with email " + request.getEmail() + " doesn't exist in our database, please sign Up");
		}
		
		return ResponseEntity.ok(userService.signInUser(request));
		
	}
	
	

}
