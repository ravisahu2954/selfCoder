package com.selfcoder.service;

import com.selfcoder.request.CreateProblemRequest;
import com.selfcoder.request.UpdateProblemRequest;
import com.selfcoder.util.APIResponseDTO;

public interface ProblemService {

	APIResponseDTO addProblem(CreateProblemRequest createProblemForm);

	APIResponseDTO getProblems();

	APIResponseDTO updateProblem(UpdateProblemRequest updateProblemRequest);
	
}
