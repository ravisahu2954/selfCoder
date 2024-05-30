package com.selfcoder.serviceimpl;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.selfcoder.dto.ProblemDTO;
import com.selfcoder.entity.Problem;
import com.selfcoder.entity.Tag;
import com.selfcoder.exception.ProblemAlreadyExistException;
import com.selfcoder.exception.ProblemNotFoundException;
import com.selfcoder.repository.ProblemRepository;
import com.selfcoder.repository.TagRepository;
import com.selfcoder.request.CreateProblemRequest;
import com.selfcoder.request.UpdateProblemRequest;
import com.selfcoder.service.ProblemService;
import com.selfcoder.util.APIResponseDTO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
@Service
public class ProblemServiceImpl implements ProblemService {

	private final ProblemRepository problemRepository;

	private final TagRepository tagRepository;

	private final ModelMapper modelMapper;

	@Override
	public APIResponseDTO addProblem(CreateProblemRequest createProblemForm) {
		String title = createProblemForm.getTitle();
		log.info("Adding problem: {}", title);

		Optional<Problem> existingProblem = problemRepository.findByTitle(title);
		if (existingProblem.isPresent()) {
			log.warn("Problem already exists: {}", title);
			throw new ProblemAlreadyExistException("Problem already exists: " + title);
		}
		log.debug("Mapping createProblemForm to Problem entity");
		Problem problem = mapProblem(createProblemForm);
		problemRepository.save(problem);
		log.info("Problem added: {}", title);

		return buildSuccessResponse("Problem added successfully", HttpStatus.CREATED);
	}

	private Problem mapProblem(CreateProblemRequest createProblemForm) {

		Problem problem = new Problem();
		problem.setTitle(createProblemForm.getTitle());
		problem.setLink(createProblemForm.getLink());
		problem.setAcceptance(createProblemForm.getAcceptance());
		problem.setDifficulty(createProblemForm.getDifficulty());
		problem.setSolution(createProblemForm.getSolution());

		List<Tag> tags = createProblemForm.getTagIds().stream().map(tagId -> tagRepository.findById(tagId))
				.filter(Optional::isPresent).map(Optional::get).toList();

		problem.setTag(tags);
		return problem;
	}

	@Override
	public APIResponseDTO getProblems() {
		log.info("Fetching all problems");

		List<Problem> listOfProblem = problemRepository.findAll();
		if (listOfProblem.isEmpty()) {
			log.warn("No problems found");
			throw new ProblemNotFoundException("Data not found");
		}

		List<ProblemDTO> listDTO = listOfProblem.stream().map(problem -> modelMapper.map(problem, ProblemDTO.class))
				.toList();

		log.info("Returning {} problems", listDTO.size());
		return buildSuccessResponse("Data found", HttpStatus.OK, listDTO);
	}

	@Override
	public APIResponseDTO updateProblem(UpdateProblemRequest updateProblemRequest) {
		Long problemId = updateProblemRequest.getId();
		log.info("Updating problem with ID: {}", problemId);

		Problem existingProblem = problemRepository.findById(problemId)
				.orElseThrow(() -> new ProblemNotFoundException("Problem not found with ID: " + problemId));

		modelMapper.map(updateProblemRequest, existingProblem);
		problemRepository.save(existingProblem);

		log.info("Problem updated successfully: {}", updateProblemRequest.getTitle());
		return buildSuccessResponse("Problem updated successfully", HttpStatus.OK);
	}

	private APIResponseDTO buildSuccessResponse(String message, HttpStatus status) {
		return APIResponseDTO.builder().timeStamp(System.currentTimeMillis()).message(message).success(true)
				.status(status.value()).build();
	}

	private APIResponseDTO buildSuccessResponse(String message, HttpStatus status, Object data) {
		return APIResponseDTO.builder().timeStamp(System.currentTimeMillis()).data(data).message(message).success(true)
				.status(status.value()).build();
	}
}
