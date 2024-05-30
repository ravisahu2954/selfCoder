package com.selfcoder.service;

import java.util.List;

import com.selfcoder.request.CreateUserRequest;
import com.selfcoder.request.UserLoginRequest;
import com.selfcoder.util.APIResponseDTO;

public interface UserService {

	APIResponseDTO addUser(CreateUserRequest createUserForm);

	APIResponseDTO getUsers(Integer page,Integer size);

	APIResponseDTO getUserByUserName(String name);

	APIResponseDTO getUserById(Long id);

	APIResponseDTO deleteUserById(List<Long> id);

	APIResponseDTO updateUser(Long id, CreateUserRequest createUserForm);

	APIResponseDTO getUserNameAndPassowrd(String pass);

	APIResponseDTO login(UserLoginRequest userLoginRequest);
	
}
