package com.selfcoder.service;

import com.selfcoder.request.CreateRoleRequest;
import com.selfcoder.util.APIResponseDTO;

public interface RoleService {
	
	APIResponseDTO addRole(CreateRoleRequest roleRequest);

}
