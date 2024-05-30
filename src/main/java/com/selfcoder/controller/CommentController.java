package com.selfcoder.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.selfcoder.request.CreateCommentRequest;
import com.selfcoder.request.UpdateCommentRequest;
import com.selfcoder.service.CommentService;
import com.selfcoder.util.APIResponseDTO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@RequestMapping("api/v1/comments")
@RestController
@Slf4j
public class CommentController {

    private final CommentService commentService;
	
	@PostMapping
	public ResponseEntity<APIResponseDTO> addComment(final Long blogId,@Validated @RequestBody CreateCommentRequest createCommentRequest)
	{
	    log.info("addcomment");
		APIResponseDTO response = commentService.addComment(blogId,createCommentRequest);
		return new ResponseEntity<>(response,HttpStatus.valueOf(response.getStatus()));
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<APIResponseDTO> updateComment(@PathVariable Long id,@RequestBody UpdateCommentRequest updateCommentRequest)
	{
		APIResponseDTO response = commentService.updateComment(id, updateCommentRequest);
		return new ResponseEntity<>(response,HttpStatus.valueOf(response.getStatus()));
	}
	
}
