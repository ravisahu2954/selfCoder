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

import com.selfcoder.request.CreateProblemRequest;
import com.selfcoder.request.UpdateProblemRequest;
import com.selfcoder.service.ProblemService;
import com.selfcoder.util.APIResponseDTO;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("api/v1/problems")
@RestController
@CrossOrigin(origins = "*")
public class ProblemController {

    private final ProblemService problemService;

    @PostMapping
    public ResponseEntity<APIResponseDTO> addProblem(@RequestBody CreateProblemRequest problemRequest) {
        APIResponseDTO apiResponse = problemService.addProblem(problemRequest);
        return buildResponseEntity(apiResponse);
    }

    @PutMapping
    public ResponseEntity<APIResponseDTO> updateProblem(@RequestBody UpdateProblemRequest updateProblemRequest) {
        APIResponseDTO apiResponse = problemService.updateProblem(updateProblemRequest);
        return buildResponseEntity(apiResponse);
    }

    @GetMapping
    public ResponseEntity<APIResponseDTO> getProblems() {
        APIResponseDTO apiResponse = problemService.getProblems();
        return buildResponseEntity(apiResponse);
    }

    private ResponseEntity<APIResponseDTO> buildResponseEntity(APIResponseDTO apiResponse) {
        return new ResponseEntity<>(apiResponse, HttpStatus.valueOf(apiResponse.getStatus()));
    }
}
