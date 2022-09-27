package com.patikadev.account.service.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JwtAuthResponse {

	private Long userId;
	
	private String username;
	
    private String accessToken;

	public JwtAuthResponse(String accessToken) {
		super();
		this.accessToken = "Bearer "+accessToken;
	}
}
