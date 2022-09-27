package com.patikadev.account.service.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ValidateTokenDto {

	private String token;
	
	private String jwtSecret;
}
