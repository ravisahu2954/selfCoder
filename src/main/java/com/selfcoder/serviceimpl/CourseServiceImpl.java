package com.selfcoder.serviceimpl;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.selfcoder.dto.CourseDTO;
import com.selfcoder.entity.Course;
import com.selfcoder.exception.CourseNotFoundException;
import com.selfcoder.exception.CustomBadRequestException;
import com.selfcoder.repository.CourseRepository;
import com.selfcoder.request.CreateCourseRequest;
import com.selfcoder.request.UpdateCourseRequest;
import com.selfcoder.service.CourseService;
import com.selfcoder.util.APIResponseDTO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;
    private final ModelMapper modelMapper;

    @Override
    public APIResponseDTO addCourse(CreateCourseRequest createCourseForm) {
        log.info("Adding a new course: {}", createCourseForm.getCourseName());
        Course course = modelMapper.map(createCourseForm, Course.class);
        courseRepository.save(course);
        log.debug("Course added: {}", course.getId());
        return buildSuccessResponse("Course added successfully", HttpStatus.CREATED);
    }

    @Override
    public APIResponseDTO getCourses(Integer page, Integer size) {
        log.info("Fetching courses, page: {}, size: {}", page, size);
        Sort sort = Sort.by(Sort.Direction.DESC, "updatedAt");
        Page<Course> coursePage = courseRepository.findAll(PageRequest.of(page, size, sort));
        
        if (coursePage.isEmpty()) {
            log.warn("No courses found");
            throw new CourseNotFoundException("Data not found");
        }

        List<CourseDTO> courseDTOList = coursePage.stream()
                .map(course -> modelMapper.map(course, CourseDTO.class))
                .toList();

        log.info("Returning {} courses", courseDTOList.size());
        return buildSuccessResponse("Data found", HttpStatus.OK, courseDTOList);
    }

    @Override
    public APIResponseDTO deleteCourseByIds(List<Long> ids) {
        log.info("Deleting courses with IDs: {}", ids);
        if (ids == null || ids.isEmpty()) {
            throw new CustomBadRequestException("IDs cannot be null or empty");
        }

        try {
            courseRepository.deleteAllById(ids);
            log.info("Courses deleted successfully");
            return buildSuccessResponse("Courses deleted successfully", HttpStatus.OK);
        } catch (EmptyResultDataAccessException e) {
            log.error("Error deleting courses: {}", e.getMessage());
            throw new CourseNotFoundException("Data not found");
        }
    }

    @Override
    public APIResponseDTO updateCourse(Long id, UpdateCourseRequest updateCourseForm) {
        log.info("Updating course with ID: {}", id);
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new CourseNotFoundException("Data not found"));

        modelMapper.map(updateCourseForm, course);
        courseRepository.save(course);
        log.debug("Course updated: {}", id);
        return buildSuccessResponse("Course updated successfully", HttpStatus.OK);
    }

    @Override
    public APIResponseDTO getCoursesById(Long id) {
        log.info("Fetching course with ID: {}", id);
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new CourseNotFoundException("Data not found"));

        CourseDTO courseDTO = modelMapper.map(course, CourseDTO.class);
        log.info("Course found: {}", id);
        return buildSuccessResponse("Data found", HttpStatus.OK, courseDTO);
    }

    private APIResponseDTO buildSuccessResponse(String message, HttpStatus status) {
        return buildSuccessResponse(message, status, null);
    }

    private APIResponseDTO buildSuccessResponse(String message, HttpStatus status, Object data) {
        return APIResponseDTO.builder()
                .timeStamp(System.currentTimeMillis())
                .message(message)
                .success(true)
                .status(status.value())
                .data(data)
                .build();
    }
}
