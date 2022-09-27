package com.patikadev.account.service.service;

import com.patikadev.account.service.dto.LoginRequest;
import com.patikadev.account.service.dto.UserDto;
import com.patikadev.account.service.entity.User;

public interface UserService {

	User getUserById(Long id);

	User createUser(User user);

	UserDto login(LoginRequest loginRequest);
	
	User getUserByUsername(String username);
}
