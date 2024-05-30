package com.selfcoder.request;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.selfcoder.entity.Comment;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class UpdateBlogRequest {

	 @NotBlank( message = "content is required")
	 private String msg;
	 
	 @NotBlank(message = "userid is required")
	 private Long userId;
	 
	 private List<Comment> comment;
	
}
