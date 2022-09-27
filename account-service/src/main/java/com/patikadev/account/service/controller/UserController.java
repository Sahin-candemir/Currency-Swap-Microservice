package com.patikadev.account.service.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.patikadev.account.service.dto.LoginRequest;
import com.patikadev.account.service.dto.UserDto;
import com.patikadev.account.service.entity.User;
import com.patikadev.account.service.service.UserService;

@RestController
@RequestMapping("/a-service/v1/users")
public class UserController {

	private UserService userService;

	public UserController(UserService userService) {
		this.userService = userService;
	}

	@PostMapping
	public User createUser(@RequestBody User user) {
		return userService.createUser(user);
	}

	@GetMapping("{userId}")
	public ResponseEntity<User> getUserById(@PathVariable Long userId) {
		return new ResponseEntity<>(userService.getUserById(userId), HttpStatus.OK);
	}

	@CrossOrigin
	@PostMapping("/login")
	public ResponseEntity<UserDto> login(@RequestBody LoginRequest loginRequest) {
		return new ResponseEntity<>(userService.login(loginRequest), HttpStatus.OK);
	}
}
