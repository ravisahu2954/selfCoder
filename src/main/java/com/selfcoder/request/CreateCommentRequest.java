package com.selfcoder.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CreateCommentRequest {

	@NotBlank
    private Long userId;
	
	@NotBlank
	private String msg;
	
}
