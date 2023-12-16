package org.example.user;

import org.example.interfaces.BaseService;
import org.example.util.exception.UserNotFoundException;
import org.example.util.exception.ValidatorException;
import org.example.util.validation.impl.UserErrorValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

public class UserService implements BaseService<User> {

	private final UserDAO userDAO;

	private final UserErrorValidator userErrorValidator;

	@Autowired
	public UserService(UserDAO userDAO, UserErrorValidator userErrorValidator) {
		this.userDAO = userDAO;
		this.userErrorValidator = userErrorValidator;
	}

	@Override
	public List<User> readAll() {
		List<User> userList = userDAO.readAll();
		return userList;
	}

	@Override
	public User readById(Long id) {

		if (userDAO.readById(id).equals(null)) {
			throw new UserNotFoundException(id);
		}
		return userDAO.readById(id);

	}

	@Override
	public User create(User createRequest) {
		if (userErrorValidator.isValidParamsForCreate(createRequest)) {
			userDAO.createOrUpdate(createRequest);
			return createRequest;
		}
		else {
			throw new ValidatorException("Something wrong with parameters");
		}
	}

	@Override
	public User update(User updateRequest) {
		if (userErrorValidator.isValidParamsForUpdate(updateRequest)) {
			userDAO.createOrUpdate(updateRequest);
			return updateRequest;
		}
		throw new ValidatorException("Something wrong with parameters");
	}

	@Override
	public boolean deleteById(Long id) {
		return userDAO.deleteById(id);
	}

	public User readByUserName(String usernname) {
		return userDAO.readByUsername(usernname);
	}

}
