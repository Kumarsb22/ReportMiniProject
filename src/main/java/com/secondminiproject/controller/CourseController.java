package com.secondminiproject.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.secondminiproject.entities.Course;
import com.secondminiproject.service.ICourseService;

@Controller
public class CourseController {

	@Autowired
	private ICourseService courseService;

	@GetMapping("/courses")
	public List<String> getAllCourses() {

		List<String> allCourse = this.courseService.getAllCourse();
		return allCourse;

	}
}
