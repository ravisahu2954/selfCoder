package com.selfcoder.serviceimpl;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.selfcoder.dto.UserDTO;
import com.selfcoder.entity.Role;
import com.selfcoder.entity.User;
import com.selfcoder.exception.CustomBadRequestException;
import com.selfcoder.exception.UserAlreadyExistException;
import com.selfcoder.exception.UserNotFoundException;
import com.selfcoder.repository.RoleRepository;
import com.selfcoder.repository.UserRepository;
import com.selfcoder.request.CreateUserRequest;
import com.selfcoder.request.UserLoginRequest;
import com.selfcoder.service.UserService;
import com.selfcoder.util.APIResponseDTO;
import com.selfcoder.util.JwtTokenProvider;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

	private final ModelMapper modelMapper;

	private final UserRepository userRepository;

	private final RoleRepository roleRepository;
	
	private final JwtTokenProvider jwtTokenProvider;

	private final PasswordEncoder passwordEncoder;

	@Transactional
	@Override
	public APIResponseDTO addUser(CreateUserRequest createUserForm) {
		log.info("Adding new user");

		userRepository.findByEmail(createUserForm.getEmail()).ifPresent(user -> {
			throw new UserAlreadyExistException("User with email " + createUserForm.getEmail() + " already exists.");
		});

		User user = User.fromCreateUserRequest(createUserForm);
		user.setPassword(passwordEncoder.encode(user.getPassword())); // Ensure the password is hashed before saving

		Set<Role> roles = createUserForm.getRoles().stream().map(roleName -> roleRepository.findByRoleName(roleName))
				.filter(Optional::isPresent).map(Optional::get).collect(Collectors.toSet());
		user.setRoles(roles);

		userRepository.save(user);

		log.debug("User added successfully");
		return APIResponseDTO.builder().timeStamp(System.currentTimeMillis()).message("User created successfully")
				.success(true).status(HttpStatus.CREATED.value()).build();
	}

	@Override
	public APIResponseDTO getUsers(Integer page, Integer size) {
		Sort sort = Sort.by(Sort.Direction.DESC, "updatedAt");
		Page<User> listOfUser = userRepository.findAll(PageRequest.of(page, size, sort));
		
		List<UserDTO> listDTO = null;
		if (listOfUser.isEmpty())
			throw new UserNotFoundException("Data not found");
		else {
			listDTO = listOfUser.stream().map(u -> modelMapper.map(u, UserDTO.class)).collect(Collectors.toList());
		}
		return APIResponseDTO.builder().timeStamp(System.currentTimeMillis()).data(listDTO).message("data found")
				.success(true).status(HttpStatus.OK.value()).build();
	}

	@Override
	public APIResponseDTO getUserByUserName(String name) {
		Optional<User> b = userRepository.findByEmail(name);
		if (b.isPresent()) {
			return APIResponseDTO.builder().timeStamp(System.currentTimeMillis())
					.data(modelMapper.map(b.get(), UserDTO.class)).message("data found").success(true)
					.status(HttpStatus.OK.value()).build();
		} else
			throw new UserNotFoundException("Data not found");
	}

	@Override
	public APIResponseDTO getUserById(Long id) {
		Optional<User> b = userRepository.findById(id);
		if (b.isPresent()) {
			return APIResponseDTO.builder().timeStamp(System.currentTimeMillis())
					.data(modelMapper.map(b.get(), UserDTO.class)).message("data found").success(true)
					.status(HttpStatus.OK.value()).build();
		} else
			throw new UserNotFoundException("Data not found");
	}

	@Override
	public APIResponseDTO deleteUserById(List<Long> ids) {
		log.info("## Deleting User ## {}", ids);
		if (ids == null) {
			throw new CustomBadRequestException("ids cannot be null!");
		}
		try {
			userRepository.deleteAllById(ids);
		} catch (EmptyResultDataAccessException e) {
			throw new UserNotFoundException("data not found");
		}
		log.info("## User deleted ##");
		return APIResponseDTO.builder().message("User deleted").timeStamp(System.currentTimeMillis()).success(true)
				.status(HttpStatus.OK.value()).build();
	}

	@Override
	public APIResponseDTO updateUser(Long id, CreateUserRequest createUserForm) {

		Optional<User> opt = userRepository.findById(id);
		if (!opt.isPresent())
			throw new UserNotFoundException("Data not found");
		User user = User.fromCreateUserRequest(id, createUserForm);
		userRepository.save(user);
		log.debug("User is added"); // log can be added in classes and enums
		return APIResponseDTO.builder().timeStamp(System.currentTimeMillis()).message("Set Data").success(true)
				.status(HttpStatus.OK.value()).build();
	}

	@Override
	public APIResponseDTO getUserNameAndPassowrd(String pass) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public APIResponseDTO login(UserLoginRequest userLoginRequest) {
		log.info("##Logging User ## {}", userLoginRequest);
		try {
			// Find the user by email or phone number
			Optional<User> user = userRepository.findByEmail(userLoginRequest.getUserName());

			if (!user.isPresent()) {

				throw new UserNotFoundException("User not found");
			}
			User getUser = user.get();
			// Check if the provided password matches the stored password
			log.info("##Password matched ## {}");

			if (!passwordEncoder.matches(userLoginRequest.getPassword(), getUser.getPassword())) {

				throw new BadCredentialsException("Invalid password");
			}
			log.info("##Password matched ## {}");

			// Generate a JWT token
			String token = null;
			try {
				token = jwtTokenProvider.createToken(getUser.getEmail(),null);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			// Return a success response with the token
			return APIResponseDTO.builder().status(HttpStatus.OK.value()).message("Login successful").data(token)
					.build();
		} catch (Exception ex) {
			// Return an error response for any other exception
			return APIResponseDTO.builder().status(HttpStatus.INTERNAL_SERVER_ERROR.value())
					.message("An error occurred during login").build();
		}
	}
}
