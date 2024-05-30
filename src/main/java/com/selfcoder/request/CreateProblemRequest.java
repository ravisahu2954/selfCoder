package com.selfcoder.request;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class CreateProblemRequest {
		
	    @NotBlank(message = "title is required")
		private String title;
		
	    @NotBlank(message = "link is required")
		private String link;
		
	    @NotBlank(message = "tag is required")
        private List<String> tagList;
		
		@NotBlank(message = "acceptance is required")
	    private String acceptance;
		
		@NotBlank(message = "difficulty is required")
		private String difficulty;
		
		@NotBlank(message = "frquency is required")
		private String solution;
	
		List<Long> tagIds;
}
