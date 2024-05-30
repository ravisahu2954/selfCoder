package com.selfcoder.serviceimpl;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.selfcoder.entity.Blog;
import com.selfcoder.entity.Comment;
import com.selfcoder.repository.BlogRepository;
import com.selfcoder.repository.CommentRepository;
import com.selfcoder.request.CreateCommentRequest;
import com.selfcoder.request.UpdateCommentRequest;
import com.selfcoder.service.CommentService;
import com.selfcoder.util.APIResponseDTO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Service
@Slf4j
public class CommentServiceImpl implements CommentService{
	
	private final CommentRepository commentRepository;
	
	private final ModelMapper modelMapper;
	
	private BlogRepository blogRepository;
	
	@Override
	public APIResponseDTO addComment(Long blogId,CreateCommentRequest createCommentForm) {
		
		log.info("addComment");

		Comment comment = modelMapper.map(createCommentForm, Comment.class);
		
		commentRepository.save(comment);
		Optional<Blog> blog = blogRepository.findById(blogId);
		//blog.get().setId(blogId);
		//blog.get().getComment().add(comment);
		blogRepository.save(blog.get());
		log.debug("Comment is added");
		return APIResponseDTO.builder().timeStamp(System.currentTimeMillis()).message("Set Data").success(true)
				.status(HttpStatus.CREATED.value()).build();
	
	}

	@Override
	public APIResponseDTO deleteCommentByIds(Long id) {
		
		log.info("## Deleting Comment ## {}", id);
		
		log.info("## Comment deleted ##");
		return APIResponseDTO.builder().message("Comment deleted").timeStamp(System.currentTimeMillis()).success(true)
				.status(HttpStatus.OK.value()).build();
		
		
	}

	@Override
	public APIResponseDTO updateComment(Long id, UpdateCommentRequest updateCommentForm) {
		
		Comment comment = Comment.convertCreateCommentFormToComment(id, updateCommentForm);
		commentRepository.save(comment);
		log.debug("Comment is added"); // log can be added in classes and enums
		return APIResponseDTO.builder().timeStamp(System.currentTimeMillis()).message("Set Data").success(true)
				.status(HttpStatus.OK.value()).build();
		
	}
   	
}
