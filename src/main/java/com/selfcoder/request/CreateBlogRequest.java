package com.selfcoder.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class CreateBlogRequest {

	 @NotBlank(message = "userId is required")
	 private Long userId;
	
	 @NotBlank(message = "blog content is required")
	 private String msg;
	 
	

}
