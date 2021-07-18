package com.example.acko.user;

import java.util.Collections;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.acko.exceptions.UserAlreadyExistException;
import com.example.acko.securityConfiguration.JwtTokenService;

@Service
public class UserService {
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	PasswordEncoder encoder;
	
	@Autowired
	RoleRepository roleRepository;
	
	@Autowired
	AuthenticationManager manager;
	
	@Autowired
	JwtTokenService tokenProvider;
	
	
	private Pattern pattern;
	private Matcher matcher;
	
	private static  final String PASSWORD_REGEX = 
	        ("((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{6,15})");
	
	

	public UserService() {
		
		pattern = Pattern.compile(PASSWORD_REGEX);
	}

	public boolean validate(final String password) {
		matcher =pattern.matcher(password);
		       return matcher.matches();
	}
	
	
	public String signUpAdmin(SignUpRequest request) {
		
		User user = newUserSignUp(request);
		
		Role role = roleRepository.findByRole(RoleName.ROLE_ADMIN);
		user.setRoles(Collections.singleton(role));
		
		userRepository.save(user);
		
		return user.getFirstName() + " " + user.getLastName() + " thank you for signing up!";
		
	}

    public String signUpUser(SignUpRequest request) {
		
		User user = newUserSignUp(request);
		
		Role role = roleRepository.findByRole(RoleName.ROLE_USER);
		user.setRoles(Collections.singleton(role));
		
		userRepository.save(user);
		
		return user.getFirstName() + " " + user.getLastName() + " thank you for signing up!";
		
	}
    
    private User newUserSignUp(SignUpRequest request) {
		User user = new User();
		user.setFirstName(request.getFirstName());
		user.setLastName(request.getLastName());
		user.setEmail(request.getEmail());
		user.setPassword(encoder.encode(request.getPassword()));
		return user;
	}

	public String signInUser(LoginRequest request) {
		
		if(!userRepository.existsByEmail(request.getEmail())) {
			return "User with email " + request.getEmail() + " doesn't exist in our database, please sign Up";
		}
		
		Authentication authentication = manager.authenticate(
				new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		UserPrincipal principal = (UserPrincipal) authentication.getPrincipal();
		String accessJwt = tokenProvider.generate(principal.getId(), principal.getEmail(), principal.getRole());
		return accessJwt;
		
	}
	
	

}
