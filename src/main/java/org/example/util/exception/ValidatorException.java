package org.example.util.exception;

import org.springframework.stereotype.Component;

@Component
public class ValidatorException extends RuntimeException {

	public ValidatorException(String message) {
		super(message);
	}

}
