package com.selfcoder.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.selfcoder.entity.Blog;
import com.selfcoder.entity.Course;
import com.selfcoder.entity.Problem;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true) 

public class UserDTO {

	private long id;
	
	private String email;
	
	private String userName;
	
	private String password;

    private String country;

	private String linkedin;

	private LocalDateTime createdAt;

	private LocalDateTime updatedAt;

	private Set<Problem> problems;
	
    private Set<Blog> blogs;

    private Set<Course> courses;

    private List<String> roles;
}
