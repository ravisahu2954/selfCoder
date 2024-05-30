package com.selfcoder.service;

import java.util.List;

import com.selfcoder.request.CreateBlogRequest;
import com.selfcoder.request.UpdateBlogRequest;
import com.selfcoder.util.APIResponseDTO;

public interface BlogService {
 
	APIResponseDTO addBlog(CreateBlogRequest createBlogForm);

	APIResponseDTO getBlogs(Integer page,Integer size);
	
	APIResponseDTO deleteBlogByIds(List<Long> ids); 
	
	APIResponseDTO updateBlog(Long id,UpdateBlogRequest updateBlogForm);
	
	APIResponseDTO getBlogsById(Long id);
}
