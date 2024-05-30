package com.selfcoder.service;

import com.selfcoder.request.CreateCommentRequest;
import com.selfcoder.request.UpdateCommentRequest;
import com.selfcoder.util.APIResponseDTO;

public interface CommentService {

	APIResponseDTO addComment(Long blogId,CreateCommentRequest createCommentForm);

	APIResponseDTO deleteCommentByIds(Long id); 
	
	APIResponseDTO updateComment(Long id,UpdateCommentRequest updateCommentForm);
		
}
