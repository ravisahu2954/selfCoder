package com.selfcoder.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class UpdateUserRequest {

	@NotBlank
	private String firstName;

	@NotBlank
	private String phoneNumber;

	@NotBlank
	private String email;

	@NotBlank
	private String password;

	private String country;

	private String linkedin;

	private String role;

}
