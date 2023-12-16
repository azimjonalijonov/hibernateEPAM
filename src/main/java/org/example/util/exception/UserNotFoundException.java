package org.example.util.exception;

import org.springframework.stereotype.Component;

@Component

public class UserNotFoundException extends RuntimeException {

	public UserNotFoundException(Long userID) {
		super("User not found with ID: " + userID);
	}

}
