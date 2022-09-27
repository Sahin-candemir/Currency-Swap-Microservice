package com.patikadev.exchange.service.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TokenValidateDto {

	private String token;
	
	private String jwtSecret;
}
