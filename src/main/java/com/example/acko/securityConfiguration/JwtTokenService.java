package com.example.acko.securityConfiguration;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwsHeader;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import com.example.acko.exceptions.InvalidTokenException;
import com.example.acko.user.UserPrincipal;
import com.example.acko.user.UserRepository;

@Component
public class JwtTokenService {
	
	@Autowired
	AuthenticationManager manager;
	
	@Autowired
	UserRepository userRepository;
	
	@Value("${tokenExpirationTime}")
	Long expirationTime;
	
	@Value("${tokenSecret}")
	String secret;
	
	String CLAIM_USER_ID = "uid";
	String CLAIM_USER_ROLE = "role";
	
	public String generate(Long userId, String username, String role) {
		Claims customClaims = Jwts.claims();
		customClaims.put(CLAIM_USER_ID, userId);
		customClaims.put(CLAIM_USER_ROLE, role);
		
		String accessToken = generateToken(customClaims, username, new Date(), getExpirationDate(expirationTime), secret);
		
		return accessToken;
	}

	private String generateToken(Claims customClaims, String username, Date date, Date expirationDate,String secret) {
		
		return Jwts.builder().setHeaderParam(JwsHeader.TYPE, JwsHeader.JWT_TYPE).setClaims(customClaims)
				.setSubject(username)
				.setIssuedAt(date)
				.setExpiration(expirationDate)
				.signWith(Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret)))
				.compact();
	}

	private Date getExpirationDate(Long validityMiliseconds) {
		if(validityMiliseconds == -1) {
			return null;
		}
		return new Date(System.currentTimeMillis() + validityMiliseconds);
	}
	
	public boolean validate(String jwt) {
		try {
			Jwts.parser()
			.setSigningKey(secret)
			.parse(jwt);
			return true;
		} catch (Exception e) {
			throw new InvalidTokenException("Invalid token");
		}
	}
	
	public UserPrincipal getPrincipalFromAccessToken(String accessToken) {
		
		try {
			
			Claims claims = getTokenBody(accessToken);
			UserPrincipal principal = new UserPrincipal();
			principal.setId(claims.get(CLAIM_USER_ID, Long.class));
			principal.setEmail(claims.getSubject());
			principal.setRole(claims.get(CLAIM_USER_ROLE, String.class));
			return principal;
			
		} catch (Exception e) {
			return new UserPrincipal();
		}
		
	}

	private Claims getTokenBody(String accessToken) {
		
		return Jwts.parser()
				.setSigningKey(secret)
				.parseClaimsJws(accessToken)
				.getBody();
		
	}
	
	
	
	

}
