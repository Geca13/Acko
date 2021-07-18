package com.example.acko;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import com.example.acko.user.Role;
import com.example.acko.user.RoleName;
import com.example.acko.user.RoleRepository;

@SpringBootApplication
@EnableConfigurationProperties
public class AckoApplication {
	
	@Autowired
	RoleRepository roleRepository;

	public static void main(String[] args) {
		SpringApplication.run(AckoApplication.class, args);
		
	}
	@PostConstruct
	public void init() {
	try {
		
		
		roleRepository.save(new Role(1L, RoleName.ROLE_ADMIN));
		roleRepository.save(new Role(2L, RoleName.ROLE_USER));
		
        
		
	} catch (Exception e) {
		System.err.println(e);
	}

	}
}


