package com.selfcoder.dto;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CourseDTO {

    private Long id;
	
    private String courseName;
    
    private LocalDateTime createdAt;
	
	private LocalDateTime updatedAt;
	
}
