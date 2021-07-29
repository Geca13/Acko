package com.example.acko.user;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.acko.securityConfiguration.JwtTokenService;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/auth")
public class UserController {
	
	@Autowired
	AuthenticationManager manager;
	
	@Autowired
	JwtTokenService tokenProvider;
	
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
		
		Authentication authentication = manager.authenticate(
				new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		UserPrincipal principal = (UserPrincipal) authentication.getPrincipal();
		String accessJwt = tokenProvider.generate(principal.getId(), principal.getEmail(), principal.getRole());
		
		
		return ResponseEntity.ok(new JwtResponse(true ,accessJwt));
		
	}
	
	@GetMapping("/my-profile/email")
	public ResponseEntity loggedInProfile(@PathVariable(value = "email") String email) {
		
		User user = userRepository.findByEmail(email);
		
		return ResponseEntity.ok(user);
	}
	
	

}
