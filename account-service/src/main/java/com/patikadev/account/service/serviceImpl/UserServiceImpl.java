package com.patikadev.account.service.serviceImpl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.patikadev.account.service.dto.LoginRequest;
import com.patikadev.account.service.dto.UserDto;
import com.patikadev.account.service.entity.User;
import com.patikadev.account.service.exception.ResourseNotFoundException;
import com.patikadev.account.service.repository.UserRepository;
import com.patikadev.account.service.service.UserService;

@Service
public class UserServiceImpl implements UserService{

	private UserRepository userRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	public UserServiceImpl(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	
	
	@Override
	public User getUserById(Long id) {
		return userRepository.findById(id).orElseThrow(()-> new ResourseNotFoundException("User not Found with id"));
	}



	@Override
	public User createUser(User user) {
		return userRepository.save(user);
	}



	@Override
	public UserDto login(LoginRequest loginRequest) {
		User user = userRepository.findByUsername(loginRequest.getUsername());
		if(!user.getPassword().equals(loginRequest.getPassword())){
			throw new ResourseNotFoundException("Username or Password Not invalid.");
		}
		return modelMapper.map(user, UserDto.class);
	}



	@Override
	public User getUserByUsername(String username) {
		return userRepository.findByUsername(username);
	}
	


}
