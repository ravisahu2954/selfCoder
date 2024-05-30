package com.selfcoder.serviceimpl;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.selfcoder.entity.Role;
import com.selfcoder.exception.RoleAlreadyExistException;
import com.selfcoder.repository.RoleRepository;
import com.selfcoder.request.CreateRoleRequest;
import com.selfcoder.service.RoleService;
import com.selfcoder.util.APIResponseDTO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
@Service
public class RoleServiceImpl implements RoleService {
	
	private final RoleRepository roleRepository;
	@Override
	public APIResponseDTO addRole(CreateRoleRequest roleRequest) {
		String roleName = roleRequest.getRoleName();
		log.info("Adding prole: {}", roleName);

		Optional<Role> existingRole = roleRepository.findByRoleName(roleName);
		if (existingRole.isPresent()) {
			log.warn("Role already exists: {}", roleName);
			throw new RoleAlreadyExistException("Role already exists: " + roleName);
		}
		log.debug("Mapping createRoleForm to Role entity");
		Role role = new Role();
		role.setRoleName(roleName);
		roleRepository.save(role);
		log.info("Role added: {}", roleName);

		return buildSuccessResponse("Role added successfully", HttpStatus.CREATED);

	}
	
	private APIResponseDTO buildSuccessResponse(String message, HttpStatus status) {
		return APIResponseDTO.builder().timeStamp(System.currentTimeMillis()).message(message).success(true)
				.status(status.value()).build();
	}

}
