package com.example.acko.securityConfiguration;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.acko.user.User;
import com.example.acko.user.UserPrincipal;
import com.example.acko.user.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {
	
	@Autowired
	UserRepository userRepository;

	@Transactional
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		User user = userRepository.findByEmail(email);
		return UserPrincipal.createUser(user);
	}
	
	public UserDetails loadUserByUserId(Long id) {
		User user = userRepository.findById(id).get();
		return UserPrincipal.createUser(user);
	}
	
	@Transactional
	public UserDetails loadUserById(Long id) {
		User user = userRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException("User with id " + id + " is not found"));
		return UserPrincipal.createUser(user);
	}
	

}
