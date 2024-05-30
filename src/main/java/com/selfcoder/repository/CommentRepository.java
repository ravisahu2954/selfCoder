package com.selfcoder.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.selfcoder.entity.Comment;

public interface CommentRepository extends JpaRepository<Comment,Long>{

	
	
}
