package com.example.acko.user;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserPrincipal implements UserDetails {
	
	private Long id;
	
	private String firstName;
	
	private String lastName;
	
	@JsonIgnore
	private String email;
	
	@JsonIgnore
	private String password;
	
	private String role;
	
	private Collection<? extends GrantedAuthority> authorities;
	
	public UserPrincipal(Long id, String firstName, String lastName, String email, String password,
			Collection<? extends GrantedAuthority> authorities) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
		this.authorities = authorities;
	}
	
	public Collection<? extends GrantedAuthority> getAuthorities() {

		return authorities;
	}
	
	
	
	public static UserPrincipal createUser(User user) {
		List<GrantedAuthority> authorities = user.getRoles().stream().map(role -> new SimpleGrantedAuthority(role.getRole().name()))
				.collect(Collectors.toList());
		
		return new UserPrincipal(user.getId(), user.getFirstName(), user.getLastName(), user.getEmail(), user.getPassword(), authorities);
	}
	
	

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return email;
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}


}
