package com.selfcoder.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.selfcoder.entity.Tag;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProblemDTO {

	private long id;

	private String title;

	private String link;

	private String acceptance;

	private String difficulty;

	private String frequency;

	private List<Tag> tag;

}
