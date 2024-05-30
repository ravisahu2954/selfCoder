package com.selfcoder.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.selfcoder.request.CreateTagRequest;
import com.selfcoder.request.UpdateTagRequest;
import com.selfcoder.service.TagService;
import com.selfcoder.util.APIResponseDTO;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("api/v1/tags")
@RestController
@CrossOrigin(origins = "*")
public class TagController {

	private final TagService tagService;

	@PostMapping
	public ResponseEntity<APIResponseDTO> addTag(@RequestBody CreateTagRequest tagRequest) {
		APIResponseDTO apiResponse = tagService.addTag(tagRequest);
		return buildResponseEntity(apiResponse);
	}

	@PutMapping
	public ResponseEntity<APIResponseDTO> updateTag(@RequestBody UpdateTagRequest updateTagRequest) {
		APIResponseDTO apiResponse = tagService.updateTag(updateTagRequest);
		return buildResponseEntity(apiResponse);
	}

	@GetMapping
	public ResponseEntity<APIResponseDTO> getTags() {
		APIResponseDTO apiResponse = tagService.getTags();
		return buildResponseEntity(apiResponse);
	}

	private ResponseEntity<APIResponseDTO> buildResponseEntity(APIResponseDTO apiResponse) {
		return new ResponseEntity<>(apiResponse, HttpStatus.valueOf(apiResponse.getStatus()));
	}

}
