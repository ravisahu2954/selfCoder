package com.selfcoder.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@JsonInclude(value = Include.NON_NULL)
public class APIResponseDTO {

	private long timeStamp;
	
	private String message;
	
	private boolean success;
	
	private int status;
	
	private Object data;

}
