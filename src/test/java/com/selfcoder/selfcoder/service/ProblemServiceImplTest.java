package com.selfcoder.selfcoder.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import com.selfcoder.dto.ProblemDTO;
import com.selfcoder.entity.Problem;
import com.selfcoder.exception.ProblemAlreadyExistException;
import com.selfcoder.exception.ProblemNotFoundException;
import com.selfcoder.repository.ProblemRepository;
import com.selfcoder.request.CreateProblemRequest;
import com.selfcoder.serviceimpl.ProblemServiceImpl;
import com.selfcoder.util.APIResponseDTO;

@SpringBootTest
public class ProblemServiceImplTest {
	   
	    @Mock
	    private ProblemRepository problemRepository;

	    @Mock
	    private ModelMapper modelMapper;

	    @InjectMocks
	    private ProblemServiceImpl problemService;

	   
	    @Test
	    void testAddProblem_WhenProblemDoesNotExist() {
	        CreateProblemRequest createProblemForm = new CreateProblemRequest();
	        Problem problem = new Problem();

	        when(problemRepository.findByTitle(createProblemForm.getTitle())).thenReturn(Optional.empty());
	        when(modelMapper.map(createProblemForm, Problem.class)).thenReturn(problem);
	        when(problemRepository.save(problem)).thenReturn(problem);

	        APIResponseDTO response = problemService.addProblem(createProblemForm);

	        assertEquals(HttpStatus.CREATED.value(), response.getStatus());
	        assertTrue(response.isSuccess());
	        assertNotNull(response.getTimeStamp());
	        assertEquals("Set Data", response.getMessage());

	        verify(problemRepository).findByTitle(createProblemForm.getTitle());
	        verify(modelMapper).map(createProblemForm, Problem.class);
	        verify(problemRepository).save(problem);
	    }

	    @Test
	    void testAddProblem_WhenProblemAlreadyExists() {
	        CreateProblemRequest createProblemForm = new CreateProblemRequest();
	        createProblemForm.setTitle("Existing Problem");

	        Problem existingProblem = new Problem();
	        when(problemRepository.findByTitle("Existing Problem")).thenReturn(Optional.of(existingProblem));

	        assertThrows(ProblemAlreadyExistException.class, () -> {
	            problemService.addProblem(createProblemForm);
	        });

	        verify(problemRepository).findByTitle("Existing Problem");
	        verifyNoMoreInteractions(modelMapper, problemRepository);
	    }

	    @Test
	    void testGetProblems_WhenProblemsExist() {
	        List<Problem> listOfProblems = new ArrayList<>();
	        listOfProblems.add(new Problem());

	        when(problemRepository.findAll()).thenReturn(listOfProblems);
	        when(modelMapper.map(any(Problem.class), eq(ProblemDTO.class))).thenReturn(new ProblemDTO());

	        APIResponseDTO response = problemService.getProblems();

	        assertEquals(HttpStatus.OK.value(), response.getStatus());
	        assertTrue(response.isSuccess());
	        assertNotNull(response.getTimeStamp());
	        assertEquals("data found", response.getMessage());
	        assertNotNull(response.getData());

	        verify(problemRepository).findAll();
	        verify(modelMapper, times(listOfProblems.size())).map(any(Problem.class), eq(ProblemDTO.class));
	    }

	    @Test
	    void testGetProblems_WhenNoProblemsExist() {
	        when(problemRepository.findAll()).thenReturn(new ArrayList<>());

	        assertThrows(ProblemNotFoundException.class, () -> {
	            problemService.getProblems();
	        });

	        verify(problemRepository).findAll();
	        verifyNoMoreInteractions(modelMapper, problemRepository);
	    }
	

}
