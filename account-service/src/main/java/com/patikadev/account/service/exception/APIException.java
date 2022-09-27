package com.patikadev.account.service.exception;
import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public class APIException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String message;
	private HttpStatus status;

	public APIException(HttpStatus status, String message) {
		this.status = status;
		this.message = message;
	}
}
