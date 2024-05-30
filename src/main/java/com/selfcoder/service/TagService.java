package com.selfcoder.service;

import com.selfcoder.request.CreateTagRequest;
import com.selfcoder.request.UpdateTagRequest;
import com.selfcoder.util.APIResponseDTO;

public interface TagService {
	
	APIResponseDTO addTag(CreateTagRequest createTagForm);

	APIResponseDTO getTags();

	APIResponseDTO updateTag(UpdateTagRequest updateTagRequest);
	

}
