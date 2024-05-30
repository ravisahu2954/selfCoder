package com.selfcoder.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.selfcoder.entity.Role;

public interface RoleRepository extends JpaRepository<Role,Long> {

	Optional<Role> findByRoleName(String roleName);

}
