package com.secondminiproject.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.secondminiproject.entities.Course;

public interface CourseRepository extends JpaRepository<Course, Integer> {

	//public List<String> findCourseName();

}
