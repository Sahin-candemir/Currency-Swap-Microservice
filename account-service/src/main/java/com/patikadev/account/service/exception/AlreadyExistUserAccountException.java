package com.patikadev.account.service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class AlreadyExistUserAccountException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final String MESSAGE = "User account allready exists.";

    public AlreadyExistUserAccountException() {
        super(MESSAGE);
    }
}
