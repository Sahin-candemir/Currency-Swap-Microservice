package com.patikadev.account.service.security;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.patikadev.account.service.exception.APIException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

@Component
public class JwtTokenProvider {

	@Value("${app.jwt-secret}")
	private String jwtSecret;
	
	@Value("${app.jwt-expiration-milliseconds}")
	private int jwtExpirationInMs;
	
	public String generateToken(Authentication authentication) {
		String username = authentication.getName();
		Date currentDate =new Date();
		Date expireDate = new Date(currentDate.getTime() + jwtExpirationInMs);
		
		String token = Jwts.builder()
				.setSubject(username)
				.setIssuedAt(new Date())  
				.setExpiration(expireDate)          
				.signWith(SignatureAlgorithm.HS512,jwtSecret)
				.compact();
		return token;
	}

	public String getUsernameFromJwt(String token) {
		Claims claims = Jwts.parser()
				.setSigningKey(jwtSecret)
				.parseClaimsJws(token)
				.getBody();
		return claims.getSubject();
	}

	public boolean validateToken(String token){
		try {
		     Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
		     return true;
		}catch (SignatureException ex) {
			throw new APIException(HttpStatus.BAD_REQUEST, "invalid JWT signature");
		}catch (MalformedJwtException ex) {
			throw new APIException(HttpStatus.BAD_REQUEST, "invalid JWT token");
		}catch (ExpiredJwtException ex) {
			throw new APIException(HttpStatus.BAD_REQUEST, "Expired JWT token");
		}catch (UnsupportedJwtException ex) {
			throw new APIException(HttpStatus.BAD_REQUEST, "Unsupported JWT token");
		}catch (IllegalArgumentException ex) {
			throw new APIException(HttpStatus.BAD_REQUEST, "JWT claims string is empty");
		}
		
		
		
		
		
	}
}
