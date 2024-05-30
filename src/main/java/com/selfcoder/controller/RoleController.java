package com.selfcoder.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.selfcoder.request.CreateRoleRequest;
import com.selfcoder.service.RoleService;
import com.selfcoder.util.APIResponseDTO;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("api/v1/roles")
@RestController
@CrossOrigin(origins = "*")
public class RoleController {

	private final RoleService roleService;

	@PostMapping
	public ResponseEntity<APIResponseDTO> addRole(@RequestBody CreateRoleRequest roleRequest) {
		APIResponseDTO apiResponse = roleService.addRole(roleRequest);
		return buildResponseEntity(apiResponse);
	}
	 private ResponseEntity<APIResponseDTO> buildResponseEntity(APIResponseDTO apiResponse) {
	        return new ResponseEntity<>(apiResponse, HttpStatus.valueOf(apiResponse.getStatus()));
	    }

}
