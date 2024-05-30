package com.selfcoder.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class UpdateProblemRequest {
	
	private Long id;

	private String title;

	private String link;

	private String tag;

	private String acceptance;

	private String difficulty;

	private String solution;

}
