package com.selfcoder.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.selfcoder.entity.Course;

public interface CourseRepository extends JpaRepository<Course, Long>{

}
