package com.selfcoder.service;

import java.util.List;

import com.selfcoder.request.CreateCourseRequest;
import com.selfcoder.request.UpdateCourseRequest;
import com.selfcoder.util.APIResponseDTO;

public interface CourseService {
 
	APIResponseDTO addCourse(CreateCourseRequest createCourseForm);

	APIResponseDTO getCourses(Integer page,Integer size);
	
	APIResponseDTO deleteCourseByIds(List<Long> ids); 
	
	APIResponseDTO updateCourse(Long id,UpdateCourseRequest updateCourseForm);
	
	APIResponseDTO getCoursesById(Long id);
}
