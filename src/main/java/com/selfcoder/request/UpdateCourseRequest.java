package com.selfcoder.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UpdateCourseRequest {

	 @NotBlank(message = "course name is required")
	 private String courseName;
	
}
