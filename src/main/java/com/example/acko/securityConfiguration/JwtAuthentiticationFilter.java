package com.example.acko.securityConfiguration;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.acko.user.UserPrincipal;

public class JwtAuthentiticationFilter extends OncePerRequestFilter {
	
	@Autowired
	JwtTokenService tokenProvider;
	
	@Autowired
	CustomUserDetailsService customUserService;
	

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		try {
			
			String jwt = getFromRequest(request);
			
			if (StringUtils.hasText(jwt) && tokenProvider.validate(jwt)) {
				UserPrincipal principal = tokenProvider.getPrincipalFromAccessToken(jwt);
				UserDetails userDetails = customUserService.loadUserByUserId(principal.getId());
				UsernamePasswordAuthenticationToken authentitication = 
						new UsernamePasswordAuthenticationToken(principal, null, userDetails.getAuthorities());
				authentitication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext();
			}
			
		} catch (Exception e) {
			
		}
		filterChain.doFilter(request, response);
		
	}


	private String getFromRequest(HttpServletRequest request) {
		
		String bearerToken = request.getHeader("Authorization");
		return bearerToken.substring(7, bearerToken.length());
	}

}
