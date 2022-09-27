package com.patikadev.account.service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.patikadev.account.service.dto.JwtAuthResponse;
import com.patikadev.account.service.dto.LoginRequest;
import com.patikadev.account.service.dto.ValidateTokenDto;
import com.patikadev.account.service.entity.User;
import com.patikadev.account.service.exception.APIException;
import com.patikadev.account.service.security.JwtTokenProvider;
import com.patikadev.account.service.service.UserService;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

@RestController
@RequestMapping("/a-service/v1/auth")
public class AuthController {

	@Autowired
	private UserService userService;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenProvider tokenProvider;

	@PostMapping("/login")
	public ResponseEntity<?> loginUser(@RequestBody LoginRequest loginRequest) {
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);

		// get token form tokenProvider
		String token = tokenProvider.generateToken(authentication);
		User user = userService.getUserByUsername(loginRequest.getUsername());
		JwtAuthResponse jwtAuthResponse = new JwtAuthResponse(token);
		jwtAuthResponse.setUserId(user.getId());
		jwtAuthResponse.setUsername(loginRequest.getUsername());
		return new ResponseEntity<>(jwtAuthResponse, HttpStatus.OK);
	}
	@PostMapping("/validate")
	public boolean validateToken(@RequestBody ValidateTokenDto validateTokenDto){
		try {
		     Jwts.parser().setSigningKey(validateTokenDto.getJwtSecret()).parseClaimsJws(validateTokenDto.getToken().substring(7));
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
