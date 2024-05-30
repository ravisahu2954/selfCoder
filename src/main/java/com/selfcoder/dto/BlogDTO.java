package com.selfcoder.dto;

import java.time.LocalDate;
import java.util.List;

import com.selfcoder.entity.Comment;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class BlogDTO {

	private Long id;
	
	private String msg;
	
	private Long userId;
	
	private LocalDate createAt;
		
	private LocalDate updateAt;
		
    private List<Comment> comment;
	
}
