package com.selfcoder.selfcoder.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;

import com.selfcoder.entity.Course;
import com.selfcoder.exception.CourseNotFoundException;
import com.selfcoder.repository.CourseRepository;
import com.selfcoder.request.CreateCourseRequest;
import com.selfcoder.request.UpdateCourseRequest;
import com.selfcoder.serviceimpl.CourseServiceImpl;
import com.selfcoder.util.APIResponseDTO;
@SpringBootTest
public class CourseServiceImplTest {

	    @MockBean
	    private CourseRepository courseRepository;

	    @MockBean
	    private ModelMapper modelMapper;

	    private CourseServiceImpl courseService;

	    @BeforeEach
	    void setUp() {
	    	
	    	courseService = new CourseServiceImpl(courseRepository,modelMapper);
	    }

	    @Test
	    void testAddCourse() {
	        CreateCourseRequest createCourseForm = new CreateCourseRequest();
	        Course course = new Course();

	        when(modelMapper.map(createCourseForm, Course.class)).thenReturn(course);
	        when(courseRepository.save(course)).thenReturn(course);

	        APIResponseDTO response = courseService.addCourse(createCourseForm);

	        assertEquals(HttpStatus.CREATED.value(), response.getStatus());
	        assertTrue(response.isSuccess());
	        assertNotNull(response.getTimeStamp());
	        assertEquals("Set Data", response.getMessage());

	        verify(modelMapper).map(createCourseForm, Course.class);
	        verify(courseRepository).save(course);
	    }

	    @Test
	    void testGetCourses() {
	        Integer page = 0;
	        Integer size = 10;
	        List<Course> courses = new ArrayList<>();
	        courses.add(new Course());
	        Page<Course> pageOfCourses = new PageImpl<>(courses);

	        when(courseRepository.findAll(any(PageRequest.class))).thenReturn(pageOfCourses);

	        APIResponseDTO response = courseService.getCourses(page, size);

	        assertEquals(HttpStatus.OK.value(), response.getStatus());
	        assertTrue(response.isSuccess());
	        assertNotNull(response.getTimeStamp());
	        assertEquals("data found", response.getMessage());
	        assertNotNull(response.getData());

	        verify(courseRepository).findAll(any(PageRequest.class));
	    }

	    @Test
	    void testDeleteCourseByIds() {
	        List<Long> ids = new ArrayList<>();
	        ids.add(1L);

	        doNothing().when(courseRepository).deleteAllById(ids);

	        APIResponseDTO response = courseService.deleteCourseByIds(ids);

	        assertEquals(HttpStatus.OK.value(), response.getStatus());
	        assertTrue(response.isSuccess());
	        assertNotNull(response.getTimeStamp());
	        assertEquals("Course deleted", response.getMessage());

	        verify(courseRepository).deleteAllById(ids);
	    }

	    @Test
	    void testUpdateCourse_WhenCourseExists() {
	        UpdateCourseRequest updateCourseForm = new UpdateCourseRequest();
	        Course course = new Course();
            course.setId(1L);
	        when(courseRepository.findById(course.getId())).thenReturn(Optional.of(course));
	        when(courseRepository.save(course)).thenReturn(course);

	        APIResponseDTO response = courseService.updateCourse(course.getId(), updateCourseForm);

	        assertEquals(HttpStatus.OK.value(), response.getStatus());
	        assertTrue(response.isSuccess());
	        assertNotNull(response.getTimeStamp());
	        assertEquals("Set Data", response.getMessage());

	        verify(courseRepository).findById(course.getId());
	        //verify(courseRepository).save(course);
	    }

	    @Test
	    void testUpdateCourse_WhenCourseDoesNotExist() {
	        Long id = 1L;
	        UpdateCourseRequest updateCourseForm = new UpdateCourseRequest();

	        when(courseRepository.findById(id)).thenReturn(Optional.empty());

	        assertThrows(CourseNotFoundException.class, () -> {
	            courseService.updateCourse(id, updateCourseForm);
	        });

	    }
	
}
