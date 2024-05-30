package com.selfcoder.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.selfcoder.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

	// HQL query
	// @Query(value="Select u From User u") == Query(value ="From User")
	// List<User> getAllUser();

	@Query(value = "Select * From users", nativeQuery = true)
	List<User> getAllUser();

	// HQL query
	// @Query("SELECT new
	// com.selfcoder.selfcoder.dto.UserDTO(user.id,user.email,user.userName,user.password,user.country,user.linkedin,user.createdAt,user.updatedAt,user.problems)
	// FROM User user where user.userName=?1")
	// Optional<User> findByUserName(String userName);
	// UserDTO findByUserName(String userName);

	Optional<User> findByEmail(String email);


}
