package com.selfcoder.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.selfcoder.request.CreateUserRequest;
import com.selfcoder.request.UserLoginRequest;
import com.selfcoder.service.UserService;
import com.selfcoder.util.APIResponseDTO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("api/v1/users")
@CrossOrigin(origins = "*")
public class UserController {

	private final UserService userService;

	@PostMapping
	public ResponseEntity<APIResponseDTO> addUser(@Validated @RequestBody CreateUserRequest createUserRequest) {

		var apiResponse = userService.addUser(createUserRequest);
		return new ResponseEntity<>(apiResponse, HttpStatus.valueOf(apiResponse.getStatus()));
	}
	
	@PostMapping("/login")
	public ResponseEntity<APIResponseDTO> login(@Validated @RequestBody UserLoginRequest userLoginRequest) {

		var apiResponse = userService.login(userLoginRequest);
		return new ResponseEntity<>(apiResponse, HttpStatus.valueOf(apiResponse.getStatus()));
	}

	@GetMapping("/fetchUserByName")
	public ResponseEntity<APIResponseDTO> getUserByUserName(@PathVariable String name) {

		var apiResponse = userService.getUserByUserName(name);
		return new ResponseEntity<>(apiResponse, HttpStatus.valueOf(apiResponse.getStatus()));
	}

	@GetMapping
	public ResponseEntity<APIResponseDTO> getUsers(@RequestParam(name = "page", defaultValue = "0") final Integer page,
			@RequestParam(name = "size", defaultValue = "10") final Integer size) {
		log.info("getuser");
		var apiResponse = userService.getUsers(page, size);
		return new ResponseEntity<>(apiResponse, HttpStatus.valueOf(apiResponse.getStatus()));
	}

	@GetMapping("/{id}")
	public ResponseEntity<APIResponseDTO> getUserById(@PathVariable("id") final Long id) {

		var apiResponse = userService.getUserById(id);
		return new ResponseEntity<>(apiResponse, HttpStatus.valueOf(apiResponse.getStatus()));

	}

	@DeleteMapping
	public ResponseEntity<APIResponseDTO> deleteUserById(@RequestBody final List<Long> ids) {

		var apiResponse = userService.deleteUserById(ids);
		return new ResponseEntity<>(apiResponse, HttpStatus.valueOf(apiResponse.getStatus()));
	}

	@PutMapping("/{id}")
	public ResponseEntity<APIResponseDTO> updateUser(@PathVariable("id") final Long id,
			@Validated @RequestBody CreateUserRequest createUserRequest) {

		var apiResponse = userService.updateUser(id, createUserRequest);
		return new ResponseEntity<>(apiResponse, HttpStatus.valueOf(apiResponse.getStatus()));
	}

}
